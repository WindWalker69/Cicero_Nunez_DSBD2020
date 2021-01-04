package dsbd2020.project.productmanager.messageKafka;

public class TopicOrderValidation {


    String key;
    ProductUpdateResponse value;

    public String getKey() {
        return key;
    }

    public TopicOrderValidation setKey(String key) {
        this.key = key;
        return this;
    }

    public ProductUpdateResponse getValue() {
        return value;
    }

    public TopicOrderValidation setValue(ProductUpdateResponse value) {
        this.value = value;
        return this;
    }
}
