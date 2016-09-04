package com.mike.arx.seekSave.daos;

import java.util.List;

import com.mike.arx.seekSave.daos.exceptions.DAOEstablishmentException;
import com.mike.arx.seekSave.model.EmailSent;
import com.mike.arx.seekSave.model.Establishment;

public interface EmailSentDAO {
	public void save(EmailSent emailSent) throws DAOEstablishmentException;
	public void update(EmailSent emailSent) throws DAOEstablishmentException;
	public EmailSent findById(String id);
	public List<EmailSent> findEailsByEstablishment(Establishment establishment);
	public List<EmailSent>findByResponse(boolean repsonse);

}
