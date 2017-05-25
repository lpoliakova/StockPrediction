import Test.ROC;
import Test.Tester;
import Test.TestingSample;
import Train.GaussianModel;
import Train.Learner;
import Train.LearningSample;
import Train.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oradchykova on 5/18/17.
 */
public class Application {
    public static void main(String[] args) {
        try {
            Model model = Learner.learn(new File("Stock.txt"), 1000, 80);
            //System.out.println(model.computeRatio(1.2951, 1.2970-1.2884));
            ROC roc = Tester.getRoc(model, new File("Stock.txt"), 1000, 15, new File("Roc.txt"));
            System.out.println(Tester.getAuc(roc));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    /*public static void main(String[] args) {
        List<LearningSample> samples = new ArrayList<>();
        samples.add(new LearningSample(true,1.0,0.0));
        samples.add(new LearningSample(true,1.0,4.0));
        samples.add(new LearningSample(true,1.0,5.0));
        samples.add(new LearningSample(true,2.0,3.0));
        samples.add(new LearningSample(true,2.0,4.0));
        samples.add(new LearningSample(true,2.0,5.0));
        samples.add(new LearningSample(true,3.0,5.0));
        samples.add(new LearningSample(true,4.0,6.0));
        samples.add(new LearningSample(false,2.0,5.0));
        samples.add(new LearningSample(false,3.0,3.0));
        samples.add(new LearningSample(false,3.0,4.0));
        samples.add(new LearningSample(false,3.0,5.0));
        samples.add(new LearningSample(false,3.0,6.0));
        samples.add(new LearningSample(false,4.0,5.0));
        GaussianModel model = new GaussianModel();
        model.learn(samples);
        model.computeRatio(1.0, 2.0);
    }*/

}
