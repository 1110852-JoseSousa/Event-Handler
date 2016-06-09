/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrowhead;

import static arrowhead.BneartIT.arrowheadSystem;
import java.net.MalformedURLException;
import java.net.URL;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Cister
 */
public class Hungary {

    private static String URI = "localhost:8080/core/serviceregistry";

    private static Client c = ClientBuilder.newClient();

    private static WebTarget target = c.target(URI);

    public static void publishPublishEvents() {
    }

    public static void publishHistoricals() {

    }

    public static void eraseServiceRegistry() {
    }

    public static void eraseServicePublishEvents() {
    }

    public static void eraseServiceHistoricals() {

    }

}
