/**
built using

mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-grizzly2 -DarchetypeGroupId=org.glassfish.jersey.archetypes -DinteractiveMode=false -DgroupId=arrowhead -DartifactId=client1 -Dpackage=arrowhead -DarchetypeVersion=2.21

security:
https://jersey.java.net/documentation/latest/user-guide.html capitolo 5.9
*/

package arrowhead;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import arrowhead.generated.Registered;
import eventhandler.operations.*;

import java.io.IOException;
import java.net.ConnectException;


/**
 * Main class.
 *
 */
public class Main {
	
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/eventhandler/";

    private static ProducerOperations producerOp = new ProducerOperations();

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
    	producerOp.setName("Sensor 1");
    	producerOp.setType("temperature");
    	producerOp.setUID("porto-sensor-1");
    	
    	
    	Response response;
		Registered r;
		producerOp.setTarget(BASE_URI);
		
		response = producerOp.registerProducer(producerOp.getTarget());
		
		System.out.println(response.readEntity(String.class));
		
		
		for (int i = 0; i < 1000; i++) {
			producerOp.addEvent(1, "Hello " + i);
			response = producerOp.publishEvents(producerOp.getEvents(), producerOp.getTarget());
			producerOp.getEvents().getEvent().clear();
		}
		
		
		System.out.println(response.readEntity(String.class));
		
		}
	
}

