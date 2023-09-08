package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Pruebas {
	

	public static void main(String[] args) throws SQLException {
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/alurahotel?useTimeZone=true&serverTimeZones=UTC",
				"root",
				"23433jui");
		
		Statement stm = conn.createStatement();
		boolean result = stm.execute("SELECT id, nombre, apellidos FROM huespedes");
		ResultSet resultSet = stm.getResultSet();
		
		while (resultSet.next()) {
			
			System.out.println(resultSet.getString(2));
			
		}
		
		System.out.println(result);
		System.out.println("Cerrando la conexion");
		stm.close();
		
		conn.close();

	}

}
