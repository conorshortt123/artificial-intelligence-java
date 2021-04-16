package ie.gmit.sw.ai;

import java.io.File;
import java.io.IOException;

import org.encog.util.obj.SerializeObject;

import ie.gmit.sw.ai.nn.NeuralNetwork;
import ie.gmit.sw.ai.nn.Utils;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Rule;

/**
 * This class loads both the NeuralNetwork and Fuzzy Control Language files
 * from file storage. It also handles the processing on input data and
 * returns output which is handled in the respective neural and fuzzy
 * minotaur classes.
 */
public class LoadAI {

	private static NeuralNetwork nn;
	private static FIS fis;
	private final String nn_file = "res/neural/minotaur.nn";
	private final String fcl_file = "res/fuzzy/minotaur.fcl";

	// Loads the neural network from file storage.
	public void LoadNetwork() throws ClassNotFoundException, IOException {
		try {
			nn = (NeuralNetwork) SerializeObject.load(new File(nn_file));
		} catch (ClassNotFoundException | IOException e) { 
			e.printStackTrace();
		}
	}
	
	// Loads fuzzy inference system from file storage.
	public void LoadFis() {
		fis = FIS.load(fcl_file, true); //Load and parse the FCL
		
		// Check for load errors
        if( fis == null ) { 
            System.err.println("Can't load file: '" + fcl_file + "'");
            return;
        }
		
		//JFuzzyChart.get().chart(fis); //Display the linguistic variables and terms
	}
	
	// Returns the state from the given input, 0 = Attack, 1 = Run, 2 = Patrol
	public int NeuralNetProcess(double[] input) throws Exception {
		double[] result = nn.process(input);
		return Utils.getMaxIndex(result);
	}
	
	// Returns the defuzzified variable "attack", an aggregation of fuzzy rules.
	public double FisProcess(double[] input) {
		fis.getFunctionBlock("Attack");
		
		fis.setVariable("health", input[0]); //Apply a value to a variable
		fis.setVariable("stamina", input[1]); //Apply a value to a variable
		fis.setVariable("rage", input[2]); //Apply a value to a variable
		
		fis.evaluate(); //Execute the fuzzy inference engine
		
		return fis.getVariable("attack").getValue(); // Return end result
	}
}
