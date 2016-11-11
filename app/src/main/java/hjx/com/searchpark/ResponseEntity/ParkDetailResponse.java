package hjx.com.searchpark.ResponseEntity;

import java.util.List;

import hjx.com.searchpark.Entity.ParkDetailEntity;

/**
 * Created by cs001 on 2016/6/30.
 */
public class ParkDetailResponse {
    int error_code;
    String reason;
    List<ParkDetailEntity> result;

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

    public List<ParkDetailEntity> getResult() {
        return result;
    }

    public void setResult(List<ParkDetailEntity> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ParkDetailResponse{" +
                "error_code=" + error_code +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }
}
