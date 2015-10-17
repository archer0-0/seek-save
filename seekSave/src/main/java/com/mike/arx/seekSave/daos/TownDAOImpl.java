package com.mike.arx.seekSave.daos;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mike.arx.seekSave.daos.exceptions.DAOTownException;
import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.model.Town;
@Repository
public class TownDAOImpl implements TownDAO{
	Logger logger= LoggerFactory.getLogger(TownDAOImpl.class);
	@Autowired
	MongoOperations operations;
	public TownDAOImpl(){}

	public void save(Town town) throws DAOTownException {
		if(town.getId()!=null){
			throw new  DAOTownException("Can't save a Town with id: "+town.toString());
		}else {
			try{
			operations.save(town);
			logger.debug("New Town saved: "+town.toString());
			}catch(DuplicateKeyException e){
				throw new DAOTownException(e.getMessage(),e);
			}
		}
		
	}

	public void update(Town town) throws DAOTownException {
		if(town.getId()==null){
			throw new  DAOTownException("Can't update a Town without id: "+town.toString());
		}else {
			operations.save(town);
			logger.debug("Town updated: "+town.toString());
		}
		
	}

	public Town findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return operations.findOne(query, Town.class);
	}

	public Town findByName(String name) {
		Query query = new Query(Criteria.where("name").is(name));
		return operations.findOne(query, Town.class);
	}

	public List<Town> findByCountry(Country country) {
		Query query= new Query(Criteria.where("country").is(country));
		return operations.find(query, Town.class);
	}
	/**
	 * This method is only for testing. DON NOT USE IN DEVELOPMENT
	 * @param operations
	 */
	public void setOperations(MongoOperations operations) {
		this.operations = operations;
	}

}
