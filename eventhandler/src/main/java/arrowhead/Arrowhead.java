/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrowhead;

import it.unibo.arrowhead.controller.ArrowheadController;
import it.unibo.arrowhead.controller.ArrowheadSystem;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.bnearit.arrowhead.common.core.service.discovery.exception.ServiceRegisterException;
import se.bnearit.arrowhead.system.service.AppServiceProducer;

/**
 *
 * @author Cister
 */
public class Arrowhead {

    public static int port = 8080;
    public static ArrowheadSystem arrowheadSystem;
    public static ArrowheadController arrowheadController;

    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Arrowhead.class);

    public static void connectACS() {
        arrowheadController = new ArrowheadController("eventhandler");

        try {
            arrowheadSystem = new ArrowheadSystem();
            System.out.println("Connection to Arrowhead Core Service succeeded with params:"
                    + "\n\tIP address: " + System.getProperty("dns.server")
                    + "\n\tDomain: " + System.getProperty("dnssd.domain")
                    + "\n\tHostname: " + System.getProperty("dnssd.hostname")
                    + "\n");
            logger.debug("Sucessfully Connected to the ACS");
        } catch (NoClassDefFoundError | NullPointerException e) {
            System.out.println("No Arrowhead Core Service found with params:"
                    + "\n\tIP address: " + System.getProperty("dns.server")
                    + "\n\tDomain: " + System.getProperty("dnssd.domain")
                    + "\n\tHostname: " + System.getProperty("dnssd.hostname")
                    + "\nExiting...");
            logger.debug(e.getMessage());
        }

    }

    public static void publishRegistry() {

        try {
            String EndpointPrefix = "/registry";
            AppServiceProducer publisher;
            publisher = arrowheadSystem.createPublisher(
                    "eh_registry1",
                    "_eh_registry-ws-http._tcp",
                    "8080|" + EndpointPrefix,
                    null);
            publisher.publish();
            logger.debug("Sucessfully published registry service");
        } catch (ServiceRegisterException ex) {
            logger.debug(ex.getMessage());
        }

    }

    public static void publishPublishEvents() {
        try {
            String EndpointPrefix = "/publish";
            AppServiceProducer publisher;

            publisher = arrowheadSystem.createPublisher(
                    "eh_publish1",
                    "_eh_publish-ws-http._tcp",
                    "8080|" + EndpointPrefix,
                    null);
            publisher.publish();
            logger.debug("Sucessfully published publisher service");
        } catch (ServiceRegisterException ex) {
            logger.debug(ex.getMessage());
        }

    }

    public static void publishHistoricals() {

        try {
            String EndpointPrefix = "/historicals";

            AppServiceProducer publisher = arrowheadSystem.createPublisher(
                    "eh_historicals1",
                    "_eh_historicals-ws-http._tcp",
                    "8080|" + EndpointPrefix,
                    null);
            publisher.publish();
            logger.debug("Sucessfully published historicals service");
        } catch (ServiceRegisterException ex) {
            logger.debug(ex.getMessage());
        }

    }

}
