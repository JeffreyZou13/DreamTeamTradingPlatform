package unit;

import com.citi.Application;
import com.citi.dream.jms.MessageSender;
import com.citi.dream.strategies.*;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {Application.class})
public class StrategyTest {

    @Autowired
    private StrategyManager strategyManager;

    @Autowired
    private PriceGetter priceGetter;

    @Autowired
    private MessageSender messageSender;

    @Test
    public void testIfManagerCanCreateStrategy() {
        ArrayList<Integer> timeList = new ArrayList<>();
        timeList.add(1);
        timeList.add(3);
        timeList.add(100);
        Strategy strategy = strategyManager.createStrategy("two moving averages",
                timeList,"HON",
                100, "aa-bb-cc-dd", 0.01);
        HashMap<String, Strategy> newStrategies = strategyManager.getStrategies();
        System.out.println("hi i am here");
        System.out.println(newStrategies.keySet());
        assert(newStrategies.size() == 1);
    }


    @Test
    public void testIfICanCalculateAverage()throws JSONException{


        TwoMovingAverages ma = new TwoMovingAverages("Two Moving Averages", 10,5,"msft",
                100, "aa-bb-cc-dd",.01, priceGetter, messageSender);

        System.out.print(ma.calculateAverage(5));

    }

//    @Repeat(value = 10)
    @Test
    public void testIfICanPerformTwoMovingAverages() throws JSONException {
        TwoMovingAverages ma = new TwoMovingAverages("Two Moving Averages", 10,5,"msft",
                100, "aa-bb-cc-dd",.01, priceGetter, messageSender);
        ma.performStrategy();
    }


//    ========
//    Bollinger Band Tests

//    public BollingerBand(String type, int durationTime, String stockName, int volume,
//                         String strategyID, double cutOffPercentage, PriceGetter priceGetter,
//                         MessageSender messageSender) {
//    ========

    @Test
    public void testIfBBCanCalculateAverage()throws JSONException{


        BollingerBand bb = new BollingerBand("Bollinger Band", 10,"msft",
                100, "aa-bb-cc-dd",.01, priceGetter, messageSender);

        System.out.print(bb.calculateAverage(5));

    }


}
