package testsuites;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.GameController;
import domain.block.block_types.Block;
import domain.game_world.Direction;
import domain.game_world.GameWorld;
import domain.game_world.Grid;
import domain.game_world.Vector;
import domain.game_world.cell.Cell;
import domain.game_world.cell.Goal;
import domain.game_world.cell.Wall;
import facade.Implementation;
/**
 * Reset Game World
 */
class UseCase3 {
	// Escape = gameController.resetWorld();
	
	static Implementation fi = new Implementation();
	static GameController gameController;
	static GameWorld gameWorld;
	static Grid testGrid;
	static Block forwardBlock;
	static Block forwardBlock2;
	
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
	void test() {
		setup();
		gameWorld.getRobot().setDirection(Direction.DOWN);
		// place blocks in program area
		forwardBlock =fi.makeMoveForwardBlock();
		gameController.addTopLevelBlock(forwardBlock);
		forwardBlock2 = fi.makeMoveForwardBlock();
		fi.connect(forwardBlock, forwardBlock2);
		gameController.execute();
		gameController.execute();
		assertEquals(new Vector(0,1),gameWorld.getRobot().getLocation());
		//step 1/2
		gameController.resetWorld();
		//step 3
		assertEquals(new Vector(0,0),gameWorld.getRobot().getLocation());
	}

}
