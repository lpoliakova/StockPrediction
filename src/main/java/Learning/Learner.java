package Learning;

import Utils.StockData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by oradchykova on 5/18/17.
 */
public class Learner {

    public static Model learn(File file, Integer amount, Integer percent) throws FileNotFoundException{
        List<StockLearnObject> learnObjects = getLearnObjects(file, amount, percent);
        GaussianModel gaussian = new GaussianModel();
        return gaussian.learn(learnObjects);
    }

    private static List<StockLearnObject> getLearnObjects(File file, Integer amount, Integer percent) throws FileNotFoundException{
        List<StockData> stockData = Utils.FileReader.read(file, amount, percent + 1, true);

        List<StockLearnObject> learnObjects = new ArrayList<>();
        for (int i = 0; i < stockData.size(); i += percent + 1){
            List<StockLearnObject> pack = BridgeToLearnObject.buildStockLearnObjects(stockData.subList(i, i + percent + 1));
            learnObjects.addAll(pack);
        }

        for (int i = learnObjects.size() - 1 ; i >= 0; i--){
            if (learnObjects.get(i) == null) learnObjects.remove(i);
        }

        return learnObjects;
    }
}
