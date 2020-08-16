package com.upstox.ohlc.dto;

import com.upstox.ohlc.enums.EventType;

//TODO We could use lombok here and @Data annotation
public class UserSubscriptionRequestDTO {

    private EventType event;
    private String symbol;
    private int interval;

    public EventType getEvent() {
        return event;
    }

    public void setEvent(EventType event) {
        this.event = event;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
