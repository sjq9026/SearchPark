package hjx.com.searchpark.RequetEntity;

/**
 * Created by cs001 on 2016/6/30.
 */
public class ParkDetailRequest {
    /*名称	     类型	必填   	说明
        key 	string	 是	  你申请的key
         CCID	string	 是	   停车场的CCID,多个用英文逗号分隔,如CCID=23070,52114*/

    private String key;
    private String CCID;

    public String getCCID() {
        return CCID;
    }

    public void setCCID(String CCID) {
        this.CCID = CCID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "ParkDetailRequest{" +
                "CCID='" + CCID + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
