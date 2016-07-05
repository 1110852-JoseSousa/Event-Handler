/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventhandler.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 *
 * @author Cister
 */
public class Server {

    private final String EndpointPrefix = "/eventhandler";
    private final int port = 8080;
    private final ResourceConfig config = new ResourceConfig().packages("eventhandler.services");
    private org.eclipse.jetty.server.Server server = null;

    public Server() {
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));
        server = new org.eclipse.jetty.server.Server(port);
        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.setContextPath(EndpointPrefix);
        context.addServlet(servlet, "/");
    }

    public void start() {
        try {
            server.start();
            System.out.println(server.getURI());
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
