/**
 * EventHandlerOperations.java by José Sousa - CISTER/INESC-TEC, ISEP, Polytechnic
 * Institute of. Porto This work was supported by National Funds through FCT
 * (Portuguese Foundation for Science and Technology) and by the EU ARTEMIS JU
 * funding, within Arrowhead project, ref. ARTEMIS/0001/2012, JU grant nr. 332987
 *
 * 
 */

package eventhandler.operations;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import arrowhead.generated.EventType;

public class EventHandlerOperations {

	public EventHandlerOperations(){}
	
	public  WebTarget setTarget(String BASE_URI, String uid){
		Client c = ClientBuilder.newClient();
		WebTarget target;
		target = c.target(BASE_URI + "/" + uid);
		return target;
	}
	
	public Response notifySubscriber(EventType e, WebTarget target){
		
		return target.path("notify").request(MediaType.APPLICATION_XML).post(Entity.entity(e, MediaType.APPLICATION_XML));
				
	}

}
