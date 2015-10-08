package com.mike.arx.seekSave.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mike.arx.seekSave.daos.exceptions.DAOException;
import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.model.Town;
import com.mongodb.MongoClient;

public class TownDAOImplTest {
	public static String DB_NAME = "saqueadorTest";
	private static MongoClient mongoClient = null;
	private static MongoOperations operations = null;
	private static TownDAOImpl townDao = null;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
			mongoClient = new MongoClient("127.0.0.1", 27017);
			operations = new MongoTemplate(mongoClient, DB_NAME);
			townDao = new TownDAOImpl();
			townDao.setOperations(operations);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		operations.dropCollection(Country.class);
		operations.dropCollection(Town.class);
	}

	@Test
	public void saveIfTownIdEqualNull() {
		int beforeSave=0;
		Town town= new Town();
		town.setName("TownTest1");
		try {
			beforeSave=operations.findAll(Town.class).size();
			townDao.save(town);
			int afterSave=operations.findAll(Town.class).size();
			assertEquals(beforeSave+1,afterSave);
		} catch (DAOException e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void exceptionIfTownIdIsFilled(){
		Town town= new Town();
		town.setName("TownTest2");
		town.setId("121434");
		try{
			townDao.save(town);
		}catch(DAOException e){
			assertTrue(e instanceof DAOException);
		}
	}
	@Test
	public void updateIfTownIdEqualsNotNull(){
		Town town= new Town();
		town.setName("TownTest3");
		operations.save(town);
		try {
			townDao.update(town);
		} catch (DAOException e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void exceptionUpdateIfTownIdNull(){
		Town town= new Town();
		town.setName("TownTest4");
		try {
			townDao.update(town);
		} catch (DAOException e) {
			assertTrue(e instanceof DAOException);
		}
	}
	@Test
	public void ifThereisaTownWithThisIdreturnIt(){
		Town town= new Town();
		town.setName("TownTest5");
		operations.save(town);
		Town savedTown=townDao.findById(town.getId());
		assertTrue(savedTown!=null);
	}
	@Test
	public void ifThereIsntTownWithThisIdReturnNull(){
		Town nullTown=townDao.findById("139402");
		assertTrue(nullTown==null);
	}
	@Test
	public void ifThereisaTownWithThisNamereturnIt(){
		Town town= new Town();
		town.setName("TownTest7");
		operations.save(town);
		Town savedTown=townDao.findByName(town.getName());
		assertTrue(savedTown!=null);
	}
	@Test
	public void ifThereIsntTownWithThisNameReturnNull(){
		Town nullTown=townDao.findById("miau");
		assertTrue(nullTown==null);
	}
	@Test
	public void ifThereIsntTownWithThisCountryReturnNull(){
		Town town= new Town();
		town.setName("TownTest8");
		operations.save(town);
		Country country= new Country("holaa");
		operations.save(country);
		List<Town> savedTown=townDao.findByCountry(country);
		assertTrue(savedTown.isEmpty());
		
	}
	@Test
	public void ifThereIsTownWithThisCountryReturnList(){
		Country country= new Country("CountryTestTown1");
		operations.save(country);
		Town town= new Town();
		town.setName("TownTest6");
		town.setCountry(country);
		operations.save(town);
		List<Town> savedTown=townDao.findByCountry(country);
		assertFalse(savedTown.isEmpty());
		
	}

}
