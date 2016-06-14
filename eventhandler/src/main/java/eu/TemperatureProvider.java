package eu;

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


@Path("humidity")
@Produces(MediaType.TEXT_PLAIN)
public class TemperatureProvider extends ArrowheadService {

	
	/////////////////////////////////////////////
	//// CONFIG /////////////////////////////////
	/////////////////////////////////////////////
	private String own_IP = "localhost";
	private String registry_IP ="arrowhead.tmit.bme.hu";
	private String serviceGroup = "HumidityProba";
	private String serviceDefinition = "IndoorHumidityProba";
	/////////////////////////////////////////////
	
	/**
	 * Own ArrowheadSystem info.
	 */
	private ArrowheadSystem arrowheadSystem = new ArrowheadSystem("PROBA", "HumidityProvider", own_IP, "8080",
			"tempcert");

	/**
	 * Constructor setting initial parameters of superclass.
	 */
	public TemperatureProvider() {
		super();
		ArrayList<String> interfaces = new ArrayList<String>();
                ArrayList<ServiceMetadata> metadata = new ArrayList<ServiceMetadata>();
		this.setServiceGroup(serviceGroup);
		this.setServiceDefinition(serviceDefinition);
		this.setMetaData(metadata);
		interfaces.add("RESTJSON");
		this.setInterfaces(interfaces);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Hello, I am the TemepratureProvider.";
	}

	/**
	 * This function invokes the provider to register itself to the Service
	 * Registry.
	 * 
	 * @return String
	 */
	@GET
	@Path("/register")
	public Response invokeRegister() {
		ServiceRegistryEntry serviceRegistryEntry = new ServiceRegistryEntry();

		// Preparing ServiceRegistryEntry
		serviceRegistryEntry.setProvider(arrowheadSystem);
		serviceRegistryEntry.setServiceURI("/client/temperature/current");
		serviceRegistryEntry.setServiceMetadata(this.getMetaData());
		//serviceRegistryEntry.settSIG_key("RIuxP+vb5GjLXJo686NvKQ=="); // .168
		 serviceRegistryEntry.settSIG_key("RM/jKKEPYB83peT0DQnYGg=="); // .237

		/*
		 * if (registerProvider(serviceRegistryEntry) == 200) { return
		 * "Provider successfully registered to Service Registry."; }
		 */

		return registerProvider(serviceRegistryEntry);
	}

	/**
	 * This function returns the current temperature data.
	 * 
	 * @return String
	 */
	@GET
	@Path("/current")
	public String getCurrentTemperature() {
		return "21";
	}

	/**
	 * This function handles the necessary communication through REST to
	 * register the TemperatureProvider to the Service Registry.
	 * 
     * @param serviceRegistryEntry
	 * @return String Temperature data.
	 */
	public Response registerProvider(ServiceRegistryEntry serviceRegistryEntry) {
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
