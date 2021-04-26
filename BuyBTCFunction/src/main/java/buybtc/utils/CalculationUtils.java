package buybtc.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculationUtils {
	public final int STG_97_PERCENT_OF_LTP = 0;
	
	/** 
	 * 少数の切り上げを行う
	 * @param num 切り上げ対象の数値
	 * @param places 少数第何位で切り上げを行うか指定
	 */
	public double roundUp(double num, int places) {
		BigDecimal numObj = BigDecimal.valueOf(num);
		return numObj.setScale(places, RoundingMode.UP).doubleValue();
	}
	
	/** 
	 * 予算を元に注文数量を算出する
	 * @param price 注文金額
	 * @param budget 予算
	 * @param minimumAmount 最低注文数量
	 * @param places
	 */
	public double calcAmount(double price, double budget, double minimumAmount, int places) {
		BigDecimal amountObj = BigDecimal.valueOf(budget)
				.divide(BigDecimal.valueOf(price), places, RoundingMode.UP);
		double amount = amountObj.doubleValue();
		if (amount < minimumAmount) { 
			return minimumAmount;
		} else {
			return amount;
		}
	}
	
	/** 
	 * 戦略に応じて価格を決定する
	 * @param ltp
	 * @param bestAsk
	 * @param bestBid
	 * @param strategy 価格決定ロジックの指定
	 */
	public int getBuyPrice(double ltp, double bestAsk, double bestBid, int strategy) {
		if (strategy == STG_97_PERCENT_OF_LTP) {
			BigDecimal price = BigDecimal.valueOf(ltp).multiply(BigDecimal.valueOf(0.97));
			return (int) roundUp(price.doubleValue(), 0);
		} else {
			BigDecimal price = BigDecimal.valueOf(bestAsk)
					.add(BigDecimal.valueOf(bestBid));
			price = price.divide(BigDecimal.valueOf(2));
			return (int) roundUp(price.doubleValue(), 0);
		}
	}
	
	/** 
	 * 売却価格を決定する
	 * @param buyPrice 購入価格
	 */
	public int getSellPrice(double buyPrice) {
		final double PROFIT_PERCENT = 1.02;
		BigDecimal price = BigDecimal.valueOf(buyPrice)
				.multiply(BigDecimal.valueOf(PROFIT_PERCENT));
		return (int) roundUp(price.doubleValue(), 0);
	}
	

}



