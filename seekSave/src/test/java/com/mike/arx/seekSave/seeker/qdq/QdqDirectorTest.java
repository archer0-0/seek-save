package com.mike.arx.seekSave.seeker.qdq;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mike.arx.seekSave.daos.CountryDAOImpl;
import com.mike.arx.seekSave.daos.EstablishmentDAOImpl;
import com.mike.arx.seekSave.daos.TownDAOImpl;
import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.model.Establishment;
import com.mike.arx.seekSave.model.Town;
import com.mongodb.MongoClient;

public class QdqDirectorTest {
	public static String DB_NAME = "saqueadorTest";
	public static final String uriLocalHtmlEstablishmentsList = "C:\\Users\\marquero\\qdq\\listaSitios\\listaSitios.html";
	public static final String uriLocalHtmlMain = "C:\\Users\\marquero\\qdq\\restauracion\\restauracion.html";
	public static final QdqDirector qdqDirector = new QdqDirector();
	private static MongoClient mongoClient = null;
	private static MongoOperations operations = null;
	private static CountryDAOImpl countryDAO = null;
	private static TownDAOImpl townDao = null;
	private static EstablishmentDAOImpl establishmentDao = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mongoClient = new MongoClient("127.0.0.1", 27017);
		operations = new MongoTemplate(mongoClient, DB_NAME);
		countryDAO = new CountryDAOImpl();
		countryDAO.setOperations(operations);
		townDao = new TownDAOImpl();
		townDao.setOperations(operations);
		establishmentDao = new EstablishmentDAOImpl();
		establishmentDao.setOperations(operations);
		qdqDirector.setCountryDAO(countryDAO);
		qdqDirector.setEstablishmentDAO(establishmentDao);
		qdqDirector.setTownDAO(townDao);
		SeekerMock seekerMock= new SeekerMock();
		qdqDirector.setSeeker(seekerMock);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		operations.dropCollection(Country.class);
		operations.dropCollection(Town.class);
		operations.dropCollection(Establishment.class);
	}

	@Test
	public void ifExistSpainReadItFromDB() {
	}

}
