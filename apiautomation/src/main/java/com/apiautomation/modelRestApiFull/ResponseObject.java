package com.apiautomation.modelRestApiFull;

import com.apiautomation.modelRestApiFull.ResponseObject.DataItem;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseObject {

    /*
     * {
        "id": "7",
        "name": "Apple MacBook Pro 16",
        "data": {
            "year": 2019,
            "price": 1849.99,
            "CPU model": "Intel Core i9",
            "Hard disk size": "1 TB"
        },
        "createdAt": "2022-11-21T20:06:23.986Z"
        }
     */

    @JsonProperty("id")
    public String id;

    @JsonProperty("createdAt")
    public String createdAt;

    @JsonProperty("name")
    public String name;

    @JsonProperty("updatedAt")
    public String updatedAt;

    @JsonProperty("data")
    public DataItem dataItem;

    @JsonProperty("updateAt")
    public String updateAt;

    public static class DataItem{
        @JsonProperty("year")
        public int year;

        @JsonProperty("price")
        public int price;

        @JsonProperty("CPU model")
        public String cpuModel;

        @JsonProperty("Hard disk size")
        public String hardiskSize;

        @JsonProperty("color")
        public String color;

        @JsonProperty("Screen size") // Sesuai dengan JSON
        public Double screenSize;

        @JsonProperty("capacityGB")
        public Integer capacityGB;

        public Integer getCapacityGB() {
            return capacityGB;
        }

        public Double getScreenSize() {
            return screenSize;
        }
    }

    // Tambahkan Kelas DeleteResponse untuk menangani respons DELETE
    public static class DeleteResponse {
        @JsonProperty("message")
        public String message;

        @JsonProperty("error")
        public String error;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    // Kelas untuk menangani respons GET Single Object
    public static class SingleObjectResponse {
        @JsonProperty("id")
        public String id;

        @JsonProperty("name")
        public String name;

        @JsonProperty("data")
        public DataItem dataItem;
    }

}