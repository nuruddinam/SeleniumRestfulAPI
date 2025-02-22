package scenario;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.apiautomation.modelDummyJson.GetAllProductsResponse;
import com.apiautomation.modelDummyJson.ResponseItem;
import com.apiautomation.modelDummyJson.UpdateProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class RestE2ETestDummyJSONMasihError {

    ResponseItem responseItem;

    /*
     * Scenario e2e test
     * 1. Hit add products (Verify Respons)
     * 2. Hit get products (Verify Respons)
     * 3. Hit update products (Verify Respons)
     */

    @Test
    public void scenarioE2ETest(){
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

        // Add Product
        RestAssured.baseURI = "https://dummyjson.com";

        Response response = given()
                            .log()
                            .all()
                            .pathParam("path", "products")
                            .pathParam("method", "add")
                            .body(json)
                            .contentType("application/json")
                            .when()
                                .post("{path}/{method}");
    
        System.out.println("create product" + response.asPrettyString());
        
        JsonPath addJsonPath = response.jsonPath();
        responseItem = addJsonPath.getObject("", ResponseItem.class);
        String idObject = responseItem.getId();

            String title = addJsonPath.get("title");
            int price = addJsonPath.get("price");
            int discount = addJsonPath.get("discountPercentage");
            int stock = addJsonPath.get("stock");
            //  int rating = addJsonPath.get("rating");
            String description = addJsonPath.get("description");
            String category = addJsonPath.get("category");

            Assert.assertEquals(response.statusCode(), 201);
            Assert.assertEquals(title, "Le Minerale");
            Assert.assertEquals(price, 50000);
            Assert.assertEquals(discount, 5);
            Assert.assertEquals(stock, 100);
            //  Assert.assertEquals(rating, 4);
            Assert.assertEquals(description, "Segar Menyehatkan");
            Assert.assertEquals(category, "test");

            Assert.assertNotNull(idObject, "Product ID is null");

        /*  
         * Get Product
         */

        Response response2 = given()
                                  .pathParam("path", "products")
                                  .pathParam("idProduct", idObject)
                                  .log()
                                  .all()
                              .when()
                                  .get ("{path}/{idProduct}");

        System.out.println("response2" + response2.asPrettyString());

        // Validation POJO 
        Response responseAllProducts = RestAssured.get("/products");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            GetAllProductsResponse productsResponse = objectMapper.readValue(responseAllProducts.getBody().asString(), GetAllProductsResponse.class);

            // Validasi response tidak null
            Assert.assertNotNull(productsResponse, "Response is null");
            Assert.assertNotNull(productsResponse.getProducts(), "Product list is null");
            Assert.assertFalse(productsResponse.getProducts().isEmpty(), "Product list is empty");

            // Validasi produk pertama
            ResponseItem firstProduct = productsResponse.getProducts().get(0);
            Assert.assertNotNull(firstProduct.getId(), "Product ID is null");
            Assert.assertNotNull(firstProduct.getTitle(), "Product title is null");

            System.out.println("Validasi sukses! Data tidak ada yang null.");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Error parsing JSON response.");
        
        }

        /*  
        * Update Product
        */

        // PUT Update Product
        Response responseUpdate = given() 
                                .log()
                                .all()
                                .pathParam("path", "products")
                                .pathParam("idProduct", idObject)
                                .body(json)
                                .contentType("application/json")
                                .when()
                                    .put("{path}/{idProduct}");
        System.out.println("update product" + responseUpdate.asPrettyString());

        try {
            // Cek apakah response valid sebelum parsing
            String responseBody = responseUpdate.getBody().asString();
            ObjectMapper objectMapper = new ObjectMapper();
            
            // Pastikan JSON bisa di-parse sebelum dikonversi ke POJO
            objectMapper.readTree(responseBody);

            UpdateProductResponse updateProduct = objectMapper.readValue(responseBody, UpdateProductResponse.class);

            Assert.assertNotNull(updateProduct.getId(), "ID tidak boleh null");
            Assert.assertNotNull(updateProduct.getTitle(), "Title tidak boleh null");
            Assert.assertNotNull(updateProduct.getPrice(), "Price tidak boleh null");
            Assert.assertNotNull(updateProduct.getDiscountPercentage(), "Discount Percentage tidak boleh null");
            Assert.assertNotNull(updateProduct.getStock(), "Stock tidak boleh null");
            Assert.assertNotNull(updateProduct.getRating(), "Rating tidak boleh null");
            Assert.assertNotNull(updateProduct.getImages(), "Images tidak boleh null");
            Assert.assertNotNull(updateProduct.getThumbnail(), "Thumbnail tidak boleh null");
            Assert.assertNotNull(updateProduct.getDescription(), "Description tidak boleh null");
            Assert.assertNotNull(updateProduct.getBrand(), "Brand tidak boleh null");
            Assert.assertNotNull(updateProduct.getCategory(), "Category tidak boleh null");

            System.out.println("Validasi sukses! Tidak ada field yang null.");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Error parsing JSON response: " + e.getMessage());
        }

            }

        }   