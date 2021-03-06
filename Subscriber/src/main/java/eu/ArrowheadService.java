package eu;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ServiceMetadata;

@XmlRootElement
public class ArrowheadService {

	private String serviceGroup;
	private String serviceDefinition;
	private List<String> interfaces = new ArrayList<String>();
	private List<ServiceMetadata> metaData;
	
	public ArrowheadService(){
		
	}
	
	public ArrowheadService(String serviceGroup, String serviceDefinition,
			List<String> interfaces, List<ServiceMetadata> metaData) {
		super();
		this.serviceGroup = serviceGroup;
		this.serviceDefinition = serviceDefinition;
		this.interfaces = interfaces;
		this.metaData = metaData;
	}

	public String getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	public String getServiceDefinition() {
		return serviceDefinition;
	}

	public void setServiceDefinition(String serviceDefinition) {
		this.serviceDefinition = serviceDefinition;
	}

	public List<String> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<String> interfaces) {
		this.interfaces = interfaces;
	}

	public List<ServiceMetadata> getMetaData() {
		return metaData;
	}

	public void setMetaData(List<ServiceMetadata> metaData) {
		this.metaData = metaData;
	}
	
	
}
