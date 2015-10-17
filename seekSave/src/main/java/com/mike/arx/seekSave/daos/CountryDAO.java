package com.mike.arx.seekSave.daos;

import com.mike.arx.seekSave.daos.exceptions.DAOCountryException;
import com.mike.arx.seekSave.model.Country;
/**
 * This class defines methods to use {@link Country} in DB
 * @author marquero
 *
 */
public interface CountryDAO {
	public void save(Country country) throws DAOCountryException;
	public void update(Country country) throws DAOCountryException;
	public Country findById(String id);
	public Country findByName(String name);

}
