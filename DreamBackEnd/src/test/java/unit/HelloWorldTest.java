package unit;

import com.citi.dream.strategies.PriceGetter;
import org.junit.Test;

public class HelloWorldTest {

    @Test
    public void testIfCanGetOneStockPrice(){

        String stockName = "msft";
        PriceGetter pg = new PriceGetter();
        String result = pg.getStockPrice(stockName);

        System.out.println(result);

    }
}
