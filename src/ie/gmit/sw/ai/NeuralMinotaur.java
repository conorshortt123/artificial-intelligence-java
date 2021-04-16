package ie.gmit.sw.ai;

/**
 * This class is used for instantiation of a minotaur controlled
 * by the backpropagation neural network. It contains methods for calculating
 * correct inputs into the network, and also handles the output in deciding which
 * state the minotaur should be in. 
 */
public class NeuralMinotaur extends Minotaur implements Command {

	// Create instance of class containing the neural network.
	LoadAI ai = new LoadAI();

	public NeuralMinotaur(double health, double stamina, double rage, MinotaurState state) {
		super(health, stamina, rage, state);
	}

	/**
	 * Inherited method from command interface, handles what the game character should do
	 * when in a specific state. Calls necessary methods for getting inputs and passing
	 * them into the neural network.
	 */
	@Override
	public void execute() throws Exception {
		
		// Checks minotaur health to evaluate whether or not it's dead.
		super.CheckHealth();
		// Get neural network inputs from health, stam and rage.
		double[] input = GetInputs();
		// Let NeuralNetwork determine result from inputs.
		int result = ai.NeuralNetProcess(input);
		// Change minotaur's state enumeration from result.
		state = DetermineState(result);
		
		switch (state) {
			case ATTACK:
				if(player.isAlive())
					super.attack(super.damage); // Attack player if alive and in attack state.
				break;
			case PATROL:
				super.Regenerate(); // Regenerate health while minotaur is searching for player.
				break;
			case RUN:
				super.Run();
				break;
			case DEAD:
				System.out.println(getClass().getSimpleName() + " was killed!");
				break;
			default:
				break;
		}
	}

	// Gets all inputs in correct format, and returns a double array of them.
	private double[] GetInputs() {

		double healthInput = GetInput(health);
		double stamInput = GetInput(stamina);
		double rageInput = GetInput(rage);

		double[] input = {healthInput, stamInput, rageInput, getPlayerRange()};

		return input;

	}

	// Returns correct format for a single input.
	private double GetInput(double input) {

		if (input <= 33)
			return 0;
		else if (input <= 66)
			return 1;
		else
			return 2;
	}

	// Switches state depending on output from neural network.
	private MinotaurState DetermineState(int state) {
		return switch (state) {
			case 0 -> MinotaurState.ATTACK;
			case 1 -> MinotaurState.RUN;
			case 2 -> MinotaurState.PATROL;
	
			default -> null;
		};
	}
}
