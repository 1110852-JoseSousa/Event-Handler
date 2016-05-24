/**
 * 
 */
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

import arrowhead.generated.ProducerType;
import arrowhead.generated.Registered;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author Cister
 *
 */
public class ProducerRegistryTest {

	/**
	 * @throws java.lang.Exception
	 */
	
	Response response;
	ProducerType producer;
        
	private static WebTarget target;
	Registered r;
	
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
		
		r = new Registered();
		
		producer = new ProducerType();
		
		producer.setUid("ProducerTest");
		producer.setName("Test Sensor");
		producer.setType("Test");
		
		r.getProducer().add(producer);
		
	}

	
        // If returned HTTP code is 201 or 204 the Registry Service is Working properly
	@Test
	public void testRegistry() throws Exception {
			response = target.path("registry").path("ProducerTest").request
        (MediaType.APPLICATION_XML).post(Entity.entity(producer, MediaType.APPLICATION_XML));
			assertThat(response.getStatus(), anyOf( is(201), is(204)));
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
            Arrowhead.disconnectACS();
	}
	
}
