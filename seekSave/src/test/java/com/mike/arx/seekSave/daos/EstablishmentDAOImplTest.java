package com.mike.arx.seekSave.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mike.arx.seekSave.daos.exceptions.DAOException;
import com.mike.arx.seekSave.model.Establishment;
import com.mike.arx.seekSave.model.Town;
import com.mongodb.MongoClient;

public class EstablishmentDAOImplTest {
	public static String DB_NAME = "saqueadorTest";
	private static MongoClient mongoClient = null;
	private static MongoOperations operations = null;
	private static EstablishmentDAOImpl establishmentDao = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mongoClient = new MongoClient("127.0.0.1", 27017);
		operations = new MongoTemplate(mongoClient, DB_NAME);
		establishmentDao = new EstablishmentDAOImpl();
		establishmentDao.setOperations(operations);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		operations.dropCollection(Town.class);
		operations.dropCollection(Establishment.class);
	}

	@Test
	public void saveIfEstablishmentIdIsNull() {
		Establishment establishment= new Establishment();
		establishment.setName("EstablishmentTest1");
		establishment.setContactMail("mailTest1");
		int beforeSave=operations.findAll(Establishment.class).size();
		try {
			establishmentDao.save(establishment);
			int afterSave=operations.findAll(Establishment.class).size();
			assertEquals(beforeSave+1,afterSave);
		} catch (DAOException e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void exceptionIfEstablishmentIdIsFilled(){
		Establishment establishment= new Establishment();
		establishment.setName("EstablishmentTest2");
		establishment.setContactMail("mailTest2");
		establishment.setId("173749582");
		try {
			establishmentDao.save(establishment);
		} catch (DAOException e) {
			assertTrue(e instanceof DAOException);
		}
	}
	@Test
	public void updateIfEstablishmentIdIsFilled(){
		Establishment establishment=new Establishment();
		establishment.setName("EstablishmentTest3");
		establishment.setContactMail("mailTest3");
		operations.save(establishment);
		establishment.setName("EstablishmentTest3A");
		try {
			establishmentDao.update(establishment);
		} catch (DAOException e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void exceptionOnUpdateIfEstablishmenIdEqualNull(){
		Establishment establishment= new Establishment();
		establishment.setName("EStablishmentTest4");
		establishment.setContactMail("mailTest4");
		try {
			establishmentDao.update(establishment);
		} catch (DAOException e) {
			assertTrue(e instanceof DAOException);
		}
	}
	@Test
	public void ifThereIsAEstablishmentWithThisIdReturnIt(){
		Establishment establishment= new Establishment();
		establishment.setName("EStablishmentTest5");
		establishment.setContactMail("mailTest5");
		operations.save(establishment);
		Establishment savedEstablishment= establishmentDao.findById(establishment.getId());
		assertEquals(establishment.getName(), savedEstablishment.getName());
	}
	@Test
	public void ifThereIsntEstablishmentWithThisIdReturnNull(){
		Establishment savedEstablishment=establishmentDao.findById("1234586");
		assertNull(savedEstablishment);
	}
	@Test
	public void ifThereIsAEstablishmentWithThisNameReturnIt(){
		Establishment establishment= new Establishment();
		establishment.setName("EStablishmentTest6");
		establishment.setContactMail("mailTest6");
		operations.save(establishment);
		Establishment savedEstablishment= establishmentDao.findByName(establishment.getName());
		assertEquals(establishment.getId(), savedEstablishment.getId());
	}
	@Test
	public void ifThereIsntEstablishmentWithThisNameReturnNull(){
		Establishment savedEstablishment=establishmentDao.findByName("abshfkoert");
		assertNull(savedEstablishment);
	}
	@Test
	public void ifThereIsAEstablishmentWithThisTownReturnIt(){
		Town town= new Town();
		town.setName("TownTestEstablishment1");
		operations.save(town);
		Establishment establishment= new Establishment();
		establishment.setName("EStablishmentTest7");
		establishment.setContactMail("mailTest7");
		establishment.setTown(town);
		operations.save(establishment);
		List<Establishment> savedEstablishment=establishmentDao.findByTown(town);
		assertFalse(savedEstablishment.isEmpty());
	}
	@Test
	public void ifThereIsntEstablishmentWithThisTownReturnNull(){
		Town town= new Town();
		town.setName("TownTestEstablishment2");
		operations.save(town);
		List<Establishment> savedEstablishments= establishmentDao.findByTown(town);
		assertTrue(savedEstablishments.isEmpty());
	}
	@Test
	public void ifThereIsAEstablishmentWithThisPostalCodeReturnIt(){
		Establishment establishment= new Establishment();
		establishment.setName("EStablishmentTest8");
		establishment.setContactMail("mailTest8");
		establishment.setPostalCode("1234");
		operations.save(establishment);
		List<Establishment> savedEstablishment=establishmentDao.findByPostalCode("1234");
		assertFalse(savedEstablishment.isEmpty());
	}
	@Test
	public void ifThereIsntEstablishmentWithThisPostalCodeReturnNull(){
		List<Establishment> savedEstablishments= establishmentDao.findByPostalCode("64646");
		assertTrue(savedEstablishments.isEmpty());
	}

}
