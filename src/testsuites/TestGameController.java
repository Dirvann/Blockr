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
	GameController gc;
	static ImplementationGameController IGC= new ImplementationGameController();
	static ImplementationBlock IB = new ImplementationBlock();
	static ImplementationGameWorld IGW = new ImplementationGameWorld();
	static Cell[] cells = {new Goal(),new Wall(), new Wall()};
	static Vector[] locations = {new Vector(1,1), new Vector(1,0), new Vector(2,2)};

	public static void setup() {
		try {
			GameController gc = IGC.makeGameController(IGW.makeGameWorld(IGW.makeGrid(3, 3, locations, cells), IGW.makeRobot(new Vector(0,0), Direction.DOWN)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//GC2 = new GameController(3, 3, locations, cells, new Vector(0,0), Direction.LEFT);
		//GC3 = new GameController(3, 3, locations, cells, new Vector(0,0), Direction.RIGHT);

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
			assertEquals(Direction.DOWN,IGW.getRobotDirection(IGC.getGameWorld(gc)));
			Block left = IB.makeTurnLeftBlock(); // create block
			IGC.addTopLevelBlock(gc,left); //drag in programArea
			IGC.execute(gc); // Run key
			IGC.execute(gc); // Run key
			assertEquals(Direction.RIGHT,IGW.getRobotDirection(IGC.getGameWorld(gc)));
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(Direction.UP,IGW.getRobotDirection(IGC.getGameWorld(gc)));
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(Direction.LEFT,IGW.getRobotDirection(IGC.getGameWorld(gc)));
			IGC.removeTopLevelBlock(gc,left);
			Block right = IB.makeTurnRightBlock();
			IGC.addTopLevelBlock(gc,right);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(Direction.UP,IGW.getRobotDirection(IGC.getGameWorld(gc)));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void forwardBlockExecute() {
		setup();
		try {
			// normal move
			assertEquals(new Vector(0,0),GC.getLocationRobot());
			assertEquals(Direction.DOWN,GC.getDirectionRobot());
			Block forward = IB.makeMoveForwardBlock();
			GC.addTopLevelBlock(forward);
			GC.execute();
			GC.execute();
			assertEquals(new Vector(0,1),GC.getLocationRobot());
			// end of grid
			GC2.addTopLevelBlock(forward);
			GC2.execute();
			GC2.execute();
			assertEquals(new Vector(0,0),GC2.getLocationRobot());
			// against wall
			GC3.addTopLevelBlock(forward);
			GC3.execute();
			GC3.execute();
			assertEquals(new Vector(0,0),GC3.getLocationRobot());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void connectActionBlocks() {
		setup();
		try {
			// 2 blocks execute TR/FW
			Block forward = IB.makeMoveForwardBlock();
			Block right = IB.makeTurnRightBlock();
			GC3.addTopLevelBlock(right);
			IB.connect(right,forward);
			GC3.execute();
			GC3.execute();
			GC3.execute();
			assertEquals(new Vector(0,1),GC3.getLocationRobot());
			assertEquals(Direction.DOWN,GC3.getDirectionRobot());
			// add a block to those 2 blocks TR/FW/TL
			GC3.resetWorld();
			assertEquals(new Vector(0,0),GC3.getLocationRobot());
			assertEquals(Direction.RIGHT,GC3.getDirectionRobot());
			Block left = IB.makeTurnLeftBlock();
			IB.connect(forward, left);
			GC3.execute();
			GC3.execute();
			GC3.execute();
			GC3.execute();
			assertEquals(Direction.RIGHT,GC3.getDirectionRobot());
			assertEquals(new Vector(0,1),GC3.getLocationRobot());
			// add a block in between TR/FW/FW2/TL
			GC3.resetWorld();
			Block forward2 = IB.makeMoveForwardBlock();
			IB.connect(forward,forward2);
			GC3.execute();
			GC3.execute();
			GC3.execute();
			GC3.execute();
			GC3.execute();
			assertEquals(new Vector(0,2),GC3.getLocationRobot());
			assertEquals(Direction.RIGHT,GC3.getDirectionRobot());
			// disconnect and reconnect TR/FW/FW2/TL -/-> FW2/TL -> TR//FW -> TR/FW2/TL/FW
			GC3.resetWorld();
			IB.disconnect(forward2);
			//TR/FW
			GC3.execute();
			GC3.execute();
			GC3.execute();
			assertEquals(new Vector(0,1),GC3.getLocationRobot()); 
			assertEquals(Direction.DOWN,GC3.getDirectionRobot());
			//FW2/TL
			GC3.addTopLevelBlock(forward2);
			GC3.removeTopLevelBlock(right);
			GC3.execute();
			GC3.execute();
			GC3.execute();
			assertEquals(new Vector(0,2),GC3.getLocationRobot()); 
			assertEquals(Direction.RIGHT,GC3.getDirectionRobot());
			//TR/FW2/TL/FW
			GC3.resetWorld();
			GC3.removeTopLevelBlock(forward2);
			GC3.addTopLevelBlock(right);
			IB.connect(right,forward2);
			GC3.execute();
			GC3.execute();
			GC3.execute();
			GC3.execute();
			GC3.execute();
			assertEquals(new Vector(1,1),GC3.getLocationRobot()); 
			assertEquals(Direction.RIGHT,GC3.getDirectionRobot());
			
		} catch (Exception e) {
			fail();
		}
	}
}
