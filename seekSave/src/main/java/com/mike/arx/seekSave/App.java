package com.mike.arx.seekSave;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.model.Town;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	 // For XML
    	  ApplicationContext ctx = new GenericXmlApplicationContext("SpringConfig.xml");
    	  MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
    	  //Pruebas
    	  Country country= new Country("spain");
    	  System.out.println("Country before saved: "+ country);
    	  mongoOperation.save(country);
    	  System.out.println("Country saved1: "+ country);
    	  Query searCountryQuery= new Query(Criteria.where("name").is("spain"));
    	  Country savedCountry= mongoOperation.findOne(searCountryQuery, Country.class);
    	  System.out.println("Country saved2: "+ savedCountry);
    	  Town testTown= new Town();
    	  testTown.setName("toledo");
    	  testTown.setCountry(savedCountry);
    	  mongoOperation.save(testTown);
    	  Query searchTownQuery= new Query(Criteria.where("name").is("toledo"));
    	  Town savedTown= mongoOperation.findOne(searchTownQuery, Town.class);
    	  System.out.println("Town saved: "+ savedTown);
    	  System.out.println("Country of the city: "+ savedTown.getCountry());
    	  
    }
}
