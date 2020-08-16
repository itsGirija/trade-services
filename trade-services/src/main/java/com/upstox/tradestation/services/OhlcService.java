package com.upstox.tradestation.services;

import com.upstox.ohlc.broker.OhlcEventBroker;
import com.upstox.ohlc.dto.UserSubscriptionRequestDTO;
import com.upstox.tradestation.dao.OhlcDao;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.OHLCDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.logging.Logger;

@Service
public class OhlcService {

    private static Logger LOGGER = Logger.getLogger(OhlcService.class.getName());

    @Autowired
    OhlcDao ohlcDao;

    @Autowired
    private OhlcEventBroker ohlcEventBroker;

    public ChartPanel getOhlcChart(String symbol) throws ParseException {

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
    }

    public void loadData(String filePath) {
        //TODO Load data from given File

        //TODO Push data to Broker
        //ohlcEventBroker.publish(data);
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
