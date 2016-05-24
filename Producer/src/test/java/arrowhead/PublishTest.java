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

import arrowhead.generated.EventType;
import arrowhead.generated.Meta;

/**
 * @author Cister
 *
 */
public class PublishTest {

    private static WebTarget target;
    Response response;
    EventType event = new EventType();

    /**
     * @throws java.lang.Exception
     */
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

        Meta meta = new Meta();
        meta.setSeverity(5);

        event.setDescription(meta);
        event.setPayload("TestPayload");
        event.setFrom("TestProducer");
        event.setType("Test");

    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        Arrowhead.disconnectACS();
    }

    @Test
    public void test() {
        response = target.path("publish").path("TestProducer").request(MediaType.APPLICATION_XML).post(Entity.entity(event, MediaType.APPLICATION_XML));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

}
