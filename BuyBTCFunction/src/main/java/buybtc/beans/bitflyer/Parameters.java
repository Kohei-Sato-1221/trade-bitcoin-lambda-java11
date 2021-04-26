package buybtc.beans.bitflyer;

import com.google.gson.annotations.SerializedName;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Parameters {
	@SerializedName("product_code")
	private String productCode;
	@SerializedName("condition_type")
	private String conditionType;
	@SerializedName("side")
	private String side;
	@SerializedName("price")
	private int price;
	@SerializedName("size")
	private double size;
}
