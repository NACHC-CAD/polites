package org.nachc.tools.polites.util.connection;

import java.sql.Connection;
import java.sql.DriverManager;

import org.nachc.tools.fhirtoomop.util.params.AppParams;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PolitesPostgresConnectionFactory {

	public static Connection getBootstrapConnection() {
		try {
			String url = AppParams.getBootstrapUrl();
			log.info("Creating connection for: \n" + url);
			Connection conn = DriverManager.getConnection(url);
			conn.setAutoCommit(true);
			return conn;
		} catch (Exception exp) {
			throw (new RuntimeException(exp));
		}
	}

	public static Connection getUserConnection() {
		try {
			String url = AppParams.getUrl();
			log.info("Creating connection for: \n" + url);
			Connection conn = DriverManager.getConnection(url);
			conn.setAutoCommit(true);
			return conn;
		} catch (Exception exp) {
			throw (new RuntimeException(exp));
		}
	}

	public static Connection getConnection(String url) {
		try {
			Connection conn = DriverManager.getConnection(url);
			return conn;
		} catch (Exception exp) {
			throw (new RuntimeException(exp));
		}
	}
	
}
