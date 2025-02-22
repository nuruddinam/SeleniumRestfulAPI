package restAssuredDummyJson;

import com.apiautomation.modelDummyJson.GetAllProductsResponse;
import com.apiautomation.modelDummyJson.ResponseItem;
import com.apiautomation.modelDummyJson.UpdateProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;

public class ValidationPOJO {
    /*
     * Scenario 1
     * 1. Hit API Create Products
     * 2. Then Validate Respons
     * - id is not empty
     * - title, price, discountPercentage, Stock, Category
     */

    ResponseItem responseItem;

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
   
        // JsonPath addJsonPath = response.jsonPath();
        // responseItem = addJsonPath.getObject("", ResponseItem.class);

         // Deserialisasi menggunakan ObjectMapper
         try {
            ObjectMapper objectMapper = new ObjectMapper();
            responseItem = objectMapper.readValue(response.getBody().asString(), ResponseItem.class);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Error parsing JSON response.");
        }

        // Validasi response
        Assert.assertEquals(response.statusCode(), 201);
        Assert.assertNotNull(responseItem.getId(), "ID should not be null");
        Assert.assertEquals(responseItem.getTitle(), "Le Minerale");
        Assert.assertEquals(responseItem.getPrice(), 50000.0);
        Assert.assertEquals(responseItem.getDiscountPercentage(), 5.0);
        Assert.assertEquals(responseItem.getStock(), 100);
        Assert.assertEquals(responseItem.getDescription(), "Segar Menyehatkan");
    }

    @Test
    public void getAllProducts() {
        RestAssured.baseURI = "https://dummyjson.com";
        Response response = RestAssured.get("/products");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            GetAllProductsResponse productsResponse = objectMapper.readValue(response.getBody().asString(), GetAllProductsResponse.class);

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
}
    @Test
    public void validateUpdateProduct() {
        String jsonUpdate = "{\n" +
                "  \"title\": \"Essence Mascara Lash Princess\",\n" +
                "  \"price\": 9.99,\n" +
                "  \"discountPercentage\": 7.17,\n" +
                "  \"stock\": 5,\n" +
                "  \"rating\": 4.94,\n" +
                "  \"images\": [\"https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/1.png\"],\n" +
                "  \"thumbnail\": \"https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/thumbnail.png\",\n" +
                "  \"description\": \"The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects.\",\n" +
                "  \"brand\": \"Essence\",\n" +
                "  \"category\": \"beauty\"\n" +
                "}";

        Response responseUpdate = RestAssured.given()
                .log().all()
                .pathParam("path", "products")
                .pathParam("idProduct", 1)
                .body(jsonUpdate)
                .contentType("application/json")
                .when()
                .put("{path}/{idProduct}");

        System.out.println("Update Product Response: " + responseUpdate.asPrettyString());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UpdateProductResponse updateProduct = objectMapper.readValue(responseUpdate.getBody().asString(), UpdateProductResponse.class);

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

            System.out.println("Validation successful! No null fields.");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Error parsing JSON response.");
        }
    }
}
