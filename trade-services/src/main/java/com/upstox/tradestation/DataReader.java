package com.upstox.tradestation;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.upstox.tradestation.dto.StockTradeData;
/**
 * Class to read data from inbound source path
 * It can be extended to read in future from live timeseries database
 * @author Girija Senapati
 *
 */
@Component
public class DataReader {
	
	private static Logger LOGGER = Logger.getLogger(DataReader.class.getName());

	private static final String OUTPUT_DIR = "C:\\Users\\ME\\Desktop\\";
	
	private static final String URL = "http://kaboom.rksv.net/trades-test/trades-data.zip";
	
	private static final int BUFFER_SIZE = 1024;

	public List<StockTradeData> read(String symbol) {
		List<StockTradeData> stockTradeDataListBySymbol = new ArrayList<StockTradeData>();
		try {
			String extratedFilePath = getFile();
			List<StockTradeData> stockTradeDataList = readFromFile(extratedFilePath);
			stockTradeDataListBySymbol = stockTradeDataList.stream()
				.filter(x -> symbol.equals(x.getSymbol()))
				.collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.info("Exception while reading data from feed file" + e.getMessage());
		}
		return stockTradeDataListBySymbol;
	}
	
	private static List<StockTradeData> readFromFile(String filePath) throws IOException, ParseException {
		LOGGER.info("Parsing the trade data into a list");
		List<StockTradeData> returnList = new ArrayList<StockTradeData>();
		JSONParser jsonparser = new JSONParser();
		List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
		for(String line : lines) {
			JSONObject jsonObject = (JSONObject) jsonparser.parse(line);
			returnList.add(parseStockTradeData(jsonObject));
		}
		return returnList;
		
	}

	private static StockTradeData parseStockTradeData(JSONObject jsonObject) {
		StockTradeData stockTradeData = new StockTradeData();
        String symbol = (String) jsonObject.get("sym");    
        stockTradeData.setSymbol(symbol);
        BigDecimal price = parseNumber(jsonObject.get("P"));    
        stockTradeData.setPrice(price);
        BigDecimal quantity = parseNumber(jsonObject.get("Q"));    
        stockTradeData.setQuantity(quantity);
        String side = (String) jsonObject.get("side");    
        stockTradeData.setSide(side);
        return stockTradeData;
	}
	
	private static BigDecimal parseNumber(Object obj) {
		if(obj instanceof Double) {
			return BigDecimal.valueOf((Double) obj);
		}
		if(obj instanceof Long) {
			return BigDecimal.valueOf((Long) obj);
		}
		return BigDecimal.valueOf((Long) obj);
	}

	private static String getFile() throws IOException {

		BufferedInputStream bis = new BufferedInputStream(new URL(URL).openStream());
		final ZipInputStream is = new ZipInputStream(bis);
		try {
			ZipEntry entry;
			while ((entry = is.getNextEntry()) != null) {
				if (!entry.getName().endsWith(".json") || entry.isDirectory()) {
					break;
				}
				//TODO-LOG.INFO
				System.out.printf("File: %s Size %d Modified on %TD %n", entry.getName(), entry.getSize(),
						new Date(entry.getTime()));

				return extractEntry(entry, is);
			}
		} finally {
			is.close();
			bis.close();
		}
		return null;
		
	}

	private static String extractEntry(ZipEntry entry, ZipInputStream is) throws IOException {
		
		String exractedFile = OUTPUT_DIR +  entry.getName();
		FileOutputStream fos = null;
		try { 
			fos = new FileOutputStream(exractedFile); 
			final byte[] buf = new byte[BUFFER_SIZE]; 
			int read = 0; 
			int length; 
			while ((length = is.read(buf, 0, buf.length)) >= 0) { 
				fos.write(buf, 0, length); 
			} 
			fos.flush();
		} catch (IOException ioex) { 
			fos.close(); 
		}
		return exractedFile;
	}
}