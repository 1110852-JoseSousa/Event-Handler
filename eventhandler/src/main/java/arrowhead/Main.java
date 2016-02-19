/*
built using

mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-grizzly2 -DarchetypeGroupId=org.glassfish.jersey.archetypes -DinteractiveMode=false -DgroupId=arrowhead -DartifactId=eventhandler -Dpackage=arrowhead -DarchetypeVersion=2.21

*/


package arrowhead;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

//import com.wordnik.swagger.jaxrs.config.*;
import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
	
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/eventhandler/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in arrowhead package
        final ResourceConfig rc = new ResourceConfig().packages("arrowhead");

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	}

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    
    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
    	
        final HttpServer server = startServer();

        System.out.println(String.format("Grizzly app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
        
    }
}

