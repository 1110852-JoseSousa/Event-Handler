package arrowhead;

import arrowhead.generated.Events;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import arrowhead.generated.FilterType;
import java.io.StringWriter;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Path("historicals")
public class Historicals {

    @POST
    @Produces(MediaType.TEXT_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Response getHistoricalData(FilterType filter) throws JAXBException {

        EventHandlerSystem ehs = EventHandlerSystem.getInstance();
        Events e = ehs.GetHistoricalDataDB(filter);

        if (e.getEvent().size() > 0) {
            final Marshaller m = JAXBContext.newInstance(Events.class)
                    .createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            final StringWriter w = new StringWriter();
            m.marshal(e, w);
            return Response.status(200).type(MediaType.TEXT_XML).entity(w.toString()).build();
        } else {
            return Response.status(201).type(MediaType.TEXT_XML).entity("No Match").build();
        }

    }

}
