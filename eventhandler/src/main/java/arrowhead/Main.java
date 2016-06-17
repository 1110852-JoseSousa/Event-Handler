package arrowhead;

import DL.DB;
import org.glassfish.jersey.server.ResourceConfig;
import java.io.IOException;
import java.net.ConnectException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import se.bnearit.arrowhead.common.core.service.discovery.exception.ServiceRegisterException;
import eu.EventHandlerProvider;
import javax.ws.rs.core.Response;

/**
 * Main class.
 *
 */
public class Main {

    public static String EndpointPrefix = "/eventhandler";
    public static int port = 8080;
    final static ResourceConfig config = new ResourceConfig().packages("arrowhead");

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

        EventHandlerSystem.openConnection();

        /*try {
            BneartIT.connectACS();
            BneartIT.publishRegistry();
            BneartIT.publishPublishEvents();
            BneartIT.publishHistoricals();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }*/
        /* Jetty Server */
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        /* Hungary Service Registry*/
        EventHandlerProvider ep = new EventHandlerProvider();

        Response response;

        response = ep.invokeRegister();
        System.out.println(response.getStatus());

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.setContextPath(EndpointPrefix);
        context.addServlet(servlet, "/*");
        System.out.println(server.getURI());
        try {
            server.start();
            System.out.println("\n--- PRESS ENTER TO STOP THE APPLICATION AND REMOVE ALL SERVICES ---\n");
            System.in.read();
            server.stop();

        } finally {
           /* BneartIT.disconnectACS();
            if (EventHandlerSystem.isConnected()) {
                EventHandlerSystem.closeConnection();
            }
            server.destroy();*/
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
