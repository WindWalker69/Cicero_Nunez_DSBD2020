package dsbd2020.project.productmanager.messageKafka;

public class TopicOrderCompleted {

    String key;
    ProductUpdateRequest value;

    public String getKey() {
        return key;
    }

    public TopicOrderCompleted setKey(String key) {
        this.key = key;
        return this;
    }

    public ProductUpdateRequest getValue() {
        return value;
    }

    public TopicOrderCompleted setValue(ProductUpdateRequest value) {
        this.value = value;
        return this;
    }
}
