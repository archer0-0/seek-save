package com.mike.arx.seekSave.daos;

import java.util.List;

import com.mike.arx.seekSave.daos.exceptions.DAOException;
import com.mike.arx.seekSave.model.Establishment;
import com.mike.arx.seekSave.model.Town;

public interface EstablishmentDAO {
	public void save(Establishment establishment) throws DAOException;
	public void update(Establishment establishment) throws DAOException;
	public Establishment findById(String id);
	public Establishment findByName(String name);
	public List<Establishment> findByTown(Town town);
	public List<Establishment> findByPostalCode(String postalCode);

}
