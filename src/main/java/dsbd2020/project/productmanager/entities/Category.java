package dsbd2020.project.productmanager.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "Category")
public class Category {

    @Transient
    public static final String SEQUENCE_NAME = "category_sequence";

    @Id
    private Integer id;

    @NotNull
    private String category;

    public Integer getId() {
        return id;
    }
    public Category setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getCategory() {
        return category;
    }
    public Category setCategory(String category) {
        this.category = category;
        return this;
    }
}