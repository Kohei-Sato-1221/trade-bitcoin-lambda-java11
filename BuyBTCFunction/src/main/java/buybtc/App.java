package buybtc;

import java.util.HashMap;
import java.util.Map;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;


import buybtc.beans.Ticker;
import buybtc.beans.Impl.BfOrderParamsImpl;
import buybtc.beans.Impl.BfOrderResImpl;
import buybtc.beans.Impl.BfTickerImpl;
import buybtc.beans.bitflyer.Parameters;
import buybtc.beans.bitflyer.ParentOrderParams;
import buybtc.beans.bitflyer.ParentOrderRes;
import buybtc.clients.APIClient;
import buybtc.clients.Impl.BfClientImpl;
import buybtc.enums.OrderType;
import buybtc.enums.ProductCode;
import buybtc.enums.Side;
import buybtc.enums.TimeInForce;
import buybtc.enums.Impl.BfOrderTypeImpl;
import buybtc.enums.Impl.BfProductCodeImpl;
import buybtc.enums.Impl.BfSideImpl;
import buybtc.enums.Impl.BfTimeInForceImpl;
import buybtc.utils.CalculationUtils;


/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
	final int BTC_BUDGET = 10000;
	final int BTC_PLACES = 4;
	final double BTC_MINIMUM_AMOUNT = 0.001;
	
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");
        
        ProductCode code = BfProductCodeImpl.BTCJPY;
        OrderType type = BfOrderTypeImpl.LIMIT;
        OrderType method = BfOrderTypeImpl.IFD;
        Side sideBuy = BfSideImpl.BUY;
        Side sideSell = BfSideImpl.SELL;
        TimeInForce tif = BfTimeInForceImpl.GTC;
        
        var req1 = new GetParameterRequest()
        		.withName("buy-btc-apikey")
        		.withWithDecryption(true);
        var req2 = new GetParameterRequest()
        		.withName("buy-btc-apisecret")
        		.withWithDecryption(true);
        
        
        var ssmclient = AWSSimpleSystemsManagementClientBuilder.defaultClient();
        
        var result1 = ssmclient.getParameter(req1);
        var result2 = ssmclient.getParameter(req2);
        
        String apiKey = result1.getParameter().getValue();
        String apiSeret = result2.getParameter().getValue();
        
        APIClient<BfTickerImpl,BfOrderResImpl, BfOrderParamsImpl, ParentOrderRes, ParentOrderParams> client = 
        		new BfClientImpl(apiKey, apiSeret);
        
        Ticker ticker = client.getTicker(code);
        var utils = new CalculationUtils();
        
        var buyPrice = utils.getBuyPrice(
        		ticker.getLtp(),
        		ticker.getBestAsk(),
        		ticker.getBestBid(),
        		utils.STG_97_PERCENT_OF_LTP
        		);
        
        var sellPrice = utils.getSellPrice(buyPrice);
        
        var buyAmount = utils.calcAmount(
        		buyPrice,
        		BTC_BUDGET,
        		BTC_MINIMUM_AMOUNT,
        		BTC_PLACES
        		);
        
        System.out.println("buyPrice:" + buyPrice);
        System.out.println("sellPrice:" + sellPrice);
        System.out.println("buyAmount:" + buyAmount);
        
		/* 以下は通常注文
        var params = OrderParams.builder()
        		.productCode(code.getCode())
        		.childOrderType(type.getType())
        		.side(side.getSide())
        		.price(buyPrice)
        		.size(buyAmount)
        		.miinuteToExpire(4320) //3days
        		.time_in_force(tif.getType())
        		.build();
        var res = client.placeOrder(params);
        */
        
        var buyOrderParams = Parameters.builder()
        		.productCode(code.getCode())
        		.conditionType(type.getType())
        		.side(sideBuy.getSide())
        		.price(buyPrice)
        		.size(buyAmount)
        		.build();
        
        var sellOrderParams = Parameters.builder()
        		.productCode(code.getCode())
        		.conditionType(type.getType())
        		.side(sideSell.getSide())
        		.price(sellPrice)
        		.size(buyAmount)
        		.build();
        
        Parameters[] paramsArray = {buyOrderParams, sellOrderParams};
        
        var parentOrderParams = ParentOrderParams.builder()
        		.orderMethod(method.getType())
        		.miinuteToExpire(43200)
        		.time_in_force(tif.getType())
        		.parameters(paramsArray)
        		.build();
        
        var res = client.placeSpecialOrder(parentOrderParams);
        		
        
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);
        
        String output = String.format("{ \"result\": \"%s\"}", res);

        return response
                .withStatusCode(200)
                .withBody(output);
        
    }
}
