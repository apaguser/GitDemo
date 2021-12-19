Feature: Validating Place API's
@AddPlace @Regression
Scenario Outline: Verify if Place is being added Successfully added using AddPlaceAPI
Given Add Place Payload with "<name>" "<language>" "<address>"
When user calls "AddPlaceAPI" with "Post" Http Request
Then the API call got success with status code 200
And "status" is response body is "OK"
And "scope" is response body is "APP"
And verify place_Id created maps to "<name>" using "GetPlaceAPI"


Examples:
    |name   |language  |address           |
    |AAhouse|English   |World Cross Centre|
#   |BBhouse|Spanish   |World Centre|


@DeletePlace @Regression
Scenario: Verify if Delete Place functionality is working
Given DeletePlace PayLoad
When user calls "DeletePlaceAPI" with "Post" Http Request
Then the API call got success with status code 200
And "status" is response body is "OK"
 