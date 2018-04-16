
package uber;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price implements Serializable
{

	public Price(double low, double high)
	{
		self.highEstimate = (Object) high;
		self.lowEstimate = (Object) low;
	}
    @SerializedName("localized_display_name")
    @Expose
    private String localizedDisplayName;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("high_estimate")
    @Expose
    private Object highEstimate;
    @SerializedName("low_estimate")
    @Expose
    private Object lowEstimate;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("estimate")
    @Expose
    private String estimate;
    @SerializedName("currency_code")
    @Expose
    private Object currencyCode;
    private final static long serialVersionUID = -7556753245276026255L;

    public String getLocalizedDisplayName() {
        return localizedDisplayName;
    }

    public void setLocalizedDisplayName(String localizedDisplayName) {
        this.localizedDisplayName = localizedDisplayName;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Object getHighEstimate() {
        return highEstimate;
    }

    public void setHighEstimate(Object highEstimate) {
        this.highEstimate = highEstimate;
    }

    public Object getLowEstimate() {
        return lowEstimate;
    }

    public void setLowEstimate(Object lowEstimate) {
        this.lowEstimate = lowEstimate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public Object getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Object currencyCode) {
        this.currencyCode = currencyCode;
    }

}
