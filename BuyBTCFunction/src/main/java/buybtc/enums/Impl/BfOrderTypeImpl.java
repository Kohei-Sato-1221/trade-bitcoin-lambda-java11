package buybtc.enums.Impl;

import buybtc.enums.OrderType;

public enum BfOrderTypeImpl implements OrderType{
	LIMIT("LIMIT")
	,MARKET("MARKET")
	,SIMPLE("SIMPLE")
	,IFD("IFD")
	,OCO("OCO")
	,IFDOCO("IFDOCO");
	
	private String type;
	
	private BfOrderTypeImpl(String type) {
		this.type = type;
	}
	
	@Override
	public String getType() {
		return this.type;
	} 

}
