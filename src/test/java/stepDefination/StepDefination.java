package stepDefination;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.APIResources;
import resources.APIUtils;

public class StepDefination {
	
	public  RequestSpecification rs;
	public static Response res;
	public String token;
	public int contractId;
	
	@Given("add request payload for {string}")   // for all 3 api given statement
	public void add_request_payload_for(String resources) throws IOException {
		
		APIResources res1= APIResources.valueOf(resources);
		if (res1.getResources().equalsIgnoreCase("GetAuthToken")) {
		rs= APIUtils.tokenRequestPayload();
		}
		else if (res1.getResources().equalsIgnoreCase("SaveHeaderInformation")) {
			rs=APIUtils.postHeaderRequest().header("Authorization", "Bearer " + token);
		}
		else if (res1.getResources().equalsIgnoreCase("GetContractHeaderDetails"))
		{
			rs=APIUtils.getPayloadbody().header("Authorization", "Bearer " + token);
		}
		
	}
	@When("post {string} http API request") // for all 3 api given statement
	public void post_http_api_request(String resources) {
		APIResources res1= APIResources.valueOf(resources);
		//res = rs.when().post(res1.getResources());
		
	    if (res1.getResources().equalsIgnoreCase("GetAuthToken")) {
			res = rs.when().post(res1.getResources()); // here only store all response into Response object
		} else if (res1.getResources().equalsIgnoreCase("SaveHeaderInformation")) {
			res = rs.when().post(res1.getResources());
		} 
	}
	
	@Then("take {string} from responce payload for {string}")
	public void take_from_responce_payload(String key, String resources ) {
		APIResources res1= APIResources.valueOf(resources);
		 res = res.then().extract().response();
		    String bodyres = res.asString();
		    JsonPath json = new JsonPath(bodyres);
		    
		if (res1.getResources().equalsIgnoreCase("GetAuthToken")) { 
	    token = json.getString("data." +key);                    //here key = tokenvalue
		}
		else if (res1.getResources().equalsIgnoreCase("SaveHeaderInformation")) {
			contractId = json.getInt(key);                     // here key = data     
		}
		
		 
	}
	@Then("check response status code is {string}")
	public void check_response_status_code_is(String statusCodeStr) {
		int expectedStatusCode = Integer.parseInt(statusCodeStr);
		res.then().assertThat().statusCode(expectedStatusCode);
	}
	
	
	@When("Get {string} http API request")              //GetContractHeaderDetails API 
	public void get_http_api_request(String resources) {
		  APIResources res1= APIResources.valueOf(resources);
			res = rs.when().get(res1.getResources());
	}
	
	@Then("compare data from responce payload is contain same keys and values of post data")
	public void compare_from_responce_payload_is_contain_same_keys_and_values_of_post_data() {
	    
		APIUtils.assertionResponse();
	
	}}
