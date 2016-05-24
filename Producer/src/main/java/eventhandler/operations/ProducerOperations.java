package eventhandler.operations;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import arrowhead.generated.*;

public class ProducerOperations {

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

    /*
     public Registered queryAllRegistered(){
     return (Registered) target.path("queryAll").request(MediaType.APPLICATION_XML).get();
     }*/
}
