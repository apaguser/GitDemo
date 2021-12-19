package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		
		//write a code that will give you place id
		//execute this code only when place id is null
		
		StepDefination m=new StepDefination();
		if(StepDefination.place_Id==null) {
		m.add_place_payload("Sheety", "French", "Asia");
		m.user_calls_with_post_http_request("AddPlaceAPI", "POST");
		m.verify_place_id_created_maps_to_using("Sheety", "GetPlaceAPI");
		}
	}

}
