package com.apiautomation.modelRestApiFull.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RItemRestfulAPI {

    /*
     * "{\r\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\r\n" + //
                        "   \"data\": {\r\n" + //
                        "      \"year\": 2019,\r\n" + //
                        "      \"price\": 1900,\r\n" + //
                        "      \"CPU model\": \"Intel Core i9\",\r\n" + //
                        "      \"Hard disk size\": \"1 TB\"\r\n" + //
                        "   }\r\n" + //
                        "}";
     */

    @JsonProperty("name")
    public String name;

    @JsonProperty("data")
    public DataItem dataItem;

    public static class DataItem{
        @JsonProperty("year")
        public int year;

        @JsonProperty("price")
        public int price;

        @JsonProperty("CPU model")
        public String cpuModel;

        @JsonProperty("Hard disk size")
        public String hardiskSize;

    }
}
