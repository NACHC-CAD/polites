package org.nachc.tools.politesforsqlserver.util.connection;

import java.sql.Connection;
import java.sql.DriverManager;

import org.nachc.tools.fhirtoomop.util.params.AppParams;

public class PolitesConnectionFactory {

	public static Connection getBootstrapConnection() {
		try {
			String url = AppParams.getBootstrapUrl();
			Connection conn = DriverManager.getConnection(url);
			return conn;
		} catch (Exception exp) {
			throw (new RuntimeException(exp));
		}
	}

}
