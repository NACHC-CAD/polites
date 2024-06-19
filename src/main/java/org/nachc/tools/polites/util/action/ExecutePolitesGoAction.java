package org.nachc.tools.polites.util.action;

import java.sql.Connection;
import java.util.ArrayList;

import org.nachc.tools.fhirtoomop.tools.build.impl.AddConstraints;
import org.nachc.tools.fhirtoomop.tools.build.impl.BurnEverythingToTheGround;
import org.nachc.tools.fhirtoomop.tools.build.impl.CreateCdmSourceRecord;
import org.nachc.tools.fhirtoomop.tools.build.impl.CreateDatabase;
import org.nachc.tools.fhirtoomop.tools.build.impl.CreateDatabaseIndexes;
import org.nachc.tools.fhirtoomop.tools.build.impl.CreateDatabaseTables;
import org.nachc.tools.fhirtoomop.tools.build.impl.CreateDatabaseUser;
import org.nachc.tools.fhirtoomop.tools.build.impl.CreateFhirResoureTables;
import org.nachc.tools.fhirtoomop.tools.build.impl.CreateMappingTables;
import org.nachc.tools.fhirtoomop.tools.build.impl.CreateSequencesForPrimaryKeys;
import org.nachc.tools.fhirtoomop.tools.build.impl.DisableConstraints;
import org.nachc.tools.fhirtoomop.tools.build.impl.EnableConstraints;
import org.nachc.tools.fhirtoomop.tools.build.impl.LoadMappingTables;
import org.nachc.tools.fhirtoomop.tools.build.impl.LoadTerminology;
import org.nachc.tools.fhirtoomop.tools.build.impl.MoveRaceEthFiles;
import org.nachc.tools.fhirtoomop.util.params.AppParams;
import org.nachc.tools.polites.util.connection.PolitesConnectionFactory;
import org.yaorma.database.Database;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutePolitesGoAction {

	public static void exec(ArrayList<String> sel, String databaseType, String cdmVersion) {
		Connection conn = PolitesConnectionFactory.getBootstrapConnection();
		try {
			if (sel.contains("burnEverythingToTheGround")) {
				// delete the existing instance
				log("BURNING EVERYTHING TO THE GROUND");
				BurnEverythingToTheGround.exec(conn);
			}
			if (sel.contains("createDatabase")) {
				// create the new database
				log("CREATING DATABASE");
				log("Creating OMOP instance...");
				CreateDatabase.exec(conn);
			}
			if (sel.contains("createDatabaseUsers")) {
				// create the user
				log("CREATING USER");
				CreateDatabaseUser.exec(conn);
			}
			if (sel.contains("createTables")) {
				// create the tables
				log("CREATING TABLES");
				use(conn);
				CreateDatabaseTables.exec(conn);
				CreateFhirResoureTables.exec(conn);
				CreateMappingTables.exec(conn);
			}
			if (sel.contains("createCDMSourceRecord")) {
				// create the cdm_source record (uses app.parameters values)
				log("CREATING CDM RECORD");
				use(conn);
				CreateCdmSourceRecord.exec(conn);
				Database.commit(conn);
			}
			if (sel.contains("loadTerminology")) {
				// load the terminologies
				log("LOADING TERMINOLOGY");
				// move the race eth files
				MoveRaceEthFiles raceFiles = new MoveRaceEthFiles();
				raceFiles.exec();
				use(conn);
				LoadMappingTables.exec(raceFiles.getSqlFile(), conn);
				LoadTerminology.exec(conn);
			}
			if (sel.contains("createSequencesForPrimaryKeys")) {
				// create the sequences
				log("CREATING SEQUENCES");
				use(conn);
				CreateSequencesForPrimaryKeys.exec(conn);
			}
			if (sel.contains("createIndexes")) {
				// create the indexes and add constraints
				log("CREATING CONSTRAINTS");
				use(conn);
				CreateDatabaseIndexes.exec(conn);
			}
			if (sel.contains("addConstraints")) {
				// create the indexes and add constraints
				log("ADDING CONSTRAINTS");
				use(conn);
				AddConstraints.exec();
			}

		
			if (sel.contains("disableConstraints")) {
				// create the indexes and add constraints
				log("DISABLING CONSTRAINTS");
				use(conn);
				DisableConstraints.exec(conn);
			}
			if (sel.contains("enableConstraints")) {
				// create the indexes and add constraints
				log("ENABLING CONSTRAINTS");
				use(conn);
				EnableConstraints.exec(conn);
			}
			if (sel.contains("uploadSyntheaCsv")) {
				// create the indexes and add constraints
				log("UPLOADING SYNTHEA CSV");
				use(conn);
			}
			if (sel.contains("runAchilles")) {
				// create the indexes and add constraints
				log("RUNNING ACHILLES");
				use(conn);
			}
		
		
		} finally {
			Database.close(conn);
		}
	}

	private static void log(String msg) {
		String str = "\n\n\n";
		str += "* * *\n";
		str += "* \n";
		str += "* " + msg + "\n";
		str += "* \n";
		str += "* * * \n\n";
		log.info(str);
	}
	
	private static void use(Connection conn) {
		log.info("Setting default schema...");
		String schemaName = AppParams.getSchemaName();
		Database.update("use " + schemaName, conn);		
		log.info("Using: " + schemaName);
	}
	
}
