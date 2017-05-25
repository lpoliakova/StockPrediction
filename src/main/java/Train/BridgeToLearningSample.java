package Train;

import Utils.StockData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oradchykova on 5/18/17.
 */
public class BridgeToLearningSample {
    public static LearningSample buildStockLearnSample(StockData nextDay, StockData stockData){
        return new LearningSample(nextDay.getChange(),
                stockData.getPrice(), //feature 1
                stockData.getHigh() - stockData.getLow()); // feature 2
    }

    public static List<LearningSample> buildStockLearnSamples(List<StockData> stockData){
        List<LearningSample> learningSamples = new ArrayList<>();
        for (int i = 0; i < stockData.size() - 1; i++){
            learningSamples.add(buildStockLearnSample(stockData.get(i), stockData.get(i + 1)));
        }
        return learningSamples;
    }
}
