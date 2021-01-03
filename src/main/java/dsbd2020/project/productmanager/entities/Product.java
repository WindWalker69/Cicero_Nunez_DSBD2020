package dsbd2020.project.productmanager.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "Product")
public class Product {

    @Transient
    public static final String SEQUENCE_NAME = "product_sequence";

    @Id
    private Integer id;

    @NotNull(message = "brand field cannot be blank!")
    private String brand;

    @NotNull(message = "model field cannot be blank!")
    private String model;

    @DBRef
    @NotNull(message = "category field cannot be blank")
    private Category category;

    private String description;

    @NotNull(message = "price field cannot be blank!")
    private Double price;

    @NotNull(message = "quantity field cannot be blank!")
    private Integer quantity;

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

    public String getModel() {
        return model;
    }
    public Product setModel(String model) {
        this.model = model;
        return this;
    }

    public Category getCategory() {
        return category;
    }
    public Product setCategory(Category category) {
        this.category = category;
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
