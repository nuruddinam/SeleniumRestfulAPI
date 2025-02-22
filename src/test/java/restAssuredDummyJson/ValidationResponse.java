package restAssuredDummyJson;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.apiautomation.modelDummyJson.ResponseItem;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ValidationResponse {
    /*
     * Scenario 1
     * 1. Hit API Create Products
     * 2. Then Validate Respons
     * - id is not empty
     * - title, price, discountPercentage, Stock, Category
     */

    @Test
    public void createProduct (){

        String json =   " {\r\n" + //
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
    
    System.out.println("create product" + response.asPrettyString());
    // System.out.println("Response Body: " + response.asPrettyString());

        /*
         *  "id": 195,
            "title": "Le Minerale",
            "price": "50000",
            "discountPercentage": "5",
            "stock": "100",
            "rating": "4",
            "description": "Segar Menyehatkan",
            "category": "test"
         */

         JsonPath addJsonPath = response.jsonPath();

         String title = addJsonPath.get("title");
         int price = addJsonPath.get("price");
         int discount = addJsonPath.get("discountPercentage");
         int stock = addJsonPath.get("stock");
        //  int rating = addJsonPath.get("rating");
         String description = addJsonPath.get("description");
        //  String category = addJsonPath.get("cateogry");

         Assert.assertEquals(response.statusCode(), 201);
         Assert.assertEquals(title, "Le Minerale");
         Assert.assertEquals(price, 50000);
         Assert.assertEquals(discount, 5);
         Assert.assertEquals(stock, 100);
        //  Assert.assertEquals(rating, 4);
         Assert.assertEquals(description, "Segar Menyehatkan");
        //  Assert.assertEquals(category, "test");

    }

    


    }


