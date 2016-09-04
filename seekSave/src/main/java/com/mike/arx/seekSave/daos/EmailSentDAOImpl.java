package com.mike.arx.seekSave.daos;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mike.arx.seekSave.daos.exceptions.DAOEstablishmentException;
import com.mike.arx.seekSave.model.EmailSent;
import com.mike.arx.seekSave.model.Establishment;

public class EmailSentDAOImpl implements EmailSentDAO {
	Logger logger= LoggerFactory.getLogger(EmailSentDAOImpl.class);
	@Autowired
	MongoOperations operations;
	public EmailSentDAOImpl(){}
	public EmailSentDAOImpl(MongoOperations mongoOperations){
		this.operations=mongoOperations;
	}
	@Override
	public void save(EmailSent emailSent) throws DAOEstablishmentException {
		if(emailSent.getId()!=null){
			throw new DAOEstablishmentException("Can't save Establishment with id: "+emailSent.toString());
		}else {
			operations.save(emailSent);
			logger.debug("Establishment saved: "+emailSent.toString());
		}

	}

	@Override
	public void update(EmailSent emailSent) throws DAOEstablishmentException {
		if(emailSent.getId()==null){
			throw new DAOEstablishmentException("Can't update Establishment without id: "+emailSent.toString());
		}else {
			operations.save(emailSent);
			logger.debug("Establishment updated: "+emailSent.toString());
		}
		
	}

	@Override
	public EmailSent findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return operations.findOne(query, EmailSent.class);
	}

	@Override
	public List<EmailSent> findEailsByEstablishment(Establishment establishment) {
		Query query = new Query(Criteria.where("town").is(establishment));
		return operations.find(query, EmailSent.class);
	}

	@Override
	public List<EmailSent> findByResponse(boolean repsonse) {
		Query query = new Query(Criteria.where("response").is(repsonse));
		return operations.find(query, EmailSent.class);
	}

}
