package eventhandler.operations;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import arrowhead.generated.*;

public class SubscriberOperations {

	
	private FilterType filter;
	private WebTarget target;
	private String UID;
	private String name;
	private String description;
	private Events events;
	// private String my_ip;
	
	public SubscriberOperations(){}
	
	public SubscriberOperations(FilterType f, WebTarget t, String uid, String n, String d){
		
	}
	
	public void flushEvents(){ this.events.getEvent().clear(); }
	
	public void setDescription(String d){
		
		this.description = d;
	}
	
	public String getDescription(){
		
		return this.description;
	}
	
	public void setName(String name){
		
		this.name = name;
	}
	
	public String getName(){
		
		return this.name;
	}
	
	public void setUID(String uid){
	
		this.UID = uid;
	}
	
	public String getUID(){
		
		return this.UID;
	}
	
	public void setTarget(String BASE_URI){
		
		Client c = ClientBuilder.newClient();
		target = c.target(BASE_URI);
	}
	
	public WebTarget getTarget(){
		
		return this.target;
	}
	
	public void setFilter(int severity, String description, String type, String from){
		javax.xml.datatype.DatatypeFactory dtFactory = null;
		try {
			dtFactory = javax.xml.datatype.DatatypeFactory.newInstance();
		} catch (javax.xml.datatype.DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
		Meta newm = new arrowhead.generated.Meta();
		newm.setSeverity(severity);
		
		this.filter = new FilterType();
		this.filter.setDescription(newm);
		this.filter.setStartDateTime(dtFactory.newXMLGregorianCalendar(2015, 8, 1, 12, 0, 30, 125, 0));
		this.filter.setEndDateTime(dtFactory.newXMLGregorianCalendar(2015, 9, 1, 12, 0, 30, 125, 0));
		this.filter.setType(type);
		this.filter.setFrom(from);
	}
	
	public FilterType getFilter(){
		
		return this.filter;
	}
	
	private Registered createSubscriber()
	{
		Registered r = new Registered();
		ConsumerType subscriber = new ConsumerType();
		
		try {
			subscriber.setIP(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		subscriber.setUid(this.getUID());
		subscriber.setName(this.getName());
		subscriber.setFilter(this.getFilter());
		
		r.getConsumer().add(subscriber);
		
		return r;
		
	}
	
	public Response registerSubscriber(WebTarget eventHandler){
		return eventHandler.path("registry").path(this.getUID()).request(MediaType.APPLICATION_XML).post(Entity.entity(createSubscriber(), MediaType.APPLICATION_XML));
	}

	public Events getEvents() { return this.events; }
	
	public String setURI() {
		
			if ( this.getUID() == null){
				System.out.println("No uid defined");
				System.exit(-1);
			}
			
			// To use ip addressing
			//return "http://0.0.0.0:8081" +  "/" + this.getUID();
			
			// For testing locally
			return "http://localhost:8081" + "/" + this.getUID();
	}

	public void setEvents(Events event) {
		flushEvents();
		for ( EventType e : event.getEvent()) {
			this.events.getEvent().add(e);
		}
	}


}
