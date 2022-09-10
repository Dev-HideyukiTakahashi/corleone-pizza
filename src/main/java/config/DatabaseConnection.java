package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import config.exception.DataBaseSourceException;

public class DatabaseConnection {

	private static Connection connection = null;

	public static Connection getPostgresSQLConnection() 
	{
		if (connection == null) {
			try 
			{
				String user = "fszcdwezednppi";
				String password = "1c953ffdbecf4d7e84cf226be32e9a7f358a4e11c7be21b0e611e161078f8281";
				String url = "jdbc:postgresql://ec2-35-168-122-84.compute-1.amazonaws.com:5432/dcgboc41u4056a";
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false);

			} 
			catch (SQLException e) 
			{
				throw new DataBaseSourceException("Error receiving connection : " + e.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
		return connection;
	}

}
