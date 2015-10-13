package com.mike.arx.seekSave;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.mike.arx.seekSave.daos.CountryDAO;
import com.mike.arx.seekSave.daos.CountryDAOImpl;
import com.mike.arx.seekSave.daos.EstablishmentDAO;
import com.mike.arx.seekSave.daos.EstablishmentDAOImpl;
import com.mike.arx.seekSave.daos.TownDAO;
import com.mike.arx.seekSave.daos.TownDAOImpl;
import com.mike.arx.seekSave.seeker.qdq.QdqDirector;

/**
 * Hello world!
 *
 */
public class App {
	private static ApplicationContext ctx;

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(CountryDAOImpl.class);
		ctx = new GenericXmlApplicationContext(
				"SpringConfig.xml");
		MongoOperations mongoOperation = (MongoOperations) ctx
				.getBean("mongoTemplate");
		CountryDAO countryDAO = ctx.getBean(CountryDAOImpl.class);
		TownDAO townDAO = ctx.getBean(TownDAOImpl.class);
		EstablishmentDAO establishmentDAO = ctx.getBean(EstablishmentDAOImpl.class);

		//Prueba Scraping
//		ParserTest.parse();
//		QdqDirector.seek();
		
		
		
		
		
		
		
		
		
		
		
////	Prueba crawling
//	CrawlingController.start();
		
//		 Pruebas
//		Country country = new Country("spain");
//		System.out.println("Country before saved: " + country);
//		try {
//			countryDAO.save(country);
//			System.out.println("Country saved1: " + country);
//			Query searCountryQuery = new Query(Criteria.where("name").is(
//					"spain"));
//			Country savedCountry = mongoOperation.findOne(searCountryQuery,
//					Country.class);
//			System.out.println("Country saved2: " + savedCountry);
//			Town testTown = new Town();
//			testTown.setName("toledo");
//			testTown.setCountry(savedCountry);
//			townDAO.save(testTown);
//			Town savedTown=townDAO.findByName("toledo");
//			System.out.println("Country of the city: " + savedTown.getCountry());
//			Establishment testEstablishment= new Establishment();
//			Establishment testEstablishment2= new Establishment();
//			testEstablishment.setName("Marisqueria Mari");
//			testEstablishment.setTown(testTown);
//			testEstablishment2.setName("Marisqueria Mari2");
//			testEstablishment2.setTown(testTown);
//			establishmentDAO.save(testEstablishment);
//			establishmentDAO.save(testEstablishment2);
//			List<Establishment> establishments= establishmentDAO.findByTown(savedTown);
//			for (Establishment establishment : establishments) {
//				System.out.println("Establishment: "+establishment.toString()+"\n");
//			}
//			
//		} catch (DAOException e) {
//			logger.debug(e.getMessage(), e);
//		}
		

	}
}
