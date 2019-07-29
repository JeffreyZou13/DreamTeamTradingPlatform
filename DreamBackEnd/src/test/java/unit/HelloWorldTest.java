package unit;

import com.citi.dream.strategies.PriceGetter;
import org.json.JSONObject;
import org.junit.Test;

public class HelloWorldTest {

    @Test
    public void testIfCanGetOneStockPrice(){

        String stockName = "msft";
        PriceGetter pg = new PriceGetter();
        JSONObject result = pg.getStockPrice(stockName);
        System.out.println(result.getString("symbol"));

    }
}
