package com.apiautomation.modelDummyJson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) // Mengabaikan properti yang tidak ada di model ini
public class ResponseItem {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("discountPercentage")
    private Double discountPercentage;

    @JsonProperty("rating")
    private Double rating;

    @JsonProperty("stock")
    private Integer stock;

    @JsonProperty("category")
    private String category;

    // Getter
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getStock() {
        return stock;
    }

    public String getCategory() {
        return category;
    }

    // Setter
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
