package ie.gmit.sw.ai;

/**
 * This class is the super class of NeuralMinotaur and
 * FuzzyMinotaur. All of the methods that are common to
 * both minotaur classes are contained here. This is an ideal
 * application of the DRY principle as all of the necessary methods
 * are automatically inherited by subclasses of this class.
 * The singleton instance of player is also retrieved for
 * calling attack methods etc.
 */
public class Minotaur implements Command {
	
	protected double health = 100.00;
	protected double stamina = 100.00;
	protected double rage = 0.00;
	protected double damage = 10.00;
	protected double healthRegen = 0.50;
	protected double staminaDrain = 5;
	protected MinotaurState state;
	protected double playerRange = 0.0;
	protected Player player;

	public Minotaur(double health, double stamina, double rage, MinotaurState state) {
		super();
		this.health = health;
		this.stamina = stamina;
		this.rage = rage;
		this.state = state;
		player = Player.GetInstance();
	}

	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getStamina() {
		return stamina;
	}

	public void setStamina(double stamina) {
		this.stamina = stamina;
	}

	public double getRage() {
		return rage;
	}

	public void setRage(double rage) {
		this.rage = rage;
	}
	
	public double getPlayerRange() {
		return playerRange;
	}

	public void setPlayerRange(double playerRange) {
		this.playerRange = playerRange;
	}

	public MinotaurState getState() {
		return state;
	}

	public void setState(MinotaurState state) {
		this.state = state;
	}

	@Override
	public void execute() throws Exception {
		System.out.println("I'm a regular minotaur");
	}

	protected void attack(double damage) {
		double actualDamage = damage + (rage / 20);
		stamina -= staminaDrain;
		player.setHealth((player.getHealth() - actualDamage));
		System.out.println(this.getClass().getSimpleName() + "\t--> attacked player for: " + actualDamage + " damage! "
				+ "Player Health: " + player.getHealth() + " Minotaur Stamina: " + stamina);
		
		if(player.getHealth() <= 0) {
			player.setAlive(false);
			System.out.println("Player is dead!");
		}
	}
	
	protected void Regenerate() {
		if(health <= 100) {
			health += healthRegen;
			System.out.println(this.getClass().getSimpleName() + "\t--> is now patrolling and regenerating health!");
		}
	}
	
	protected void CheckHealth() {
		if(this.health <= 0)
			this.state = MinotaurState.DEAD;
	}

	protected void Run() {
		if(stamina >= 0) {
			stamina -= staminaDrain;
			System.out.println(this.getClass().getSimpleName() + "\t--> is running while it still has stamina! Stamina is: " + stamina);
		}
	}
}
