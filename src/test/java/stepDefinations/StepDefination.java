package stepDefinations;

import static io.restassured.RestAssured.given;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class StepDefination extends Utils{
	ResponseSpecification resSpec;
	RequestSpecification rs;
	Response res;
	TestDataBuild data=new TestDataBuild();
	static String place_Id;
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload(String name,String language,String address) throws IOException {



		rs=given().spec(requestSpecification())
				.body(data.addPlacePayLoad(name,language,address));


	}
	@When("user calls {string} with {string} Http Request")
	public void user_calls_with_post_http_request(String resource, String method) {

		APIResources resourceAPI=APIResources.valueOf(resource);
		System.out.println(resourceAPI);

		resSpec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if(method.equalsIgnoreCase("Post")) {
			res=rs.when().post(resourceAPI.getResource());
		}else if(method.equalsIgnoreCase("GET")) {
			res=rs.when().get(resourceAPI.getResource());
		}else if(method.equalsIgnoreCase("DELETE")) {
			res=rs.when().delete(resourceAPI.getResource());
		}


	}
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {

		assertEquals(res.getStatusCode(),200);
	}
	@Then("{string} is response body is {string}")
	public void is_response_body_is(String keyvalue, String ExpectedValue) {
		// Write code here that turns the phrase above into concrete actions

		assertEquals(getJsonPath(res,keyvalue),ExpectedValue);

	}


	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {

		place_Id=getJsonPath(res,"place_id");
		rs=given().spec(requestSpecification()).queryParam("place_id", place_Id);
		user_calls_with_post_http_request(resource,"GET");
		String actualName=getJsonPath(res,"name");
		assertEquals(actualName,expectedName);
	}

	@Given("DeletePlace PayLoad")
	public void delete_place_pay_load() throws IOException {
		// Write code here that turns the phrase above into concrete actions
		rs=given().spec(requestSpecification()).body(data.deletePlacePayLoad(place_Id));
	}



}
