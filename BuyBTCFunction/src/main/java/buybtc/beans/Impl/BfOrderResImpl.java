package buybtc.beans.Impl;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import buybtc.beans.OrderRes;

@Data
public class BfOrderResImpl implements OrderRes {
	@SerializedName("child_order_acceptance_id")
	private String childOrderAcceptanceId;
}
