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
import com.mike.arx.seekSave.daos.exceptions.DAOEstablishmentException;
import com.mike.arx.seekSave.daos.exceptions.DAOException;
import com.mike.arx.seekSave.daos.exceptions.DAOTownException;
import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.model.Establishment;
import com.mike.arx.seekSave.model.Town;
import com.mike.arx.seekSave.seeker.GenericSeeker;
import com.mike.arx.seekSave.seeker.SeekException;
import com.mike.arx.seekSave.seeker.exceptions.SeekerException;

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
					+ 900 + "/s:/");
			logger.debug("Add " + urlToObtainWebToScrap + "pag-0/rows-"
					+ maxAmount + "/s:/" + "to Url to seek");
			min = min + 900;
			max = max + 900;
		}
		urlsToSeekList.add(urlToObtainWebToScrap + "pag-" + min + "/rows-"
				+ maxAmount + "/s:/");
		logger.debug("Add " + urlToObtainWebToScrap + "pag-0/rows-" + maxAmount
				+ "/s:/" + "to Url to seek");

		return urlsToSeekList;

	}

	@Override
	public Elements seekEstablishmentDocumentList(
			Document documentToObtainEstablishments) {
		return documentToObtainEstablishments.select("#listadoResultados")
				.select("li.estirar");
	}

	@Override
	public void saveEstablishment(Element establishmentNode, TownDAO townDao,
			EstablishmentDAO establishmentDAO, Country country)
			throws SeekerException {
		try {
			String townName =establishmentNode.select("span").select("[itemprop=locality]").text();
			Town townSaved = townDao.findByName(townName);
			if (townSaved == null) {
				townSaved = new Town();
				townSaved.setName(townName);
				townSaved.setCountry(country);
				townDao.save(townSaved);
			}
			Establishment establishment = new Establishment();
			establishment.setName(establishmentNode.select("[itemprop=name]")
					.text());
			establishment.setAddress(establishmentNode.select(
					"[itemprop=street-address]").text());
			establishment.setPostalCode(establishmentNode.select(
					"[itemprop=postal-code]").text());
			establishment.setTown(townSaved);
			establishment.setWebPage(establishmentNode.select(
					"[itemprop=url]").attr("href"));
			establishment.setPhone(establishmentNode.select("[itemprop=tel]").text());
			establishmentDAO.save(establishment);
			logger.debug("Seeked Establishment Saved!: "
					+ establishment.toString());

		} catch (DAOEstablishmentException e) {
			throw new SeekerException(
					"I can't save establisment. It must be in DB already", e);

		} catch (DAOTownException e) {
			throw new SeekerException(
					"Can't save Town. If I have not Town y can't save Establishment",
					e);
		}

	}
}
