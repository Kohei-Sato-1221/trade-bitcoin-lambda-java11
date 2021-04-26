package buybtc.beans.bitflyer;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParentOrderParams {
	@SerializedName("order_method")
	private String orderMethod;
	@SerializedName("minute_to_expire")
	private int miinuteToExpire;
	@SerializedName("time_in_force")
	private String time_in_force;
	@SerializedName("parameters")
	private Parameters[] parameters;
}
