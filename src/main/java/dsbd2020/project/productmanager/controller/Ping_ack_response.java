package dsbd2020.project.productmanager.controller;

public class Ping_ack_response {
    
    String serviceStatus;
    String dbStatus;

    public String getServiceStatus() {
        return serviceStatus;
    }

    public Ping_ack_response setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
        return this;
    }

    public String getDbStatus() {
        return dbStatus;
    }

    public Ping_ack_response setDbStatus(String dbStatus) {
        this.dbStatus = dbStatus;
        return this;
    }
}
