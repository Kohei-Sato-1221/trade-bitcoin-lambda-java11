package buybtc.clients;

import buybtc.enums.ProductCode;

public interface APIClient<T,R,P,Q,S> {
	public T getTicker(ProductCode code);
	public R placeOrder(P params);
	public Q placeSpecialOrder(S params);
}

