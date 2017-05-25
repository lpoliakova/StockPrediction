package Test;

import Train.BridgeToLearningSample;
import Train.LearningSample;
import Train.Model;
import Utils.FileReader;
import Utils.StockData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by oradchykova on 5/25/17.
 */
public class Tester {
    public static ROC getRoc(Model learningModel, File inputFile, Integer amount, Integer percent, File outputFile) throws  FileNotFoundException{
        List<LearningSample> learningSamples = getLearnSamples(inputFile, amount, percent);
        List<TestingSample> testingSamples = getTestSamples(learningSamples, learningModel);
        sortTestSamples(testingSamples);
        ROC roc = buildRoc(testingSamples);
        if (outputFile != null) writeDown(outputFile, roc);
        return roc;
    }

    private static List<LearningSample> getLearnSamples(File file, Integer amount, Integer percent) throws FileNotFoundException {
        List<StockData> stockData = FileReader.read(file, amount, percent + 1, false);

        List<LearningSample> learningSamples = new ArrayList<>();
        for (int i = 0; i < stockData.size(); i += percent + 1){
            List<LearningSample> pack = BridgeToLearningSample.buildStockLearnSamples(stockData.subList(i, i + percent + 1));
            learningSamples.addAll(pack);
        }

        for (int i = learningSamples.size() - 1 ; i >= 0; i--){
            if (learningSamples.get(i) == null) learningSamples.remove(i);
        }

        return learningSamples;
    }

    private static List<TestingSample> getTestSamples(List<LearningSample> learnSamples, Model model){
        List<TestingSample> testingSamples = new ArrayList<>();
        for (LearningSample learningSample : learnSamples) {
            testingSamples.add(new TestingSample(learningSample.getResult(), model.computeRatio(learningSample.getFeatures())));
        }
        return testingSamples;
    }

    private static void sortTestSamples(List<TestingSample> testingSamples){
        testingSamples.sort((first, second) -> {
            if (first.getExpectedResult() < second.getExpectedResult()) return -1;
            if (first.getExpectedResult() > second.getExpectedResult()) return 1;
            return 0;
        });
    }

    private static ROC buildRoc(List<TestingSample> testSamples){
        ROC roc = new ROC();
        ROCPoint maxPoint = countMaxPoint(testSamples);
        Double truePositive = 0.0;
        Double falsePositive = 0.0;
        roc.addPoint(new ROCPoint(truePositive, falsePositive));
        for (TestingSample testingSample : testSamples) {
            if (testingSample.getActualResult()) {
                truePositive++;
            } else {
                falsePositive++;
            }
            roc.addPoint(new ROCPoint(truePositive / maxPoint.getTruePositive(), falsePositive / maxPoint.getFalsePositive()));
        }

        return roc;
    }

    private static ROCPoint countMaxPoint(List<TestingSample> testSamples){
        Double positive = 0.0;
        Double negative = 0.0;
        for (TestingSample testSample : testSamples) {
            if (testSample.getActualResult()) {
                positive++;
            } else {
                negative++;
            }
        }
        return new ROCPoint(positive, negative);
    }

    public static void writeDown(File file, ROC roc) throws FileNotFoundException{
        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(file))){
            for (ROCPoint point : roc.getRoc()) {
                printWriter.println(point);
            }
            printWriter.flush();
        }
    }

    public static Double getAuc(ROC roc){
        Double auc = 0.0;
        for (int i = 0; i < roc.getRoc().size() - 1; i++){
            Double height = (roc.getRoc().get(i).getTruePositive() + roc.getRoc().get(i+1).getTruePositive()) / 2;
            Double length = roc.getRoc().get(i + 1).getFalsePositive() - roc.getRoc().get(i).getFalsePositive();
            auc += (height * length);
        }
        return auc;
    }
}
