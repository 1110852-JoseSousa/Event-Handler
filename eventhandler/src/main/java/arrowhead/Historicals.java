package arrowhead;

import arrowhead.generated.EventType;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import arrowhead.generated.FilterType;
import arrowhead.generated.LogData;
import java.util.ArrayList;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("historicals")
public class Historicals {

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Response getHistoricalData(FilterType filter) {
        
        EventHandlerSystem ehs = EventHandlerSystem.getInstance();
        ArrayList<EventType> events = ehs.GetHistoricalDataDB(filter);
        
        return Response.status(200).build();
        
    }

}
