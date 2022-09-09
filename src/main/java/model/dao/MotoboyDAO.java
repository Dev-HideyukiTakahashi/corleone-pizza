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

	public void deleteByName(String motoboyName) throws SQLException {
		
		String sql           = "DELETE FROM motoboy WHERE motoboy_name = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1,motoboyName);
		ps.executeUpdate();
	}

	public void motoboyUpdate(String type, String value, String name) throws SQLException {
		
		String sql;
		PreparedStatement ps;
		if(type.equals("adress")) {
			sql = "UPDATE motoboy SET motoboy_adress=? WHERE motoboy_name =?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, value);
			ps.setString(2, name);
			ps.executeUpdate();
			connection.commit();
		}
		else if(type.equals("phone")) {
			sql = "UPDATE motoboy SET motoboy_phone=? WHERE motoboy_name =?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, value);
			ps.setString(2, name);
			ps.executeUpdate();
			connection.commit();
		}
	}
}
