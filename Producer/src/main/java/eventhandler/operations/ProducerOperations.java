package eventhandler.operations;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import arrowhead.generated.*;
import eu.ArrowheadService;
import eu.ServiceMetadata;
import eu.ServiceQueryForm;
import eu.ServiceQueryResult;
import eu.ServiceRequestForm;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.UriBuilder;

public class ProducerOperations {

    private static String hungary_registry_IP = "arrowhead.tmit.bme.hu";

    public static EventType createEvent(int severity, String from, String type, String payload) {

        Meta m = new Meta();
        m.setSeverity(severity);

        EventType event = new EventType();
        event.setPayload(payload);
        event.setFrom(from);
        event.setDescription(m);
        event.setType(type);

        return event;
    }

    public static ProducerType createProducer(String uid, String type, String name) {

        ProducerType p = new ProducerType();

        p.setUid(uid);
        p.setType(type);
        p.setName(name);

        return p;

    }

    public static Response registerProducer(WebTarget target, ProducerType producer) {
        return target.path("registry").path(producer.getUid()).request(MediaType.APPLICATION_XML).post(Entity.entity(producer, MediaType.APPLICATION_XML));
    }

    public static Response publishEvent(WebTarget target, String producer_uid, EventType e) {
        return target.path("publish").path(producer_uid).request(MediaType.APPLICATION_XML).post(Entity.entity(e, MediaType.APPLICATION_XML));
    }

    public Response getHistoricalData(WebTarget target, FilterType filter) {
        return target.path("historicals").request(MediaType.TEXT_XML).post(Entity.entity(filter, MediaType.APPLICATION_XML));
    }

    public static String getEventHandlerUri() throws IOException {

        //ServiceQueryResult sqr = 
        Client client = ClientBuilder.newClient();
        List<String> listInt = new ArrayList<String>();
        listInt.add("RESTJSON");

        ServiceQueryForm sqf = new ServiceQueryForm();
        sqf.setPingProviders(false);
        sqf.setServiceInterfaces(listInt);
        sqf.setMetadataSearch(false);
        sqf.setServiceMetadata(new ArrayList<ServiceMetadata>());
        sqf.setTsig_key("RM/jKKEPYB83peT0DQnYGg==");

        URI uri = UriBuilder.fromPath("http://" + hungary_registry_IP + ":" + "8080").path("core").path("serviceregistry").path("eventhandler").path("eventhandler").build();
        WebTarget target = client.target(uri);
        System.out.println(target.getUri().toString());
        Response response = target.request().header("Content-type", "application/json")
                .put(Entity.json(sqf));

        ServiceQueryResult sq = response.readEntity(ServiceQueryResult.class);
        
        return "http://" + sq.getServiceQueryData().get(0).getProvider().getIPAddress() + ":" + sq.getServiceQueryData().get(0).getProvider().getPort() + "/eventhandler";
    }
    /*
     public Registered queryAllRegistered(){
     return (Registered) target.path("queryAll").request(MediaType.APPLICATION_XML).get();
     }*/
}
