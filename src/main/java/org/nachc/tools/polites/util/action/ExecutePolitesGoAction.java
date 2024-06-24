package org.nachc.tools.polites.util.action;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;

import org.nachc.tools.fhirtoomop.tools.build.achilles.RunAchilles;
import org.nachc.tools.fhirtoomop.tools.build.atlas.impl.CreateAchillesDatabases;
import org.nachc.tools.fhirtoomop.tools.build.impl.AddConstraints;
import org.nachc.tools.fhirtoomop.tools.build.impl.BurnEverythingToTheGround;
import org.nachc.tools.fhirtoomop.tools.build.impl.CreateAchillesAnalysisTable;
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
import org.nachc.tools.fhirtoomop.tools.download.terminology.DownloadDefaultTerminology;
import org.nachc.tools.fhirtoomop.util.db.truncate.impl.TruncateCdmTables;
import org.nachc.tools.fhirtoomop.util.params.AppParams;
import org.nachc.tools.fhirtoomop.util.uploadcsv.sqlserver.UploadCsvForSqlServer;
import org.nachc.tools.fhirtoomop.util.webapi.CreateWebApiRecords;
import org.nachc.tools.fhirtoomop.util.webapi.DeleteWebApiRecords;
import org.nachc.tools.polites.util.connection.PolitesConnectionFactory;
import org.yaorma.database.Database;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutePolitesGoAction {

	public static void exec(ArrayList<String> sel, String databaseType, String cdmVersion) {
		Connection conn = PolitesConnectionFactory.getBootstrapConnection();
		try {
			// reset
			if (sel.contains("burnEverythingToTheGround")) {
				log("BURNING EVERYTHING TO THE GROUND");
				BurnEverythingToTheGround.exec(conn);
				log.info("Done with Burn Everything to the Ground.");
			}
			// create database objects
			if (sel.contains("createDatabase")) {
				log("CREATING DATABASE");
				log("Creating OMOP instance...");
				CreateDatabase.exec(conn);
				log.info("Done with Create Database.");
			}
			if (sel.contains("createDatabaseUsers")) {
				log("CREATING USER");
				CreateDatabaseUser.exec(conn);
				log.info("Done with Create Database Users.");
			}
			if (sel.contains("createTables")) {
				log("CREATING TABLES");
				use(conn);
				CreateDatabaseTables.exec(conn);
				CreateFhirResoureTables.exec(conn);
				CreateMappingTables.exec(conn);
				log.info("Done with Create Tables.");
			}
			if (sel.contains("createCDMSourceRecord")) {
				log("CREATING CDM RECORD");
				use(conn);
				CreateCdmSourceRecord.exec(conn);
				Database.commit(conn);
				log.info("Done with Create CDM Record.");
			}
			// terminology
			if (sel.contains("truncateTerminology")) {
				log("TRUNCATING TERMINOLOGY");
				TruncateCdmTables.truncateVocabularyTables();
				log.info("Done truncating.");
			}
			if (sel.contains("loadTerminology")) {
				log("LOADING TERMINOLOGY");
				use(conn);
				// move the race eth files
				String destDir = AppParams.getTerminologyRootDir();
				File dir = new File(destDir).getParentFile();
				String dirName = FileUtil.getCanonicalPath(dir);
				MoveRaceEthFiles raceFiles = new MoveRaceEthFiles();
				raceFiles.exec(dirName);
				// load the race eth file
				log.info("Loading mapping tables...");
				LoadMappingTables.exec(raceFiles.getSqlFile(), conn);
				// download terminology
				log.info("Checking for default terminology...");
				DownloadDefaultTerminology.exec();
				// load terminology
				log.info("Loading terminology...");
				LoadTerminology.exec(conn);
				log.info("Done loading terminology.");
			}
			if (sel.contains("importTerminology")) {
				log("IMPORTING TERMINOLOGY");
				UploadCsvForSqlServer.uploadTerminologyTables();
				log.info("Done with import terminology.");
			}
			// sequences, indexes, and constraints
			if (sel.contains("createSequencesForPrimaryKeys")) {
				log("CREATING SEQUENCES");
				use(conn);
				CreateSequencesForPrimaryKeys.exec(conn);
				log.info("Done with Create Sequences.");
			}
			if (sel.contains("createIndexes")) {
				log("CREATING CONSTRAINTS");
				use(conn);
				CreateDatabaseIndexes.exec(conn);
				log.info("Done with Create Indexes.");
			}
			if (sel.contains("addConstraints")) {
				log("ADDING CONSTRAINTS");
				use(conn);
				AddConstraints.exec();
				log.info("Done Adding Constraints.");
			}
			if (sel.contains("disableConstraints")) {
				log("DISABLING CONSTRAINTS");
				use(conn);
				DisableConstraints.exec(conn);
				log.info("Done with Disable Constraints.");
			}
			if (sel.contains("enableConstraints")) {
				log("ENABLING CONSTRAINTS");
				use(conn);
				EnableConstraints.exec(conn);
				log.info("Done with Enable Constraints.");
			}
			// truncate, import, and export data tables
			if (sel.contains("truncateDataTables")) {
				log("TRUNCATING DATA TABLES");
				use(conn);
				TruncateCdmTables.truncateDataTables();
				log.info("Done truncating.");
			}
			if (sel.contains("importDataTables")) {
				log("IMPORTING DATA TABLES");
				use(conn);
				UploadCsvForSqlServer.uploadDatatables();
				log.info("Done importing.");
			}
			// truncate, import, and export all tables
			if (sel.contains("truncateAll")) {
				log("TRUNCATING ALL TABLES");
				use(conn);
				TruncateCdmTables.truncateAllTables();
				log.info("Done truncating.");
			}
			if (sel.contains("importAll")) {
				log("IMPORTING ALL TABLES");
				use(conn);
				UploadCsvForSqlServer.uploadAll();
				log.info("Done importing.");
			}
			// load synthea csv files
			if (sel.contains("uploadSyntheaCsv")) {
				log("UPLOAD SYNTHEA CSV: NOT IMPLEMENTED YET ");
				use(conn);
				log.info("Done with Synthea Upload.");
			}
			// run achilles
			if (sel.contains("deleteWebApiRecords")) {
				log("DELETING WEBAPI RECORDS");
				use(conn);
				DeleteWebApiRecords.exec();
				log.info("Done deleting webapi records.");
			}
			if (sel.contains("addWebApiRecords")) {
				log("ADDING WEBAPI RECORDS");
				use(conn);
				CreateWebApiRecords.exec();
				log.info("Done adding webapi records.");
			}
			if (sel.contains("createAchillesDatabase")) {
				log("CREATING ACHILLES DATABASE");
				use(conn);
				CreateAchillesDatabases.exec();
				CreateAchillesAnalysisTable.exec();
				log.info("Done creating Achilles database.");
			}
			if (sel.contains("runAchilles")) {
				log("RUNNING ACHILLES");
				use(conn);
				RunAchilles.exec();
				log.info("Done running Achilles.");
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
		String schemaName = AppParams.getDatabaseName();
		Database.update("use " + schemaName, conn);
		log.info("Using: " + schemaName);
	}

}
