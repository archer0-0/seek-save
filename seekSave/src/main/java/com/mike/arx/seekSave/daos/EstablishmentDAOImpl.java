package com.mike.arx.seekSave.daos;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mike.arx.seekSave.daos.exceptions.DAOException;
import com.mike.arx.seekSave.model.Establishment;
import com.mike.arx.seekSave.model.Town;

@Repository
public class EstablishmentDAOImpl implements EstablishmentDAO {
	Logger logger= LoggerFactory.getLogger(EstablishmentDAOImpl.class);
	@Autowired
	MongoOperations operations;
	public EstablishmentDAOImpl(){}
	public EstablishmentDAOImpl(MongoOperations mongoOperations){
		this.operations=mongoOperations;
	}

	public void save(Establishment establishment) throws DAOException {
		if(establishment.getId()!=null){
			throw new DAOException("Can't save Establishment with id: "+establishment.toString());
		}else {
			operations.save(establishment);
			logger.debug("Establishment saved: "+establishment.toString());
		}
		
	}

	public void update(Establishment establishment) throws DAOException {
		if(establishment.getId()==null){
			throw new DAOException("Can't update Establishment without id: "+establishment.toString());
		}else {
			operations.save(establishment);
			logger.debug("Establishment updated: "+establishment.toString());
		}
		
	}

	public Establishment findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return operations.findOne(query, Establishment.class);
	}

	public Establishment findByName(String name) {
		Query query = new Query(Criteria.where("name").is(name));
		return operations.findOne(query, Establishment.class);
	}

	public List<Establishment> findByTown(Town town) {
		Query query = new Query(Criteria.where("town").is(town));
		return operations.find(query, Establishment.class);
	}

	public List<Establishment> findByPostalCode(String postalCode) {
		Query query = new Query(Criteria.where("postalCode").is(postalCode));
		return operations.find(query, Establishment.class);
	}
	/**
	 * This method is only for testing. DON NOT USE IN DEVELOPMENT
	 * @param operations
	 */
	public void setOperations(MongoOperations operations) {
		this.operations = operations;
	}

}
