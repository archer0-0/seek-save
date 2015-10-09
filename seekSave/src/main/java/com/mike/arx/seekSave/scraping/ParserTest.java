package com.mike.arx.seekSave.scraping;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserTest {
	private static Logger logger=LoggerFactory.getLogger(ParserTest.class);
	private static String URLPARSER="http://es.qdq.com/arrocerias/";
	public static void parse(){
		try {
			
			 Document document= Jsoup.connect(URLPARSER).get();
			 logger.info("Document jsoup: "+document.toString());
		} catch (MalformedURLException e) {
			logger.debug(e.getMessage(),e);
		} catch (IOException e) {
			logger.debug(e.getMessage(),e);
		}
		
	}

}
