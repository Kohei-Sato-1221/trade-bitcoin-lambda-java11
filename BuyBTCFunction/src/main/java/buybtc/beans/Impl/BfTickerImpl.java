package buybtc.beans.Impl;

import buybtc.beans.Ticker;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class BfTickerImpl implements Ticker {
	@SerializedName("product_code")
	private String productCode;
	@SerializedName("state")
	private String state;
	@SerializedName("timestamp")
	private String timestamp;
	@SerializedName("best_bid")
	private double bestBid;
	@SerializedName("best_ask")
	private double bestAsk;
	@SerializedName("best_bid_size")
	private double bestBidSize;
	@SerializedName("best_ask_size")
	private double bestAskSize;
	@SerializedName("total_bid_depth")
	private double totalBidDepth;
	@SerializedName("total_ask_depth")
	private double totalAskDepth;
	@SerializedName("ltp")
	private double ltp;
	@SerializedName("volume")
	private double volume;
	@SerializedName("volume_by_product")
	private double volumeByProduct;
}
