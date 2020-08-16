package com.upstox.tradestation.webservices;

import java.text.ParseException;
import java.util.logging.Logger;

import org.jfree.chart.ChartPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.upstox.tradestation.chart.util.HighLowChartFrame;
import com.upstox.tradestation.request.DataRequest;
import com.upstox.tradestation.services.OhlcService;

@RestController("/ohlc")
public class OhlcRestController {
	
	private static Logger LOGGER = Logger.getLogger(OhlcRestController.class.getName());
	
	@Autowired
	private OhlcService ohlcService;
	
	@GetMapping("/{symbol}")
	public ChartPanel getOhlcGraphBySymbol(@PathVariable("symbol") String symbol) {
		try {
			DataRequest request = new DataRequest();
			request.setSymbol(symbol);
			return ohlcService.getOhlcChart(symbol);
		} catch (ParseException e) {
			LOGGER.warning("Error while getting ohlc chart " + e);
			return new ChartPanel(null);
		}
	}
	

}
