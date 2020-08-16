package com.upstox.ohlc.dto;

import com.upstox.tradestation.dto.StockTradeData;

public interface OhlcEventListener {
    void emit(StockTradeData stockTradeData);
}