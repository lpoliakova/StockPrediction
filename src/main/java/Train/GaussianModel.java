package Train;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by oradchykova on 5/18/17.
 */
public class GaussianModel implements Model{
    private Double[] meanRaising;
    private Double[][] distanceRaising;
    private Double[] meanFalling;
    private Double[][] distanceFalling;

    public GaussianModel(){
        meanRaising = new Double[] {0.0, 0.0};
        distanceRaising = new Double[][] {{0.0, 0.0}, {0.0, 0.0}};
        meanFalling = new Double[] {0.0, 0.0};
        distanceFalling = new Double[][] {{0.0, 0.0}, {0.0, 0.0}};
    }

    @Override
    public Double computeRatio(Double... features){
        return computePrediction(features, distanceRaising, meanRaising)
                / computePrediction(features, distanceFalling, meanFalling);
    }

    private Double computePrediction(Double[] features, Double[][] distance, Double[] mean){
        Double[] toMultiply = new Double[features.length];
        Arrays.fill(toMultiply, 0.0);
        for (int row = 0; row < features.length; row++){
            for (int i = 0; i < features.length; i++){
                toMultiply[row] += distance[row][i] * (features[i] - mean[i]);
            }
        }
        Double prediction = 0.0;
        for (int i = 0; i < features.length; i++){
            prediction += toMultiply[i] * (features[i] - mean[i]);
        }
        System.out.println(prediction);
        return prediction;
    }

    public Model learn(List<LearningSample> learnObjects){
        List<LearningSample> raisingSamples = getRaisingObjects(learnObjects);
        List<LearningSample> fallingSamples = getFallingObjects(learnObjects);

        meanRaising = findMean(raisingSamples);
        System.out.println(Arrays.toString(meanRaising));
        meanFalling = findMean(fallingSamples);
        System.out.println(Arrays.toString(meanFalling));

        distanceRaising = findInverse2(findDistance(raisingSamples, meanRaising));
        System.out.println(Arrays.deepToString(distanceRaising));
        distanceFalling = findInverse2(findDistance(fallingSamples, meanFalling));
        System.out.println(Arrays.deepToString(distanceFalling));
        return this;
    }

    private List<LearningSample> getRaisingObjects(List<LearningSample> learnSamples){
        List<LearningSample> raisingSamples = new ArrayList<>();
        for (LearningSample learnSample : learnSamples) {
            if (learnSample.getResult()) raisingSamples.add(learnSample);
        }
        return raisingSamples;
    }

    private List<LearningSample> getFallingObjects(List<LearningSample> learnSamples){
        List<LearningSample> fallingSamples = new ArrayList<>();
        for (LearningSample learnSample : learnSamples) {
            if (!learnSample.getResult()) fallingSamples.add(learnSample);
        }
        return fallingSamples;
    }

    private Double[] findMean(List<LearningSample> learnSamples){
        int featureLen = learnSamples.get(0).getFeatures().length;
        Double[] mean = new Double[featureLen];
        Arrays.fill(mean, 0.0);
        for (LearningSample learnObject : learnSamples) {
            for (int i = 0; i < featureLen; i++){
                mean[i] += (learnObject.getFeatures()[i] / learnSamples.size());
            }
        }
        return mean;
    }

    private Double[][] findDistance(List<LearningSample> learnSamples, Double[] mean){
        int featureLen = learnSamples.get(0).getFeatures().length;
        Double[][] distance = new Double[featureLen][featureLen];
        for (int i = 0; i < featureLen; i++) Arrays.fill(distance[i], 0.0);
        for (LearningSample learnSample : learnSamples) {
            for (int i = 0; i < featureLen; i++) {
                for (int j = 0; j < i + 1; j++) {
                    Double curDistance = (learnSample.getFeatures()[i] - mean[i]) * (learnSample.getFeatures()[j] - mean[j]) / learnSamples.size();
                    distance[i][j] += curDistance;
                    if (i != j) distance[j][i] += curDistance;
                }
            }
        }
        return distance;
    }

    private Double[][] findInverse2(Double[][] matrix) {
        Double det = 1 / (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);
        return new Double[][] {{det * matrix[1][1], - det * matrix[0][1]}, {- det * matrix[1][0], det * matrix[0][0]}};
    }
}
