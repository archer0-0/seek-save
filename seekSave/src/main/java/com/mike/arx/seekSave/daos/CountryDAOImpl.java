package com.mike.arx.seekSave.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mike.arx.seekSave.daos.exceptions.DAOException;
import com.mike.arx.seekSave.model.Country;
public class CountryDAOImpl implements CountryDAO {
	Logger logger = LoggerFactory.getLogger(CountryDAOImpl.class);
	private MongoOperations operations;

	public CountryDAOImpl(MongoOperations operations) {
		this.operations = operations;
	}

	public void save(Country country) throws DAOException {
		if (country.getId() != null) {
			throw new DAOException("Can't save a Country with id: "
					+ country.toString());
		} else {
			operations.save(country);
			logger.debug("New Country saved: "+country.toString());
		}

	}

	public void update(Country country) throws DAOException {
		if (country.getId() == null) {
			throw new DAOException("Can't update a Country without id: "
					+ country.toString());
		} else {
			operations.save(country);
			logger.debug("Country updated: "+country.toString());
		}

	}

	public Country findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return operations.findOne(query, Country.class);
	}

	public Country findByName(String name) {
		Query query = new Query(Criteria.where("name").is(name));
		return operations.findOne(query, Country.class);
	}

}
