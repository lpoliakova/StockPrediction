import Learning.Learner;
import Learning.Model;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by oradchykova on 5/18/17.
 */
public class Application {
    public static void main(String[] args) {
        try {
            Model model = Learner.learn(new File("Stock.txt"), 100, 70);
            System.out.println(model.computeRatio(1.2951, 1.2970-1.2884));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
