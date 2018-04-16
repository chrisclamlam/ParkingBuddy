
package interpreter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coords {

    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    
    public Coords(double lat, double lng) {
    	latitude = lat;
    	longitude = lng;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
