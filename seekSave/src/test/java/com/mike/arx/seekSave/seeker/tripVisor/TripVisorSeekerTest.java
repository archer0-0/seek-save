package com.mike.arx.seekSave.seeker.tripVisor;

import java.util.List;

import org.jsoup.nodes.Document;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mike.arx.seekSave.seeker.SeekException;

public class TripVisorSeekerTest {
	private static Logger logger = LoggerFactory.getLogger(TripVisorSeekerTest.class);
	TripVisorSeeker tripvisorseeker= new  TripVisorSeeker();
	String urlSite="https://www.tripadvisor.es/Restaurants-g187485-Castile_La_Mancha.html#LOCATION_LIST";
	@Test
	public void probandoTripVisorSeeker(){
		try {
			Document document=tripvisorseeker.obtainDocumentSite(urlSite);
			List<String> urls= tripvisorseeker.obtainURLs(document);
			logger.debug("Size of the list: "+urls.size());
			for (String string : urls) {
				logger.debug("Site:"+string);
			}
			
		} catch (SeekException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
