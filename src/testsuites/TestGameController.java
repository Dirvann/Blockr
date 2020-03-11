package testsuites;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.GameController;
import domain.block.block_types.Block;
import domain.game_world.*;
import domain.game_world.cell.Cell;
import domain.game_world.cell.Goal;
import domain.game_world.cell.Wall;
import facade.*;

class TestGameController {
	static Implementation fi = new Implementation();
	
	static GameController gameController;
	static GameWorld gameWorld;
	static Cell[] cells = {new Goal(),new Wall(), new Wall()};
	static Vector[] locations = {new Vector(1,1), new Vector(1,0), new Vector(2,2) };
	static Block ifBlock;
	static Block forwardBlock;
	static Block forwardBlock2;
	static Block notBlock;
	static Block turnLeftBlock;
	static Block turnRightBlock;
	static Block wallInFrontBlock;
	static Block whileBlock;
	
	public static void setup() {
		gameController = fi.makeGameController();
		gameWorld = fi.makeGameWorld();// input: (3,3,locations,cells)
		ifBlock = fi.makeIfBlock();
		forwardBlock = fi.makeMoveForwardBlock();
		forwardBlock2 = fi.makeMoveForwardBlock();
		notBlock = fi.makeNotBlock();
		turnLeftBlock = fi.makeTurnLeftBlock();
		turnRightBlock = fi.makeTurnRightBlock();
		wallInFrontBlock = fi.makeWallInFrontBlock();
		whileBlock = fi.makeWhileBlock();
	}
	
	/**
	 * [[Robot , Wall , ____ ],
	 *  [ ____ , Goal , ____ ],
	 *  [ ____ , ____ , Wall ]]
	 */
	
	// ACTION BLOCKS
	
	@Test
	void turnLeftRightBlockExecute() {
		try {
			assertEquals(gameWorld.getRobot().getDirection(),Direction.UP);
			turnLeftBlock.execute(gameController);
			assertEquals(gameWorld.getRobot().getDirection(),Direction.LEFT);
			turnLeftBlock.execute(gameController);
			assertEquals(gameWorld.getRobot().getDirection(),Direction.DOWN);
			turnLeftBlock.execute(gameController);
			assertEquals(gameWorld.getRobot().getDirection(),Direction.RIGHT);
			turnRightBlock.execute(gameController);
			assertEquals(gameWorld.getRobot().getDirection(),Direction.DOWN);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void forwardBlockExecute() {
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
		try {
			// 2 blocks execute TR/FW
			gameWorld.getRobot().setDirection(Direction.RIGHT);
			fi.connect(turnRightBlock, forwardBlock);
			turnRightBlock.execute(gameController);
			assertEquals(gameWorld.getRobot().getLocation(),new Vector(0,1));
			// add a block to those 2 blocks TR/FW/TL
			gameWorld.getRobot().setDirection(Direction.RIGHT);
			gameWorld.getRobot().setLocation(new Vector(0,0));
			fi.connect(forwardBlock,turnLeftBlock);
			turnRightBlock.execute(gameController);
			assertEquals(gameWorld.getRobot().getDirection(),Direction.RIGHT);
			// add a block in between TR/FW/FW2/TL
			gameWorld.getRobot().setDirection(Direction.RIGHT);
			gameWorld.getRobot().setLocation(new Vector(0,0));
			fi.connect(forwardBlock,forwardBlock2);
			turnRightBlock.execute(gameController);
			assertEquals(gameWorld.getRobot().getLocation(),new Vector(0,2));
			assertEquals(gameWorld.getRobot().getDirection(),Direction.RIGHT);
			// disconnect and reconnect TR/FW/FW2/TL -/-> FW2/TL -> TR//FW -> TR/FW2/TL/FW
			gameWorld.getRobot().setDirection(Direction.RIGHT);
			gameWorld.getRobot().setLocation(new Vector(0,0));
			fi.disconnect(forwardBlock2);
			//TR/FW
			turnRightBlock.execute(gameController);
			assertEquals(gameWorld.getRobot().getLocation(),new Vector(0,1)); 
			//FW2/TL
			gameWorld.getRobot().setDirection(Direction.DOWN);
			gameWorld.getRobot().setLocation(new Vector(0,0));
			forwardBlock2.execute(gameController);
			assertEquals(gameWorld.getRobot().getLocation(),new Vector(0,1)); 
			assertEquals(gameWorld.getRobot().getDirection(),Direction.RIGHT);
			//TR/FW2/TL/FW
			gameWorld.getRobot().setDirection(Direction.RIGHT);
			gameWorld.getRobot().setLocation(new Vector(0,0));
			fi.connect(turnRightBlock,forwardBlock2);
			turnRightBlock.execute(gameController);
			assertEquals(gameWorld.getRobot().getLocation(),new Vector(1,1)); 
			assertEquals(gameWorld.getRobot().getDirection(),Direction.RIGHT);
		} catch (Exception e) {
			fail();
		}
	}
}
