package com.mike.arx.seekSave.cruds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;

import com.mike.arx.seekSave.model.Country;

public class CrudCountry {
	Logger logger = LoggerFactory.getLogger(CrudCountry.class);
	private MongoOperations operations;
	public CrudCountry(MongoOperations operations){
		this.operations=operations;
	}
	public void save(Country country){
		this.operations.save(country);
	}

}
