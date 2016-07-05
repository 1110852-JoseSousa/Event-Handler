package eventhandler;

import eventhandler.datalayer.Database;
import org.glassfish.jersey.server.ResourceConfig;
import java.io.IOException;
import java.net.ConnectException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import se.bnearit.arrowhead.common.core.service.discovery.exception.ServiceRegisterException;
import eventhandler.hungary.EventHandlerProvider;
import eventhandler.services.EventHandlerSystem;
import eventhandler.services.EventHandlerSystem;
import javax.ws.rs.core.Response;

/**
 * Main class.
 *
 */
public class Main {

   
    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     * @throws
     * se.bnearit.arrowhead.common.core.service.discovery.exception.ServiceRegisterException
     */
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws IOException, ServiceRegisterException, Exception {

        //EventHandlerSystem.openConnection();
        /*try {
         BneartIT.connectACS();
         BneartIT.publishRegistry();
         BneartIT.publishPublishEvents();
         BneartIT.publishHistoricals();
         } catch (NullPointerException ex) {
         ex.printStackTrace();
         }*/
       
        /* Hungary Service Registry*/
        /*EventHandlerProvider ep = new EventHandlerProvider();
        Response response;

        response = ep.invokeRegister();
*/
        try{
            System.out.println("\n--- PRESS ENTER TO STOP THE APPLICATION AND REMOVE ALL SERVICES ---\n");
            System.in.read();
        } finally {
            //BneartIT.disconnectACS();
            if (EventHandlerSystem.isConnected()) {
                EventHandlerSystem.closeConnection();
            }
        }
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
     * application.
     *
     * @return Grizzly HTTP server.
     */
    /* Base URI the Grizzly HTTP server will listen on
     public static final String BASE_URI = "http://localhost:8080/eventhandler/"
     public static HttpServer startServer() {
     // create a resource config that scans for JAX-RS resources and providers
     // in arrowhead package
     final ResourceConfig rc = new ResourceConfig().packages("arrowhead");

     return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
     }*/
    /*final HttpServer server = startServer();
     System.out.println(String.format("Grizzly app started with WADL available at "
     + "%sapplication.wadl\n\n PRESS ENTER IN ORDER TO ERASE PUBLISHED SERVICES AND KILL PROCESS!", BASE_URI));
     System.in.read();
        
     server.stop(); */
}
