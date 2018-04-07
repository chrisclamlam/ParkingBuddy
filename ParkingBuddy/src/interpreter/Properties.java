
package interpreter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Properties {

    @SerializedName("OBJECTID")
    @Expose
    private Integer oBJECTID;
    @SerializedName("SENSOR_UNIQUE_ID")
    @Expose
    private String sENSORUNIQUEID;
    @SerializedName("GPSX")
    @Expose
    private Double gPSX;
    @SerializedName("GPSY")
    @Expose
    private Double gPSY;
    @SerializedName("MTYPE")
    @Expose
    private String mTYPE;
    @SerializedName("SENSOR_STATUS")
    @Expose
    private Integer sENSORSTATUS;
    @SerializedName("PMZ")
    @Expose
    private String pMZ;
    @SerializedName("PMZ2")
    @Expose
    private String pMZ2;
    @SerializedName("ADDRESS_SPACE")
    @Expose
    private String aDDRESSSPACE;
    @SerializedName("TOOLTIP")
    @Expose
    private String tOOLTIP;
    @SerializedName("NLA_URL")
    @Expose
    private String nLAURL;

    public Integer getOBJECTID() {
        return oBJECTID;
    }

    public void setOBJECTID(Integer oBJECTID) {
        this.oBJECTID = oBJECTID;
    }

    public String getSENSORUNIQUEID() {
        return sENSORUNIQUEID;
    }

    public void setSENSORUNIQUEID(String sENSORUNIQUEID) {
        this.sENSORUNIQUEID = sENSORUNIQUEID;
    }

    public Double getGPSX() {
        return gPSX;
    }

    public void setGPSX(Double gPSX) {
        this.gPSX = gPSX;
    }

    public Double getGPSY() {
        return gPSY;
    }

    public void setGPSY(Double gPSY) {
        this.gPSY = gPSY;
    }

    public String getMTYPE() {
        return mTYPE;
    }

    public void setMTYPE(String mTYPE) {
        this.mTYPE = mTYPE;
    }

    public Integer getSENSORSTATUS() {
        return sENSORSTATUS;
    }

    public void setSENSORSTATUS(Integer sENSORSTATUS) {
        this.sENSORSTATUS = sENSORSTATUS;
    }

    public String getPMZ() {
        return pMZ;
    }

    public void setPMZ(String pMZ) {
        this.pMZ = pMZ;
    }

    public String getPMZ2() {
        return pMZ2;
    }

    public void setPMZ2(String pMZ2) {
        this.pMZ2 = pMZ2;
    }

    public String getADDRESSSPACE() {
        return aDDRESSSPACE;
    }

    public void setADDRESSSPACE(String aDDRESSSPACE) {
        this.aDDRESSSPACE = aDDRESSSPACE;
    }

    public String getTOOLTIP() {
        return tOOLTIP;
    }

    public void setTOOLTIP(String tOOLTIP) {
        this.tOOLTIP = tOOLTIP;
    }

    public String getNLAURL() {
        return nLAURL;
    }

    public void setNLAURL(String nLAURL) {
        this.nLAURL = nLAURL;
    }

}
