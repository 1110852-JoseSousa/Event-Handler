package arrowhead;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

import arrowhead.generated.Events;
import arrowhead.generated.FilterType;
import arrowhead.generated.LogData;
//import arrowhead.generated.filter.FilterType;

@Path("historicals")
public class Historicals {

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public LogData getHistoricalData(FilterType filter) {
        EventHandlerSystem ehs = EventHandlerSystem.getInstance();
        LogData data = ehs.GetHistoricalData(filter);
        return data;
    }

}
