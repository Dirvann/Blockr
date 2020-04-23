package game_world;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.OutOfBoundsException;
import game_world.api.ActionResult;
import game_world.api.FacadeGameWorld;
import game_world.api.PredicateResult;

public class ImplementationGameWorld implements FacadeGameWorld {

	private SimpleGameController gameController;
	
	private Map<String, SimpleGameController> snapshots;
	private int snapshotIndex;
	
	public ImplementationGameWorld() {
		gameController = new SimpleGameController();
		gameController.startGame();
		snapshots = new HashMap<>();
		snapshotIndex = 0;
	}
	
	@Override
	public List<String> getAllActions() {
		return Arrays.asList("MoveLeft", "MoveRight", "StandStill"/*, "StartGame"*/);
	}
	
	@Override
	public List<String> getAllPRedicates() {
		return Arrays.asList("BlockAbovePlayer"/*, "GoalReached"*/);
	}
	
	@Override
	public ActionResult executeAction(String action) {
		switch (action) {
		case "MoveLeft": {
			try {
				gameController.actionPlayerLeft();
				return ActionResult.Success;
			} catch (OutOfBoundsException e) {
				return ActionResult.Illegal;
			}
		}
		
		case "MoveRight": {
			try {
				gameController.actionPlayerRight();
				return ActionResult.Success;
			} catch (OutOfBoundsException e) {
				return ActionResult.Illegal;
			}
		}
		
		case "StandStill": {
			gameController.actionPlayerStay();
			return ActionResult.Success;
		}
		
		/*case "StartGame": {
			gameController.startGame();
			return ActionResult.Success;
		}*/
		
		default:
			return ActionResult.UnknownAction;
		}
	}
	
	@Override
	public PredicateResult evaluatePredicate(String predicate) {
		switch (predicate) {
		case "BlockAbovePlayer": {
			if (gameController.blockAbovePlayer()) {
				return PredicateResult.True;
			} else {
				return PredicateResult.False;
			}
		}
		
		/*case "GoalReached": {
			if (gameController.goalReached()) {
				return PredicateResult.True;
			} else {
				return PredicateResult.False;
			}
		}*/
		
		default:
			return PredicateResult.BadPredicate;
		}
	}

	@Override
	public void drawGameWorld(Graphics g, int width, int height) {
		int gridWidth = gameController.getWidth();
		int gridHeight = gameController.getHeight();
		
		int worldWidth = width;
		int worldHeight = height;
		
		int cellWidth = worldWidth / gridWidth;
		int cellHeight = worldHeight / gridHeight;
		
		g.setColor(Color.black);
		
		// Vertical lines
		for (int i = 0; i < gridWidth +1; i++) {
			g.drawLine(i * cellWidth, 0, i * cellWidth, worldHeight);
		}
		
		// Horizontal lines
		for (int i = 0; i < gridHeight +1; i++) {
			g.drawLine(0, i * cellHeight, worldWidth, i * cellHeight);
		}
		
		if (gameController.gameInProgress()) {
			drawBlocks(g, cellWidth, cellHeight);
			drawPlayer(g, cellWidth, cellHeight);
			drawDodgeScore(g);
		} else {
			drawStartMessage(g);
		}
	}
	
	private void drawBlocks(Graphics g, int cellWidth, int cellHeight) {
		g.setColor(Color.black);
		for (FallingBlock block : gameController.getGameWorld().getAllBlocks()) {
			g.fillRect(cellWidth * block.getPosition().getX(), cellHeight * block.getPosition().getY(), cellWidth, cellHeight);
		}
	}
	
	private void drawPlayer(Graphics g, int cellWidth, int cellHeight) {
		g.setColor(Color.gray);
		Position playerPosition = gameController.getGameWorld().getPlayer().getPosition();
		g.fillRect(cellWidth * playerPosition.getX(), cellHeight * playerPosition.getY(), cellWidth, cellHeight);
	}
	
	private void drawDodgeScore(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		String dodgedString = "Blocks dodged: " + gameController.getCurrentNrDodged() + "/" + gameController.getDodgeGoal();
		g.drawString(dodgedString, 10, 25);
	}
	
	private void drawStartMessage(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString("press start to begin new game", 10, 25);
	}
	
	
	@Override
	public void makeNewGameWorld() {
		gameController = new SimpleGameController();
		gameController.startGame();
	}

	@Override
	public List<String> getAllSnapshots() {
		return new ArrayList<>(snapshots.keySet());
	}


	@Override
	public void loadSnapshot(String snapshotName) {
		this.gameController = snapshots.get(snapshotName).createCopy(); //TODO create copy
	}



	@Override
	public String makeSnapshot() {
		String snapshotName = "AutoSnapshot" + snapshotIndex;
		snapshotIndex += 1;
		makeSnapshot(snapshotName);
		return snapshotName;
	}



	@Override
	public void makeSnapshot(String snapshotName) {
		snapshots.put(snapshotName, gameController.createCopy());
	}



	@Override
	public void removeSnapshot(String snapshotName) {
		snapshots.remove(snapshotName);		
	}

	@Override
	public void resetGameWorld() {
		// TODO Auto-generated method stub
	}

	@Override
	public ActionResult undoAction(String arg0) {
		// TODO Auto-generated method stub
		return ActionResult.Success;
	}

	@Override
	public boolean goalReached() {
		return gameController.goalReached();
	}

}
