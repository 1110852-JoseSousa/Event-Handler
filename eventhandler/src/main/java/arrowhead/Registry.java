/**
 * Registry.java by Michele Albano - CISTER/INESC-TEC, ISEP, Polytechnic
 * Institute of. Porto This work was supported by National Funds through FCT
 * (Portuguese Foundation for Science and Technology) and by the EU ARTEMIS JU
 * funding, within Arrowhead project, ref. ARTEMIS/0001/2012, JU grant nr.
 * 332987
 *
 *
 */
package arrowhead;

import arrowhead.EventHandlerSystem;
import arrowhead.generated.ConsumerType;
import arrowhead.generated.Events;
import arrowhead.generated.ProducerType;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
//import javax.ws.rs.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import arrowhead.generated.Registered;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Path("registry")
public class Registry {

    /*@GET
     @Path("/{uid}")
     @Produces(MediaType.APPLICATION_XML)
     public Registered retrieveDetails(@PathParam("uid") String uid) {
     EventHandlerSystem ehs = EventHandlerSystem.getInstance();
     Registered ret = null;
     if (null == ret) {
     ret = ehs.ResolveQueryProducer(uid);
     }
     if (null == ret) {
     ret = ehs.ResolveQueryConsumer(uid);
     }

     return ret;
     }
     */
    @GET
    @Path("queryAll")
    @Produces(MediaType.TEXT_XML)
    public Response queryAllRegistered() throws JAXBException {
        EventHandlerSystem ehs = EventHandlerSystem.getInstance();
        if( ehs.m_registered.getConsumer().size() > 0 || ehs.m_registered.getProducer().size() > 0){
        final Marshaller m = JAXBContext.newInstance(Registered.class)
                    .createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            final StringWriter w = new StringWriter();
            m.marshal(ehs.m_registered, w);
        return Response.status(200).type(MediaType.TEXT_XML).entity(w.toString()).build();
        }
        else
            return Response.status(201).type(MediaType.TEXT_XML).entity("No entity matches the specified criteria\n").build();
    }

    @GET
    @Path("query")
    @Produces(MediaType.TEXT_XML)
    public Response queryRegistered(
            @DefaultValue("false") @QueryParam("condition") boolean condition,
            @DefaultValue("") @QueryParam("name") String q_name,
            @DefaultValue("") @QueryParam("type") String q_type,
            @DefaultValue("") @QueryParam("from") String q_from
    ) throws JAXBException {
        
        EventHandlerSystem ehs = EventHandlerSystem.getInstance();
        Registered r = ehs.m_registered;
                
        // condition = true -> Event Producer | condition = false -> Event Consumer
        if (condition) {
            r = ehs.QueryProducer(q_name, q_type);
        } else {
            r = ehs.QueryConsumer(q_name, q_type, q_from);
        }
        
        if( r.getConsumer().size() > 0 || r.getProducer().size() > 0){
        final Marshaller m = JAXBContext.newInstance(Registered.class)
                    .createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            final StringWriter w = new StringWriter();
            m.marshal(r, w);
        return Response.status(200).type(MediaType.TEXT_XML).entity(w.toString()).build();
        }
        else
            return Response.status(201).type(MediaType.TEXT_XML).entity("No entity matches the specified criteria\n").build();
    }

    // In case the UID is not yet registed and there is a Producer which matches the Consumer filter response 200 is returned
    // In case the UID is not yet registed and there isnt a Producer which matches the Consumer filter response 201 is returned
    // In case the UID is already registed response 204 is returned
    @PUT
    @Path("/{uid}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response registerConsumer(@PathParam("uid") String uid, ConsumerType c) {

        EventHandlerSystem ehs = EventHandlerSystem.getInstance();

         if (ehs.GetConsumer(uid) == null) {
            ehs.ImportConsumer(uid, c);
            if(ehs.ExistsEventsForConsumer(c)){
                return Response.status(200).entity("Created subscriber with UID: " + uid).build();}
            else{
                return Response.status(201).entity("Created subscriber with UID: " + uid + "\nNo producers exist"
                        + " for the event type " + c.getFilter().getType()).build();
            }
        } else {
            return Response.status(204).entity("UID: " + uid + "already exists!!").build();
        }
    }

    // In case the UID is not yet registed a response 200 is returned
    // In case the UID is already registered a response 204 is returned
    @POST
    @Path("/{uid}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response registerProducer(@PathParam("uid") String uid, ProducerType p) {

        EventHandlerSystem ehs = EventHandlerSystem.getInstance();

        if (ehs.GetProducer(uid) == null) {
            ehs.ImportProducer(uid, p);
            return Response.status(201).entity("created " + uid).build();
        } else {
            return Response.status(204).entity("UID: " + uid + "already exists!!").build();
        }
    }
    
    /*@PUT
     @Path("/{uid}")
     @Consumes(MediaType.APPLICATION_XML)
     public Response modifySystem(@PathParam("uid") String uid, Registered r) {
     EventHandlerSystem ehs = EventHandlerSystem.getInstance();

     if (ehs.SubstituteProducer(uid, r)) return Response.status(200).entity("substituted Producer "+uid).build();
     if (ehs.SubstituteConsumer(uid, r)) return Response.status(200).entity("substituted Consumer "+uid).build();

     return Response.status(200).entity("could not substitute "+uid).build();
     }*/

    @DELETE
    @Path("/{uid}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response unRegisterEntity(@PathParam("uid") String uid) {

        EventHandlerSystem ehs = EventHandlerSystem.getInstance();

        if (ehs.DeleteProducer(uid)) {
            return Response.status(200).entity("removed Producer " + uid).build();
        }
        if (ehs.DeleteConsumer(uid)) {
            return Response.status(200).entity("removed Consumer " + uid).build();
        }

        return Response.status(204).entity("could not remove " + uid).build();

    }

}
