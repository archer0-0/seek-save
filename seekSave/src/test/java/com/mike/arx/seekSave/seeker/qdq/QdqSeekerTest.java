package com.mike.arx.seekSave.seeker.qdq;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mike.arx.seekSave.daos.CountryDAOImpl;
import com.mike.arx.seekSave.daos.EstablishmentDAO;
import com.mike.arx.seekSave.daos.EstablishmentDAOImpl;
import com.mike.arx.seekSave.daos.TownDAOImpl;
import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.model.Establishment;
import com.mike.arx.seekSave.model.Town;
import com.mongodb.MongoClient;

public class QdqSeekerTest {
	public static String DB_NAME = "saqueadorTest";
	private static MongoClient mongoClient = null;
	private static MongoOperations operations = null;
	private static CountryDAOImpl countryDAO = null;
	private static TownDAOImpl townDao= null;
	private static EstablishmentDAOImpl establishmentDao= null;
	private static QdqSeeker qdqSeeker= null;
	@BeforeClass
	public static void startUp() {
		try {
			mongoClient = new MongoClient("127.0.0.1", 27017);
			operations = new MongoTemplate(mongoClient, DB_NAME);
			countryDAO = new CountryDAOImpl();
			countryDAO.setOperations(operations);
			townDao= new TownDAOImpl();
			townDao.setOperations(operations);
			establishmentDao= new EstablishmentDAOImpl();
			establishmentDao.setOperations(operations);
			qdqSeeker= new QdqSeeker();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		operations.dropCollection(Country.class);
		operations.dropCollection(Town.class);
		operations.dropCollection(Establishment.class);
		
	}

	@Test
	public void test() {
		Document documentOfQdqSite=null;
		File file = new File("C:\\Users\\marquero\\qdq\\restauracion.html");
		try {
			documentOfQdqSite = Jsoup.parse(file, "UTF-8");
		} catch (IOException e) {
			logger.debug(e.getMessage(), e);
		}
		return documentOfQdqSite;
	}

}
