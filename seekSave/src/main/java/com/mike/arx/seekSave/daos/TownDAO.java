package com.mike.arx.seekSave.daos;

import java.util.List;

import com.mike.arx.seekSave.daos.exceptions.DAOException;
import com.mike.arx.seekSave.daos.exceptions.DAOTownException;
import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.model.Town;

public interface TownDAO {
	public void save(Town town) throws DAOTownException;
	public void update(Town town)throws DAOTownException;
	public Town findById(String id);
	public Town findByName(String name);
	public List<Town> findByCountry(Country country);
}
