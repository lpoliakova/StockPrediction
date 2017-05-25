package Train;

import Utils.StockData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oradchykova on 5/18/17.
 */
public class BridgeToLearningSample {
    public static LearningSample buildStockLearnObject(StockData nextDay, StockData stockData){
        return new LearningSample(nextDay.getChange(),
                stockData.getPrice(), //feature 1
                stockData.getHigh() - stockData.getLow()); // feature 2
    }

    public static List<LearningSample> buildStockLearnObjects(List<StockData> stockData){
        List<LearningSample> learnObjects = new ArrayList<>();
        for (int i = 0; i < stockData.size() - 1; i++){
            learnObjects.add(buildStockLearnObject(stockData.get(i), stockData.get(i + 1)));
        }
        return learnObjects;
    }
}
