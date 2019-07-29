package unit;

import com.citi.dream.strategies.PriceGetter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class HelloWorldTest {

    @Test
    public void testIfCanGetOneStockPrice() throws JSONException {

        String stockName = "msft";
        PriceGetter pg = new PriceGetter();
        JSONObject result = pg.getStockPrice(stockName);
        System.out.println(result.getString("symbol"));

    }

    @Test
    public void testIfCanGetListOfStockPrice() throws JSONException {

        String stockName = "msft";
        int numOfValues = 20;
        PriceGetter pg = new PriceGetter();
        JSONArray result = pg.getStockPriceList(stockName, numOfValues);
        
        System.out.println(result);
//        System.out.println(result.getString("symbol"));

    }
}
