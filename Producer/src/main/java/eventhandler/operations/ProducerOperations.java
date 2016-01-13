package eventhandler.operations;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import arrowhead.generated.*;

public class ProducerOperations {

		
	
	
	/*
	 * Criar constantes de class;;
	 * Severity, type, ver quais.
	 * */
	
	private WebTarget target;
	private String UID;
	private String type;
	private String name;
	
	Events events;
	
	public ProducerOperations(){
		
		events = new Events();
	}
	
	
	public Events getEvents(){
		return this.events;
	}
	
	public void addEvent(int severity, String payload){
		
		Meta m = new Meta();
		m.setSeverity(severity);
		
		EventType event = new EventType();
		
		event.setPayload(payload);
		event.setFrom(this.getUID());
    	event.setDescription(m);
    	event.setType(this.getType());
    	
    	this.events.getEvent().add(event);
	}
	
	public void setTarget(String BASE_URI){
		
		Client c = ClientBuilder.newClient();
		target = c.target(BASE_URI);
	}
	
	public WebTarget getTarget(){
		
		return this.target;
	}
	
	public void setType(String t){
		
		this.type = t;
	}
	
	public String getType(){
		
		return this.type;
	}
	
	public void setName(String n){
		
		this.name = n;
	}
	
	public String getName(){
		
		return this.name;
	}
	
	public void setUID(String u){
	
		this.UID = u;
	}
	
	public String getUID(){
		
		return this.UID;
	}
	
	private Registered createProducer() {
		
		arrowhead.generated.ProducerType newp = new arrowhead.generated.ProducerType();
		newp.setUid(this.getUID());
		newp.setType(this.getType());
		newp.setName(this.getName());
		arrowhead.generated.Registered r1 = new arrowhead.generated.Registered();
		r1.getProducer().add(newp);
		return r1;
	
	}
	
	public Response registerProducer(WebTarget eventHandler){
		return eventHandler.path("registry").path(this.getUID()).request(MediaType.APPLICATION_XML).post(Entity.entity(createProducer(), MediaType.APPLICATION_XML));
	}
	
	public Response publishEvents(Events e, WebTarget target){
    	
    	return target.path("publish").path(this.getUID()).request(MediaType.APPLICATION_XML).post(Entity.entity(e , MediaType.APPLICATION_XML));
    	
    }
	/*
	public Registered queryAllRegistered(){
		return (Registered) target.path("queryAll").request(MediaType.APPLICATION_XML).get();
	}*/
	
}
