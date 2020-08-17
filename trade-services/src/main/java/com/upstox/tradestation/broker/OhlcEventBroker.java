package com.upstox.tradestation.broker;

import com.upstox.tradestation.dto.StockTradeData;
import com.upstox.tradestation.ohlc.listener.OhlcEventListener;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OhlcEventBroker {

    /* Lister for each symbol */
    private Map<String, List<OhlcEventListener>> listenersMap = new ConcurrentHashMap<>();

    /**
     * Add Subscriber for a symbol
     *
     * @param symbol
     * @param listener
     */
    public void subscribe(String symbol, OhlcEventListener listener) {
        List<OhlcEventListener> listeners = listenersMap.computeIfAbsent(symbol, s -> new ArrayList<>());
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void unsubscribe(String symbol, OhlcEventListener listener) {
        List<OhlcEventListener> listeners = listenersMap.get(symbol);
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    /**
     * Publish {@Code StockTradeData} to interested listeners
     *
     * @param data
     */
    public void publish(List<StockTradeData> data) {
        List<OhlcEventListener> listeners = listenersMap.get(data);

        if (!CollectionUtils.isEmpty(listeners)) {
            for (OhlcEventListener listener : listeners) {
                listener.emit(data);
            }
        }
    }
}