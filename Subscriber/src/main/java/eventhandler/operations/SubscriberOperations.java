package eventhandler.operations;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import arrowhead.generated.*;

public class SubscriberOperations {

	
	
	public static FilterType createFilter(int severity, String type, String from){
		
                FilterType f = new FilterType();
                javax.xml.datatype.DatatypeFactory dtFactory = null;
		try {
			dtFactory = javax.xml.datatype.DatatypeFactory.newInstance();
		} catch (javax.xml.datatype.DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
		Meta newm = new arrowhead.generated.Meta();
		newm.setSeverity(severity);
		
		f.setDescription(newm);
		f.setStartDateTime(dtFactory.newXMLGregorianCalendar(2015, 8, 1, 12, 0, 30, 125, 0));
		f.setEndDateTime(dtFactory.newXMLGregorianCalendar(2015, 9, 1, 12, 0, 30, 125, 0));
		f.setType(type);
		f.setFrom(from);
                
                return f;
	}
		
	public static ConsumerType createSubscriber(String uid, String name, FilterType f)
	{
		ConsumerType subscriber = new ConsumerType();
		
		try {
			subscriber.setIP(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
                
		subscriber.setUid(uid);
		subscriber.setName(name);
		subscriber.setFilter(f);
			
		return subscriber;
		
	}
	
	public static Response registerSubscriber(WebTarget eventHandler, ConsumerType c){
		return eventHandler.path("registry").path(c.getUid()).request(MediaType.APPLICATION_XML).put(Entity.entity(c, MediaType.APPLICATION_XML));
	}
	/*
	public static String setURI(ConsumerType c) {
		
			if ( c.getUid() == null){
				System.out.println("No uid defined");
                                return null;
			}
			else
                            return "http://localhost:8081" + "/" + c.getUid();
	}
        */
}
