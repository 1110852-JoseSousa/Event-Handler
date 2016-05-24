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

/**
 * @author Cister
 *
 */
public class RegistryServiceTest {

    /**
     * @throws java.lang.Exception
     */
    Response response;

    private static WebTarget target;
    Registered r;

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

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        r = new Registered();

        ProducerType producer = new ProducerType();

        producer.setUid("ProducerTest");
        producer.setName("Test Sensor");
        producer.setType("Test");

        r.getProducer().add(producer);

    }

    @Test
    public void testRegistry() throws Exception {
        response = target.path("registry").path("ProducerTest").request(MediaType.APPLICATION_XML).post(Entity.entity(r, MediaType.APPLICATION_XML));
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

}
