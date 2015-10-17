package com.mike.arx.seekSave.seeker.qdq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mike.arx.seekSave.daos.CountryDAOImpl;
import com.mike.arx.seekSave.daos.CountryNames;
import com.mike.arx.seekSave.daos.EstablishmentDAOImpl;
import com.mike.arx.seekSave.daos.TownDAOImpl;
import com.mike.arx.seekSave.daos.exceptions.DAOCountryException;
import com.mike.arx.seekSave.daos.exceptions.DAOException;
import com.mike.arx.seekSave.daos.exceptions.DAOTownException;
import com.mike.arx.seekSave.model.Country;
import com.mike.arx.seekSave.model.Establishment;
import com.mike.arx.seekSave.model.Town;
import com.mike.arx.seekSave.seeker.SeekException;
import com.mike.arx.seekSave.seeker.exceptions.SeekerException;
import com.mongodb.MongoClient;

public class QdqSeekerTest {
	public static String DB_NAME = "saqueadorTest";
	public static final String uriLocalHtmlEstablishmentsList = "C:\\Users\\marquero\\qdq\\listaSitios\\listaSitios.html";
	public static final String uriLocalHtmlMain = "C:\\Users\\marquero\\qdq\\restauracion\\restauracion.html";
	private static MongoClient mongoClient = null;
	private static MongoOperations operations = null;
	private static CountryDAOImpl countryDAO = null;
	private static TownDAOImpl townDao = null;
	private static EstablishmentDAOImpl establishmentDao = null;
	private static QdqSeeker qdqSeeker = null;
	public static final Country country = new Country(
			CountryNames.SPAIN.getCountryName());

	@BeforeClass
	public static void startUp() {
		try {
			mongoClient = new MongoClient("127.0.0.1", 27017);
			operations = new MongoTemplate(mongoClient, DB_NAME);
			countryDAO = new CountryDAOImpl();
			countryDAO.setOperations(operations);
			townDao = new TownDAOImpl();
			townDao.setOperations(operations);
			establishmentDao = new EstablishmentDAOImpl();
			establishmentDao.setOperations(operations);
			qdqSeeker = new QdqSeeker();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		operations.dropCollection(Country.class);
		operations.dropCollection(Town.class);
		operations.dropCollection(Establishment.class);

	}

	@Test
	public void obtainUrlsRerturnListFilledIfDocummentExistsAndContainsCorrectTags() {
		Document documentOfQdqSite = null;
		File file = new File(uriLocalHtmlMain);
		try {
			List<String> expectedList = new ArrayList<String>();
			expectedList.add("http://es.qdq.com/arrocerias/");
			expectedList.add("http://es.qdq.com/asadores/");
			expectedList.add("http://es.qdq.com/cocina+internacional/");
			expectedList.add("http://es.qdq.com/cocina+italiana/");
			documentOfQdqSite = Jsoup.parse(file, "UTF-8");
			List<String> listreturned = qdqSeeker.obtainURLs(documentOfQdqSite);
			assertEquals(expectedList, listreturned);
		} catch (IOException | SeekException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void exceptionIfObtainUrlsReceivesADocumentWithoutCorrectTags() {
		Document documentOfQdqSite = null;
		File file = new File(uriLocalHtmlMain);
		try {
			documentOfQdqSite = Jsoup.parse(file, "UTF-8");
			documentOfQdqSite.select("div").remove();
			qdqSeeker.obtainURLs(documentOfQdqSite);
		} catch (IOException | SeekException e) {
			assertTrue(e instanceof SeekException);
		}
	}

	@Test
	public void ifObtainUrlListToObtainWebsToScrapReceiveADocumentThatHasLessThan1000SitesGenerates1Url() {
		Document documentOfEstablishmentSites = null;
		File file = new File(uriLocalHtmlEstablishmentsList);
		try {
			List<String> list = new ArrayList<String>();
			documentOfEstablishmentSites = Jsoup.parse(file, "UTF-8");
			Elements elements = documentOfEstablishmentSites.select(
					"div.paginacion").select("a.marcado");
			list = qdqSeeker.obtainUrlListToObtainWebsToScrap(
					documentOfEstablishmentSites,
					uriLocalHtmlEstablishmentsList);
			assertEquals(list.size(), 1);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void ifObtainUrlListToObtainWebsToScrapReceiveADocumentThatHasMoreThan1000SitesGenerates2Url() {
		Document documentOfEstablishmentSites = null;
		File file = new File(uriLocalHtmlEstablishmentsList);
		try {
			List<String> list = new ArrayList<String>();
			documentOfEstablishmentSites = Jsoup.parse(file, "UTF-8");
			Elements elements = documentOfEstablishmentSites.select(
					"div.paginacion").select("a.marcado");
			elements.get(0).text("1 - 15 de 1005");
			list = qdqSeeker.obtainUrlListToObtainWebsToScrap(
					documentOfEstablishmentSites,
					uriLocalHtmlEstablishmentsList);
			assertEquals(list.size(), 2);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void obtainUrlListToObtainsWebsToScrapProducesACorrectUrl() {
		Document documentOfEstablishmentSites = null;
		File file = new File(uriLocalHtmlEstablishmentsList);
		try {
			List<String> list = new ArrayList<String>();
			documentOfEstablishmentSites = Jsoup.parse(file, "UTF-8");
			Elements elements = documentOfEstablishmentSites.select(
					"div.paginacion").select("a.marcado");
			list = qdqSeeker.obtainUrlListToObtainWebsToScrap(
					documentOfEstablishmentSites,
					uriLocalHtmlEstablishmentsList);
			assertEquals(list.get(list.size() - 1),
					uriLocalHtmlEstablishmentsList + "pag-0/rows-368/s:/");
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void obtainUrlListToObtainsWebstoScrapProducesCorectUrls() {
		Document documentOfEstablishmentSites = null;
		File file = new File(uriLocalHtmlEstablishmentsList);
		try {
			List<String> list = new ArrayList<String>();
			List<String> lisExpected = new ArrayList<String>();
			lisExpected.add(uriLocalHtmlEstablishmentsList
					+ "pag-0/rows-900/s:/");
			lisExpected.add(uriLocalHtmlEstablishmentsList
					+ "pag-900/rows-1005/s:/");
			documentOfEstablishmentSites = Jsoup.parse(file, "UTF-8");
			Elements elements = documentOfEstablishmentSites.select(
					"div.paginacion").select("a.marcado");
			elements.get(0).text("1 - 15 de 1005");
			list = qdqSeeker.obtainUrlListToObtainWebsToScrap(
					documentOfEstablishmentSites,
					uriLocalHtmlEstablishmentsList);
			assertEquals(lisExpected, list);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void ThereIsPreoviousTownSaveEstablishmentUsesIt() {
		try {
			countryDAO.save(country);
			Town town = new Town();
			town.setName("Valencia");
			town.setCountry(country);

			townDao.save(town);
			Document documentOfEstablishmentSites = null;
			File file = new File(uriLocalHtmlEstablishmentsList);
			documentOfEstablishmentSites = Jsoup.parse(file, "UTF-8");
			List<Element> establishmentElements = documentOfEstablishmentSites
					.select("#listadoResultados").select("li.estirar");
			establishmentElements.get(0);
			qdqSeeker.saveEstablishment(establishmentElements.get(0), townDao,
					establishmentDao, country);
			Establishment establishmentExpected = establishmentDao
					.findByName("Arrocería Maribel");
			assertEquals(establishmentExpected.getTown().getId(), town.getId());
		} catch (IOException | DAOTownException | DAOCountryException
				| SeekerException e) {
			fail(e.getMessage());
		}
	}

	@After
	public void afterTestthereIsPreoviousTownSaveEstablishmentUsesIt() {
		operations.dropCollection(Town.class);
		operations.dropCollection(Establishment.class);
	}

	@Test
	public void ThereisntPreviousTwonSavedCreatedNewTown() {
		Document documentOfEstablishmentSites = null;
		File file = new File(uriLocalHtmlEstablishmentsList);
		try {
			documentOfEstablishmentSites = Jsoup.parse(file, "UTF-8");
			List<Element> establishmentElements = documentOfEstablishmentSites
					.select("#listadoResultados").select("li.estirar");
			establishmentElements.get(0);
			qdqSeeker.saveEstablishment(establishmentElements.get(0), townDao,
					establishmentDao, country);
			assertNotNull(townDao.findByName("Valencia"));
		} catch (IOException | SeekerException e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void notFoundsomeValuesSavedAnyway(){
		Document documentOfEstablishmentSites = null;
		File file = new File(uriLocalHtmlEstablishmentsList);
		try {
			documentOfEstablishmentSites = Jsoup.parse(file, "UTF-8");
		List<Element> establishmentElements = documentOfEstablishmentSites
				.select("#listadoResultados").select("li.estirar");
		Element element=establishmentElements.get(0);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

}
