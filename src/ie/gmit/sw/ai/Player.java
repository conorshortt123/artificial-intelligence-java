package ie.gmit.sw.ai;

public class Player implements Command{
	
	private static Player instance = null;
	
	private double health = 100;
	private double stamina = 100;
	private double damage = 15;
	private int row;
	private int col;
	private boolean isAlive = true;
	
	private Player(double health, double stamina, double damage) {
		super();
		this.health = health;
		this.stamina = stamina;
		this.damage = damage;
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

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public static Player GetInstance() {
		// To ensure only one instance is created
        if (instance == null)
        {
            instance = new Player(100, 100, 10);
        }
        return instance;
	}

	public void attack(Minotaur minotaur) {
		if(minotaur.getHealth() >= 0) {
			minotaur.setHealth(minotaur.getHealth() - damage);
			minotaur.setRage(minotaur.getRage() + damage);
			System.out.println("Player\t\t--> attacked " + minotaur.getClass().getSimpleName() + " for " + damage + " damage, health is now " + minotaur.getHealth());
		}
	}

	@Override
	public void execute() throws Exception {
		
	}
}
