package buybtc.enums.Impl;

import buybtc.enums.ProductCode;

public enum BfProductCodeImpl implements ProductCode {
	BTCJPY("BTC_JPY")
	,ETHJPY("ETH_JPY");

	private String code;
	
	private BfProductCodeImpl(String code) {
		this.code = code;
	}
	
	@Override
	public String getCode() {
		return this.code;
	} 

}
