package buybtc.enums.Impl;

import buybtc.enums.Side;

public enum BfSideImpl implements Side {
	BUY("BUY")
	,SELL("SELL");

	private String side;
	
	private BfSideImpl(String side) {
		this.side = side;
	}
	
	@Override
	public String getSide() {
		return this.side;
	} 

}
