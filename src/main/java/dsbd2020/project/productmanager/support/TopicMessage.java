package dsbd2020.project.productmanager.support;

public class TopicMessage {

    String key;
    Object value;

    public String getKey() {
        return key;
    }

    public TopicMessage setKey(String key) {
        this.key = key;
        return this;
    }


    public Object getValue() {
        return value;
    }

    public TopicMessage setValue(Object value) {
        this.value = value;
        return this;
    }

    public TopicMessage setValue(ProductUpdateResponse value) {
        this.value = value;
        return this;
    }
}
