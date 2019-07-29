package unit;

import com.citi.dream.strategies.PriceGetter;
import com.citi.dream.strategies.TwoMovingAverages;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class HelloWorldTest {

    @Test
    public void testIfCanGetOneStockPrice() throws JSONException {

//        String stockName = "msft";
//        PriceGetter pg = new PriceGetter();
////        JSONObject result = pg.getStockPrice(stockName);
//        System.out.println(result.getString("symbol"));

    }

    @Test
    public void testIfCanGetListOfStockPrice() throws JSONException {

        String stockName = "msft";
        int numOfValues = 20;

        double[] priceArray = new double[numOfValues];
        PriceGetter pg = new PriceGetter();
        JSONArray result = pg.getStockPriceList(stockName, numOfValues);
        for(int i=0; i<numOfValues; i++) {
            priceArray[i] = Double.parseDouble(result.getJSONObject(i).getString("price"));
//            System.out.println(result.getJSONObject(i).getString("price"));

        }
        for (int i=0; i<numOfValues; i++) {
            System.out.println(priceArray[i]);
        }
    }


    @Test
    public void testIfICanCalculateAverage()throws JSONException{

//        this.type = type;
//        this.longTime = longTime;
//        this.shortTime = shortTime;
//        this.stockName = stockName;
//        this.volume = volume;
//        this.strategyID = strategyID;
//        this.cutOffPercentage = cutOffPercentage;

        TwoMovingAverages ma = new TwoMovingAverages("Two Moving Averages", 10,5,"msft",
                100, 3244,.01);

        System.out.print(ma.calculateAverage(5));


    }
}
