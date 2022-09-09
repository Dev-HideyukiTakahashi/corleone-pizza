package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.entities.Motoboy;

public class MotoboyDAO {

	private Connection connection;

	public MotoboyDAO() {
		connection = DatabaseConnection.getPostgresSQLConnection();
	}

	public List<Motoboy> motoboySearchAll() 
	{
		List<Motoboy> motoboy = new ArrayList<>();
		try 
		{
			String sql = "SELECT * FROM motoboy";

			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Motoboy mb = new Motoboy();
				mb.setMotoboyName(rs.getString("motoboy_name"));
				mb.setMotoboyPhone(rs.getString("motoboy_phone"));
				mb.setMotoboyAdress(rs.getString("motoboy_adress"));
				motoboy.add(mb);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return motoboy;
	}

}
