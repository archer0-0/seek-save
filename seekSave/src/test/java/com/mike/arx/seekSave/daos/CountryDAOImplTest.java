package com.mike.arx.seekSave.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.UnknownHostException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mike.arx.seekSave.daos.exceptions.DAOException;
import com.mike.arx.seekSave.model.Country;
import com.mongodb.MongoClient;

public class CountryDAOImplTest {
	public static String DB_NAME = "saqueadorTest";
	private static MongoClient mongoClient = null;
	private static MongoOperations operations = null;
	private static CountryDAOImpl countryDAO = null;

	@BeforeClass
	public static void startUp() {
		try {
			mongoClient = new MongoClient("127.0.0.1", 27017);
			operations = new MongoTemplate(mongoClient, DB_NAME);
			countryDAO = new CountryDAOImpl();
			countryDAO.setOperations(operations);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void saveCountryIfIdEqualNull() {
		Country country = new Country("CountryTest1");
		int beforeSaveSize = 0;
		try {
			beforeSaveSize = operations.findAll(Country.class).size();
			countryDAO.save(country);
			int afterSize=operations.findAll(Country.class).size();
			assertEquals(beforeSaveSize+1, afterSize);
		} catch (DAOException e) {
			fail(e.getMessage());
		}
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		operations.dropCollection(Country.class);
	}
	@Test
	public void exceptionIfCountryIdNotEqualNull(){
		Country country= new Country("CountryTest2");
		operations.save(country);
		try {
			countryDAO.save(country);
		} catch (DAOException e) {
			assertTrue(e instanceof DAOException);
		}
	}
	@Test
	public void exceptionIfCountryHasSameNameAsAnother(){
		Country country= new Country("CountryTest2A");
		operations.save(country);
		Country country2= new Country("CountryTest2A");
		try {
			countryDAO.save(country2);
		} catch (DAOException e) {
			assertTrue(e instanceof DAOException);
		}
	}
	@Test
	public void updateCountryIfIdNotEqualNull(){
		Country country= new Country("CountryTest3");
		operations.save(country);
		country.setName("CountryTest3A");
		try {
			countryDAO.update(country);
			Country savedCountry=operations.findOne(new Query(Criteria.where("_id").is(
					country.getId())), Country.class);
			assertEquals(country.getName(), savedCountry.getName());
		} catch (DAOException e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void exceptionIfUpdateCountryIdEqualNull(){
		Country country=new Country("CountryTest4");
		try {
			countryDAO.update(country);
		} catch (DAOException e) {
			assertTrue(e instanceof DAOException);
		}
	}
	@Test
	public void ifThereIsACountryWithThisIdReturnIt(){
		Country country= new Country("CountryTest5");
		operations.save(country);
		Country foundCountry=countryDAO.findById(country.getId());
		assertTrue(foundCountry!=null);
	}
	@Test
	public void ifThereIsntACountryWithThisIdReturnNull(){
		Country foundCountry=countryDAO.findById("1022834");
		assertTrue(foundCountry==null);
	}
	@Test
	public void ifThereIsACountryWithThisNameReturnIt(){
		Country country= new Country("CountryTest6");
		operations.save(country);
		Country foundCountry=countryDAO.findByName(country.getName());
		assertTrue(foundCountry!=null);
	}
	@Test
	public void ifThereIsntACountryWithThisNameReturnNull(){
		Country foundCountry=countryDAO.findByName("abcd");
		assertTrue(foundCountry==null);
	}
	

}
