package buybtc.beans.bitflyer;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ParentOrderRes {
	@SerializedName("parent_order_acceptance_id")
	private String parentOrderAcceptanceId;
}
