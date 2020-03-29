package testsuites;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.GameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
import domain.game_world.*;
import domain.game_world.cell.Cell;
import domain.game_world.cell.Goal;
import domain.game_world.cell.Wall;

class TestGameController {
	static GameController GC;
	static ImplementationBlock IB = new ImplementationBlock();
	static Cell[] cells = {new Goal(),new Wall(), new Wall()};
	static Vector[] locations = {new Vector(1,1), new Vector(1,0), new Vector(2,2) };

	public static void setup() {
		GC = new GameController(3, 3, locations, cells, new Vector(0,0));
	}
	
	/**
	 * [[Robot , Wall , ____ ],
	 *  [ ____ , Goal , ____ ],
	 *  [ ____ , ____ , Wall ]]
	 */
	
	// ACTION BLOCKS
	
	@Test
	void turnLeftRightBlockExecute() {
		setup();
		try {
			assertEquals(GC.robotGetDirection(),Direction.UP);
			Block left = IB.makeTurnLeftBlock();
			GC.addTopLevelBlock(left);
			GC.execute();
			assertEquals(GC.robotGetDirection(),Direction.LEFT);
			GC.execute();
			assertEquals(GC.robotGetDirection(),Direction.DOWN);
			GC.execute();
			assertEquals(GC.robotGetDirection(),Direction.RIGHT);
			GC.removeTopLevelBlock(left);
			Block right = IB.makeTurnRightBlock();
			GC.addTopLevelBlock(right);
			GC.execute();
			assertEquals(GC.robotGetDirection(),Direction.DOWN);
		} catch (Exception e) {
			fail();
		}
	}
/*	
	@Test
	void forwardBlockExecute() {
		setup();
		try {
			// normal move
			assertEquals(gameWorld.getRobot().getLocation(),new Vector(0,0));
			gameWorld.getRobot().setDirection(Direction.DOWN);
			forwardBlock.execute(gameController);
			assertEquals(gameWorld.getRobot().getLocation(),new Vector(0,1));
			// end of grid
			gameWorld.getRobot().setDirection(Direction.LEFT);
			forwardBlock.execute(gameController);
			assertEquals(gameWorld.getRobot().getLocation(),new Vector(0,1));
			// against wall
			gameWorld.getRobot().setLocation(new Vector(0,0));
			gameWorld.getRobot().setDirection(Direction.RIGHT);
			forwardBlock.execute(gameController);
			assertEquals(gameWorld.getRobot().getLocation(),new Vector(0,0));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void connectActionBlocks() {
		setup();
		try {
			// 2 blocks execute TR/FW
			gameWorld.getRobot().setDirection(Direction.RIGHT);
			fi.connect(turnRightBlock, forwardBlock);
			turnRightBlock.execute(gameController).execute(gameController);
			assertEquals(gameWorld.getRobot().getLocation(),new Vector(0,1));
			// add a block to those 2 blocks TR/FW/TL
			gameWorld.getRobot().setDirection(Direction.RIGHT);
			gameWorld.getRobot().setLocation(new Vector(0,0));
			fi.connect(forwardBlock,turnLeftBlock);
			turnRightBlock.execute(gameController).execute(gameController).execute(gameController);
			assertEquals(gameWorld.getRobot().getDirection(),Direction.RIGHT);
			// add a block in between TR/FW/FW2/TL
			gameWorld.getRobot().setDirection(Direction.RIGHT);
			gameWorld.getRobot().setLocation(new Vector(0,0));
			fi.connect(forwardBlock,forwardBlock2);
			turnRightBlock.execute(gameController).execute(gameController).execute(gameController).execute(gameController);
			assertEquals(gameWorld.getRobot().getLocation(),new Vector(0,2));
			assertEquals(gameWorld.getRobot().getDirection(),Direction.RIGHT);
			// disconnect and reconnect TR/FW/FW2/TL -/-> FW2/TL -> TR//FW -> TR/FW2/TL/FW
			gameWorld.getRobot().setDirection(Direction.RIGHT);
			gameWorld.getRobot().setLocation(new Vector(0,0));
			fi.disconnect(forwardBlock2);
			//TR/FW
			turnRightBlock.execute(gameController).execute(gameController);
			assertEquals(gameWorld.getRobot().getLocation(),new Vector(0,1)); 
			//FW2/TL
			gameWorld.getRobot().setDirection(Direction.DOWN);
			gameWorld.getRobot().setLocation(new Vector(0,0));
			forwardBlock2.execute(gameController).execute(gameController);
			assertEquals(gameWorld.getRobot().getLocation(),new Vector(0,1)); 
			assertEquals(gameWorld.getRobot().getDirection(),Direction.RIGHT);
			//TR/FW2/TL/FW
			gameWorld.getRobot().setDirection(Direction.RIGHT);
			gameWorld.getRobot().setLocation(new Vector(0,0));
			fi.connect(turnRightBlock,forwardBlock2);
			turnRightBlock.execute(gameController).execute(gameController).execute(gameController).execute(gameController);
			assertEquals(gameWorld.getRobot().getLocation(),new Vector(1,1)); 
			assertEquals(gameWorld.getRobot().getDirection(),Direction.RIGHT);
		} catch (Exception e) {
			fail();
		}
	}
*/
}
