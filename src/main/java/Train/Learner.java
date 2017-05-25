package Train;

import Utils.StockData;
import Utils.FileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oradchykova on 5/18/17.
 */
public class Learner {

    public static Model learn(File file, Integer amount, Integer percent) throws FileNotFoundException{
        List<LearningSample> learnSamples = getLearnSamples(file, amount, percent);
        GaussianModel gaussian = new GaussianModel();
        return gaussian.learn(learnSamples);
    }

    private static List<LearningSample> getLearnSamples(File file, Integer amount, Integer percent) throws FileNotFoundException{
        List<StockData> stockData = FileReader.read(file, amount, percent + 1, true);

        List<LearningSample> learnSamples = new ArrayList<>();
        for (int i = 0; i < stockData.size(); i += percent + 1){
            List<LearningSample> pack = BridgeToLearningSample.buildStockLearnSamples(stockData.subList(i, i + percent + 1));
            learnSamples.addAll(pack);
        }

        for (int i = learnSamples.size() - 1 ; i >= 0; i--){
            if (learnSamples.get(i) == null) learnSamples.remove(i);
        }

        return learnSamples;
    }
}
