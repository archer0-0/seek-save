package com.mike.arx.seekSave.seeker.qdq;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mike.arx.seekSave.daos.EstablishmentDAO;
import com.mike.arx.seekSave.daos.TownDAO;
import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.seeker.GenericSeeker;
import com.mike.arx.seekSave.seeker.SeekException;
import com.mike.arx.seekSave.seeker.exceptions.SeekerException;

public class SeekerMock implements GenericSeeker {

	@Override
	public Document obtainDocumentSite(String site) throws SeekException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> obtainURLs(Document documentOfQdqSite)
			throws SeekException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> obtainUrlListToObtainWebsToScrap(
			Document documentToOtainWebToScrap, String urlToObtainWebToScrap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Elements seekEstablishmentDocumentList(
			Document documentToObtainEstablishments) throws SeekException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveEstablishment(Element establishmentNode, TownDAO townDao,
			EstablishmentDAO establishmentDAO, Country country)
			throws SeekerException {
		// TODO Auto-generated method stub
		
	}

}
