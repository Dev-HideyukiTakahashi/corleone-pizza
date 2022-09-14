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
				String user = "suwtkcuqdlvqmn";
				String password = "7bc97f83ae12ba2d60aa9b9489fdec7218b85ce48c806adf7dfc74bcbcc13314";
				String url = "jdbc:postgresql://ec2-44-209-158-64.compute-1.amazonaws.com:5432/dfeef4sj87rbd6";
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
