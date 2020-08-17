package com.upstox.tradestation.services;

import com.upstox.tradestation.DataReader;
import com.upstox.tradestation.broker.OhlcEventBroker;
import com.upstox.tradestation.dao.OhlcDao;
import com.upstox.tradestation.dto.StockTradeData;
import com.upstox.tradestation.dto.UserSubscriptionRequestDTO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.OHLCDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class OhlcService {

    private static Logger LOGGER = Logger.getLogger(OhlcService.class.getName());

    @Autowired
    OhlcDao ohlcDao;

    @Autowired
    private OhlcEventBroker ohlcEventBroker;
    
    @Autowired
    DataReader dataReader;

    public ChartPanel getOhlcChart(String symbol) {
    	try {
	        LOGGER.info("Get dataset from inbound json file");
	        OHLCDataset dataset = ohlcDao.getDataset(symbol);
	
	        LOGGER.info("Create chart");
	        JFreeChart chart = ChartFactory.createHighLowChart("OHLC Stock chart", "Date", "Price", dataset, true);
	        XYPlot plot = (XYPlot) chart.getPlot();
	        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setAutoRangeIncludesZero(false);
	
	        LOGGER.info("Create the Panel containing the chart");
	        ChartPanel panel = new ChartPanel(chart);
	        return panel;
    	} catch (ParseException e) {
			LOGGER.warning("Error while getting ohlc chart " + e);
			return new ChartPanel(null);
		}
    }

    public void loadData(String filePath) {
    	try {
    		List<StockTradeData> data = dataReader.readFromFile(filePath);
    		ohlcEventBroker.publish(data);
    	} catch (IOException | org.json.simple.parser.ParseException e) {
			LOGGER.warning("Exception while loading data " + e);
		}
    }

    public void subscribe(String symbol, int intervalInSeconds) {
        // Start a timer to increase bar number in the
    }

    public void subscribe(UserSubscriptionRequestDTO request) {

        // WebSocket should be created
        // Bar cound should be changed in every request.getInterval() seconds
        // Response should be sent in every request.getInterval() seconds irrespective of data
        // Start a thread and listen for events
    }
}
