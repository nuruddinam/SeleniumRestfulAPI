package restAssuredDummyJson;

import io.restassured.path.json.JsonPath;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;

public class RestAssuredImpl {
    public static void main(String[] args) {
        // getAllProducts();
        getSingleProduct();
        // searchProduct();
        // addProduct();
        // updateProduct();
        // deleteProduct();
        // auth();
    }

    // public static void auth() {
        
    //     String json = "{\n" + //
    //                     "    \"username\": \"emilys\",\n" + //
    //                     "    \"password\": \"emilyspass\",\n" + //
    //                     "    \"expiresInMins\": 30\n" + //
    //                     "}";

    //     RestAssured.baseURI = "https://dummyjson.com";
    //     RequestSpecification requestSpecification = RestAssured
    //                                                 .given();

    //     Response response  = requestSpecification
    //                              .log()
    //                              .all()
    //                              .contentType("application/json")
    //                              .body(json)
    //                              .pathParam("path", "auth")
    //                              .pathParam("section", "login")
    //                             .when()
    //                              .post ("{path}/{section}");
            
    //     System.out.println("Login " + response.asPrettyString());

    // }

    public static String auth() {
        
        String token;
        String json = "{\n" + //
                        "    \"username\": \"emilys\",\n" + //
                        "    \"password\": \"emilyspass\",\n" + //
                        "    \"expiresInMins\": 30\n" + //
                        "}";

        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response  = requestSpecification
                                 .log()
                                 .all()
                                 .contentType("application/json")
                                 .body(json)
                                 .pathParam("path", "auth")
                                 .pathParam("section", "login")
                                .when()
                                 .post ("{path}/{section}");
            
        System.out.println("Login " + response.asPrettyString());
        JsonPath jsonPath = response.jsonPath();

        System.out.println("token" + jsonPath.get("accessToken"));

        token = jsonPath.get("accessToken");

        return token;

    }

    public static void getAllProducts() {

        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();
                            
        Response response = requestSpecification.log().all().get("products");

        Response response2 = requestSpecification
                             .given()
                                 .log()
                                 .all()
                             .when()
                                 .get ("products");

        System.out.println("Hasilnya adalah " + response2.asPrettyString());

    }

    public static void getSingleProduct(){
        /*
         * 'https://dummyjson.com/products/1'
         */

        String token;
        token = auth();
        
        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();
        
        Response response = requestSpecification
                                .log() 
                                .all()
                                .pathParam("idProduct", 1)
                                .pathParam("path", "products")
                                .header(" Authorization ", " Bearer " + token)
                            .when()
                                .get("{path}/{idProduct}");

        System.out.println("single Product" + response.asPrettyString());
    }
    
    public static void searchProduct(){
        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();
        
        Response response = requestSpecification
                                .log()
                                .all()
                                .pathParam("path", "products")
                                .pathParam("method", "search")
                                .queryParam("q", "phone")
                                .when()
                                    .get("{path}/{method}");
        
        System.out.println("Ini adalah hasil search" +response.asPrettyString());
    }

    public static void addProduct(){

        String json = " {\r\n" + //
                        "    \"title\": \"BMW Pencil\",\r\n" + //
                        "    \"price\": \"25.000\",\r\n" + //
                        "    \"description\" : \"test\"\r\n" + //
                        " }";

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
    
    System.out.println("add product" + response.asPrettyString());
    }

    public static void updateProduct(){

        String json = " {\r\n" + //
                        "    \"title\": \"Nuruddin\",\r\n" + //
                        "    \"price\": \"25.000\",\r\n" + //
                        "    \"description\" : \"test\"\r\n" + //
                        " }";

        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

    Response response = requestSpecification 
                        .log()
                        .all()
                        .pathParam("path", "products")
                        .pathParam("idProduct", "1")
                        .body(json)
                        .contentType("application/json")
                        .when()
                            .put("{path}/{idProduct}");
    
    System.out.println("update product" + response.asPrettyString());
    }

    public static void deleteProduct(){

        RestAssured.baseURI = "https://dummyjson.com";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

    Response response = requestSpecification 
                        .log()
                        .all()
                        .pathParam("path", "products")
                        .pathParam("idProduct", "1")
                        .contentType("application/json")
                        .when()
                            .delete("{path}/{idProduct}");
    
    System.out.println("delete product" + response.asPrettyString());
    }

}


