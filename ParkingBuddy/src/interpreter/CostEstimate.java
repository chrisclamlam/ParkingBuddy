
package interpreter;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CostEstimate implements Serializable
{
    
    public CostEstimate(int min,int max)
    {
        this.estimatedCostCentsMax = max;
        this.estimatedCostCentsMin = min;
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
    @SerializedName("price_quote_id")
    @Expose
    private String priceQuoteId;
    @SerializedName("estimated_cost_cents_max")
    @Expose
    private Integer estimatedCostCentsMax;
    @SerializedName("primetime_percentage")
    @Expose
    private String primetimePercentage;
    @SerializedName("is_valid_estimate")
    @Expose
    private Boolean isValidEstimate;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("cost_token")
    @Expose
    private Object costToken;
    @SerializedName("estimated_cost_cents_min")
    @Expose
    private Integer estimatedCostCentsMin;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("primetime_confirmation_token")
    @Expose
    private Object primetimeConfirmationToken;
    @SerializedName("can_request_ride")
    @Expose
    private Boolean canRequestRide;
    private final static long serialVersionUID = -6002236419909805586L;
    
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
    
    public String getPriceQuoteId() {
        return priceQuoteId;
    }
    
    public void setPriceQuoteId(String priceQuoteId) {
        this.priceQuoteId = priceQuoteId;
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
    
    public Boolean getIsValidEstimate() {
        return isValidEstimate;
    }
    
    public void setIsValidEstimate(Boolean isValidEstimate) {
        this.isValidEstimate = isValidEstimate;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public Object getCostToken() {
        return costToken;
    }
    
    public void setCostToken(Object costToken) {
        this.costToken = costToken;
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
    
    public Boolean getCanRequestRide() {
        return canRequestRide;
    }
    
    public void setCanRequestRide(Boolean canRequestRide) {
        this.canRequestRide = canRequestRide;
    }
    
}

