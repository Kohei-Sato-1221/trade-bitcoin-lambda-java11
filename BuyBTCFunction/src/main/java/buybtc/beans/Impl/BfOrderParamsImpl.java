package buybtc.beans.Impl;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import buybtc.beans.OrderParams;

@Data
@Builder
public class BfOrderParamsImpl implements OrderParams {
	@SerializedName("product_code")
	private String productCode;
	@SerializedName("child_order_type")
	private String childOrderType;
	@SerializedName("side")
	private String side;
	@SerializedName("price")
	private int price;
	@SerializedName("size")
	private double size;
	@SerializedName("minute_to_expire")
	private int miinuteToExpire;
	@SerializedName("time_in_force")
	private String time_in_force;
}
