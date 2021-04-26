package buybtc.enums.Impl;

import buybtc.enums.TimeInForce;

public enum BfTimeInForceImpl implements TimeInForce {
	GTC("GTC")
	,IOC("IOC")
	,FOK("FOK");

	private String type;
	
	private BfTimeInForceImpl(String type) {
		this.type = type;
	}
	
	@Override
	public String getType() {
		return this.type;
	} 

}
