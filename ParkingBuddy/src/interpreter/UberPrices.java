
package interpreter;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UberPrices implements Serializable
{

    @SerializedName("prices")
    @Expose
    private List<Price> prices = null;
    private final static long serialVersionUID = -3703219432427529701L;

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

}
