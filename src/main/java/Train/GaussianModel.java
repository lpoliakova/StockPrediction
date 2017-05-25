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

    GaussianModel(){
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
        return prediction;
    }

    public Model learn(List<LearningSample> learnObjects){
        List<LearningSample> raisingObjects = getRaisingObjects(learnObjects);
        List<LearningSample> fallingObjects = getFallingObjects(learnObjects);
        meanRaising = findMean(raisingObjects);
        meanFalling = findMean(fallingObjects);
        distanceRaising = findInverse2(findDistance(raisingObjects, meanRaising));
        distanceFalling = findInverse2(findDistance(fallingObjects, meanFalling));
        return this;
    }

    private List<LearningSample> getRaisingObjects(List<LearningSample> learnObjects){
        List<LearningSample> raisingObjects = new ArrayList<>();
        for (LearningSample learnObject : learnObjects) {
            if (learnObject.getResult()) raisingObjects.add(learnObject);
        }
        return raisingObjects;
    }

    private List<LearningSample> getFallingObjects(List<LearningSample> learnObjects){
        List<LearningSample> fallingObjects = new ArrayList<>();
        for (LearningSample learnObject : learnObjects) {
            if (!learnObject.getResult()) fallingObjects.add(learnObject);
        }
        return fallingObjects;
    }

    private Double[] findMean(List<LearningSample> learnObjects){
        int featureLen = learnObjects.get(0).getFeatures().length;
        Double[] mean = new Double[featureLen];
        Arrays.fill(mean, 0.0);
        for (LearningSample learnObject : learnObjects) {
            for (int i = 0; i < featureLen; i++){
                mean[i] += (learnObject.getFeatures()[i] / learnObjects.size());
            }
        }
        return mean;
    }

    private Double[][] findDistance(List<LearningSample> learnObjects, Double[] mean){
        int featureLen = learnObjects.get(0).getFeatures().length;
        Double[][] distance = new Double[featureLen][featureLen];
        for (int i = 0; i < featureLen; i++) Arrays.fill(distance[i], 0.0);
        for (LearningSample learnObject : learnObjects) {
            for (int i = 0; i < featureLen; i++) {
                for (int j = 0; j < i + 1; j++) {
                    Double curDistance = (learnObject.getFeatures()[i] - mean[i]) * (learnObject.getFeatures()[j] - mean[j]) / learnObjects.size();
                    distance[i][j] += curDistance;
                    distance[j][i] += curDistance;
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
