package dsbd2020.project.productmanager.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Categories")
public class Categories {

    @Transient
    public static final String SEQUENCE_NAME = "categories_sequence";

    @Id
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public Categories setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Categories setName(String name) {
        this.name = name;
        return this;
    }
}