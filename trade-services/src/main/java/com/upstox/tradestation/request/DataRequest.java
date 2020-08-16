package com.upstox.tradestation.request;

import org.springframework.stereotype.Component;

@Component
public class DataRequest {
	
	/* Stock symbol */
	String symbol;
	
	/* Time frame like 15min , 1hr , daily, monthly, yearly chart */
	String timeframe;
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getTimeframe() {
		return timeframe;
	}
	public void setTimeframe(String timeframe) {
		this.timeframe = timeframe;
	}
	
	
}
