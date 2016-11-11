package hjx.com.searchpark.ResponseEntity;

import java.util.List;

import hjx.com.searchpark.Entity.ParkEntity;

/**
 * Created by cs001 on 2016/6/24.
 */
public class NearParkResponse {

    /**
     * error_code : 0
     * reason : success
     * count : 5
     * result:[]
     */

    private int error_code;
    private String reason;
    private int count;
    private List<ParkEntity> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ParkEntity> getResult() {
        return result;
    }

    public void setResult(List<ParkEntity> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "NearParkResponse{" +
                "count=" + count +
                ", error_code=" + error_code +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
