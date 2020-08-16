package com.upstox.tradestation.chart.util;

import java.text.ParseException;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.OHLCDataset;
import org.springframework.stereotype.Component;

import com.upstox.tradestation.dao.OhlcDao;
/**
 * 
 * @author Girija Senapati
 */
@Component
public class HighLowChartFrame extends JFrame {
	
	private static Logger LOGGER = Logger.getLogger(HighLowChartFrame.class.getName());
	
	private static final long serialVersionUID = 789093670273693445L;
	
	private OhlcDao ohlcDao = new OhlcDao();

	public HighLowChartFrame(String title) {
		super(title);
		try {
			LOGGER.info("Get Real trade data");
			OHLCDataset dataset = ohlcDao.getDataset();

			LOGGER.info("Create chart");
			JFreeChart chart = ChartFactory.createHighLowChart("OHLC Stock chart", "Date", "Price", dataset, true);

			XYPlot plot = (XYPlot) chart.getPlot();
			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setAutoRangeIncludesZero(false);

			LOGGER.info("Create the Panel containing the chart");
			ChartPanel panel = new ChartPanel(chart);
			setContentPane(panel);
		} catch (ParseException pe) {
			LOGGER.warning("Exception while creating chart " + pe);
		}
	}
}
