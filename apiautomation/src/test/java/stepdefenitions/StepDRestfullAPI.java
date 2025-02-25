package stepdefenitions;

import java.util.Map;

import org.testng.Assert;

import com.apiautomation.modelDummyJson.ResponseItem;
import com.apiautomation.modelDummyJson.request.RequestItem;
import com.apiautomation.modelRestApiFull.ResponseObject;
import com.apiautomation.modelRestApiFull.request.RItemRestfulAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.DRequestRestfulAPI;
import resources.DataRequest;

public class StepDRestfullAPI {

    ResponseObject responseObject;
    RItemRestfulAPI rItemRestfulAPI;
    DRequestRestfulAPI dRequestRestfulAPI;
    String json;
    int idProduct;  

/*
 *  Given A list of item are available (get list of all objects)
    When I add item to list "<payload>" (Add object)
    Then The item is available (Single object)
 */

 @Given ("A list of item are available")
 public void getAllObjects(){
    //Implementation
    System.out.println("get All Objects");
    RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                                    .log()
                                    .all()
                                    .when()
                                        .get("objects");
            
            System.out.println("Ini adalah hasil search: \n" +response.asPrettyString());
            
            JsonPath addJsonPath = response.jsonPath();
            String id = addJsonPath.getString("id");

            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertNotNull(id);
        }

@When ("I add item to list")
public void addObject(){
    //Implementation
    System.out.println("add New Object");
    String json = "{\r\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\r\n" + //
                        "   \"data\": {\r\n" + //
                        "      \"year\": 2019,\r\n" + //
                        "      \"price\": 1900,\r\n" + //
                        "      \"CPU model\": \"Intel Core i9\",\r\n" + //
                        "      \"Hard disk size\": \"1 TB\"\r\n" + //
                        "   }\r\n" + //
                        "}";

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification 
                                .log()
                                .all()
                                .body(json)
                                .contentType("application/json")
                            .when()
                                .post("objects"); 
    
        System.out.println("add object " + response.asPrettyString());

        JsonPath addJsonPath = response.jsonPath();
        responseObject = addJsonPath.getObject("", ResponseObject.class);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(responseObject.name, "Apple MacBook Pro 16");
        Assert.assertEquals(responseObject.dataItem.year, 2019);
        // Assert.assertEquals(responseObject.dataItem.get(index:0) year, 2019);
        Assert.assertEquals(responseObject.dataItem.price, 1900);
        Assert.assertEquals(responseObject.dataItem.cpuModel, "Intel Core i9");
        Assert.assertEquals(responseObject.dataItem.hardiskSize, "1 TB");
        Assert.assertNotNull(responseObject.id);
        Assert.assertNotNull(responseObject.createdAt);
    
}

@When ("I add item to list {string}")
public void addNewProductPayload(String payload) throws JsonMappingException, JsonProcessingException{
    //implementation
    dRequestRestfulAPI = new DRequestRestfulAPI();

    // System.out.println("test" +payload);
    RestAssured.baseURI = "https://api.restful-api.dev";
    RequestSpecification requestSpecification = RestAssured
                                                    .given();

    for(Map.Entry<String, String> entry : dRequestRestfulAPI.addItemCollection().entrySet()){
            if (entry.getKey().equals(payload)){
                json = entry.getValue();
                break;
            }
        }                                                    

    Response response = requestSpecification 
                                .log()
                                .all()
                                .body(json)
                                .contentType("application/json")
                            .when()
                                .post("objects"); 
    
        System.out.println("add object " + response.asPrettyString());

        ObjectMapper requestAddItem = new ObjectMapper();
        rItemRestfulAPI = requestAddItem.readValue(json, RItemRestfulAPI.class);


        JsonPath addJsonPath = response.jsonPath();
        responseObject = addJsonPath.getObject("", ResponseObject.class);

        // Assert.assertEquals(response.statusCode(), 200);
        // Assert.assertEquals(responseObject.name, "Apple MacBook Pro 16");
        // Assert.assertEquals(responseObject.dataItem.year, 2019);
        // Assert.assertEquals(responseObject.dataItem.price, 1900);
        // Assert.assertEquals(responseObject.dataItem.cpuModel, "Intel Core i9");
        // Assert.assertEquals(responseObject.dataItem.hardiskSize, "1 TB");
        // Assert.assertNotNull(responseObject.id);
        // Assert.assertNotNull(responseObject.createdAt);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(responseObject.name, rItemRestfulAPI.name);
        Assert.assertEquals(responseObject.dataItem.year, rItemRestfulAPI.dataItem.year);
        Assert.assertEquals(responseObject.dataItem.price, rItemRestfulAPI.dataItem.price);
        Assert.assertEquals(responseObject.dataItem.cpuModel, rItemRestfulAPI.dataItem.cpuModel);
        Assert.assertEquals(responseObject.dataItem.hardiskSize, rItemRestfulAPI.dataItem.hardiskSize);
        Assert.assertNotNull(responseObject.id);
        Assert.assertNotNull(responseObject.createdAt);

}


@Then("The item is available")
public void getSingleObjects() {
    // Implementation
    System.out.println("get Single Object");
    
    RestAssured.baseURI = "https://api.restful-api.dev";
    RequestSpecification requestSpecification = RestAssured
                                                .given();
    
    Response response = requestSpecification
                                .log()
                                .all()
                                .queryParam("id", 3)
                            .when()
                                .get("objects"); 
                
    System.out.println("Ini adalah hasil search:\n" + response.asPrettyString());

    // Pastikan status code 200
    Assert.assertEquals(response.statusCode(), 200, "Status code harus 200");

    // Parsing JSON untuk validasi
    JsonPath jsonPath = response.jsonPath();

    // Validasi data berdasarkan key JSON
    Assert.assertEquals(jsonPath.getString("[0].name"), "Apple iPhone 12 Pro Max", "Nama tidak sesuai");
    Assert.assertEquals(jsonPath.getString("[0].data.color"), "Cloudy White", "Warna tidak sesuai");
    Assert.assertEquals(jsonPath.getInt("[0].data['capacity GB']"), 512, "Kapasitas tidak sesuai");

    System.out.println("✅ Validasi sukses! Semua data valid.");

    }

    
}