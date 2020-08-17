package com.upstox.tradestation.ohlc.web.rest.controler;

import java.util.logging.Logger;

import org.jfree.chart.ChartPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.upstox.tradestation.dto.UserSubscriptionRequestDTO;
import com.upstox.tradestation.request.DataRequest;
import com.upstox.tradestation.services.OhlcService;

@RestController("/ohlc")
public class OhlcRestController {

    @Autowired
    private OhlcService ohlcService;

    @GetMapping("/load/data/{filePath}")
	public void loadData(@PathVariable("filePath") String filePath) {
		ohlcService.loadData(filePath);
	}

    @GetMapping("/subscribe")
    public void subscribe(@RequestBody UserSubscriptionRequestDTO request) {
        ohlcService.subscribe(request);
    }
    
    @GetMapping("/{symbol}")
	public ChartPanel getOhlcGraphBySymbol(@PathVariable("symbol") String symbol) {
		DataRequest request = new DataRequest();
		request.setSymbol(symbol);
		return ohlcService.getOhlcChart(symbol);
	}
}
