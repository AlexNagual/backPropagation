import Network.Network;
import utils.FileEditor;

import java.io.IOException;

public class Main {
    private final static int NUMBER_OF_STEPS = 1000000;

    public static void main(String[] args) throws IOException {
        run();
    }

    private static void run() {
        FileEditor loadVariable = new FileEditor();
        double[][] inputs = loadVariable.loadArrayFromFile("src/main/resources/input.txt");
        double[][] answers = loadVariable.loadArrayFromFile("src/main/resources/answers.txt");
        double[][] testInputs= loadVariable.loadArrayFromFile("src/main/resources/test_inputs.txt");
        int numberOfOutput = 1;
        double[][] normalizedInputs = new double[inputs.length][inputs[0].length];
        double[][] normalizedAnswers = new double[answers.length][answers[0].length];
        double[][] normalizedTestInputs = new double[testInputs.length][testInputs[0].length];
        double[][] finalOutput = new double[testInputs.length][numberOfOutput];
        Network network = new Network(new int[]{1, numberOfOutput}, inputs[0].length);
        double sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[0].length; j++) {
                sum += network.rationingSum(inputs[i]) + network.rationingSum(answers[i]);
            }
        }
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[0].length; j++) {
                normalizedInputs[i][j] = network.rationing(inputs[i], sum)[j];
            }
        }
        for (int i = 0; i < answers.length; i++) {
            for (int j = 0; j < answers[0].length; j++) {
                normalizedAnswers[i][j] = network.rationing(answers[i], sum)[j];
            }
        }
        for (int i = 0; i < testInputs.length; i++) {
            for (int j = 0; j < testInputs[0].length; j++) {
                normalizedTestInputs[i][j] = network.rationing(testInputs[i], sum)[j];
            }
        }
        network.train(NUMBER_OF_STEPS, normalizedInputs, normalizedAnswers);
        System.out.println("Тренировочная выборка");
        for (int i = 0; i < inputs.length; i++) {
            System.out.print(i + ") ");
            for (int j = 0; j < inputs[0].length; j++) {
                System.out.print(inputs[i][j] + " ");
            }
            System.out.println(" Ответ: " + answers[i][0] + " Результат: " + network.getOutput(normalizedInputs[i])[0]*Math.sqrt(sum) );
        }
        System.out.println("Тестовые данные");
        for (int i = 0; i < testInputs.length; i++) {
            for (int j = 0; j < testInputs[0].length; j++) {
                System.out.print(testInputs[i][j] + " ");
            }
            System.out.println("Ответ: " + network.getOutput(normalizedTestInputs[i])[0]*Math.sqrt(sum));
        }
        for (int i = 0; i < numberOfOutput; i++) {
            finalOutput[i][0] = network.getOutput(normalizedTestInputs[i])[0]*Math.sqrt(sum);
        }
        loadVariable.saveArrayToFile(finalOutput, "src/main/resources/output.txt");
    }
}
