package arrowhead;

import arrowhead.generated.EventType;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import javax.ws.rs.Consumes;

//import javax.ws.rs.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import arrowhead.generated.Events;

@Path("publish")
public class PublishEvents {

    // Receives events from Producers and for each event filters the Subscribers and "sends" its information
    @POST
    @Path("/{uid}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response publishEvents(EventType event) {

        EventHandlerSystem ehs = EventHandlerSystem.getInstance();

        ehs.addEvent(event);

        ehs.notifyEvent(event);
        
        ehs.flushEvents();

        return Response.status(200).entity("Events Posted!").build();

    }

}
