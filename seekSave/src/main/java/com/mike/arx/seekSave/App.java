package com.mike.arx.seekSave;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mike.arx.seekSave.daos.CountryDAO;
import com.mike.arx.seekSave.daos.CountryDAOImpl;
import com.mike.arx.seekSave.daos.EstablishmentDAO;
import com.mike.arx.seekSave.daos.EstablishmentDAOImpl;
import com.mike.arx.seekSave.daos.TownDAO;
import com.mike.arx.seekSave.daos.TownDAOImpl;
import com.mike.arx.seekSave.daos.exceptions.DAOException;
import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.model.Establishment;
import com.mike.arx.seekSave.model.Town;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(CountryDAOImpl.class);
		// For XML
		ApplicationContext ctx = new GenericXmlApplicationContext(
				"SpringConfig.xml");
		MongoOperations mongoOperation = (MongoOperations) ctx
				.getBean("mongoTemplate");
		CountryDAO countryDAO = new CountryDAOImpl(mongoOperation);
		TownDAO townDAO = new TownDAOImpl(mongoOperation);
		EstablishmentDAO establishmentDAO = new EstablishmentDAOImpl(
				mongoOperation);
		// Pruebas
		Country country = new Country("spain");
		System.out.println("Country before saved: " + country);
		try {
			countryDAO.save(country);
			System.out.println("Country saved1: " + country);
			Query searCountryQuery = new Query(Criteria.where("name").is(
					"spain"));
			Country savedCountry = mongoOperation.findOne(searCountryQuery,
					Country.class);
			System.out.println("Country saved2: " + savedCountry);
			Town testTown = new Town();
			testTown.setName("toledo");
			testTown.setCountry(savedCountry);
			townDAO.save(testTown);
			Town savedTown=townDAO.findByName("toledo");
			System.out.println("Country of the city: " + savedTown.getCountry());
			Establishment testEstablishment= new Establishment();
			Establishment testEstablishment2= new Establishment();
			testEstablishment.setName("Marisqueria Mari");
			testEstablishment.setTown(testTown);
			testEstablishment2.setName("Marisqueria Mari2");
			testEstablishment2.setTown(testTown);
			establishmentDAO.save(testEstablishment);
			establishmentDAO.save(testEstablishment2);
			List<Establishment> establishments= establishmentDAO.findByTown(savedTown);
			for (Establishment establishment : establishments) {
				System.out.println("Establishment: "+establishment.toString()+"\n");
			}
			
		} catch (DAOException e) {
			logger.debug(e.getMessage(), e);
		}
		

	}
}
