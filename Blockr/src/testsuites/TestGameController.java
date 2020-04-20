package testsuites;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.Block;
import domain.block.ConditionBlock;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import domain.block.SurroundingBlock;
import exceptions.domainExceptions.CantRunConditionException;
import exceptions.domainExceptions.NotOneStartingBlockException;
import exceptions.domainExceptions.robotExceptions.RobotEnteringWallException;
import exceptions.domainExceptions.robotExceptions.RobotMovingOffGridException;
import game_world.Direction;
import game_world.ImplementationGameWorld;
import game_world.Vector;
import game_world.cell.Cell;
import game_world.cell.Goal;
import game_world.cell.Wall;

class TestGameController {
	static GameController gc;
	static ImplementationGameController IGC= new ImplementationGameController();
	static ImplementationBlock IB = new ImplementationBlock();
	static ImplementationGameWorld IGW = new ImplementationGameWorld();
	
	static Cell[] cells = {new Goal(),new Wall(), new Wall()};
	static Vector[] locations = {new Vector(1,1), new Vector(1,0), new Vector(2,2)};
	static Block left,right,forward,forward2,wallInFront,not,ifB,whileB;

	private static void setup() {
		try {
			left = IB.makeTurnLeftBlock(); // create blocks
			right = IB.makeTurnRightBlock();
			forward = IB.makeMoveForwardBlock();
			forward2 = IB.makeMoveForwardBlock();
			wallInFront = IB.makeWallInFrontBlock();
			not = IB.makeNotBlock();
			ifB = IB.makeIfBlock();
			whileB = IB.makeWhileBlock();
			gc = IGC.makeGameController(IGW.makeGameWorld(IGW.makeGrid(3, 3, locations, cells), IGW.makeRobot(new Vector(0,0), Direction.DOWN)));
		} catch (Exception e) {
			System.out.println("Testclass failed in setup phase.");
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
			assertEquals(Direction.RIGHT,robotDirection(gc));
			IGC.removeTopLevelBlock(gc,left);
			IGC.addTopLevelBlock(gc,right);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(Direction.LEFT,robotDirection(gc));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void forwardBlockExecute() {
		setup();
		try {
			// robot moves one down
			assertEquals(new Vector(0,0),robotLocation(gc));
			assertEquals(Direction.DOWN,robotDirection(gc));
			IGC.addTopLevelBlock(gc,forward);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,1),robotLocation(gc));
			// robot moves one down
			IGC.setGameWorld(gc,IGW.makeGameWorld(IGW.makeGrid(3, 3, locations, cells), IGW.makeRobot(new Vector(0,0), Direction.DOWN)));
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,1),robotLocation(gc));
			// robot moves one right
			IGC.setGameWorld(gc,IGW.makeGameWorld(IGW.makeGrid(3, 3, locations, cells), IGW.makeRobot(new Vector(1,1), Direction.RIGHT)));
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(2,1),robotLocation(gc));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}
	
	@Test
	void walkAgainstWallOrOffGrid() {
		setup();
		// walk against wall
		try {
		IGC.setGameWorld(gc,IGW.makeGameWorld(IGW.makeGrid(3, 3, locations, cells), IGW.makeRobot(new Vector(0,0), Direction.RIGHT)));
		IGC.addTopLevelBlock(gc,forward);
		IGC.execute(gc);
		IGC.execute(gc);
		fail();
		} catch(Exception e) {
			assertTrue(e instanceof RobotEnteringWallException);
		}
		// walk off grid
		try {
		IGC.setGameWorld(gc,IGW.makeGameWorld(IGW.makeGrid(3, 3, locations, cells), IGW.makeRobot(new Vector(0,0), Direction.UP)));
		IGC.execute(gc);
		IGC.execute(gc);
		fail();
		} catch(Exception e) {
			assertTrue(e instanceof RobotMovingOffGridException);
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
			IGC.setGameWorld(gc,IGW.makeGameWorld(IGW.makeGrid(3, 3, locations, cells), IGW.makeRobot(new Vector(0,0), Direction.DOWN)));
			IGC.addTopLevelBlock(gc,forward2);
			IGC.removeTopLevelBlock(gc,right);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,1),robotLocation(gc)); 
			assertEquals(Direction.RIGHT,robotDirection(gc));
			//TR/FW2/TL/FW
			IGC.setGameWorld(gc,IGW.makeGameWorld(IGW.makeGrid(3, 3, locations, cells), IGW.makeRobot(new Vector(0,0), Direction.RIGHT)));
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
	
	@Test
	void twoBlocksOnTopLevel() {
		setup();
		try {
			IGC.addTopLevelBlock(gc,right);
			IGC.addTopLevelBlock(gc,left);
			IGC.execute(gc);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof NotOneStartingBlockException);
			assertEquals(Direction.DOWN,robotDirection(gc));
		}
	}
	
	// CONDITION BLOCKS
	
	@Test
	void topLevelBlockIsCondition() {
		setup();
		IGC.addTopLevelBlock(gc,wallInFront);
		try {
		IGC.execute(gc);
		fail();
		} catch (Exception e) {
			assertTrue(e instanceof CantRunConditionException);
		}
	}
	
	// SINGLE SURROUNDING BLOCKS
	
	@Test
	void withoutCondition() {
		setup();
		try {
			// IF () {}
			IGC.addTopLevelBlock(gc,ifB);
			try {
				IGC.execute(gc);
				fail();
			} catch(Exception e) {;}
			
			// IF () {forward}
			IB.addBodyBlock((SurroundingBlock) ifB, (SequenceBlock) forward);
			try {
				IGC.execute(gc);
				fail();
			} catch(Exception e) {;}
			
			// IF (not) {forward}
			IB.setConditionBlock((SurroundingBlock) ifB,(ConditionBlock) not);
			try {
				IGC.execute(gc);
				fail();
			} catch(Exception e) {;}
			IGC.removeTopLevelBlock(gc, ifB);
			
			// WHILE () {}
			IGW.resetGameWorld(IGC.getGameWorld(gc));
			IGC.addTopLevelBlock(gc, whileB);
			try {
				IGC.execute(gc);
				fail();
			} catch(Exception e) {;}
			
			// WHILE () {forward}
			IB.addBodyBlock((SurroundingBlock) whileB,(SequenceBlock) forward);
			try {
				IGC.execute(gc);
				fail();
			} catch(Exception e) {;}
			
			// WHILE (not) {forward}
			IB.setConditionBlock((SurroundingBlock) whileB,(ConditionBlock) not);
			try {
				IGC.execute(gc);
				fail();
			} catch(Exception e) {;}
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void addWrongType() {
		setup();
		try {
			IGC.addTopLevelBlock(gc,ifB); 
			IB.addBodyBlock((SurroundingBlock) ifB,(SequenceBlock) forward2);
			try {
				IB.addBodyBlock((SurroundingBlock) ifB,(SequenceBlock) wallInFront);
				fail();
			} catch(Exception e) {;}
			Boolean connected = IB.connect(ifB,not);
			assertFalse(connected);
			assertEquals(null,IB.getNextBlock(ifB));
			connected = IB.connect(not,forward);
			assertFalse(connected);
			assertEquals(null,IB.getNextBlock(not));
			try {
				IB.setConditionBlock((SurroundingBlock) ifB,(ConditionBlock) left);
				fail();
			} catch(Exception e) {;}			
			IB.connect(not, wallInFront);
			IB.setConditionBlock((SurroundingBlock) ifB,(ConditionBlock) not);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,1),robotLocation(gc));
		} catch (Exception e) {
			fail();
		}
	}
	
	// IF BLOCK
	
	@Test
	void ifBlock() {
		setup();
		try {
			// IF ( WallInFront) { F } 
			IGC.addTopLevelBlock(gc,ifB); 
			IB.addBodyBlock((SurroundingBlock) ifB,(SequenceBlock) forward);
			IB.setConditionBlock((SurroundingBlock) ifB, (ConditionBlock) wallInFront);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,0),robotLocation(gc));
			// run twice
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,0),robotLocation(gc));
			// IF ( Not / WallInFront) { F }
			IGW.resetGameWorld(IGC.getGameWorld(gc));
			IB.disconnect(wallInFront);
			IB.connect(not,wallInFront);
			IB.setConditionBlock((SurroundingBlock) ifB,(ConditionBlock) not);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,1),robotLocation(gc));
			//  F2 / IF ( Not / WallInFront ) { Left } / F
			IGW.resetGameWorld(IGC.getGameWorld(gc));
			IGC.addTopLevelBlock(gc, forward2);
			IGC.removeTopLevelBlock(gc,ifB);
			IB.connect(forward2, ifB);
			IB.disconnect(forward);
			IB.connect(ifB, forward);
			IB.addBodyBlock((SurroundingBlock) ifB, (SequenceBlock) left);
			IGC.execute(gc); // run
			IGC.execute(gc); // forward (0,1)
			assertEquals(new Vector(0,1),robotLocation(gc));
			IGC.execute(gc); // if
			IGC.execute(gc); // left
			IGC.execute(gc); // forward (1,1)
			assertEquals(new Vector(1,1),robotLocation(gc));
			assertEquals(Direction.RIGHT,robotDirection(gc));

		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	void whileBlock() {
		setup();
		try {
			// WHILE ( WallInFront) { F } 
			IGC.addTopLevelBlock(gc,whileB); 
			IB.addBodyBlock((SurroundingBlock) whileB,(SequenceBlock) forward);
			IB.setConditionBlock((SurroundingBlock) whileB, (ConditionBlock) wallInFront);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,0),robotLocation(gc));
			// run twice
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,0),robotLocation(gc));
			
			// WHILE ( Not / WallInFront) { F }
			IGW.resetGameWorld(IGC.getGameWorld(gc));
			IB.disconnect(wallInFront);
			IB.connect(not,wallInFront);
			IB.setConditionBlock((SurroundingBlock) whileB,(ConditionBlock) not);
			IGC.execute(gc);
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,1),robotLocation(gc));
			IGC.execute(gc);
			IGC.execute(gc);
			assertEquals(new Vector(0,2),robotLocation(gc));
			try {
				IGC.execute(gc);
				IGC.execute(gc);
				fail();
			}catch(Exception e) {
				// catches exception when going against wall
				assertTrue(e instanceof RobotMovingOffGridException);
			}
			
			//  F2 / WHILE ( Not / WallInFront ) { Right } / Left
			IGC.setGameWorld(gc,IGW.makeGameWorld(IGW.makeGrid(3, 3, locations, cells), IGW.makeRobot(new Vector(0,1), Direction.RIGHT)));
			IGC.addTopLevelBlock(gc, forward2);
			IGC.removeTopLevelBlock(gc,whileB);
			IB.connect(forward2, whileB);
			IB.disconnect(forward);
			IB.connect(whileB, left);
			IB.addBodyBlock((SurroundingBlock) whileB, (SequenceBlock) right);
			// this needs to happen, because in the program, you have to grab something, this performs the stopExecution function
			IGC.stopExecution(gc);
			IGC.execute(gc); // run
			IGC.execute(gc); // forward (1,1)
			IGC.execute(gc); // while >
			IGC.execute(gc); // Right 
			IGC.execute(gc); // while v
			IGC.execute(gc); // Right 
			IGC.execute(gc); // while <
			IGC.execute(gc); // Right 
			IGC.execute(gc); // while ^ (sees wall)
			IGC.execute(gc); // left
			assertEquals(new Vector(1,1),robotLocation(gc));
			assertEquals(Direction.LEFT,robotDirection(gc));
			
		} catch (Exception e) {
			fail();
		}
	}
}
