package Network;

import java.util.ArrayList;

public class Network {
    private final Perceptron[][] perceptron;
    private final float eta = 0.2f;
    double delta;

    public Network(int[] numberOfPerceptrons, int inputCount) {
        perceptron = new Perceptron[numberOfPerceptrons.length][];
        for (int i = 0; i < perceptron.length; i++) {
            perceptron[i] = new Perceptron[numberOfPerceptrons[i]];
            int num = (i == 0) ? inputCount : perceptron[i - 1].length;
            for (int j = 0; j < perceptron[i].length; j++) {
                perceptron[i][j] = new Perceptron(num);
            }
        }
    }

    public void train(int numberOfSteps, double[][] inputs, double[][] answers) {
        for (int i = 0; i < numberOfSteps; i++) {
            for (int j = 0; j < inputs.length; j++) {
                train(inputs[j], answers[j]);
            }

        }
    }

    private void train(double[] inputs, double[] answers) {
        double[][] outputs = calculateOutputs(inputs);
        final ArrayList<Double> deltas = new ArrayList<Double>();
        final ArrayList<Double> previousDeltas = new ArrayList<Double>();
        for (int i = perceptron.length - 1; i >= 0; i--) {
            for (int j = 0; j < perceptron[i].length; j++) {
                final double output = outputs[i][j];
                if (i == perceptron.length - 1) {
                    delta = answers[j] - output;
                }
                else {
                    delta = 0;
                    for (int k = 0; k < perceptron[i + 1].length; k++) {
                        delta += previousDeltas.get(k) * perceptron[i + 1][k].getWeight(j);
                    }
                }
                delta *= output * (1 - output);
                deltas.add(delta);
                for (int k = 0; k < perceptron[i][j].getInputCount(); k++) {
                    final double in = (i == 0) ? inputs[k] : outputs[i - 1][k];
                    perceptron[i][j].setWeight(k, perceptron[i][j].getWeight(k) + eta * delta * in);
                }
            }
            previousDeltas.clear();
            previousDeltas.addAll(deltas);
            deltas.clear();
        }
    }

    public double[][] calculateOutputs(double[] inputs) {
        final double[][] result = new double[perceptron.length][];
        for (int i = 0; i < perceptron.length; i++) {
            result[i] = new double[perceptron[i].length];
            for (int j = 0; j < perceptron[i].length; j++) {
                final double[] in = (i == 0) ? inputs : result[i - 1];
                result[i][j] = perceptron[i][j].getOutput(in);
            }
        }
        return result;
    }

    public double[] getOutput(double[] inputs) {
        if (inputs.length != perceptron[0][0].getInputCount()) {
            throw new IllegalArgumentException("wrong number of inputs");
        }
        return calculateOutputs(inputs)[perceptron.length - 1];
    }

    public double[] rationing(double[] inputs, double sum) {
        double[] result = new double[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            result[i] = inputs[i]/(Math.sqrt(sum));
        }
        return result;
    }

    public double rationingSum(double[] inputs) {
        double sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i]*inputs[i];
        }
        return sum;
    }
}
