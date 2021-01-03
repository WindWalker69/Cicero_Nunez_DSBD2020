package dsbd2020.project.productmanager.message;

import java.io.Serializable;

public class HttpErrorMessage implements Serializable {
    private String timestamp;
    private String sourceIp;
    private String service;
    private String request;
    private String error;

    public String getTimestamp() {
        return timestamp;
    }
    public HttpErrorMessage setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getSourceIp() {
        return sourceIp;
    }
    public HttpErrorMessage setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
        return this;
    }

    public String getService() {
        return service;
    }
    public HttpErrorMessage setService(String service) {
        this.service = service;
        return this;
    }

    public String getRequest() {
        return request;
    }
    public HttpErrorMessage setRequest(String request) {
        this.request = request;
        return this;
    }

    public String getError() {
        return error;
    }
    public HttpErrorMessage setError(String error) {
        this.error = error;
        return this;
    }
}
