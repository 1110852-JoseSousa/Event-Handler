/**
 * built using
 *
 * mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-grizzly2
 * -DarchetypeGroupId=org.glassfish.jersey.archetypes -DinteractiveMode=false
 * -DgroupId=arrowhead -DartifactId=client1 -Dpackage=arrowhead
 * -DarchetypeVersion=2.21
 *
 * security: https://jersey.java.net/documentation/latest/user-guide.html
 * capitolo 5.9
 */
package arrowhead;

import arrowhead.generated.EventType;
import arrowhead.generated.Events;
import arrowhead.generated.ProducerType;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import arrowhead.generated.Registered;
import eventhandler.operations.*;

import java.io.IOException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import jdk.nashorn.internal.parser.JSONParser;

/**
 * Main class.
 *
 */
public class Main {

    // Base URI the Grizzly HTTP server will listen on
    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    private static final String uid = "porto-sensor-1";
    private static final String type = "temperature";
    private static final String name = "Sensor Bulding 1st Floor";

    private static WebTarget target;
    private static Client client;
    private static EventType event;

    public static void main(String[] args) throws IOException {

       // Arrowhead.connectACS();

        Response response;
        ProducerType producer;

        producer = ProducerOperations.createProducer(uid, type, name);

        client = ClientBuilder.newClient();
        target = client.target(ProducerOperations.getEventHandlerUri());

        System.out.println(ProducerOperations.getEventHandlerUri());
        response = ProducerOperations.registerProducer(target, producer);

        Arrowhead.publishEvent();

        System.out.println(response.readEntity(String.class));

        event = ProducerOperations.createEvent(1, "porto-sensor-1", "temperature", "");

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            event.setPayload("" + i);
            ProducerOperations.publishEvent(target, uid, event);
        }

        long endTime = System.currentTimeMillis();

        long timeneeded = ((endTime - startTime) / 1000);
        System.out.println(timeneeded);

        //System.out.println(response.readEntity(String.class));
        //Arrowhead.disconnectACS();
        /* Get Historicals example   */
 /*
        producerOp.setFilter(1, "temperature", "porto-sensor-1");
        response = producerOp.getHistoricalData();
        System.out.println(response.getStatus() + " " +response.readEntity(String.class));
   */      

    }

}
