package game_world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import exceptions.OutOfBoundsException;

public class SimpleGameController {

	private int width = 8;
	private int height = 24;
	
	private GameWorld gameWorld;
	
	private int baseDodgeGoal = 10;
	private int dodgeGoal;
	private int dodgeGoalLevelIncrease = 5;
	private int currentNrDodged;
	
	private boolean gameInProgress;
	
	private Random blockSpawnRandom;
	
	public SimpleGameController() {
		this.gameWorld = new GameWorld(width, height);
		
		dodgeGoal = baseDodgeGoal;
		
		gameInProgress = false;
		
		blockSpawnRandom = new Random();
	}
	
	public SimpleGameController(GameWorld w, int dodgeGoal, boolean gameInProgress, Random blockSpawnRandom) {
		gameWorld = w;
		this.dodgeGoal = dodgeGoal;
		this.gameInProgress = gameInProgress;
		this.blockSpawnRandom = blockSpawnRandom;
	}
	
	// State inspection
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public GameWorld getGameWorld() {
		return this.gameWorld;
	}
	
	public int getDodgeGoal() {
		return this.dodgeGoal;
	}
	
	public int getCurrentNrDodged() {
		return this.currentNrDodged;
	}
	
	public boolean gameInProgress() {
		return this.gameInProgress;
	}
	
	
	// Actions
	public void actionPlayerLeft() throws OutOfBoundsException {
		getGameWorld().movePlayerLeft();
		endTurn();
	}
	
	public void actionPlayerRight() throws OutOfBoundsException {
		getGameWorld().movePlayerRight();
		endTurn();
	}
	
	public void actionPlayerStay() {
		endTurn();
	}
	
	public void startGame() {
		if (!gameInProgress()) {
			this.currentNrDodged = 0;
			getGameWorld().clearAllBlocks();
			this.gameInProgress = true;
		}
	}
	
	// Predicates
	public boolean blockAbovePlayer() {
		int playerX = getGameWorld().getPlayer().getPosition().getX();
		for (FallingBlock block : getGameWorld().getAllBlocks()) {
			if (block.getPosition().getX() == playerX)
				return true;
		}
		
		return false;
	}
	
	public boolean goalReached() {
		return getCurrentNrDodged() >= getDodgeGoal();
	}
	
	
	// State Evolution
	private void endTurn() {
		if (!gameInProgress()) {
			startGame();
		}
		
		moveAllBlocksDown();
		
		if (playerCollidesWithBlock())
			gameOver();
		
		if (goalReached())
			gameSuccess();
		
		spawnBlock();
	}
	
	private void moveAllBlocksDown() {
		
		List<FallingBlock> blocksToDelete = new ArrayList<>();
		
		for (FallingBlock block : getGameWorld().getAllBlocks()) {
			block.moveDown();
			if (block.getPosition().getY() >= getHeight()) {
				currentNrDodged += 1;
				blocksToDelete.add(block);
			}
		}
		
		getGameWorld().getAllBlocks().removeAll(blocksToDelete);


	}
	
	private boolean playerCollidesWithBlock() {
		Position playerPosition = getGameWorld().getPlayer().getPosition();
		
		for (FallingBlock block :getGameWorld().getAllBlocks()) {
			if (playerPosition.equals(block.getPosition()))
				return true;
		}
		
		return false;
	}
	
	private void gameOver() {
		gameInProgress = false;
		dodgeGoal = baseDodgeGoal;
	}
	
	
	private void gameSuccess() {
		gameInProgress = false;
		dodgeGoal += dodgeGoalLevelIncrease;
	}
	
	private void spawnBlock() {
		if (blockSpawnRandom.nextBoolean()) {
			gameWorld.addBlock(blockSpawnRandom.nextInt(getWidth()));
		}
	}
	
	public SimpleGameController createCopy() {
		return new SimpleGameController(this.gameWorld.createCopy(), this.dodgeGoal, this.gameInProgress, this.blockSpawnRandom);
	}
}
