package resources;

import java.util.HashMap;
import java.util.Map;

public class DRequestRestfulAPI {
    //Berisi data test
    /*
     * Mapping berisi 2 komponen
     * 1. Key
     * 2. Value
     */
    public Map<String, String> addItemCollection(){
        Map<String, String> dataCollection = new HashMap<>();

        dataCollection.put("addItem", "{\r\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\r\n" + //
                        "   \"data\": {\r\n" + //
                        "      \"year\": 2019,\r\n" + //
                        "      \"price\": 1900,\r\n" + //
                        "      \"CPU model\": \"Intel Core i9\",\r\n" + //
                        "      \"Hard disk size\": \"1 TB\"\r\n" + //
                        "   }\r\n" + //
                        "}");

        dataCollection.put("addItem2", "{\r\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\r\n" + //
                        "   \"data\": {\r\n" + //
                        "      \"year\": 2019,\r\n" + //
                        "      \"price\": 1900,\r\n" + //
                        "      \"CPU model\": \"Intel Core i9\",\r\n" + //
                        "      \"Hard disk size\": \"1 TB\"\r\n" + //
                        "   }\r\n" + //
                        "}");
        return dataCollection;
    }

    public Map<String, String> updateItemCollection(){
        Map<String, String> dataCollection = new HashMap<>();

        
        dataCollection.put("updateItem", "{\r\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\r\n" + //
                        "   \"data\": {\r\n" + //
                        "      \"year\": 2019,\r\n" + //
                        "      \"price\": 1900,\r\n" + //
                        "      \"CPU model\": \"Intel Core i9\",\r\n" + //
                        "      \"Hard disk size\": \"1 TB\"\r\n" + //
                        "   }\r\n" + //
                        "}");

        dataCollection.put("updateItem2", "{\r\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\r\n" + //
                        "   \"data\": {\r\n" + //
                        "      \"year\": 2019,\r\n" + //
                        "      \"price\": 1900,\r\n" + //
                        "      \"CPU model\": \"Intel Core i9\",\r\n" + //
                        "      \"Hard disk size\": \"1 TB\"\r\n" + //
                        "   }\r\n" + //
                        "}");
                        
        return dataCollection;


    }

}