package eventhandler.arrowhead;

import eventhandler.hungary.ArrowheadService;
import eventhandler.hungary.ArrowheadSystem;
import eventhandler.hungary.ServiceMetadata;
import eventhandler.hungary.ServiceRegistryEntry;
import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("eventhandler")
@Produces(MediaType.TEXT_PLAIN)
public class Hungary extends ArrowheadService {

	
	/////////////////////////////////////////////
	//// CONFIG /////////////////////////////////
	/////////////////////////////////////////////
	private String own_IP = "localhost";
	private String registry_IP ="arrowhead.tmit.bme.hu";
	private String serviceGroup = "eventhandler";
	private String serviceDefinition = "eventhandler";
	/////////////////////////////////////////////
	
	/**
	 * Own ArrowheadSystem info.
	 */
	private ArrowheadSystem arrowheadSystem = new ArrowheadSystem("PROBA", "eventhandler", own_IP, "8081",
			"eventhandlercert");

	/**
	 * Constructor setting initial parameters of superclass.
	 */
	public Hungary() {
		super();
		ArrayList<String> interfaces = new ArrayList<String>();
                ArrayList<ServiceMetadata> metadata = new ArrayList<ServiceMetadata> ();
		this.setServiceGroup(serviceGroup);
		this.setServiceDefinition(serviceDefinition);
		this.setMetaData(metadata);
		interfaces.add("RESTJSON");
		this.setInterfaces(interfaces);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Hello, I am the Eventhandler.";
	}

	/**
	 * This function invokes the provider to register itself to the Service
	 * Registry.
	 * 
	 * @return String
	 */
	@GET
	@Path("/registry")
	public Response invokeRegistry() {
		ServiceRegistryEntry serviceRegistryEntry = new ServiceRegistryEntry();

		// Preparing ServiceRegistryEntry
		serviceRegistryEntry.setProvider(arrowheadSystem);
		serviceRegistryEntry.setServiceURI("/eventhandler");
		serviceRegistryEntry.setServiceMetadata(this.getMetaData());
		//serviceRegistryEntry.settSIG_key("RIuxP+vb5GjLXJo686NvKQ=="); // .168
		 serviceRegistryEntry.settSIG_key("RM/jKKEPYB83peT0DQnYGg=="); // .237

		/*
		 * if (registerProvider(serviceRegistryEntry) == 200) { return
		 * "Provider successfully registered to Service Registry."; }
		 */

		return registerEventhandler(serviceRegistryEntry);
	}
        /*
        @GET
	@Path("/publishservice")
	public Response invokePublishServiceRegistry() {
		ServiceRegistryEntry serviceRegistryEntry = new ServiceRegistryEntry();

		// Preparing ServiceRegistryEntry
		serviceRegistryEntry.setProvider(arrowheadSystem);
		serviceRegistryEntry.setServiceURI("/eventhandler/publish");
		serviceRegistryEntry.setServiceMetadata(this.getMetaData());
		//serviceRegistryEntry.settSIG_key("RIuxP+vb5GjLXJo686NvKQ=="); // .168
		 serviceRegistryEntry.settSIG_key("RM/jKKEPYB83peT0DQnYGg=="); // .237

		/*
		 * if (registerProvider(serviceRegistryEntry) == 200) { return
		 * "Provider successfully registered to Service Registry."; }
		 

		return registerEventhandler(serviceRegistryEntry);
	}
        
        @GET
	@Path("/historicalsservice")
	public Response invokeHistoricalsServiceRegistry() {
		ServiceRegistryEntry serviceRegistryEntry = new ServiceRegistryEntry();

		// Preparing ServiceRegistryEntry
		serviceRegistryEntry.setProvider(arrowheadSystem);
		serviceRegistryEntry.setServiceURI("/eventhandler/historicals");
		serviceRegistryEntry.setServiceMetadata(this.getMetaData());
		//serviceRegistryEntry.settSIG_key("RIuxP+vb5GjLXJo686NvKQ=="); // .168
		 serviceRegistryEntry.settSIG_key("RM/jKKEPYB83peT0DQnYGg=="); // .237

		/*
		 * if (registerProvider(serviceRegistryEntry) == 200) { return
		 * "Provider successfully registered to Service Registry."; }
		 

		return registerEventhandler(serviceRegistryEntry);
	}
        */
	/**
	 * This function returns the current temperature data.
	 * 
	 * @return String
	 */
	
	/**
	 * This function handles the necessary communication through REST to
	 * register the TemperatureProvider to the Service Registry.
	 * 
	 * @return String Temperature data.
	 */
	public Response registerEventhandler(ServiceRegistryEntry serviceRegistryEntry) {
		Client client = ClientBuilder.newClient();
		URI uri = UriBuilder.fromPath("http://" + registry_IP + ":" + "8080").path("core").path("serviceregistry")
				.path(this.getServiceGroup()).path(this.getServiceDefinition()).path("RESTJSON").build();
		WebTarget target = client.target(uri);
		System.out.println(target.getUri().toString());
		Response response = target.request().header("Content-type", "application/json")
				.post(Entity.json(serviceRegistryEntry));
		return response;
	}

}