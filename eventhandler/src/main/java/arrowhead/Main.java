package arrowhead;

import DL.DB;
import org.glassfish.jersey.server.ResourceConfig;
import java.io.IOException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import se.bnearit.arrowhead.common.core.service.discovery.exception.ServiceRegisterException;

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
     */
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws IOException, ServiceRegisterException, Exception {
        
        EventHandlerSystem.getDataBase().openConnection();
        Arrowhead.connectACS();
        Arrowhead.publishRegistry();
        Arrowhead.publishPublishEvents();
        Arrowhead.publishHistoricals();

        /* Jetty Server */
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.setContextPath(EndpointPrefix);
        context.addServlet(servlet, "/*");

        try {
            server.start();
            System.out.println("\n--- PRESS ENTER TO STOP THE APPLICATION AND REMOVE ALL SERVICES ---\n");
            System.in.read();
            server.stop();
            
        } finally {
            Arrowhead.eraseServiceHistoricals();
            Arrowhead.eraseServiceRegistry();
            Arrowhead.eraseServicePublishEvents();
            Arrowhead.disconnectACS();
            EventHandlerSystem.getDataBase().closeConnection();
            server.destroy();
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
