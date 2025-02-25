package restfulAPI;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.apiautomation.modelRestApiFull.ResponseObject;
import com.apiautomation.modelRestApiFull.ResponseObject.DeleteResponse;



import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class ValidationPOJO {

    ResponseObject responseObject;

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
    public void getAllObjectsByIds() {
            RestAssured.baseURI = "https://api.restful-api.dev";
            RequestSpecification requestSpecification = RestAssured
                                                        .given();
        
            Response response = requestSpecification
                                    .log()
                                    .all()
                                    .queryParam("id", 3)
                                    .queryParam("id", 5)
                                    .queryParam("id", 10)
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
        
            Assert.assertEquals(jsonPath.getString("[1].name"), "Samsung Galaxy Z Fold2", "Nama tidak sesuai");
            Assert.assertEquals(jsonPath.getString("[1].data.color"), "Brown", "Warna tidak sesuai");
            Assert.assertTrue(jsonPath.getDouble("[1].data.price") > 0, "Harga harus lebih dari 0");
        
            Assert.assertEquals(jsonPath.getString("[2].name"), "Apple iPad Mini 5th Gen", "Nama tidak sesuai");
            Assert.assertEquals(jsonPath.getDouble("[2].data['Screen size']"), 7.9, "Ukuran layar tidak sesuai");
        
            System.out.println("✅ Validasi sukses! Semua data valid.");
        }
    
    @Test
    public void getSingleObjects() {
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

    @Test
    public void updateObject(){

                String json = "{\r\n" + //
                "   \"name\": \"Nuruddin\",\r\n" + //
                "   \"data\": {\r\n" + //
                "      \"year\": 9919,\r\n" + //
                "      \"price\": 9949.99,\r\n" + //
                "      \"CPU model\": \"Intel Core i9\",\r\n" + //
                "      \"Hard disk size\": \"1 TB\",\r\n" + //
                "      \"color\": \"silver\"\r\n" + //
                "   }\r\n" + //
                "}";

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                            .given();

        Response response = requestSpecification 
                .log()
                .all()
                .pathParam("path", "objects")
                .pathParam("idObject", "ff808181932badb6019526a765bc74d2")
                .body(json)
                .contentType("application/json")
                .when()
                    .put("{path}/{idObject}");

        System.out.println("Update object response: \n" + response.asPrettyString());

        // Pastikan status code 200
        Assert.assertEquals(response.statusCode(), 200, "Status code harus 200");

        // Parsing JSON untuk validasi
        JsonPath addJsonPath = response.jsonPath();
        ResponseObject responseObject = addJsonPath.getObject("", ResponseObject.class);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(responseObject.name, "Nuruddin");
        Assert.assertEquals(responseObject.dataItem.year, 9919);
  
        Assert.assertEquals(responseObject.dataItem.price, 9949.0, 0.01);
        Assert.assertEquals(responseObject.dataItem.cpuModel, "Intel Core i9");
        Assert.assertEquals(responseObject.dataItem.hardiskSize, "1 TB");
        Assert.assertEquals(responseObject.dataItem.color, "silver");
        
        Assert.assertNotNull(responseObject.id);
        Assert.assertNotNull(responseObject.updatedAt);
    }

    @Test
    public void partiallyUpdateObject(){

        String json = "{\r\n" + //
                        "   \"name\": \"Apple MacBook Pro 16 (Updated Name)\"\r\n" + //
                        "}";

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

    Response response = requestSpecification 
                        .log()
                        .all()
                        .pathParam("idObject", "ff808181932badb6019526a765bc74d2")  // Ganti dengan ID yang valid
                        .body(json)
                        .contentType("application/json")
                        .when()
                            .patch("/objects/{idObject}");

    System.out.println("Partially Update object response: \n" + response.asPrettyString());

    // Pastikan status code 200
    Assert.assertEquals(response.statusCode(), 200, "Status code harus 200");

    // Parsing JSON untuk validasi
    JsonPath addJsonPath = response.jsonPath();
    ResponseObject responseObject = addJsonPath.getObject("", ResponseObject.class);

    Assert.assertEquals(response.statusCode(), 200);
    Assert.assertEquals(responseObject.name, "Apple MacBook Pro 16 (Updated Name)");
    Assert.assertEquals(responseObject.dataItem.year, 9919);

    Assert.assertEquals(responseObject.dataItem.price, 9949.0, 0.01);
    Assert.assertEquals(responseObject.dataItem.cpuModel, "Intel Core i9");
    Assert.assertEquals(responseObject.dataItem.hardiskSize, "1 TB");
    Assert.assertEquals(responseObject.dataItem.color, "silver");
    
    Assert.assertNotNull(responseObject.id);
    Assert.assertNotNull(responseObject.updatedAt);
}

    @Test
    public void deleteObject(){

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

    String idObject = "ff808181932badb6019526a3e68374cd";

    Response response = requestSpecification 
                        .log()
                        .all()
                        .pathParam("path", "objects")
                        .pathParam("idObject", "ff808181932badb6019526a3e68374cd")
                        .contentType("application/json")
                        .when()
                            .delete("{path}/{idObject}");

    System.out.println("delete objects " + response.asPrettyString());

     // **Validasi status code**
     int statusCode = response.statusCode();
     Assert.assertTrue(statusCode == 200 || statusCode == 204 || statusCode == 404 || statusCode == 405,
             "Status code harus 200, 204, 404, atau 405, tetapi mendapatkan: " + statusCode);

     // **Parsing JSON menggunakan DeleteResponse**
     DeleteResponse deleteResponse = response.as(DeleteResponse.class);

     if (statusCode == 200 || statusCode == 204) {
         String actualMessage = deleteResponse.getMessage();
         String expectedMessage = "Object with id = " + idObject + " has been deleted.";
         Assert.assertEquals(actualMessage, expectedMessage, "Pesan sukses tidak sesuai.");
     } else if (statusCode == 404) {
         String actualError = deleteResponse.getError();
         String expectedError = "Object with id = " + idObject + " doesn't exist.";
         Assert.assertEquals(actualError, expectedError, "Pesan error tidak sesuai.");
     } else if (statusCode == 405) {
         String actualError = deleteResponse.getError();
         Assert.assertNotNull(actualError, "Pesan error harus ada jika status 405.");
     }
  }
}