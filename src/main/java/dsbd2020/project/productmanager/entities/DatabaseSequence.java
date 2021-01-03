package dsbd2020.project.productmanager.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
public class DatabaseSequence {

    @Id
    private String id;
    private Integer seq;

    public String getId() {
        return id;
    }
    public DatabaseSequence setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getSeq() {
        return seq;
    }
    public DatabaseSequence setSeq(Integer seq) {
        this.seq = seq;
        return this;
    }
}
