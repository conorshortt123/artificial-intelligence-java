package ie.gmit.sw.ai.nn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.encog.util.obj.SerializeObject;

import ie.gmit.sw.ai.nn.activator.Activator;

public class Runner {

	public static void main(String[] args) throws Exception {
		new Runner().go();
	}
	
	public void go() throws Exception {
		
		/*
		 * This neural network contains four input variables:
		 * 
		 * Health (2 = Healthy, 1 = Minor Injuries, 0 = Serious Injuries)
		 * Stamina (2 = Fit, 1 = Tired, 0 = Exhausted)
		 * Rage (2 = Enraged, 1 = Angry, 0 = Calm)
		 * Player (1 = In range, 0 = Out of Range)
		 * 
		 * And three output variables, dictating the minotaur's state:
		 * 
		 * Attack (1, 0)
		 * Run (1, 0)
		 * Patrol (1, 0)
		 */
		
		// Create neural network topology, 4 input layers, 2 hidden, 3 output.
		NeuralNetwork nn = new NeuralNetwork(Activator.ActivationFunction.Sigmoid, 4, 2, 3);
		BackpropagationTrainer trainer = new BackpropagationTrainer(nn);
		
		// Read in data from csv file
		double[][] data = ReadCsvFile("res/neural/data.csv", 4, 22); // 4 rows, 22 cols of data
		double[][] expected = ReadCsvFile("res/neural/expected.csv", 3, 22); // 3 rows, 22 cols of data
		
		// Train the neural network with inputs and expected outputs, with a learning rate of 0.01
		// and 10000 epochs.
		trainer.train(data, expected, 0.01, 10000);
				
		// Test neural network
		double[] test = { 2, 2, 2, 0 };
		double[] result = nn.process(test);
		for (int i = 0; i < test.length; i++){ 
			System.out.print(test[i] + ",");
		}
		System.out.println("===> " + (Utils.getMaxIndex(result)));
		
		System.out.println(result[0] + " " + result[1] + " " + result[2]);
		
		// Saving neural network model to file storage
		Scanner sc = new Scanner(System.in); 
		System.out.println("\nDo you want to save the neural network model? This will overwrite the last model. (Y/N)");
		String input = sc.nextLine();
		
		if(input.equalsIgnoreCase("Y"))
			SaveNetwork(nn);
	}
	
	private double[][] ReadCsvFile(String filename, int cols, int rows) throws FileNotFoundException, IOException {
		
		double[][] output = new double[rows][cols];
		int i = 0, j = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
		    String line = br.readLine(); // skip first line containing variable names
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(",");
		        for (String value : values) {
					output[j][i] = Double.parseDouble(value);
					i++;
				}
		        i = 0;
		        j++;
		    }
		    return output;
		}
	}

	public void SaveNetwork(NeuralNetwork network) throws IOException {
		try {
			SerializeObject.save(new File("res/neural/minotaur.nn"), network);
			System.out.println("Network was successfully saved to 'res/neural/minotaur.nn'!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
