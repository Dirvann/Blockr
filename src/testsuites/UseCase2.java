package testsuites;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.GameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
import domain.game_world.Direction;
import domain.game_world.GameWorld;
import domain.game_world.Grid;
import domain.game_world.Vector;
import domain.game_world.cell.Cell;
import domain.game_world.cell.Goal;
import domain.game_world.cell.Wall;
/**
 * Run Program
 */
class UseCase2 {
	
	// F5 = gameController.execute() (Block)
	// highlighted = gameController.nextToExecute() (Block/ null if not started)
	
	static ImplementationBlock fi = new ImplementationBlock();
	static GameController gameController;
	static GameWorld gameWorld;
	static Grid testGrid;
	static Block forwardBlock;
	static Block forwardBlock2;
	static Block wallInFrontBlock;
	static Block whileBlock;
	
	public static void setup() {
		gameController = fi.makeGameController();
		try {
			testGrid = new Grid(); // --> 5x5 grid
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
		gameWorld = fi.makeGameWorld(testGrid, new Vector(0,0));
		gameController.setGameWorld(gameWorld);
	}
	
	@Test
	void oneBlockRunProgram() {
		setup();
		gameWorld.getRobot().setDirection(Direction.DOWN);
		// place block in program area
		forwardBlock =fi.makeMoveForwardBlock();
		gameController.addTopLevelBlock(forwardBlock);
		// step 1
		gameController.execute();
		// step 2
		assertEquals(forwardBlock,gameController.getNextBlockToExecute());
		// step 3
		gameController.execute();
		// step 4
		assertEquals(new Vector(0,1),gameWorld.getRobot().getLocation());
	}
	
	@Test
	void oneConditionBlockRunProgram() {
		setup();
		gameWorld.getRobot().setDirection(Direction.DOWN);
		// place block in program area
		wallInFrontBlock =fi.makeWallInFrontBlock();
		gameController.addTopLevelBlock(wallInFrontBlock);
		// step 1b
		gameController.execute();
		assertEquals(null,gameController.getNextBlockToExecute());
	}
	
	@Test
	void surroundingBlockNoConditionFail() {
		setup();
		// place blocks in program area
		whileBlock =fi.makeMoveForwardBlock();
		gameController.addTopLevelBlock(whileBlock);
		// step 1b
		gameController.execute();
		assertEquals(null,gameController.getNextBlockToExecute());
	}
	
	@Test
	void moreBlocksProgramFail() {
		setup();
		gameWorld.getRobot().setDirection(Direction.DOWN);
		// place blocks in program area
		forwardBlock =fi.makeMoveForwardBlock();
		gameController.addTopLevelBlock(forwardBlock);
		forwardBlock2 = fi.makeMoveForwardBlock();
		gameController.addTopLevelBlock(forwardBlock2);
		// step 1a
		gameController.execute();
		assertEquals(null,gameController.getNextBlockToExecute());
		gameController.execute();
		assertEquals(new Vector(0,0),gameWorld.getRobot().getLocation());
	}
	
	@Test
	void connectedBlocksRunProgram() {
		setup();
		gameWorld.getRobot().setDirection(Direction.DOWN);
		// place blocks in program area
		forwardBlock =fi.makeMoveForwardBlock();
		gameController.addTopLevelBlock(forwardBlock);
		forwardBlock2 = fi.makeMoveForwardBlock();
		fi.connect(forwardBlock, forwardBlock2);
		// step 1
		gameController.execute();
		// step 2
		assertEquals(forwardBlock,gameController.getNextBlockToExecute());
		// step 3
		gameController.execute();
		// step 4
		assertEquals(new Vector(0,1),gameWorld.getRobot().getLocation());
		// step 2
		assertEquals(forwardBlock2,gameController.getNextBlockToExecute());
		// step 3
		gameController.execute();
		// step 4
		assertEquals(new Vector(0,2),gameWorld.getRobot().getLocation());
	}
	
}
