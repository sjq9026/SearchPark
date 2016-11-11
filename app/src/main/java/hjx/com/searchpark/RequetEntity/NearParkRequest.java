package hjx.com.searchpark.RequetEntity;

/**
 * Created by cs001 on 2016/6/22.
 */
public class NearParkRequest {
    //申请的key
    String key;
    //地图中心的经度
    float JD;
    //地图中心的纬度
    float WD;
    //	读取深度信息（0：不读取；1：读取；默认为：0）
    int SDXX;
    //停车场分类(1：占道停车场； 2：路外露天停车场； 3：非露天停车场地上； 4：非露天停车场地下； )
    int TCCFL;
    //停车场类型（1：平面自走式；2：机械式；3：立体自动车）
    int TCCLX;
    //距离查询（最多支持0-3000米之内的查询）
    int JLCX;
    //距离排序（1：正序[由近到远]；2:倒序[由远到近]；默认为1正序）
    int JLPX;

    public float getJD() {
        return JD;
    }

    public void setJD(float JD) {
        this.JD = JD;
    }

    public int getJLCX() {
        return JLCX;
    }

    public void setJLCX(int JLCX) {
        this.JLCX = JLCX;
    }

    public int getJLPX() {
        return JLPX;
    }

    public void setJLPX(int JLPX) {
        this.JLPX = JLPX;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getSDXX() {
        return SDXX;
    }

    public void setSDXX(int SDXX) {
        this.SDXX = SDXX;
    }

    public int getTCCFL() {
        return TCCFL;
    }

    public void setTCCFL(int TCCFL) {
        this.TCCFL = TCCFL;
    }

    public int getTCCLX() {
        return TCCLX;
    }

    public void setTCCLX(int TCCLX) {
        this.TCCLX = TCCLX;
    }

    public float getWD() {
        return WD;
    }

    public void setWD(float WD) {
        this.WD = WD;
    }

    @Override
    public String toString() {
        return "NearParkRequest{" +
                "JD=" + JD +
                ", Key='" + key + '\'' +
                ", WD=" + WD +
                ", SDXX=" + SDXX +
                ", TCCFL=" + TCCFL +
                ", TCCLX=" + TCCLX +
                ", JLCX=" + JLCX +
                ", JLPX=" + JLPX +
                '}';
    }
}
