package com.upstox.tradestation.ohlc.listener;

import java.util.List;

import com.upstox.tradestation.dto.StockTradeData;

public interface OhlcEventListener {
	
    void emit(List<StockTradeData> data);
}