package impl.root;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;

import actions.MoveLeftAction;
import actions.MoveRightAction;
import actions.StandStillAction;
import exceptions.OutOfBoundsException;
import game_world.FallingBlock;
import game_world.Position;
import game_world.SimpleGameController;
import game_world.api.Action;
import game_world.api.ActionResult;
import game_world.api.FacadeGameWorld;
import game_world.api.Predicate;
import game_world.api.PredicateResult;
import game_world.api.Snapshot;
import predicates.BlockAbovePlayerPredicate;

public class ImplementationGameWorld implements FacadeGameWorld {

	private SimpleGameController gameController;

	public ImplementationGameWorld() {
		gameController = new SimpleGameController();
		gameController.startGame();
	}

	@Override
	public List<Action> getAllActions() {
		return Arrays.asList(new MoveLeftAction(), new MoveRightAction(), new StandStillAction());
	}

	@Override
	public List<Predicate> getAllPRedicates() {
		return Arrays.asList(new BlockAbovePlayerPredicate());
	}

	@Override
	public ActionResult executeAction(Action action) {
		if (action instanceof MoveLeftAction) {
			try {
				gameController.actionPlayerLeft();
				return ActionResult.Success;
			} catch (OutOfBoundsException e) {
				return ActionResult.Illegal;
			}
		} else if (action instanceof MoveRightAction) {
			try {
				gameController.actionPlayerRight();
				return ActionResult.Success;
			} catch (OutOfBoundsException e) {
				return ActionResult.Illegal;
			}
		} else if (action instanceof StandStillAction) {
			gameController.actionPlayerStay();
			return ActionResult.Success;

		} else {
			return ActionResult.UnknownAction;
		}

	}

	@Override
	public PredicateResult evaluatePredicate(Predicate predicate) {

		if (predicate instanceof BlockAbovePlayerPredicate) {
			if (gameController.blockAbovePlayer()) {
				return PredicateResult.True;
			} else {
				return PredicateResult.False;
			}
		} else {
			return PredicateResult.BadPredicate;
		}
	}

	@Override
	public void drawGameWorld(Graphics g, int width, int height) {
		int gridWidth = gameController.getWidth();
		int gridHeight = gameController.getHeight();

		int worldWidth = width;
		int worldHeight = height;

		int cellWidth = worldWidth / (gridWidth + 1);
		int cellHeight = worldHeight / (gridHeight);

		g.setColor(Color.black);

		// Vertical lines
		for (int i = 0; i < gridWidth + 1; i++) {
			g.drawLine(i * cellWidth, 0, i * cellWidth, worldHeight);
		}

		// Horizontal lines
		for (int i = 0; i < gridHeight + 1; i++) {
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
			g.fillRect(cellWidth * block.getPosition().getX(), cellHeight * block.getPosition().getY(), cellWidth,
					cellHeight);
		}
	}

	private void drawPlayer(Graphics g, int cellWidth, int cellHeight) {
		g.setColor(Color.gray);
		Position playerPosition = gameController.getGameWorld().getPlayer().getPosition();
		g.fillRect(cellWidth * playerPosition.getX(), cellHeight * playerPosition.getY(), cellWidth, cellHeight);
	}

	private void drawDodgeScore(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		String dodgedString = "Blocks dodged: " + gameController.getCurrentNrDodged() + "/"
				+ gameController.getDodgeGoal();
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
	public void loadSnapshot(Snapshot snapshot) {
		if(snapshot instanceof GameSnapshot) {
			this.gameController = ((GameSnapshot)snapshot).getState().createCopy();
		}
	}

	@Override
	public Snapshot makeSnapshot() {
		return (Snapshot)new GameSnapshot(gameController.createCopy());
	}

	@Override
	public void resetGameWorld() {
		gameController.startGame();
	}

	@Override
	public boolean goalReached() {
		return gameController.goalReached();
	}

}
