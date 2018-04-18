
package interpreter;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LyftPrices implements Serializable
{
    
    @SerializedName("cost_estimates")
    @Expose
    private List<CostEstimate> costEstimates = null;
    private final static long serialVersionUID = 1503774698560627383L;
    
    public List<CostEstimate> getCostEstimates() {
        return costEstimates;
    }
    
    public void setCostEstimates(List<CostEstimate> costEstimates) {
        this.costEstimates = costEstimates;
    }
    
}

