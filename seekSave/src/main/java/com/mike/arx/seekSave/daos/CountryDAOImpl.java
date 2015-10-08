package com.mike.arx.seekSave.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mike.arx.seekSave.daos.exceptions.DAOException;
import com.mike.arx.seekSave.model.Country;
@Repository
public class CountryDAOImpl implements CountryDAO {
	Logger logger = LoggerFactory.getLogger(CountryDAOImpl.class);
	@Autowired
	private MongoOperations operations;
	public CountryDAOImpl() {
	}

	public void save(Country country) throws DAOException {
		if (country.getId() != null) {
			throw new DAOException("Can't save a Country with id: "
					+ country.toString());
		} else {
			try{
			operations.save(country);
			logger.debug("New Country saved: "+country.toString());
			}catch(DuplicateKeyException e){
				throw new DAOException(e.getMessage()
						+ country.toString(),e);
			}
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
	/**
	 * This method is only for testing. DON NOT USE IN DEVELOPMENT
	 * @param operations
	 */
	public void setOperations(MongoOperations operations) {
		this.operations = operations;
	}

}
