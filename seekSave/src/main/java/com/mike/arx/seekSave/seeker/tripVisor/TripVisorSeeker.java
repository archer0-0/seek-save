package com.mike.arx.seekSave.seeker.tripVisor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mike.arx.seekSave.daos.EstablishmentDAO;
import com.mike.arx.seekSave.daos.TownDAO;
import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.seeker.GenericSeeker;
import com.mike.arx.seekSave.seeker.SeekException;
import com.mike.arx.seekSave.seeker.exceptions.SeekerException;

public class TripVisorSeeker implements GenericSeeker {
	private static Logger logger = LoggerFactory.getLogger(TripVisorSeeker.class);
	String urlRoot = "https://www.tripadvisor.es";

	@Override
	public Document obtainDocumentSite(String site) throws SeekException {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new SeekException(e.getMessage());
		}
		Document documentOfTripVisor = null;
		try {
			documentOfTripVisor = Jsoup.connect(site).get();
			logger.debug("Obtain document from Url: " + site);
			if (documentOfTripVisor == null) {
				throw new SeekException("Can't obtain Document from the site:" + site);
			}
		} catch (IOException e) {
			throw new SeekException("Attemp to acces to Url : " + site + " fails\n" + e.getMessage(), e);
		}
		return documentOfTripVisor;
	}

	@Override
	public List<String> obtainURLs(Document documentOfTripVisor) throws SeekException {
		List<String> geneList = new ArrayList<>();
		try {
			Thread.sleep(1000);
			Element lastHref = documentOfTripVisor.select("div.pageNumbers").select("a").last();
			String lastUri = lastHref.attr("href");
			String[] uriTroceada = lastUri.split("oa");
			String[] cifra = uriTroceada[1].split("-");
			int cifraintegral = Integer.valueOf(cifra[0]);
			List<String> urlsDePaginas = new ArrayList<>();
			int i = 0;
			while (i <= cifraintegral) {
				String urlRecontruida = uriTroceada[0] + "oa" + i + cifra[1];
				logger.debug("urlRecontruida: " + urlRecontruida);
				urlsDePaginas.add(urlRecontruida);
				i = i + 20;
			}
			for (String string : urlsDePaginas) {
				Document document = Jsoup.connect(urlRoot + string).get();
				Elements elements = document.select("div.geo_name").select("a");
				logger.debug("elements: " + elements.toString());
				for (Element element : elements) {
					geneList.add(urlRoot+element.attr("href"));
				}
			}
		} catch (InterruptedException | IOException e) {
			throw new SeekException(e.getMessage());
		}
		return geneList;
	}

	@Override
	public List<String> obtainUrlListToObtainWebsToScrap(Document documentToOtainWebToScrap,
			String urlToObtainWebToScrap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Elements seekEstablishmentDocumentList(Document documentToObtainEstablishments) throws SeekException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveEstablishment(Element establishmentNode, TownDAO townDao, EstablishmentDAO establishmentDAO,
			Country country) throws SeekerException {
		// TODO Auto-generated method stub

	}

}
