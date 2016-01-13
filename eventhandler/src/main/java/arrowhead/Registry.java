/**
 * Registry.java by Michele Albano - CISTER/INESC-TEC, ISEP, Polytechnic
 * Institute of. Porto This work was supported by National Funds through FCT
 * (Portuguese Foundation for Science and Technology) and by the EU ARTEMIS JU
 * funding, within Arrowhead project, ref. ARTEMIS/0001/2012, JU grant nr. 332987
 *
 * 
 */
package arrowhead;

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
	@Produces(MediaType.APPLICATION_XML)
    public Registered queryAllRegistered() {
		EventHandlerSystem ehs = EventHandlerSystem.getInstance();
		return ehs.m_registered;
	}
    
    
    @GET
    @Path("query")
	@Produces(MediaType.TEXT_PLAIN)
    public Registered queryRegistered(
		@DefaultValue("false") @QueryParam("condition") boolean condition,
		@DefaultValue("") @QueryParam("name") String q_name,
		@DefaultValue("") @QueryParam("type") String q_type,
		@DefaultValue("") @QueryParam("from") String q_from
//		@Context UriInfo uri,
		) {
			EventHandlerSystem ehs = EventHandlerSystem.getInstance();
			Registered r = ehs.m_registered;
			
			if (condition)
				r = ehs.QueryProducer(q_name, q_type);
			else{
				r = ehs.QueryConsumer(q_name, q_type, q_from);
			}
			return r;
	}
	
    
    @POST
    @Path("/{uid}")
	@Consumes(MediaType.APPLICATION_XML)
    public Response registerEntity(@PathParam("uid") String uid, Registered r) {
    	
		EventHandlerSystem ehs = EventHandlerSystem.getInstance();

		if (ehs.GetProducer(uid) == null && ehs.GetConsumer(uid) == null) {
			if (ehs.ImportOne(uid, r))
				return Response.status(201).entity("created "+uid).build();
			else
				return Response.status(204).entity("problem with importing "+uid).build();
		}

		return Response.status(201).entity("already exists "+uid).build();
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

		if (ehs.DeleteProducer(uid)) return Response.status(200).entity("removed Producer "+uid).build();
		if (ehs.DeleteConsumer(uid)) return Response.status(200).entity("removed Consumer "+uid).build();
		
		return Response.status(200).entity("could not remove "+uid).build();
		
	}


}
