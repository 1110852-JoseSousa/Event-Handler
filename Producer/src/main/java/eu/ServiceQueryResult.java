package eu;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServiceQueryResult {
	
	private List<ProvidedService> provider = new ArrayList<ProvidedService>();

	public ServiceQueryResult() {
		super();
	}

	public ServiceQueryResult(List<ProvidedService> result) {
		super();
		this.provider = result;
	}

	public List<ProvidedService> getServiceQueryData() {
		return provider;
	}

	public void setServiceQueryData(List<ProvidedService> result) {
		this.provider = result;
	}

}
