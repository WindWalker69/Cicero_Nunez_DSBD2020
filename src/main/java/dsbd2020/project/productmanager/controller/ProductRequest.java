package dsbd2020.project.productmanager.controller;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class ProductRequest {

    @Id
    private Integer id;

    @NotNull
    private String brand;

    @NotNull
    private String model;

    @NotNull
    private String category;

    private String description;

    @NotNull
    private Double price;

    @NotNull
    private Integer quantity;

    public Integer getId() {
        return id;
    }
    public ProductRequest setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getBrand() {
        return brand;
    }
    public ProductRequest setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }
    public ProductRequest setModel(String model) {
        this.model = model;
        return this;
    }

    public String getCategory() {
        return category;
    }
    public ProductRequest setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }
    public ProductRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }
    public ProductRequest setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public ProductRequest setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
