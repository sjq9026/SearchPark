package hjx.com.searchpark.Entity;

import java.io.Serializable;

/**
 * Created by cs001 on 2016/6/30.
 */
public class ParkDetailEntity implements Serializable{

    /**
     * CCID : 23070
     * CCMC : 大地大厦地下收费停车场
     * JD : 118.778
     * WD : 32.047243
     * ZCW : 132
     * KCW : 51
     * CCTP : 23070.jpg
     * CCDZ : 华侨路56号
     * CCFL : 非露天地下停车场
     * CCLX : 平面自走式
     * BTTCJG : 小型车2元/15分钟；大型车3元/15分钟(08:00-20:00)
     * WSTCJG : 小型车1元/每小时；大型车1.5元/每小时(20:00-08:00)
     * YYKSSJ : 00:00:00
     * YYJSSJ : 23:59:00
     * SFKF : 1  是否开放(0：不开放；1：开放；)
     * JCSFA :	计次收费-大型车[示例：10元/次、19：30-07：30(10元/次)]
     * JCSFB :  计次收费-小型车[示例：10元/次、19：30-07：30(10元/次)]
     * KCWZT : P1002.png
     * QYCS : 南京市
     */

    private String CCID;
    private String CCMC;
    private double JD;
    private double WD;
    private int ZCW;
    private int KCW;
    private String CCTP;
    private String CCDZ;
    private String CCFL;
    private String CCLX;
    private String BTTCJG;
    private String WSTCJG;
    private String YYKSSJ;
    private String YYJSSJ;
    private String SFKF;
    private String JCSFA;
    private String JCSFB;
    private String KCWZT;
    private String QYCS;

    public String getCCID() {
        return CCID;
    }

    public void setCCID(String CCID) {
        this.CCID = CCID;
    }

    public String getCCMC() {
        return CCMC;
    }

    public void setCCMC(String CCMC) {
        this.CCMC = CCMC;
    }

    public double getJD() {
        return JD;
    }

    public void setJD(double JD) {
        this.JD = JD;
    }

    public double getWD() {
        return WD;
    }

    public void setWD(double WD) {
        this.WD = WD;
    }

    public int getZCW() {
        return ZCW;
    }

    public void setZCW(int ZCW) {
        this.ZCW = ZCW;
    }

    public int getKCW() {
        return KCW;
    }

    public void setKCW(int KCW) {
        this.KCW = KCW;
    }

    public String getCCTP() {
        return CCTP;
    }

    public void setCCTP(String CCTP) {
        this.CCTP = CCTP;
    }

    public String getCCDZ() {
        return CCDZ;
    }

    public void setCCDZ(String CCDZ) {
        this.CCDZ = CCDZ;
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

    public String getBTTCJG() {
        return BTTCJG;
    }

    public void setBTTCJG(String BTTCJG) {
        this.BTTCJG = BTTCJG;
    }

    public String getWSTCJG() {
        return WSTCJG;
    }

    public void setWSTCJG(String WSTCJG) {
        this.WSTCJG = WSTCJG;
    }

    public String getYYKSSJ() {
        return YYKSSJ;
    }

    public void setYYKSSJ(String YYKSSJ) {
        this.YYKSSJ = YYKSSJ;
    }

    public String getYYJSSJ() {
        return YYJSSJ;
    }

    public void setYYJSSJ(String YYJSSJ) {
        this.YYJSSJ = YYJSSJ;
    }

    public String getSFKF() {
        return SFKF;
    }

    public void setSFKF(String SFKF) {
        this.SFKF = SFKF;
    }

    public String getJCSFA() {
        return JCSFA;
    }

    public void setJCSFA(String JCSFA) {
        this.JCSFA = JCSFA;
    }

    public String getJCSFB() {
        return JCSFB;
    }

    public void setJCSFB(String JCSFB) {
        this.JCSFB = JCSFB;
    }

    public String getKCWZT() {
        return KCWZT;
    }

    public void setKCWZT(String KCWZT) {
        this.KCWZT = KCWZT;
    }

    public String getQYCS() {
        return QYCS;
    }

    public void setQYCS(String QYCS) {
        this.QYCS = QYCS;
    }

    @Override
    public String toString() {
        return "ParkDetailEntity{" +
                "BTTCJG='" + BTTCJG + '\'' +
                ", CCID='" + CCID + '\'' +
                ", CCMC='" + CCMC + '\'' +
                ", JD=" + JD +
                ", WD=" + WD +
                ", ZCW=" + ZCW +
                ", KCW=" + KCW +
                ", CCTP='" + CCTP + '\'' +
                ", CCDZ='" + CCDZ + '\'' +
                ", CCFL='" + CCFL + '\'' +
                ", CCLX='" + CCLX + '\'' +
                ", WSTCJG='" + WSTCJG + '\'' +
                ", YYKSSJ='" + YYKSSJ + '\'' +
                ", YYJSSJ='" + YYJSSJ + '\'' +
                ", SFKF='" + SFKF + '\'' +
                ", JCSFA='" + JCSFA + '\'' +
                ", JCSFB='" + JCSFB + '\'' +
                ", KCWZT='" + KCWZT + '\'' +
                ", QYCS='" + QYCS + '\'' +
                '}';
    }
}
