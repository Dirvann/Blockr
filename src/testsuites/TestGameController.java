package testsuites;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
import domain.game_world.*;
import domain.game_world.cell.Cell;
import domain.game_world.cell.Goal;
import domain.game_world.cell.Wall;

class TestGameController {
	static GameController gc;
	static ImplementationGameController IGC= new ImplementationGameController();
	static ImplementationBlock IB = new ImplementationBlock();
	static ImplementationGameWorld IGW = new ImplementationGameWorld();
	
	static Cell[] cells = {new Goal(),new Wall(), new Wall()};
	static Vector[] locations = {new Vector(1,1), new Vector(1,0), new Vector(2,2)};
	static Block left;
	static Block right;
	static Block forward;
	static Block forward2;

	private static void setup() {
		try {
			left = IB.makeTurnLeftBlock(); // create blocks
			right = IB.makeTurnRightBlock();
			forward = IB.makeMoveForwardBlock();
			forward2 = IB.makeMoveForwardBlock();
			gc = IGC.makeGameController(IGW.makeGameWorld(IGW.makeGrid(3, 3, locations, cells), IGW.makeRobot(new Vector(0,0), Direction.DOWN)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// formatting functions
	private Direction robotDirection(GameController gc){
		return IGW.getRobotDirection(IGC.getGameWorld(gc));
	}
	private Vector robotLocation(GameController gc) {
		return IGW.getRobotLocation(IGC.getGameWorld(gc));
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
			assertEquals(Direction.DOWN,robotDirection(gc));
			IGC.addTopLevelBlock(gc,left); //drag in programArea
			IGC.execute(gc); // Run key
			IGC.execute(gc); // Run key
			assertEquals(Direction.RIGHT,robotDirection(gc));
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(Direction.UP,robotDirection(gc));
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(Direction.LEFT,robotDirection(gc));
			IGC.removeTopLevelBlock(gc,left);
			IGC.addTopLevelBlock(gc,right);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(Direction.UP,robotDirection(gc));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void forwardBlockExecute() {
		setup();
		try {
			// normal move - robot moves one down
			assertEquals(new Vector(0,0),robotLocation(gc));
			assertEquals(Direction.DOWN,robotDirection(gc));
			IGC.addTopLevelBlock(gc,forward);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,1),robotLocation(gc));
			// end of grid - robot moves one left
			IGW.resetGameWorld(IGC.getGameWorld(gc));
			IGC.setGameWorld(gc,IGW.makeGameWorld(IGW.makeGrid(3, 3, locations, cells), IGW.makeRobot(new Vector(0,0), Direction.DOWN)));
			IGC.addTopLevelBlock(gc,forward);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,0),robotLocation(gc));
			// against wall - robot moves one right
			IGW.resetGameWorld(IGC.getGameWorld(gc));
			IGC.setGameWorld(gc,IGW.makeGameWorld(IGW.makeGrid(3, 3, locations, cells), IGW.makeRobot(new Vector(0,0), Direction.RIGHT)));
			IGC.addTopLevelBlock(gc, forward);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,0),robotLocation(gc));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void connectActionBlocks() {
		setup();
		try {
			IGC.setGameWorld(gc,IGW.makeGameWorld(IGW.makeGrid(3, 3, locations, cells), IGW.makeRobot(new Vector(0,0), Direction.RIGHT)));
			// 2 blocks execute TR/FW
			IGC.addTopLevelBlock(gc,right);
			IB.connect(right,forward);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,1),robotLocation(gc));
			assertEquals(Direction.DOWN,robotDirection(gc));
			// add a block to those 2 blocks TR/FW/TL
			IGW.resetGameWorld(IGC.getGameWorld(gc));
			assertEquals(new Vector(0,0),robotLocation(gc));
			assertEquals(Direction.RIGHT,robotDirection(gc));
			IB.connect(forward, left);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,1),robotLocation(gc));
			assertEquals(Direction.RIGHT,robotDirection(gc));
			// add a block in between TR/FW/FW2/TL
			IGW.resetGameWorld(IGC.getGameWorld(gc));
			IB.connect(forward,forward2);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,2),robotLocation(gc));
			assertEquals(Direction.RIGHT,robotDirection(gc));
			// disconnect and reconnect TR/FW/FW2/TL -/-> FW2/TL -> TR//FW -> TR/FW2/TL/FW
			IGW.resetGameWorld(IGC.getGameWorld(gc));
			IB.disconnect(forward2);
			//TR/FW
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,1),robotLocation(gc)); 
			assertEquals(Direction.DOWN,robotDirection(gc));
			//FW2/TL
			IGC.addTopLevelBlock(gc,forward2);
			IGC.removeTopLevelBlock(gc,right);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,2),robotLocation(gc)); 
			assertEquals(Direction.RIGHT,robotDirection(gc));
			//TR/FW2/TL/FW
			IGW.resetGameWorld(IGC.getGameWorld(gc));
			IGC.removeTopLevelBlock(gc,forward2);
			IGC.addTopLevelBlock(gc,right);
			IB.connect(right,forward2);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(1,1),robotLocation(gc)); 
			assertEquals(Direction.RIGHT,robotDirection(gc));
			
		} catch (Exception e) {
			fail();
		}
	}
}
