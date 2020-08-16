package com.upstox.tradestation.dao;

import java.text.ParseException;
import java.util.List;

import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.OHLCDataset;
import org.springframework.stereotype.Repository;

import com.upstox.tradestation.DataReader;
import com.upstox.tradestation.dto.StockTradeData;

@Repository
public class OhlcDao {
	
	private static final String STOCK_TRADE_DATA = "Stock Trade Data";
	
	OhlcDataAdapter ohlcDataAdapter = new OhlcDataAdapter();
	
	private DataReader dataReader = new DataReader();

	public OHLCDataset getDataset(String symbol) throws ParseException {
		List<StockTradeData> stockTradeDataList = dataReader.read(symbol);
		OHLCDataItem dataItem[] = ohlcDataAdapter.getConvertedFeedData(stockTradeDataList);
		OHLCDataset dataset = new DefaultOHLCDataset(STOCK_TRADE_DATA, dataItem);
		return dataset;
	}
}
