package com.mike.arx.seekSave.seeker;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mike.arx.seekSave.daos.EstablishmentDAO;
import com.mike.arx.seekSave.daos.TownDAO;
import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.model.Establishment;
import com.mike.arx.seekSave.model.Town;
import com.mike.arx.seekSave.seeker.exceptions.SeekerException;

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
	 *Thios method extracts the nodes that contain the info of {@link Establishment}s from {@link Document}, and returns a list of them. 
	 * @param documentToObtainEstablishments
	 * @param dao
	 * @param establishmentDAO
	 */
	public abstract Elements seekEstablishmentDocumentList(Document documentToObtainEstablishments)throws SeekException;
	/**
	 * This method extracts he information to create {@link Establishment} and saves it on DB
	 * @param establishmentNode
	 * @param townDao dao of {@link Town}
	 * @param establishmentDAO dao of {@link Establishment}
	 * @param country of these {@link Establishment}s
	 * @throws SeekException 
	 * @throws SeekerException 
	 */
	public abstract void saveEstablishment(Element establishmentNode,TownDAO townDao,EstablishmentDAO establishmentDAO,Country country) throws  SeekerException;

}
