package com.citi.dream.strategies;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class PriceGetter {

    public JSONObject getStockPrice(String stockName){

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
            return actualResult;
        }

    }


    public JSONArray getStockPriceList(String stockName, int numOfValues){

        String requestURL  = "http://nyc31.conygre.com:31/Stock/getStockPriceList/" + stockName + "?howManyValues=" +
                Integer.toString(numOfValues);

        StringBuilder result = new StringBuilder();
        JSONArray actualResult = new JSONArray();
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
            actualResult = new JSONArray(result.toString());
        } catch(Exception e){

        }finally{
//            return result.toString();
            return actualResult;
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
