package hjx.com.searchpark.Entity;

import java.io.Serializable;

/**
 * Created by cs001 on 2016/6/24.
 */
public class ParkEntity implements Serializable{

    /**
     * CCID : 6202
     * JD : 116.339689
     * WD : 39.991769
     * CCMC : 优盛大厦（五道口购物中心）地下停车场
     * CCDZ : 海淀区成府路28号
     * ZCW : 448
     * KCW : 71
     * CCFL : 非露天地下停车场
     * CCLX : 平面自走式
     * CCTP : 6202.jpg  http://images.juheapi.com/park/+CCTP
     * QYCS : 北京市
     * KCWZT : P1003.png   停车场空车位状态图片（P1001.png：车位充足； P1002.png：车位够用； P1003.png：车位较少；P1004.png：车位紧张；P1005.png：车位未知；P1006.png：车位关闭；）[路径：http://images.juheapi.com/park/+KCWZT]
     */

    private String CCID;
    private Double JD;
    private Double WD;
    private String CCMC;
    private String CCDZ;
    private String ZCW;
    private String KCW;
    private String CCFL;
    private String CCLX;
    private String CCTP;
    private String QYCS;
    private String KCWZT;
    private int distance;

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "ParkEntity{" +
                "CCDZ='" + CCDZ + '\'' +
                ", CCID='" + CCID + '\'' +
                ", JD=" + JD +
                ", WD=" + WD +
                ", CCMC='" + CCMC + '\'' +
                ", ZCW='" + ZCW + '\'' +
                ", KCW='" + KCW + '\'' +
                ", CCFL='" + CCFL + '\'' +
                ", CCLX='" + CCLX + '\'' +
                ", CCTP='" + CCTP + '\'' +
                ", QYCS='" + QYCS + '\'' +
                ", KCWZT='" + KCWZT + '\'' +
                ", distance=" + distance +
                '}';
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getCCID() {
        return CCID;
    }

    public void setCCID(String CCID) {
        this.CCID = CCID;
    }

    public Double getJD() {
        return JD;
    }

    public void setJD(Double JD) {
        this.JD = JD;
    }

    public Double getWD() {
        return WD;
    }

    public void setWD(Double WD) {
        this.WD = WD;
    }

    public String getCCMC() {
        return CCMC;
    }

    public void setCCMC(String CCMC) {
        this.CCMC = CCMC;
    }

    public String getCCDZ() {
        return CCDZ;
    }

    public void setCCDZ(String CCDZ) {
        this.CCDZ = CCDZ;
    }

    public String getZCW() {
        return ZCW;
    }

    public void setZCW(String ZCW) {
        this.ZCW = ZCW;
    }

    public String getKCW() {
        return KCW;
    }

    public void setKCW(String KCW) {
        this.KCW = KCW;
    }

    public String getCCFL() {
        return CCFL;
    }

    public void setCCFL(String CCFL) {
        this.CCFL = CCFL;
    }

    public String getCCLX() {
        return CCLX;
    }

    public void setCCLX(String CCLX) {
        this.CCLX = CCLX;
    }

    public String getCCTP() {
        return CCTP;
    }

    public void setCCTP(String CCTP) {
        this.CCTP = CCTP;
    }

    public String getQYCS() {
        return QYCS;
    }

    public void setQYCS(String QYCS) {
        this.QYCS = QYCS;
    }

    public String getKCWZT() {
        return KCWZT;
    }

    public void setKCWZT(String KCWZT) {
        this.KCWZT = KCWZT;
    }

}
