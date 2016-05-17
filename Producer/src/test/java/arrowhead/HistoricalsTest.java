/**
 * 
 */
package arrowhead;

import arrowhead.Arrowhead;
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

import arrowhead.generated.FilterType;
import arrowhead.generated.Meta;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author Cister
 *
 */
public class HistoricalsTest {

	/**
	 * @throws java.lang.Exception
	 */
	
	private Response response;
	private static WebTarget target;

	private FilterType filter = new FilterType();
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
                Arrowhead.connectACS();
		Client c = ClientBuilder.newClient();
		target = c.target(Arrowhead.getEventHandlerURL());	
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		javax.xml.datatype.DatatypeFactory dtFactory = null;
		try {
			dtFactory = javax.xml.datatype.DatatypeFactory.newInstance();
		} catch (javax.xml.datatype.DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
		Meta m = new arrowhead.generated.Meta();
		m.setSeverity(1);
		
		this.filter = new FilterType();
		this.filter.setDescription(m);
		this.filter.setStartDateTime(dtFactory.newXMLGregorianCalendar(2015, 8, 1, 12, 0, 30, 125, 0));
		this.filter.setEndDateTime(dtFactory.newXMLGregorianCalendar(2015, 9, 1, 12, 0, 30, 125, 0));
		this.filter.setType("temperature");
		this.filter.setFrom("porto-sensor-1");
		
		
		
	}

        
	@Test
	public void testHistoricals() throws Exception {
			response =  this.target.path("historicals").request(MediaType.TEXT_XML).post(Entity.entity(this.filter,MediaType.APPLICATION_XML));
                        assertThat(response.getStatus(), anyOf( is(200), is(201)));
	}
	
        /**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
            Arrowhead.disconnectACS();
	}
	
}
