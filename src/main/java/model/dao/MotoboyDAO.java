package model.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.entities.Motoboy;

/**
 * The Class MotoboyDAO.
 *
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */

public class MotoboyDAO {

	/** The connection. */
	private Connection connection;

	/**
	 * Instantiates a new motoboy DAO.
	 */
	public MotoboyDAO() {
		connection = DatabaseConnection.getPostgresSQLConnection();
	}

	/**
	 * Busca todos registros de motoboy no banco de dados.
	 *
	 * @return lista com todos motoboys
	 */
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
				mb.setMotoboyId(rs.getLong("motoboy_id"));
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

	/**
	 * Deleta um registro de motoboy pelo nome.
	 *
	 * @param motoboyName nome do motoboy
	 * @throws SQLException the SQL exception
	 */
	public void deleteByName(String motoboyName) throws SQLException {
		
		String sql           = "DELETE FROM motoboy WHERE motoboy_name = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1,motoboyName);
		ps.executeUpdate();
		
		connection.commit();
	}

	/**
	 * Atualiza os dados do motoboy
	 *
	 * @param type qual dado vai ser alterado
	 * @param value valor a ser alterado
	 * @param name the name
	 * @throws SQLException the SQL exception
	 */
	public void motoboyUpdate(String type, String value, String id) throws SQLException 
	{
		String sql;
		PreparedStatement ps;
		if(type.equals("adress")) {
			sql = "UPDATE motoboy SET motoboy_adress=? WHERE motoboy_id =?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, value);
			ps.setLong(2, Long.parseLong(id));
			ps.executeUpdate();
			connection.commit();
		}
		else if(type.equals("phone")) {
			sql = "UPDATE motoboy SET motoboy_phone=? WHERE motoboy_id =?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, value);
			ps.setLong(2, Long.parseLong(id));
			ps.executeUpdate();
			connection.commit();
		}
	}

	/**
	 * Motoboy insert.
	 *
	 * @param name the name
	 * @param phone the phone
	 * @param adress the adress
	 * @throws SQLException the SQL exception
	 */
	public void motoboyInsert(String name, String phone, String adress) throws SQLException 
	{
		String sql = "INSERT INTO motoboy(motoboy_name, motoboy_phone, motoboy_adress) VALUES (?, ?, ?)";
		
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, name);
		ps.setString(2, phone);
		ps.setString(3, adress);
		ps.execute();
		
		connection.commit();
	}
}
