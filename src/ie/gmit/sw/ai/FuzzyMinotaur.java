package ie.gmit.sw.ai;

/**
 * This class is used for instantiation of a minotaur controlled
 * by the fuzzy inference system. It's only non-inherited method
 * "EvaluateState" passes the necessary variable in in their true form,
 * as they are used as crisp inputs into the fuzzy inference system.
 * The FIS returns a defuzzified crisp value "attack", which depending
 * on the value will cause the minotaur to enter either the attack or run state.
 * Or, if the player is out of range, the minotaur enters the patrol state.
 */
public class FuzzyMinotaur extends Minotaur implements Command {

	public FuzzyMinotaur(double health, double stamina, double rage, MinotaurState state) {
		super(health, stamina, rage, state);
	}

	/**
	 * Inherited method from command interface, handles what the game character should do
	 * when in a specific state. Calls necessary methods for getting inputs and passing
	 * them into the neural network.
	 */
	@Override
	public void execute() {
		// Create instance of AI class containing FIS.
		LoadAI lai = new LoadAI();
		// Create input variable for process method.
		double[] input = { health, stamina, rage };
		// Call FIS process method on input, return value is passed into state
		// evaluation method to set correct MinotaurState.
		EvaluateState(lai.FisProcess(input));
		
		switch (state) {
			case ATTACK:
				if (player.isAlive())
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

		super.CheckHealth();
	}

	// Evaluates correct state.
	private void EvaluateState(double attackValue) {
		if (attackValue > 50 && this.getPlayerRange() == 1)
			this.setState(MinotaurState.ATTACK);
		else if (attackValue < 50 && this.getPlayerRange() == 1)
			this.setState(MinotaurState.RUN);
		else
			this.setState(MinotaurState.PATROL);
	}
}
