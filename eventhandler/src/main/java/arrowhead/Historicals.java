package arrowhead;

import arrowhead.generated.Events;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import arrowhead.generated.FilterType;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("historicals")
public class Historicals {

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Events getHistoricalData(FilterType filter) {
        
        EventHandlerSystem ehs = EventHandlerSystem.getInstance();
        return ehs.GetHistoricalDataDB(filter);
        
    }

}
