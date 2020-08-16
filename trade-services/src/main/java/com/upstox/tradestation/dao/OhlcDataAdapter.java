package com.upstox.tradestation.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.data.xy.OHLCDataItem;
import org.springframework.stereotype.Component;

import com.upstox.tradestation.dto.Ohlc;
import com.upstox.tradestation.dto.StockTradeData;

@Component
public class OhlcDataAdapter {
	private static final String DATE_FORMAT = "dd MMM,yyyy";
	DateFormat format = new SimpleDateFormat(DATE_FORMAT);
	
	public OHLCDataItem[] getConvertedFeedData(List<StockTradeData> stockTradeDataList) throws ParseException {
		OHLCDataItem[] ohlcDataItems = convertContinousDataToOhlcData(stockTradeDataList);
		return ohlcDataItems;
	}
	
	private OHLCDataItem[] convertContinousDataToOhlcData(List<StockTradeData> stockTradeDataList) throws ParseException {
		Map<String, List<Ohlc>> map = new HashMap<String, List<Ohlc>>();
		for(StockTradeData data : stockTradeDataList) {
			Ohlc ohlc = calculateOhlc(data);
			if(!map.containsKey(data.getSymbol())) {
				List<Ohlc> ohlcList = new ArrayList<Ohlc>();
				ohlcList.add(ohlc);
				continue;
			}
			List<Ohlc> ohlcList = map.get(data.getSymbol());
			ohlcList.add(ohlc);
			map.put(data.getSymbol(), ohlcList);
			
		}
		OHLCDataItem[] dataItem = convertStockDataToOhlcData(map);
		return dataItem;
	}

	private OHLCDataItem[] convertStockDataToOhlcData(Map<String, List<Ohlc>> map) throws ParseException {
		// TODO - add logic to convert in memory for every 15min - use HashMap of Map
		//For each key, create below data, create single thread for each symbol
		List<OHLCDataItem> ohlcDataItemList = new ArrayList<OHLCDataItem>();
		OHLCDataItem[] dataItem = new OHLCDataItem[] {};
		for(String symbol : map.keySet()) {
			List<Ohlc> ohlcList = map.get(symbol);
			
			for(Ohlc ohlc : ohlcList) {
				OHLCDataItem ohlcDataItem = new OHLCDataItem(ohlc.getDate(), 
						ohlc.getOpen().doubleValue(),
						ohlc.getHigh().doubleValue(),
						ohlc.getLow().doubleValue(),
						ohlc.getClose().doubleValue(),0);
				ohlcDataItemList.add(ohlcDataItem);
			}
			
			
		}
		
		return ohlcDataItemList.toArray(new OHLCDataItem[0]);
	}

	private Ohlc calculateOhlc(StockTradeData data) {
		Ohlc ohlc = new Ohlc();
		ohlc.setOpen(data.getPrice());
		ohlc.setLow(data.getPrice());
		ohlc.setHigh(data.getPrice());
		ohlc.setClose(data.getPrice());
		ohlc.setSymbol(data.getSymbol());
		//TBD based on full logic
		return ohlc;
	}

	public OHLCDataItem[] getSampleData(DateFormat format) throws ParseException {
		OHLCDataItem[] dataItem;
		OHLCDataItem data1 = new OHLCDataItem(format.parse("8 Feb, 2017"), 288.00, 288.50, 282.70, 285.80,
				15148100);
		OHLCDataItem data2 = new OHLCDataItem(format.parse("7 Feb, 2017"), 288.00, 292.70, 285.30, 288.10,
				11663000);
		OHLCDataItem data3 = new OHLCDataItem(format.parse("6 Feb, 2017 "), 283.55, 292.25, 283.55, 290.30,
				20474400);

		dataItem = new OHLCDataItem[] { data1, data2, data3};
		return dataItem;
	}
}
