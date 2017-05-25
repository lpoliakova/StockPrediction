package Test;

/**
 * Created by oradchykova on 5/25/17.
 */
public class ROCPoint {
    private Double truePositive;
    private Double falsePositive;

    public ROCPoint(Double truePositive, Double falsePositive) {
        this.truePositive = truePositive;
        this.falsePositive = falsePositive;
    }

    public Double getTruePositive() {
        return truePositive;
    }

    public Double getFalsePositive() {
        return falsePositive;
    }

    @Override
    public String toString(){
        return truePositive + "\t" + falsePositive;
    }
}
