package restfulAPI;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ValidationResponse {

    @Test
    public void getAllObjects() {

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


    @Test
    public void addObject(){
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
         String name = addJsonPath.get("name");
         int year = addJsonPath.get("data.year");
         int price = addJsonPath.get("data.price");
         String cpuModel = addJsonPath.get("data. 'CPU model'");
         String hardiskSize = addJsonPath.get("data. 'Hard disk size'");
         String id = addJsonPath.getString("id");
         String createdAt = addJsonPath.getString("createdAt");

         Assert.assertEquals(response.statusCode(), 200);
         Assert.assertEquals(name, "Apple MacBook Pro 16");
         Assert.assertEquals(year, 2019);
         Assert.assertEquals(price, 1900);
         Assert.assertEquals(cpuModel, "Intel Core i9");
         Assert.assertEquals(hardiskSize, "1 TB");
         Assert.assertNotNull(id);
         Assert.assertNotNull(createdAt);

    }   

}

