package scenario;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.apiautomation.modelRestApiFull.ResponseObject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestE2ETestResftfulAPI {

    ResponseObject responseObject ;
    String idObject;
    /*
     * Create new object (hit API add_object)
       Verify new object is added (hit API single_object)
       Delete product (hit API delete_object)
       Verify new object is deleted (hit API single_object)
     */

    @Test
    public void scenarioE2ETest(){
        String json = "{\r\n" + //
                        "   \"name\": \"Nuruddin\",\r\n" + //
                        "   \"data\": {\r\n" + //
                        "      \"year\": 2019,\r\n" + //
                        "      \"price\": 1900,\r\n" + //
                        "      \"CPU model\": \"Intel Core i9\",\r\n" + //
                        "      \"Hard disk size\": \"1 TB\"\r\n" + //
                        "   }\r\n" + //
                        "}";

    //Post Add Product
        RestAssured.baseURI = "https://api.restful-api.dev";
        Response response = given()
                                .log()
                                .all()
                                .body(json)
                                .contentType("application/json")
                            .when()
                                .post("objects"); 
    
        System.out.println("add object " + response.asPrettyString());

        JsonPath addJsonPath = response.jsonPath();
        responseObject = addJsonPath.getObject("", ResponseObject.class);
        String idObject = responseObject.id;

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(responseObject.name, "Nuruddin");
        Assert.assertEquals(responseObject.dataItem.year, 2019);
        // Assert.assertEquals(responseObject.dataItem.get(index:0) year, 2019);
        Assert.assertEquals(responseObject.dataItem.price, 1900);
        Assert.assertEquals(responseObject.dataItem.cpuModel, "Intel Core i9");
        Assert.assertEquals(responseObject.dataItem.hardiskSize, "1 TB");
        Assert.assertNotNull(responseObject.id);
        Assert.assertNotNull(responseObject.createdAt);
    
      
    //Verify Get Single Object
        Response getResponse = given()
                                    .log()
                                    .all()
                                    // .pathParam("path", "objects")
                                    .pathParam("id", idObject)
                                .when()
                                    .get("/objects/{id}"); 
                    
        System.out.println("Ini adalah hasil search:\n" + getResponse.asPrettyString());

        // Pastikan status code 200
        Assert.assertEquals(response.statusCode(), 200, "Status code harus 200");

        // Parsing JSON untuk validasi
        JsonPath jsonPath = response.jsonPath();

        // Validasi data berdasarkan key JSON
        Assert.assertEquals(jsonPath.getString("name"), "Nuruddin", "Nama tidak sesuai");
        Assert.assertEquals(jsonPath.getInt("data.year"), 2019, "Tahun tidak sesuai");
        Assert.assertEquals(jsonPath.getInt("data.price"), 1900, "Harga tidak sesuai");
        Assert.assertEquals(jsonPath.getString("data['CPU model']"), "Intel Core i9", "CPU Model tidak sesuai");
        Assert.assertEquals(jsonPath.getString("data['Hard disk size']"), "1 TB", "Hard Disk Size tidak sesuai");

        System.out.println("✅ Validasi sukses! Semua data valid.");

    //Delete Product
        Response deleteResponse = given()
                            .log()
                            .all()
                            .pathParam("id", idObject)
                            .contentType("application/json")
                            .when()
                                .delete("/objects/{id}");

        System.out.println("delete objects " + response.asPrettyString());

        // **Validasi status code**
        int statusCode = deleteResponse.statusCode();
        Assert.assertTrue(statusCode == 200 || statusCode == 204 || statusCode == 404 || statusCode == 405,
                "Status code harus 200, 204, 404, atau 405, tetapi mendapatkan: " + statusCode);

        JsonPath deleteJsonPath = deleteResponse.jsonPath();
        if (statusCode == 200 || statusCode == 204) {
            Assert.assertEquals(deleteJsonPath.getString("message"), "Object with id = " + idObject + " has been deleted.", "Pesan sukses tidak sesuai.");
        } else if (statusCode == 404) {
            Assert.assertEquals(deleteJsonPath.getString("error"), "Object with id = " + idObject + " doesn't exist.", "Pesan error tidak sesuai.");
        

    //Verify Get Single Object
        Response verifyDeleteResponse = given()
                                        .log()
                                        .all()
                                        // .pathParam("path", "objects")
                                        .pathParam("id", idObject)
                                    .when()
                                        .get("/objects/{id}"); 
                        
            System.out.println("Verify delete response:\n" + verifyDeleteResponse.asPrettyString());

            Assert.assertEquals(verifyDeleteResponse.statusCode(), 404, "Status code harus 404 setelah DELETE.");
            JsonPath verifyDeleteJsonPath = verifyDeleteResponse.jsonPath();
            Assert.assertEquals(verifyDeleteJsonPath.getString("error"),
                    "Object with id=" + idObject + " was not found.",
                    "Pesan error setelah DELETE tidak sesuai.");
    
            System.out.println("✅ Object berhasil dihapus!");
        }
    }
}


/*
 * Gherkin
 * 1. Feature
 * - Given, Then, When, And, But
 * 
 * 
 * - Checkout Barang
 * Given :
 * - User Login to Apps
 * 
 * When :
 * - Action -> User checkout item
 * 
 * Then :
 * - Result/Expectation Scenario
 * - User Successfully Checkout
 * 
 * And :
 * Simply Prefix di Step
 * 
 * 2. Stepdefenition
 * 3. Runner
 * 
 * 
 */