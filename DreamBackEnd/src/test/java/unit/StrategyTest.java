package unit;

import com.citi.Application;
import com.citi.dream.jms.MessageSender;
import com.citi.dream.strategies.PriceGetter;
import com.citi.dream.strategies.Strategy;
import com.citi.dream.strategies.StrategyManager;
import com.citi.dream.strategies.TwoMovingAverages;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class})
public class StrategyTest {

    @Autowired
    private StrategyManager strategyManager;

    @MockBean
    private PriceGetter priceGetter;

    @MockBean
    private MessageSender messageSender;

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
    public void testIfManagerCanCreateStrategy() {
        Strategy strategy = strategyManager.createStrategy("two moving averages", 5, 1, "HON", 100, "aa-bb-cc-dd", 0.01);
        ArrayList<Strategy> newStrategies = strategyManager.getStrategies();
        assert(newStrategies.size() == 1);
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
                100, "aa-bb-cc-dd",.01, priceGetter, messageSender);

        System.out.print(ma.calculateAverage(5));

    }

    @Repeat(value = 10)
    @Test
    public void testIfICanPerformTwoMovingAverages() throws JSONException {
        TwoMovingAverages ma = new TwoMovingAverages("Two Moving Averages", 10,5,"msft",
                100, "aa-bb-cc-dd",.01, priceGetter, messageSender);
        ma.performStrategy();
    }
}
