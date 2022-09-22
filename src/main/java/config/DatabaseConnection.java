package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import config.exception.DataBaseSourceException;

/**
 * The Class DatabaseConnection.
 * 
 * @author Hideyuki Takahashi
 * @github https://github.com/Dev-HideyukiTakahashi
 * @email  dev.hideyukitakahashi@gmail.com
 */
public class DatabaseConnection {

	/** The connection. */
	private static Connection connection = null;

	/**
	 * Instancia uma conexao com banco de dados postgreSQL
	 *
	 * @return conexao com postgresql
	 */
	public static Connection getPostgresSQLConnection() 
	{
		if (connection == null) {
			try 
			{

				String user = "dgaqvbxuhliwlb";
				String password = "ffda1dbc0a0b588ba443f17ae115afbf2498b15b5fbe34bfa9b9d46978db4acc";
				String url = "jdbc:postgresql://ec2-52-70-45-163.compute-1.amazonaws.com:5432/d691kjesg1gbql";
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);

			} 
			catch (SQLException e){
				throw new DataBaseSourceException("Error receiving connection : " + e.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
}
