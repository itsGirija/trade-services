package com.upstox.ohlc.web.rest.controler;

import com.upstox.ohlc.dto.UserSubscriptionRequestDTO;
import com.upstox.tradestation.services.OhlcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController("/new/ohlc")
public class NewOhlcRestController {

    private static Logger LOGGER = Logger.getLogger(NewOhlcRestController.class.getName());

    @Autowired
    private OhlcService ohlcService;

    @GetMapping("/load/data/{filePath}")
    public void loadData(@PathVariable("filePath") String filePath) {
        ohlcService.loadData(filePath);
    }

    @GetMapping("/subscribe/{symbol}")
    public void subscribe(@RequestBody UserSubscriptionRequestDTO request) {
        // request.event info is redundant here
        ohlcService.subscribe(request);
    }
}
