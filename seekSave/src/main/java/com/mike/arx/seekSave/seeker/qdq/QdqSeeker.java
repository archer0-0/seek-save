package com.mike.arx.seekSave.seeker.qdq;

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
import com.mike.arx.seekSave.daos.exceptions.DAOException;
import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.model.Establishment;
import com.mike.arx.seekSave.model.Town;
import com.mike.arx.seekSave.seeker.GenericSeeker;
import com.mike.arx.seekSave.seeker.SeekException;

public class QdqSeeker implements GenericSeeker {
	private static Logger logger = LoggerFactory.getLogger(QdqSeeker.class);

	@Override
	public Document obtainDocumentSite(String site) throws SeekException {
		Document documentOfQdqSite = null;
		try {
			documentOfQdqSite = Jsoup.connect(site).get();
			logger.debug("Obtain document from Url: " + site);
		} catch (IOException e) {
			throw new SeekException("Attemp to acces to Url : " + site
					+ " fails\n" + e.getMessage(), e);
		}
		return documentOfQdqSite;
	}

	@Override
	public List<String> obtainURLs(Document documentOfQdqSite)
			throws SeekException {
		List<String> geneList = new ArrayList<String>();
		Elements hrefs = documentOfQdqSite.select("div.dosCol").select("a");
		for (Element element : hrefs) {
			geneList.add(element.attr("href"));
		}
		logger.debug("Obtained list of intermediate site: "
				+ geneList.toString());
		if (geneList.isEmpty()) {
			throw new SeekException("There aren't any Intermediate URLs");
		}
		return geneList;
	}

	@Override
	public List<String> obtainUrlListToObtainWebsToScrap(
			Document documentToOtainWebToScrap, String urlToObtainWebToScrap) {
		List<String> urlsToSeekList = new ArrayList<String>();
		String as = documentToOtainWebToScrap.select("a").select(".marcado")
				.get(0).text();
		logger.debug("Max amount(" + as + " of : " + urlToObtainWebToScrap);
		int maxAmount = Integer.parseInt(as.split(" de ")[1]);
		int max = 1000;
		int min = 0;

		while (max < maxAmount) {
			urlsToSeekList.add(urlToObtainWebToScrap + "pag-" + min + "/rows-"
					+ 1000 + "/s:/");
			logger.debug("Add " + urlToObtainWebToScrap + "pag-0/rows-"
					+ maxAmount + "/s:/" + "to Url to seek");
			min = min + 1000;
			max = max + 1000;
		}
		urlsToSeekList.add(urlToObtainWebToScrap + "pag-" + min + "/rows-"
				+ maxAmount + "/s:/");
		logger.debug("Add " + urlToObtainWebToScrap + "pag-0/rows-" + maxAmount
				+ "/s:/" + "to Url to seek");

		return urlsToSeekList;

	}

	@Override
	public void seeker(Document documentToObtainEstablishments, TownDAO dao,
			EstablishmentDAO establishmentDAO, Country country) {
		Elements establisElements = documentToObtainEstablishments.select(
				"#listadoResultados").select("li.estirar");
		for (int i = 0; i > establisElements.size(); i++) {
			Element element = establisElements.get(i);
			String townName = element.select("h2")
					.select("itemprop=\"locality\"").text();
			Town townSaved = null;
			townSaved = new Town();
			townSaved.setName(townName);
			townSaved.setCountry(country);
			try {
				dao.save(townSaved);

			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				Establishment establishment = new Establishment();
				establishment.setName(element.select("itemprop=\"name\"")
						.text());
				establishment.setAddress(element.select(
						"itemprop=\"street-address\"").text());
				establishment.setPostalCode(element.select(
						"itemprop=\"postal-code\"").text());
				establishment.setTown(townSaved);
				establishment.setWebPage(element.select("itemprop=\"url\"").attr("href"));
				try {
					establishmentDAO.save(establishment);
				} catch (DAOException e) {
					logger.debug("Problemas guardado el Establishment!");
				}

			}

			/**
			 * TODO me he quedado en la creacion del Establishment
			 */

		}
	}

}
