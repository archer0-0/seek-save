package com.mike.arx.seekSave;

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
import com.mike.arx.seekSave.seeker.GenericSeeker;
import com.mike.arx.seekSave.seeker.qdq.QdqDirector;
import com.mike.arx.seekSave.seeker.qdq.QdqSeeker;

/**
 * Hello world!
 *
 */
public class App {
	private static ApplicationContext ctx;

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(CountryDAOImpl.class);
		ctx = new GenericXmlApplicationContext("SpringConfig.xml");
		MongoOperations mongoOperation = (MongoOperations) ctx
				.getBean("mongoTemplate");
		CountryDAO countryDAO = ctx.getBean(CountryDAOImpl.class);
		TownDAO townDAO = ctx.getBean(TownDAOImpl.class);
		EstablishmentDAO establishmentDAO = ctx
				.getBean(EstablishmentDAOImpl.class);
		GenericSeeker qdqSeeker = new QdqSeeker();
		QdqDirector director = new QdqDirector();
		director.setCountryDAO(countryDAO);
		director.setTownDAO(townDAO);
		director.setEstablishmentDAO(establishmentDAO);
		director.setSeeker(qdqSeeker);
		director.seek();

	}
}
