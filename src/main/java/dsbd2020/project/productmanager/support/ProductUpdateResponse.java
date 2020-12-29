package dsbd2020.project.productmanager.support;

import java.time.Instant;
import java.util.Map;

public class ProductUpdateResponse {


    //long unixTime;
    Integer status;
    Integer userID;
    Map<Integer, Integer> extraArgs;

  /*  public ProductUpdateResponse() {
        this.unixTime =  Instant.now();
    }

    public long getUnixTime() {
        return unixTime;
    }

    public ProductUpdateResponse setUnixTime(long unixTime) {
        this.unixTime = unixTime;
        return this;
    }
*/
    public Integer getStatus() {
        return status;
    }

    public ProductUpdateResponse setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getUserID() {
        return userID;
    }

    public ProductUpdateResponse setUserID(Integer userID) {
        this.userID = userID;
        return this;
    }

    public Map<Integer, Integer> getExtraArgs() {
        return extraArgs;
    }

    public ProductUpdateResponse setExtraArgs(Map<Integer, Integer> extraArgs) {
        this.extraArgs = extraArgs;
        return this;
    }
}
