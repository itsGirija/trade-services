package com.upstox.tradestation.services;

import java.text.ParseException;
import java.util.logging.Logger;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.OHLCDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upstox.tradestation.dao.OhlcDao;

@Service
public class OhlcService {
	
	private static Logger LOGGER = Logger.getLogger(OhlcService.class.getName());
	
	@Autowired
	OhlcDao ohlcDao;
	
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
	
}
