package Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oradchykova on 5/25/17.
 */
public class ROC {
    private List<ROCPoint> roc;

    public ROC() {
        roc = new ArrayList<>();
    }

    public ROC(List<ROCPoint> roc) {
        this.roc = roc;
    }

    public List<ROCPoint> getRoc() {
        return roc;
    }

    public void addPoint(ROCPoint point){
        roc.add(point);
    }

    public void addPoints(List<ROCPoint> points){
        roc.addAll(points);
    }
}
