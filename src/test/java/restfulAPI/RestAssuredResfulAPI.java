package restfulAPI;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredResfulAPI {
    public static void main(String[] args) {
        // getAllObjects();
        // getAllObjectsByIds();
        // getSingleObjects();
        // addObject();
        // updateObject();
        // partiallyUpdateObject();
        deleteObject();
}
    public static void getAllObjects() {

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                                    .log()
                                    .all()
                                    .when()
                                        .get("objects");
            
            System.out.println("Ini adalah hasil search: \n" +response.asPrettyString());
        }

    public static void getAllObjectsByIds() {

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
        }
    
    public static void getSingleObjects() {

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();
        
        Response response = requestSpecification
                                    .log()
                                    .all()
                                    .queryParam("id", 7)
                                .when()
                                    .get("objects"); 
                    
            System.out.println("Ini adalah hasil search:\n" + response.asPrettyString());
        }

    public static void addObject(){

        String json = "{\r\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\r\n" + //
                        "   \"data\": {\r\n" + //
                        "      \"year\": 2019,\r\n" + //
                        "      \"price\": 1849.99,\r\n" + //
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
    }

    public static void updateObject(){

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
                        .pathParam("idObject", "ff808181932badb60195135007244121")
                        .body(json)
                        .contentType("application/json")
                        .when()
                            .put("{path}/{idObject}");

    System.out.println("Update object response: \n" + response.asPrettyString());
}

    public static void partiallyUpdateObject(){

        String json = "{\r\n" + //
                        "   \"name\": \"Apple MacBook Pro 16 (Updated Name)\"\r\n" + //
                        "}";

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

    Response response = requestSpecification 
                        .log()
                        .all()
                        .pathParam("idObject", "ff808181932badb60195135007244121")  // Ganti dengan ID yang valid
                        .body(json)
                        .contentType("application/json")
                        .when()
                            .patch("/objects/{idObject}");

    System.out.println("Partially Update object response: \n" + response.asPrettyString());

}

    public static void deleteObject(){

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

    Response response = requestSpecification 
                        .log()
                        .all()
                        .pathParam("path", "objects")
                        .pathParam("idObject", "ff808181932badb601951375659d422d")
                        .contentType("application/json")
                        .when()
                            .delete("{path}/{idObject}");

    System.out.println("delete objects " + response.asPrettyString());
}

}

