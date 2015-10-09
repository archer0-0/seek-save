package com.mike.arx.seekSave.seeker.qdq;

import java.util.List;

import org.jsoup.nodes.Document;

import com.mike.arx.seekSave.daos.CountryDAO;
import com.mike.arx.seekSave.daos.CountryDAOImpl;
import com.mike.arx.seekSave.daos.EstablishmentDAO;
import com.mike.arx.seekSave.daos.EstablishmentDAOImpl;
import com.mike.arx.seekSave.daos.TownDAO;
import com.mike.arx.seekSave.daos.TownDAOImpl;
import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.seeker.GenericSeeker;

public class QdqDirector {
	private static final String starterSite = "http://es.qdq.com/empresas-restauracion/";
	private Document documentOfQdqSite = null;
	private TownDAO townDAO=null;
	private EstablishmentDAO establishmentDAO= null;
	private CountryDAO countryDAO= null;
	public QdqDirector(){
		townDAO= new TownDAOImpl();
		establishmentDAO= new EstablishmentDAOImpl();
		countryDAO= new CountryDAOImpl();
		
	}
	public void seek(){
		Country country= new Country();
		GenericSeeker qdqSeeker= new QdqSeeker();
		documentOfQdqSite=qdqSeeker.obtainDocumentSite(starterSite);
		List<String> intermediateUrls=qdqSeeker.obtainURLs(documentOfQdqSite);
		for (String string : intermediateUrls) {
			Document documentToOtainWebToScrap=qdqSeeker.obtainDocumentSite(string);
			List<String> urlsofEstablishmentsWebList=qdqSeeker.obtainUrlListToObtainWebsToScrap(documentToOtainWebToScrap, string);
			for (String string2 : urlsofEstablishmentsWebList) {
				Document document2=qdqSeeker.obtainDocumentSite(string2);
				qdqSeeker.seeker(document2, townDAO, establishmentDAO, country);
			}
		}
	}
	public void setEstablishmentDAO(EstablishmentDAO establishmentDAO) {
		this.establishmentDAO = establishmentDAO;
	}
	public void setTownDAO(TownDAO townDAO) {
		this.townDAO = townDAO;
	}
	public void setCountryDAO(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}

}
