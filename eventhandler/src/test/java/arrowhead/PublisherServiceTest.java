package arrowhead;


import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import arrowhead.generated.EventType;
import arrowhead.generated.Events;
import arrowhead.generated.Meta;

/**
 * @author Cister
 *
 */
public class PublisherServiceTest {
	
	
	private static WebTarget target;
	Response response;
	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		Client c = ClientBuilder.newClient();
		target = c.target("http://localhost:8080/eventhandler");	
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private Events events;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		Meta m = new Meta();
		m.setSeverity(2);
		
		this.events = new Events();
		
		EventType event = new EventType();
		
		event.setPayload("TestPayload");
		event.setFrom("TestProducer");
    	event.setDescription(m);
    	event.setType("Test");
    	
    	this.events.getEvent().add(event);
    	
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
    	response = target.path("publish").path("TestProducer").request(MediaType.APPLICATION_XML).post(Entity.entity(events , MediaType.APPLICATION_XML));
    	assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

}
