
package Lyft;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CostEstimate implements Serializable
{
	public CostEstimate(int estimatedCostCentsMax, int estimatedCostCentsMin)
	{
		this.estimatedCostCentsMax = estimatedCostCentsMax;
		this.estimatedCostCentsMin
	}

    @SerializedName("ride_type")
    @Expose
    private String rideType;
    @SerializedName("estimated_duration_seconds")
    @Expose
    private Integer estimatedDurationSeconds;
    @SerializedName("estimated_distance_miles")
    @Expose
    private Double estimatedDistanceMiles;
    @SerializedName("estimated_cost_cents_max")
    @Expose
    private Integer estimatedCostCentsMax;
    @SerializedName("primetime_percentage")
    @Expose
    private String primetimePercentage;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("estimated_cost_cents_min")
    @Expose
    private Integer estimatedCostCentsMin;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("primetime_confirmation_token")
    @Expose
    private Object primetimeConfirmationToken;
    @SerializedName("cost_token")
    @Expose
    private Object costToken;
    @SerializedName("is_valid_estimate")
    @Expose
    private Boolean isValidEstimate;
    private final static long serialVersionUID = -3599134079345614157L;

    public String getRideType() {
        return rideType;
    }

    public void setRideType(String rideType) {
        this.rideType = rideType;
    }

    public Integer getEstimatedDurationSeconds() {
        return estimatedDurationSeconds;
    }

    public void setEstimatedDurationSeconds(Integer estimatedDurationSeconds) {
        this.estimatedDurationSeconds = estimatedDurationSeconds;
    }

    public Double getEstimatedDistanceMiles() {
        return estimatedDistanceMiles;
    }

    public void setEstimatedDistanceMiles(Double estimatedDistanceMiles) {
        this.estimatedDistanceMiles = estimatedDistanceMiles;
    }

    public Integer getEstimatedCostCentsMax() {
        return estimatedCostCentsMax;
    }

    public void setEstimatedCostCentsMax(Integer estimatedCostCentsMax) {
        this.estimatedCostCentsMax = estimatedCostCentsMax;
    }

    public String getPrimetimePercentage() {
        return primetimePercentage;
    }

    public void setPrimetimePercentage(String primetimePercentage) {
        this.primetimePercentage = primetimePercentage;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getEstimatedCostCentsMin() {
        return estimatedCostCentsMin;
    }

    public void setEstimatedCostCentsMin(Integer estimatedCostCentsMin) {
        this.estimatedCostCentsMin = estimatedCostCentsMin;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Object getPrimetimeConfirmationToken() {
        return primetimeConfirmationToken;
    }

    public void setPrimetimeConfirmationToken(Object primetimeConfirmationToken) {
        this.primetimeConfirmationToken = primetimeConfirmationToken;
    }

    public Object getCostToken() {
        return costToken;
    }

    public void setCostToken(Object costToken) {
        this.costToken = costToken;
    }

    public Boolean getIsValidEstimate() {
        return isValidEstimate;
    }

    public void setIsValidEstimate(Boolean isValidEstimate) {
        this.isValidEstimate = isValidEstimate;
    }

}
