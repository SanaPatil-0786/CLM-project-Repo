package stepDefination;

import java.io.IOException;

import io.cucumber.java.en.*;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.APIResources;
import resources.APIUtils;
import resources.TestDataProvider;

public class StepDefination {

	public RequestSpecification rs;
	public static Response res;
	public static String token;
	public static int contractId;
	public static APIResources res1;

	@Given("add request payload for {string}") // for all 3 api given statement
	public void add_request_payload_for(String resources) throws IOException {

	 res1 = APIResources.valueOf(resources);
		
		if ("GetAuthToken".equalsIgnoreCase(res1.name().trim())) {
			rs = APIUtils.tokenRequestPayload();
			System.out.println("given method -tokenapi run if condition ");   //pass

		
		} else if ("SaveHeaderInformation".equalsIgnoreCase(res1.name())) {

			rs = APIUtils.postHeaderRequest().header("Authorization","Bearer "+token);
			System.out.println("get token to saveHeader API given elseif condition : " + token);  //pass
			
		} else if ("GetContractHeaderDetails".equalsIgnoreCase(res1.name())) {
			rs = APIUtils.getPayloadbody().header("Authorization", "Bearer "+token);
			System.out.println("get token to GetHeader API given elseif condition : " + token);   //null /
			System.out.println(" contract ID/data :" +contractId);
		}
	
		
	}

	@When("post {string} http API request") // for all 3 api given statement
	public void post_http_api_request(String resources) {
	res1 = APIResources.valueOf(resources);
		//System.out.println("res1 value in when statement : "+ res1);

		if ("GetAuthToken".equalsIgnoreCase(res1.name())) {
			res = rs.when().post(res1.getResources()); // here only store all response into Response object
		} 
		else if ("SaveHeaderInformation".equalsIgnoreCase(res1.name())) {
			res = rs.when().post(res1.getResources());
			System.out.println("when() -  saveHeader API given elseif condition run  : " + token);  //pass
		}		
	}

	@Then("take {string} from responce payload for {string}")
	public void take_from_responce_payload(String key, String resources) {
	  res1 = APIResources.valueOf(resources);
		res = res.then().extract().response();
		String bodyres = res.asString();

		JsonPath json = new JsonPath(bodyres);
		
		if ("GetAuthToken".equalsIgnoreCase(res1.name().trim())) {
			token = json.getString("data.clmUserDto." + key); // here key = tokenvalue
		    System.out.println("loop Then condition :"+res1.name() );
		    
			System.out.println("then condition token API generated token: " + token);    //pass   2nd time fail

		} else if ("SaveHeaderInformation".equalsIgnoreCase(res1.name().trim())) {
			String postmessage = json.getString("message");
			System.out.println("responce of saveheader body: " + res.getBody());   
			contractId = json.getInt(key); // here key = data
			System.out.println("postmessage: "+postmessage);
			System.out.println("loop then() for saveheader :"+ res1.name());
			System.out.println("save header api then() condition execute:" + contractId);

		}
	}
	
	@Then("check response status code is {string}")
	public void check_response_status_code_is(String statusCodeStr) {
		int expectedStatusCode = Integer.parseInt(statusCodeStr);
		System.out.println(" then() expected status code value :"+ expectedStatusCode); //pass   2nd time -pass
		res.then().assertThat().statusCode(expectedStatusCode);
	}

	@When("Get {string} http API request") // GetContractHeaderDetails API
	public void get_http_api_request(String resources) {
	  res1 = APIResources.valueOf(resources);
		res = rs.when().get(res1.getResources());
		String getbody = res.getBody().asString();
		JsonPath jsonGet = new JsonPath(getbody);
		System.out.println("contract Number taken from response of getAPI : "+ jsonGet.getShort("data.contractheaderinformationList.contractnumber"));
		System.out.println(" when of getAPI Resource: " + res1.name());    //pass
		System.out.println("GETAPI Token value in another api : " + token);    //pass
		
	}

	@Then("compare data from responce payload is contain same keys and values of post data")
	public void compare_from_responce_payload_is_contain_same_keys_and_values_of_post_data() {

		APIUtils.assertionResponse();
	}

	//System.out.printf(" \"res1.name(): %s | res1.getResources(): %s%n\", ",res1.name(), res1.getResources());
    // for multiple var have to print in single statement then 
	//System.out.printf("\"value of res1 object of API Resources:%s |value of res object: %s%n" ," "+ res1, res);
}
