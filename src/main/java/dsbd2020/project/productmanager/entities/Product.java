package dsbd2020.project.productmanager.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "products")
public class Product {

    @Id
    private Integer id;

    @DBRef
    private Categories category;

    @NotNull
    private String brand;

    @NotNull
    private String model;

    private String description;

    @NotNull
    private Double price;

    @NotNull
    private Integer quantity;

    public Product setProductRequest(ProductRequest productRequest, Categories category) {
        this.id = productRequest.getId();
        this.category = category;
        this.brand = productRequest.getBrand();
        this.model = productRequest.getModel();
        this.description = productRequest.getDescription();
        this.price = productRequest.getPrice();
        this.quantity = productRequest.getQuantity();
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Product setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public Product setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public Categories getCategory() {
        return category;
    }

    public Product setCategory(Categories category) {
        this.category = category;
        return this;
    }

    public String getModel() {
        return model;
    }

    public Product setModel(String model) {
        this.model = model;
        return this;
    }



    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Product setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
