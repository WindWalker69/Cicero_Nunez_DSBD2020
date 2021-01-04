package dsbd2020.project.productmanager.messageKafka;

import java.util.Map;

public class ProductUpdateResponse {


    //long unixTime;
    Integer status;
    Integer orderId;
    Map<Integer, Integer> extraArgs;
    long timestamp;


    public Integer getStatus() {
        return status;
    }

    public ProductUpdateResponse setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public ProductUpdateResponse setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public Map<Integer, Integer> getExtraArgs() {
        return extraArgs;
    }

    public ProductUpdateResponse setExtraArgs(Map<Integer, Integer> extraArgs) {
        this.extraArgs = extraArgs;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public ProductUpdateResponse setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
