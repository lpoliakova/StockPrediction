package Train;

import java.util.Arrays;

/**
 * Created by oradchykova on 5/18/17.
 */
public class LearningSample {
    private final Double[] features;
    private final Boolean result;

    public LearningSample(Boolean result, Double... features) {
        this.features = features;
        this.result = result;
    }

    public Double[] getFeatures() {
        return features;
    }

    public Boolean getResult() {
        return result;
    }

    @Override
    public String toString(){
        return String.format("[result: %s, features: %s]", result, Arrays.toString(features));
    }
}
