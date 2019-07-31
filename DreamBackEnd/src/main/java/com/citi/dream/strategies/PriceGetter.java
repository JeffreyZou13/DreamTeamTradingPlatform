package com.citi.dream.strategies;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Component
public class PriceGetter {

//    private JSONObject stock;
//    private JSONArray stocklist;

    private Logger logger = LogManager.getLogger(this.getClass());

    private String stockName;
    private int numOfStocks;
    private HashMap<String, JSONArray> stockData = new HashMap<>();


    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getNumOfStocks() {
        return numOfStocks;
    }

    public void setNumOfStocks(int numOfStocks) {
        this.numOfStocks = numOfStocks;
    }

    public HashMap<String, JSONArray> getStockData() {
        return stockData;
    }

    public void setStockData(HashMap<String, JSONArray> stockData) {
        this.stockData = stockData;
    }

//    public JSONObject getStock() {
//        return stock;
//    }
//
//    public void setStock(JSONObject stock) {
//        this.stock = stock;
//    }
//
//    public JSONArray getStocklist() {
//        return stocklist;
//    }
//
//    public void setStocklist(JSONArray stocklist) {
//        this.stocklist = stocklist;
//    }

    /*
    @Scheduled(fixedRate = 1000)
    public double getStockPrice(String stockName) throws JSONException {
        String requestURL  = "http://nyc31.conygre.com:31/Stock/getStockPrice/" + stockName;
        StringBuilder result = new StringBuilder();
        JSONObject actualResult = new JSONObject();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            actualResult = new JSONObject(result.toString());
        } catch(Exception e){

        }finally{
//            return result.toString();
            setStock(actualResult);
            return Double.parseDouble(actualResult.getString("price"));
        }

    }*/

    @Scheduled(fixedRate = 1000)
    public void populateStockData(){
        logger.info("Populating stock data for <" + this.stockName + ">");
        // Don't get data until stock is set
        if (this.stockName == null) {
            return;
        }
        String requestURL  = "http://nyc31.conygre.com:31/Stock/getStockPriceList/" + this.stockName + "?howManyValues=" +
                Integer.toString(this.numOfStocks);

        StringBuilder result = new StringBuilder();
//        JSONArray actualResult = new JSONArray();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
//            actualResult = new JSONArray(result.toString());
            stockData.put(this.stockName, new JSONArray(result.toString()));
        } catch(Exception e){
            logger.info("Exception in getStockData");
        }finally{
        }

    }

}

//    public static String getHTML(String urlToRead) throws Exception {
//        StringBuilder result = new StringBuilder();
//        URL url = new URL(urlToRead);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        String line;
//        while ((line = rd.readLine()) != null) {
//            result.append(line);
//        }
//        rd.close();
//        return result.toString();
//    }
//
//    public static void main(String[] args) throws Exception
//    {
//        System.out.println(getHTML(args[0]));
//    }
//}
