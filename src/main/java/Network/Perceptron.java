package Network;

import java.util.ArrayList;

public class Perceptron {
    private ArrayList<Double> weights = new ArrayList<>();
    public Perceptron(int numberOfInputs) {
        for (int i = 0; i < numberOfInputs; i++) {
            weights.add(Math.random()*1);
        }
    }

    public int getInputCount() {
        return weights.size();
    }

    public double getWeight(int i) {
        return weights.get(i);
    }

    void setWeight(int i, double value) {
        weights.set(i, value);
    }

    private double sigmoid (double sum){
        return 1.0/(1.0 + Math.exp(-sum));
    }

    public double getOutput(double[] input) {
        double sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += weights.get(i) * input[i];
        }
        return sigmoid(sum);
    }
}
