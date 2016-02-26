/**
built using

mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-grizzly2 -DarchetypeGroupId=org.glassfish.jersey.archetypes -DinteractiveMode=false -DgroupId=arrowhead -DartifactId=client1 -Dpackage=arrowhead -DarchetypeVersion=2.21

security:
https://jersey.java.net/documentation/latest/user-guide.html capitolo 5.9
*/

package arrowhead;


import eventhandler.operations.SubscriberOperations;

import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import arrowhead.generated.EventType;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main implements EventOperations {
    // Base URI the Grizzly HTTP server will listen on
	
	
    public static final String HANDLER_URI = "http://localhost:8080/eventhandler/";

    public static final SubscriberOperations subOp = new SubscriberOperations();
        
	private static String SUB_URI;
	
        public static HttpServer startServer(String u) {
    	
        final ResourceConfig rc = new ResourceConfig().packages("arrowhead");

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(u), rc);
	}
    
	public static void main(String[] args) throws IOException {
    	
        Arrowhead.connectACS();
            
    	subOp.setTarget(HANDLER_URI);
    	subOp.setUID("Subscriber1");
    	SUB_URI = subOp.setURI();
    	System.out.println(SUB_URI);
    	final HttpServer server = startServer(SUB_URI);
    	// To get the response from the EventHandler Services
    	Response response;
    	
    	
    	subOp.setFilter(1 ,"temperature", "porto-sensor-1");
			
		// Registers this subscriber to the eventhandler
		response = subOp.registerSubscriber(subOp.getTarget()); System.out.println(response.readEntity(String.class));
			
		System.out.println(String.format("Jersey app started with WADL available at "
                + "%s/application.wadl\nHit enter to stop it...", SUB_URI));
		
		
		
	    System.in.read();
	    
	    server.stop();
		
		}

    @Override
    public void handleEvents(EventType event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
}

