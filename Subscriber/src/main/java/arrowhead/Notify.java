package arrowhead;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import arrowhead.generated.EventType;

@Path("notify")
public class Notify {
		
    @POST
	@Consumes(MediaType.APPLICATION_XML)
    public Response notifyEvents(EventType event) {
    	System.out.println("Event Payload: " + event.getPayload());
		return Response.status(200).entity("events posted ").build();
		
	}


}

