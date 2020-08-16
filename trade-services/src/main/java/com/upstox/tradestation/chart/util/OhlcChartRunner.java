package com.upstox.tradestation.chart.util;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.springframework.stereotype.Component;

@Component
public class OhlcChartRunner implements Runnable {
	
	private static final int HEIGHT = 600;
	private static final int WIDTH = 1000;

	@Override
	public void run() {
		SwingUtilities.invokeLater(() -> {
			HighLowChartFrame chartFrame = new HighLowChartFrame("High Low Chart");
			chartFrame.setSize(WIDTH, HEIGHT);
			chartFrame.setLocationRelativeTo(null);
			chartFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			chartFrame.setVisible(true);
		});
		
	}
}
