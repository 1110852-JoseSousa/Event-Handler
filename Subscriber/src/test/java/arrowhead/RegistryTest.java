/**
 * 
 */
package arrowhead;

import static org.junit.Assert.*;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import arrowhead.generated.ConsumerType;
import arrowhead.generated.Events;
import arrowhead.generated.FilterType;
import arrowhead.generated.Meta;
import arrowhead.generated.Registered;

/**
 * @author Cister
 *
 */
public class RegistryTest {

	/**
	 * @throws java.lang.Exception
	 */
	
	private Response response;
	private static WebTarget target;

	private FilterType filter;
	private String UID;
	private Events events;
	final static ResourceConfig rc = new ResourceConfig().packages("arrowhead");
	private static HttpServer sub;
	
	Registered r;
	private String name;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		Client c = ClientBuilder.newClient();
		target = c.target("http://localhost:8080/eventhandler");	
		sub = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8081"), rc);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		sub.stop();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		Meta m = new Meta();
		m.setSeverity(5);
		
		this.UID = "sub";
		this.name = "TestSub";
		
		javax.xml.datatype.DatatypeFactory dtFactory = null;
		try {
			dtFactory = javax.xml.datatype.DatatypeFactory.newInstance();
		} catch (javax.xml.datatype.DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
		
		this.filter = new FilterType();
		this.filter.setStartDateTime(dtFactory.newXMLGregorianCalendar(2015, 8, 1, 12, 0, 30, 125, 0));
		this.filter.setEndDateTime(dtFactory.newXMLGregorianCalendar(2015, 9, 1, 12, 0, 30, 125, 0));
		filter = new FilterType();
		filter.setDescription(m);
		filter.setType("Test");
		
		r = new Registered();
		
		ConsumerType subscriber = new ConsumerType();
		
		subscriber.setUid(this.UID);
		subscriber.setName(this.name);
		subscriber.setFilter(this.filter);
		
		r.getConsumer().add(subscriber);
		
	}

	

	@Test
	public void testRegistry() throws Exception {
			System.out.println("Running Registry Test");
			response = target.path("registry").path(this.UID).request(MediaType.APPLICATION_XML).post(Entity.entity(r, MediaType.APPLICATION_XML));
			assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
	}
	

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
}
