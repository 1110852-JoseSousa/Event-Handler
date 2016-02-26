/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrowhead;

import it.unibo.arrowhead.controller.ArrowheadController;
import it.unibo.arrowhead.controller.ArrowheadSystem;
import java.net.MalformedURLException;

import java.net.URL;
import se.bnearit.arrowhead.common.core.service.discovery.exception.ServiceRegisterException;
import se.bnearit.arrowhead.common.service.ServiceIdentity;
import se.bnearit.arrowhead.system.service.AppServiceProducer;

/**
 *
 * @author Cister
 */
public class Arrowhead {

    public static int port = 8080;
    public static ArrowheadSystem arrowheadSystem;
    public static ArrowheadController arrowheadController;

    //final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Arrowhead.class);
    public static void disconnectACS(){
        arrowheadSystem.stop();
    }
    
    public static void connectACS() {
        arrowheadController = new ArrowheadController("eh_subscriber");

        try {
            arrowheadSystem = new ArrowheadSystem();
            System.out.println("Connection to Arrowhead Core Service succeeded with params:"
                    + "\n\tIP address: " + System.getProperty("dns.server")
                    + "\n\tDomain: " + System.getProperty("dnssd.domain")
                    + "\n\tHostname: " + System.getProperty("dnssd.hostname")
                    + "\n");
            //logger.debug("Sucessfully Connected to the ACS");
        } catch (NoClassDefFoundError | NullPointerException e) {
            System.out.println("No Arrowhead Core Service found with params:"
                    + "\n\tIP address: " + System.getProperty("dns.server")
                    + "\n\tDomain: " + System.getProperty("dnssd.domain")
                    + "\n\tHostname: " + System.getProperty("dnssd.hostname")
                    + "\nExiting...");
            //logger.debug(e.getMessage());
        }

    }

    public static String getEventHandlerURL() {
        ServiceIdentity service = arrowheadSystem.getServiceByName("eh_registry");
        URL endpoint = null;
        try {
            endpoint = arrowheadSystem.serviceGetCompleteUrlForResource(service, "");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return endpoint.toString().replace("/registry", "");
    }

}
