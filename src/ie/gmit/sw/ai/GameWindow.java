package ie.gmit.sw.ai;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * Main UI for the game. You should not have to alter anything in this class.
 * 
 */
public class GameWindow extends Application{
	private static final char PLAYER_ID = '1';
	private static final int DEFAULT_SIZE = 60;
	private static final int IMAGE_COUNT = 9;
	private GameView view;
	private GameModel model;
	private int currentRow;
	private int currentCol;
	private Player player;

	@Override
    public void start(Stage stage) throws Exception {
		model = new GameModel(DEFAULT_SIZE); //Create a model
    	view = new GameView(model); //Create a view of the model

    	stage.setTitle("GMIT - B.Sc. in Computing (Software Development) - AI Assignment 2021");
		stage.setWidth(600);
		stage.setHeight(630);
		stage.setOnCloseRequest((e) -> model.tearDown()); //Shut down the executor service
    	
		VBox box = new VBox();
		Scene scene = new Scene(box);
		scene.setOnKeyPressed(e -> keyPressed(e)); //Add a key listener
		stage.setScene(scene);
		
    	Sprite[] sprites = getSprites(); //Load the sprites from the res directory
    	view.setSprites(sprites); //Add the sprites to the view
    	placePlayer(); //Add the player
    	box.getChildren().add(view);
		
    	view.draw(); //Paint the view
    	
		//Display the window
		stage.show();
		stage.centerOnScreen();
	}
	
    public void keyPressed(KeyEvent e) { //Handle key events
    	// Allows the player to control player model if player is alive.
    	if(player.isAlive()) {
	    	KeyCode key = e.getCode(); 
	        if (key == KeyCode.RIGHT && currentCol < DEFAULT_SIZE - 1) {
	        	if (model.isValidMove(currentRow, currentCol, currentRow, currentCol + 1, PLAYER_ID)) currentCol++;   		
	        }else if (key == KeyCode.LEFT && currentCol > 0) {
	        	if (model.isValidMove(currentRow, currentCol, currentRow, currentCol - 1, PLAYER_ID)) currentCol--;	
	        }else if (key == KeyCode.UP && currentRow > 0) {
	        	if (model.isValidMove(currentRow, currentCol, currentRow - 1, currentCol, PLAYER_ID)) currentRow--;
	        }else if (key == KeyCode.DOWN && currentRow < DEFAULT_SIZE - 1) {
	        	if (model.isValidMove(currentRow, currentCol, currentRow + 1, currentCol, PLAYER_ID)) currentRow++;        	  	
	        }else if (key == KeyCode.Z){
	        	view.toggleZoom();
	        }else{
	        	return;
	        }
    	}
        
        updateView();
    }
	
	private void placePlayer(){  //Place the main player character	
    	currentRow = (int) (DEFAULT_SIZE * Math.random());
    	currentCol = (int) (DEFAULT_SIZE * Math.random());
    	model.set(currentRow, currentCol, PLAYER_ID); //Player is at index 1
    	player = Player.GetInstance();
    	updateView();
	}
	
	private void updateView(){ 
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
		player.setRow(currentRow); // updates current row in player class
		player.setCol(currentCol); // updates current col in player class
	}
	
	private Sprite[] getSprites() throws Exception{
		/*
		 * Read in the images from the resources directory as sprites. Each sprite is
		 * referenced by its index in the array, e.g. a 3 implies a Pink Enemy... Ideally, 
		 * the array should dynamically created from the images... 
		 */
		Sprite[] sprites = new Sprite[IMAGE_COUNT];
		sprites[0] = new Sprite("Player", "/res/player/player-0.png", "/res/player/player-1.png", "/res/player/player-2.png", "/res/player/player-3.png", "/res/player/player-4.png", "/res/player/player-5.png", "/res/player/player-6.png", "/res/player/player-7.png");
		sprites[1] = new Sprite("Minotaur", "/res/original-minotaur/minotaur-0.png", "/res/original-minotaur/minotaur-1.png", "/res/original-minotaur/minotaur-2.png", "/res/original-minotaur/minotaur-3.png", "/res/original-minotaur/minotaur-4.png", "/res/original-minotaur/minotaur-5.png", "/res/original-minotaur/minotaur-6.png", "/res/original-minotaur/minotaur-7.png");
		sprites[2] = new Sprite("Red Minotaur", "/res/red-minotaur/red-minotaur-0.png", "/res/red-minotaur/red-minotaur-1.png", "/res/red-minotaur/red-minotaur-2.png", "/res/red-minotaur/red-minotaur-3.png", "/res/red-minotaur/red-minotaur-4.png", "/res/red-minotaur/red-minotaur-5.png", "/res/red-minotaur/red-minotaur-6.png", "/res/red-minotaur/red-minotaur-7.png");
		sprites[3] = new Sprite("Blue Minotaur", "/res/blue-minotaur/blue-minotaur-0.png", "/res/blue-minotaur/blue-minotaur-1.png", "/res/blue-minotaur/blue-minotaur-2.png", "/res/blue-minotaur/blue-minotaur-3.png", "/res/blue-minotaur/blue-minotaur-4.png", "/res/blue-minotaur/blue-minotaur-5.png", "/res/blue-minotaur/blue-minotaur-6.png");
		sprites[4] = new Sprite("Green Minotaur", "/res/green-minotaur/green-minotaur-0.png", "/res/green-minotaur/green-minotaur-1.png", "/res/green-minotaur/green-minotaur-2.png", "/res/green-minotaur/green-minotaur-3.png", "/res/green-minotaur/green-minotaur-4.png", "/res/green-minotaur/green-minotaur-5.png", "/res/green-minotaur/green-minotaur-6.png");
		sprites[5] = new Sprite("Yellow Minotaur", "/res/yellow-minotaur/yellow-minotaur-0.png", "/res/yellow-minotaur/yellow-minotaur-1.png", "/res/yellow-minotaur/yellow-minotaur-2.png", "/res/yellow-minotaur/yellow-minotaur-3.png", "/res/yellow-minotaur/yellow-minotaur-4.png", "/res/yellow-minotaur/yellow-minotaur-5.png", "/res/yellow-minotaur/yellow-minotaur-6.png", "/res/yellow-minotaur/yellow-minotaur-7.png");
		sprites[6] = new Sprite("Hedge", "/res/grass.png");
		sprites[7] = new Sprite("Floor", "/res/floor/floor-0.png");
		sprites[8] = new Sprite("Dead Player", "/res/player/player-dead.png");
		return sprites;
	}
}