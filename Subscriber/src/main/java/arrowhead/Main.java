package arrowhead;

import eventhandler.operations.SubscriberOperations;

import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import arrowhead.generated.EventType;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import se.bnearit.arrowhead.common.service.exception.ServiceNotStartedException;

/**
 * Main class.
 *
 */
public class Main implements EventOperations {
    // Base URI the Grizzly HTTP server will listen on

    public static final SubscriberOperations subOp = new SubscriberOperations();

    public static String UID = "Subscriber1";
    public static String EndpointPrefix = "/" + UID;
    public static int port = 8081;
    final static ResourceConfig config = new ResourceConfig().packages("arrowhead");

    public static void main(String[] args) throws IOException {

        Arrowhead.connectACS();
        Arrowhead.publishNotify();

        /* Jetty Server */
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.setContextPath(EndpointPrefix);
        context.addServlet(servlet, "/*");
       
        
        // To get the response from the EventHandler Services
        Response response;
        
         
        try {
            subOp.setTarget(Arrowhead.getEventHandlerURL());
        } catch (ServiceNotStartedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(subOp.getTarget());
        subOp.setUID(UID);
        subOp.setFilter(1, "temperature", "porto-sensor-1");
        response = subOp.registerSubscriber(subOp.getTarget());
        
        
        System.out.println(response.readEntity(String.class));
        
        try {
            server.start();
            System.out.println("\n--- PRESS ENTER TO STOP THE APPLICATION AND REMOVE ALL SERVICES ---\n");
            System.in.read();
            server.stop();
            
        } catch(Exception e){}
        finally {
            Arrowhead.eraseServiceNotify();
            Arrowhead.disconnectACS();
            server.destroy();
        }
       
    }

    @Override
    public void handleEvents(EventType event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
