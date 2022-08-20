package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import config.exception.DataBaseSourceException;

public class DatabaseConnection {

	private static Connection connection = null;

	public static Connection getConnection() 
	{
		if (connection == null) {
			try 
			{
				String user = "postgres";
				String password = "1234567";
				String url = "jdbc:postgresql://localhost:5432/corleone-pizza";
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

	private static Properties loadProperties() 
	{

		try (FileInputStream fs = new FileInputStream("database.properties")) 
		{
			Properties props = new Properties();
			props.load(fs);
			return props;

		} 
		catch (IOException e) 
		{
			throw new DataBaseSourceException("Error receiving connection : " + e.getMessage());
		}
	}
	
}
