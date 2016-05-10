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

    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Arrowhead.class);

    public static void disconnectACS() {
        arrowheadSystem.stop();
    }

    public static void connectACS() {
        arrowheadController = new ArrowheadController("eventhandler");

        try {
            arrowheadSystem = new ArrowheadSystem();
            System.out.println("Connection to Arrowhead Core Service succeeded with params:"
                    + "\n\tIP address: " + System.getProperty("dns.server")
                    + "\n\tDomain: " + System.getProperty("dnssd.domain")
                    + "\n\tHostname: " + System.getProperty("dnssd.hostname")
                    + "\n");
            //logger.warn("Sucessfully Connected to the ACS");
        } catch (NoClassDefFoundError | NullPointerException e) {
            System.out.println("No Arrowhead Core Service found with params:"
                    + "\n\tIP address: " + System.getProperty("dns.server")
                    + "\n\tDomain: " + System.getProperty("dnssd.domain")
                    + "\n\tHostname: " + System.getProperty("dnssd.hostname")
                    + "\nExiting...");
            //logger.warn(e.getMessage());
        }

    }

    public static void publishRegistry() {

        try {
            String EndpointPrefix = "/eventhandler/registry";
            AppServiceProducer publisher;
            publisher = arrowheadSystem.createPublisher(
                    "eh_registry",
                    "_eh_registry-ws-http._tcp",
                    "8080|" + EndpointPrefix,
                    null);
            publisher.publish();
            //logger.warn("Sucessfully published registry service");
        } catch (ServiceRegisterException ex) {
            //logger.warn(ex.getMessage());
        }

    }

    public static void publishPublishEvents() {
        try {
            String EndpointPrefix = "/eventhandler/publish";
            AppServiceProducer publisher;

            publisher = arrowheadSystem.createPublisher(
                    "eh_publish",
                    "_eh_publish-ws-http._tcp",
                    "8080|" + EndpointPrefix,
                    null);
            publisher.publish();
            //logger.warn("Sucessfully published publisher service");
        } catch (ServiceRegisterException ex) {
            //logger.warn(ex.getMessage());
        }

    }

    public static void publishHistoricals() {

        try {
            String EndpointPrefix = "/eventhandler/historicals";

            AppServiceProducer publisher = arrowheadSystem.createPublisher(
                    "eh_historicals",
                    "_eh_historicals-ws-http._tcp",
                    "8080|" + EndpointPrefix,
                    null);
            publisher.publish();
            //logger.warn("Sucessfully published historicals service");
        } catch (ServiceRegisterException ex) {
            //logger.warn(ex.getMessage());
        }

    }

    public static void eraseServiceRegistry() {
        ServiceIdentity service = arrowheadSystem.getServiceByName("eh_registry");
        arrowheadSystem.eraseService(service);
        //logger.warn("Erased registry service");
    }

    public static void eraseServicePublishEvents() {
        ServiceIdentity service = arrowheadSystem.getServiceByName("eh_publish");
        arrowheadSystem.eraseService(service);
        //logger.warn("Erased publish events service");
    }

    public static void eraseServiceHistoricals() {
        ServiceIdentity service = arrowheadSystem.getServiceByName("eh_historicals");
        arrowheadSystem.eraseService(service);
        //logger.warn("Erased historicals service");

    }

    public static String getSubscriberURL(String uid) {
        ServiceIdentity service = arrowheadSystem.getServiceByName("eh_notify_" + uid);
        URL endpoint = null;
        try {
            endpoint = arrowheadSystem.serviceGetCompleteUrlForResource(service, "");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return endpoint.toString();
    }
    
}
