package com.mike.arx.seekSave.seeker.qdq;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mike.arx.seekSave.daos.CountryDAO;
import com.mike.arx.seekSave.daos.CountryDAOImpl;
import com.mike.arx.seekSave.daos.CountryNames;
import com.mike.arx.seekSave.daos.EstablishmentDAO;
import com.mike.arx.seekSave.daos.EstablishmentDAOImpl;
import com.mike.arx.seekSave.daos.TownDAO;
import com.mike.arx.seekSave.daos.TownDAOImpl;
import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.seeker.GenericSeeker;
import com.mike.arx.seekSave.seeker.SeekException;
import com.mike.arx.seekSave.seeker.exceptions.SeekerException;

public class QdqDirector {
	Logger logger= LoggerFactory.getLogger(QdqDirector.class);
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
		Country country=countryDAO.findByName(CountryNames.SPAIN.getCountryName());
		if(country==null){
			country= new Country(CountryNames.SPAIN.getCountryName(), 34);
		}
		GenericSeeker qdqSeeker= new QdqSeeker();
		try {
			documentOfQdqSite=qdqSeeker.obtainDocumentSite(starterSite);
			List<String> intermediateUrls=qdqSeeker.obtainURLs(documentOfQdqSite);
			for (String string : intermediateUrls) {
				Document documentToOtainWebToScrap=qdqSeeker.obtainDocumentSite(string);
				List<String> urlsofEstablishmentsWebList=qdqSeeker.obtainUrlListToObtainWebsToScrap(documentToOtainWebToScrap, string);
				for (String string2 : urlsofEstablishmentsWebList) {
					Document document2=qdqSeeker.obtainDocumentSite(string2);
					Elements establishmentElementsList=qdqSeeker.seekEstablishmentDocumentList(document2);
					for (Element element : establishmentElementsList) {
						try {
							qdqSeeker.saveEstablishment(element, townDAO, establishmentDAO, country);
						} catch (SeekerException e) {
							logger.debug("Establishment not saved", e);
						}
					}
				}
			}
		} catch (SeekException e) {
			logger.debug(e.getMessage(),e);
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
