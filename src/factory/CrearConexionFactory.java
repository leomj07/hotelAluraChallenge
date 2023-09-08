package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CrearConexionFactory {

	public Connection recuperaConexion() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost/alurahotel?useTimeZone=true&serverTimeZones=UTC",
				"root",
				"23433jui");
	}

}
