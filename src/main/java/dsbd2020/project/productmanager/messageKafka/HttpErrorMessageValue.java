package dsbd2020.project.productmanager.messageKafka;

import java.io.Serializable;

public class HttpErrorMessageValue implements Serializable {
    private long timestamp;
    private String sourceIp;
    private String service;
    private String request;
    private String error;

    public long getTimestamp() {
        return timestamp;
    }
    public HttpErrorMessageValue setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getSourceIp() {
        return sourceIp;
    }
    public HttpErrorMessageValue setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
        return this;
    }

    public String getService() {
        return service;
    }
    public HttpErrorMessageValue setService(String service) {
        this.service = service;
        return this;
    }

    public String getRequest() {
        return request;
    }
    public HttpErrorMessageValue setRequest(String request) {
        this.request = request;
        return this;
    }

    public String getError() {
        return error;
    }
    public HttpErrorMessageValue setError(String error) {
        this.error = error;
        return this;
    }
}
