
package interpreter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSONParkingSpot {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("remoteid")
    @Expose
    private String remoteid;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("spotType")
    @Expose
    private Integer spotType;
    @SerializedName("coodrs")
    @Expose
    private Coords coords;
    
    public JSONParkingSpot(int id, String remoteid, String label, int spotType, double latitude, double longitude) {
    	this.id = id;
    	this.remoteid = remoteid;
    	this.label = label;
    	this.spotType = spotType;
    	this.coords = new Coords(latitude, longitude);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemoteid() {
        return remoteid;
    }

    public void setRemoteid(String remoteid) {
        this.remoteid = remoteid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getSpotType() {
        return spotType;
    }

    public void setSpotType(Integer spotType) {
        this.spotType = spotType;
    }

    public Coords getCoodrs() {
        return coords;
    }

    public void setCoodrs(Coords coords) {
        this.coords = coords;
    }

}
