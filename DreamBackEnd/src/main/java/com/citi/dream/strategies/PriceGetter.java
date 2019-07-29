package com.citi.dream.strategies;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class PriceGetter {

    public String getStockPrice(String stockName){

        String requestURL  = "http://nyc31.conygre.com:31/Stock/getStockPrice/" + stockName;
        StringBuilder result = new StringBuilder();
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
        } catch(Exception e){
        }finally{
            return result.toString();
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
