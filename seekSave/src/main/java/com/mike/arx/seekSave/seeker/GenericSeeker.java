package com.mike.arx.seekSave.seeker;

import java.util.List;

import org.jsoup.nodes.Document;

import com.mike.arx.seekSave.daos.EstablishmentDAO;
import com.mike.arx.seekSave.daos.TownDAO;
import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.model.Establishment;

public interface GenericSeeker {
	/**
	 * This method obtains {@link Document} from {@link String} of URL
	 * @return
	 * @throws SeekException 
	 */
	public abstract Document obtainDocumentSite(String site) throws SeekException;
	/**
	 * This method obtains a list of Url Sites intermediate to obtain the webs to scrap establishments
	 * @param documentOfQdqSite
	 * @return
	 * @throws SeekException 
	 */
	public abstract List<String> obtainURLs(Document documentOfQdqSite) throws SeekException;
	/**
	 * This method obtains a list of web where there are establishments information
	 * @param documentToOtainWebToScrap
	 * @param urlToObtainWebToScrap
	 * @return
	 */
	public abstract List<String> obtainUrlListToObtainWebsToScrap(Document documentToOtainWebToScrap,String urlToObtainWebToScrap);

	/**
	 *Thios method extracts the info of {@link Establishment}s from {@link Document}, and save them using {@link EstablishmentDAO} and {@link TownDAO} 
	 * @param documentToObtainEstablishments
	 * @param dao
	 * @param establishmentDAO
	 */
	public abstract void seeker(Document documentToObtainEstablishments, TownDAO dao,
			EstablishmentDAO establishmentDAO,Country country);

}
