package resources;

public enum APIResources {
	GetAuthToken("/api/Token/GetAuthToken"),
	SaveHeaderInformation("/api/Contract/SaveContractHeaderDetails"),
	GetContractHeaderDetails("/api/Contract/GetContractHeaderDetails");
	
	
	private String resources;
	
	APIResources(String resources){
		this.resources= resources;
	}
	
	public String getResources() {
		return resources;
	}
}
