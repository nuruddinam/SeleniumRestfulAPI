package stepdefenitions;

import java.util.Map;

import org.testng.Assert;

import com.apiautomation.modelDummyJson.ResponseItem;
import com.apiautomation.modelDummyJson.request.RequestItem;
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
import resources.DataRequest;

public class StepDefenitionsimpl {

    ResponseItem responseItem;
    RequestItem requestItem;
    DataRequest dataRequest;
    String json;
    int idProduct;      

    /*
     *  Given A list of products are available
        When I add new products to etalase
        Then The product is available
    */

    @Given ("A list of products are available")
    public void getAllProducts(){
        //Implementation
        System.out.println("get All Products");
        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();
                            
        Response response2 = requestSpecification
                                 .log()
                                 .all()
                             .when()
                                 .get ("products");

        // System.out.println("response" + response2.asPrettyString());
    }

    @When ("I add new products to etalase")
    public void addNewProduct(){
        //Implementation
        System.out.println("add New Product");
        String json = " {\r\n" + //
                        "  \"id\": 195,\r\n" + //
                        "  \"title\": \"Le Minerale\",\r\n" + //
                        "  \"description\" : \"Segar Menyehatkan\",\r\n" + //
                        "  \"category\" : \"test\",\r\n" + //
                        "  \"price\" : 50000,\r\n" + //
                        "  \"discountPercentage\" : 5,\r\n" + //
                        "  \"rating\" : 4,\r\n" + //
                        "  \"stock\" : 100,\r\n" + //
                        "  \"tags\" : [\r\n" + //
                        "    \"beauty\",\r\n" + //
                        "    \"mascara\"\r\n" + //
                        "  ],\r\n" + //
                        "  \"dimensions\" : {\r\n" + //
                        "    \"width\" : 24,\r\n" + //
                        "    \"height\" : 10,\r\n" + //
                        "    \"depth\" : 10\r\n" + //
                        "  } \r\n" + //
                        "\r\n" + //
                        "}";

        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification 
                            .log()
                            .all()
                            .pathParam("path", "products")
                            .pathParam("method", "add")
                            .body(json)
                            .contentType("application/json")
                            .when()
                                .post("{path}/{method}");
    
        // System.out.println("add product" + response.asPrettyString());

    // Validasi response

        JsonPath addJsonPath = response.jsonPath();
        responseItem = addJsonPath.getObject("", ResponseItem.class);
        
        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertNotNull(responseItem.getId(), "ID should not be null");
        Assert.assertEquals(responseItem.getTitle(), "Le Minerale");
        Assert.assertEquals(responseItem.getPrice(), 50000.0);
        Assert.assertEquals(responseItem.getDiscountPercentage(), 5.0);
        Assert.assertEquals(responseItem.getStock(), 100);
        Assert.assertEquals(responseItem.getDescription(), "Segar Menyehatkan");
        Assert.assertEquals(responseItem.getCategory(), "test");

    }

    @When ("I add new {string} to etalase")
    public void addNewProductPayload(String payload) throws JsonMappingException, JsonProcessingException{
        //implementation
        dataRequest = new DataRequest();
        
        // System.out.println("Add new product-1" + payload);
        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        for(Map.Entry<String, String> entry : dataRequest.addItemCollection().entrySet()){
            if (entry.getKey().equals(payload)){
                json = entry.getValue();
                break;
            }
        }

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "products")
                            .pathParam("method", "add")
                            .body(json)
                            .contentType("application/json")
                            .when()
                                .post("{path}/{method}");
        // System.out.println("add product" + response.asPrettyString());

        //Object mapper
        /*
         * Convert JSON to POJO
         */
        ObjectMapper requestAddItem = new ObjectMapper();
        requestItem = requestAddItem.readValue(json, RequestItem.class);

        //Validation
        JsonPath addJsonPath = response.jsonPath();
        responseItem = addJsonPath.getObject("", ResponseItem.class);

        Assert.assertEquals(response.statusCode(), 201);

        // Assert.assertEquals(responseItem.getTitle(), "Le Minerale");
        // Assert.assertEquals(responseItem.getPrice(), 50000.0);
        // Assert.assertEquals(responseItem.getDiscountPercentage(), 5.0);
        // Assert.assertEquals(responseItem.getStock(), 100);
        // Assert.assertEquals(responseItem.getDescription(), "Segar Menyehatkan");
        // Assert.assertEquals(responseItem.getCategory(), "test");
        Assert.assertEquals(responseItem.title,requestItem.title);
        Assert.assertEquals(responseItem.price,requestItem.price);
        Assert.assertEquals(responseItem.discountPercentage, requestItem.discountPercentage);
        Assert.assertEquals(responseItem.stock, requestItem.stock);
        Assert.assertEquals(responseItem.category, requestItem.category);

        System.out.println("ini adalah payload " + payload);
        System.out.println(requestItem.title);
        System.out.println(requestItem.price);
    }

    @Then ("The product is available")
    public void getSingleProduct(){
        //Implementation
        System.out.println("get Single Product");
        
        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();
        
        Response response = requestSpecification
                                .log() 
                                .all()
                                .pathParam("idProduct", 1)
                                .pathParam("path", "products")
                            .when()
                                .get("{path}/{idProduct}");

        // System.out.println("single Product" + response.asPrettyString());

        // Validasi response

    // @Then("I can update item {string}")
    // public void updateSingleProduct(String payload){
    //     System.out.println("update single product");
    // }
    
    }

}


