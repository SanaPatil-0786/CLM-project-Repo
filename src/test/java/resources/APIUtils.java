package resources;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import serialization.SaveHeaderInfo;
import stepDefination.StepDefination;

public class APIUtils {

	private static final String Base_Url = ConfigReader.get("base_url");
	private static final String CONTENT_TYPE = ConfigReader.get("Content-Type");
	public static SaveHeaderInfo contractreqdata; // create pojo class object to store setter data
	//private static SaveHeaderInfo contractreqdata = TestDataProvider.createRandomContract();
	//private static Response res3 =StepDefination.res;

	// token post API

	public static RequestSpecification tokenRequestPayload() throws IOException {
		String jsonBody = new String(Files.readAllBytes(Paths.get("src/test/payload/tokenbodypayload.json")));
		return given().baseUri(Base_Url).header("Content-Type", CONTENT_TYPE).body(jsonBody);
	}
	public static void assertionResponse() {
		contractreqdata = TestDataProvider.createRandomContract(); // stored setter data
		//JsonPath jsonPath = new JsonPath(StepDefination.res.toString());
		JsonPath jsonPath = new JsonPath(StepDefination.res.asString());

		Map<String, Object> responseMap = jsonPath.getMap("data.contractheaderinformationList[0]"); // response of
																									// GetContractHeaderDetails
																									// API
		// response of get api stored in responseMap

		// Convert request object to map
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> requestMap = mapper.convertValue(contractreqdata, Map.class);

		// Compare maps
		List<String> matched = new ArrayList<>();
		List<String> mismatched = new ArrayList<>();

		for (String reqKey : requestMap.keySet()) {
			Object reqVal = requestMap.get(reqKey);
			Object resVal = responseMap.getOrDefault(reqKey.toLowerCase(), responseMap.get(reqKey));

			if (resVal != null && reqVal != null && reqVal.toString().equalsIgnoreCase(resVal.toString())) {
				matched.add(reqKey);
			} else {
				mismatched.add(reqKey + " → Expected: " + reqVal + ", Actual: " + resVal);
			}
		}

		System.out.println("Matched Keys: " + matched);
		System.out.println("Mismatched Keys: " + mismatched);

	}

	// Common method to post request ---post header informaiton
	public static RequestSpecification postHeaderRequest() throws IOException {
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		
		return given().filter(RequestLoggingFilter.logRequestTo(log)).
				filter(ResponseLoggingFilter.logResponseTo(log)).
				baseUri(Base_Url).header("Content-Type", CONTENT_TYPE).
				body(TestDataProvider.createRandomContract());
		
	}
	
	// common method for GET API request 
	
	public static RequestSpecification getPayloadbody() throws IOException
	{
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		//System.out.println("File will be created at: " + new File("login.txt").getAbsolutePath());
		return given().filter(RequestLoggingFilter.logRequestTo(log)).filter(ResponseLoggingFilter.logResponseTo(log)).baseUri(Base_Url).queryParam("contractId", StepDefination.contractId);
	}

	

	// verify post request body data available in response of get api or not
	{
		
	}
	
	public static RequestSpecification loggingReports() throws FileNotFoundException {
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		//System.out.println("File will be created at: " + new File("login.txt").getAbsolutePath());
		return given().filter(RequestLoggingFilter.logRequestTo(log)).filter(ResponseLoggingFilter.logResponseTo(log));
	}
	
}
