package org.nachc.tools.politesforsqlserver.util.action;

import java.sql.Connection;

import org.junit.Test;
import org.nachc.tools.politesforsqlserver.util.connection.PolitesConnectionFactory;
import org.yaorma.database.Data;
import org.yaorma.database.Database;
import org.yaorma.database.Row;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestConnectionStringActionManualTest {

	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=synthea_omop;encrypt=false;TrustServerCertificate=True;user=synthea_omop;password=Sneaker01";

	@Test
	public void shouldGetConnection() {
		Connection conn = null;
		try {
			log.info("Getting connection...");
			conn = PolitesConnectionFactory.getConnection(URL);
			log.info("Got connection: \n" + conn);
			String sqlString = "select count(*) cnt from dbo.vocabulary";
			Data data = Database.query(sqlString, conn);
			Row row = data.get(0);
			String cnt = row.get("cnt");
			log.info("Concept count: " + cnt);
		} finally {
			Database.close(conn);
		}
		log.info("Done.");
	}

}
