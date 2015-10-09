package com.mike.arx.seekSave.seeker.qdq;

import java.io.File;
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
import com.mike.arx.seekSave.model.Town;
import com.mike.arx.seekSave.seeker.GenericSeeker;

public class QdqSeeker implements GenericSeeker {
	private static Logger logger = LoggerFactory.getLogger(QdqSeeker.class);


	@Override
	public List<String> obtainURLs(Document documentOfQdqSite) {
		List<String> geneList = new ArrayList<String>();
		Elements hrefs = documentOfQdqSite.select("div.dosCol").select("a");
		for (Element element : hrefs) {
			geneList.add(element.attr("href"));
			logger.info("href: " + element.attr("href"));
		}
		return geneList;
	}

	@Override
	public Document obtainDocumentSite(String site) {
		Document documentOfQdqSite= null;
		try {
		 documentOfQdqSite=Jsoup.connect(site).get();
		 } catch (IOException e) {
		 logger.debug(e.getMessage(),e);
		 }
		return documentOfQdqSite;
		

	}

	@Override
	public List<String> obtainUrlListToObtainWebsToScrap(Document documentToOtainWebToScrap,String urlToObtainWebToScrap ) {
			List<String> urlsToSeekList= new ArrayList<String>();
			String as = documentToOtainWebToScrap.select("a").select(".marcado").get(0).text();
			int maxAmount = Integer.parseInt(as.split(" de ")[1]);
			urlsToSeekList.add(urlToObtainWebToScrap+"pag-0/rows-"+maxAmount+"/s:/");
		return urlsToSeekList;

	}

	@Override
	public void seeker(Document documentToObtainEstablishments,TownDAO dao,EstablishmentDAO establishmentDAO,Country country) {
		Elements establisElements=documentToObtainEstablishments.select("#listadoResultados").select("li.destacado");
		for(int i=0;i>establisElements.size();i++){
			Town town=new Town();
			Element element=establisElements.get(i);
			town.setName(element.select("h2").select("itemprop=\"locality\"").text());
		}
		
		
	}
	
	public String obtainUrlOfEstablishmentTermination(int init, int finish){
		String response = null;
		if(finish-init>1000){
			response=obtainUrlOfEstablishmentTermination(init, finish/2);
		}
		return "pag-"+init+"/rows-"+finish+"/s:/";
	}




}
