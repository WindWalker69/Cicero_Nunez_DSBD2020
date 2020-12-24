package dsbd2020.project.productmanager.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Categories")
public class Categories {

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