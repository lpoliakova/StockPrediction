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
        List<LearningSample> learnObjects = getLearnSamples(file, amount, percent);
        GaussianModel gaussian = new GaussianModel();
        return gaussian.learn(learnObjects);
    }

    private static List<LearningSample> getLearnSamples(File file, Integer amount, Integer percent) throws FileNotFoundException{
        List<StockData> stockData = FileReader.read(file, amount, percent + 1, true);

        List<LearningSample> learnObjects = new ArrayList<>();
        for (int i = 0; i < stockData.size(); i += percent + 1){
            List<LearningSample> pack = BridgeToLearningSample.buildStockLearnObjects(stockData.subList(i, i + percent + 1));
            learnObjects.addAll(pack);
        }

        for (int i = learnObjects.size() - 1 ; i >= 0; i--){
            if (learnObjects.get(i) == null) learnObjects.remove(i);
        }

        return learnObjects;
    }
}
