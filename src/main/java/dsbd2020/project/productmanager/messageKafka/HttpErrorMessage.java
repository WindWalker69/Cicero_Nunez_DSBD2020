package dsbd2020.project.productmanager.messageKafka;

public class HttpErrorMessage {

    String key;
    HttpErrorMessageValue value;

    public String getKey() {
        return key;
    }

    public HttpErrorMessage setKey(String key) {
        this.key = key;
        return this;
    }

    public HttpErrorMessageValue getValue() {
        return value;
    }

    public HttpErrorMessage setValue(HttpErrorMessageValue value) {
        this.value = value;
        return this;
    }
}
