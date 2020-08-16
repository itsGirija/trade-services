package com.upstox.tradestation.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Component;
/**
 * DTO to hold trade data  for each stock
 * @author Girija Senapati
 *
 */

@Component
public class StockTradeData {
	
	private String symbol;
	private BigDecimal price;
	private BigDecimal quantity;
	private String side;
	private Date date;
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
