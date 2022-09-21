package config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException {

		System.out.println(test());

	}
	public static  Integer  test() throws SQLException {
		Connection connection = DatabaseConnection.getPostgresSQLConnection();
		String 			  sql  = "SELECT COUNT(1) as total FROM tb_log";
		PreparedStatement ps   = connection.prepareStatement(sql);
		ResultSet		  rs   = ps.executeQuery();
		
		Double result = 0.0;
		while(rs.next()) {result = rs.getDouble("total");}

		Double offset = result / 10;
		offset 		  = offset % 2 > 0 ? offset + 1 : offset;
		
		return offset.intValue();
	}
}
