package testsuites;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import domain.game_world.*;
import domain.game_world.cell.*;
import game_world.Vector;

public class TestGameWorld {
	static Grid testGrid;
	static Cell[] cells = {new Goal(),new Wall(), new Wall()};
	static Vector[] locations = {new Vector(1,1), new Vector(1,0), new Vector(2,2) };
	static GameWorld gameWorld;
	static Robot robot;
	static ImplementationGameWorld GW = new ImplementationGameWorld();
	/**
	 * [[Robot , Wall , ____ ],
	 *  [ ____ , Goal , ____ ],
	 *  [ ____ , ____ , Wall ]]
	 */
	public static void makeGameWorld() {
		try {
			testGrid = GW.makeGrid(3, 3, locations, cells);
			gameWorld = GW.makeGameWorld(testGrid, new Vector(0,0));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	@Test
	public void rotateRobot() {
		makeGameWorld();
		assertEquals(Direction.UP,GW.getRobotDirection(gameWorld));
		GW.robotTurnRight(gameWorld);
		assertEquals(Direction.RIGHT,GW.getRobotDirection(gameWorld));
		GW.robotTurnRight(gameWorld);
		assertEquals(Direction.DOWN,GW.getRobotDirection(gameWorld));
		GW.robotTurnRight(gameWorld);;
		assertEquals(Direction.LEFT,GW.getRobotDirection(gameWorld));
		GW.robotTurnLeft(gameWorld);
		assertEquals(Direction.DOWN,GW.getRobotDirection(gameWorld));
	}
	
	@Test
	public void robotInFrontOfWall() {
		makeGameWorld();
		assertFalse(GW.robotWallInFront(gameWorld));
		GW.robotTurnRight(gameWorld);
		assertTrue(GW.robotWallInFront(gameWorld));
	}
	
	@Test
	public void robotOnGoal() {
		makeGameWorld();
		assertFalse(GW.robotOnGoal(gameWorld));
		gameWorld = GW.makeGameWorld(testGrid, new Vector(1,1));
		assertTrue(GW.robotOnGoal(gameWorld));
	}
	
	@Test
	public void moveRobot() {
		makeGameWorld();
		try {
			GW.robotTurnRight(gameWorld);
			GW.robotTurnRight(gameWorld);
			GW.robotStepForwards(gameWorld);
			assertEquals(GW.getRobotLocation(gameWorld),new Vector(0,1));
			GW.robotStepForwards(gameWorld);
			assertEquals(GW.getRobotLocation(gameWorld),new Vector(0,2));
			GW.robotTurnLeft(gameWorld);
			GW.robotStepForwards(gameWorld);
			assertEquals(GW.getRobotLocation(gameWorld),new Vector(1,2));
			try {
				GW.robotStepForwards(gameWorld);
				fail();
			}catch (Exception e) {
				assertEquals(GW.getRobotLocation(gameWorld),new Vector(1,2));
			}
			GW.robotTurnLeft(gameWorld);
			GW.robotStepForwards(gameWorld);
			assertEquals(GW.getRobotLocation(gameWorld),new Vector(1,1));
			assertTrue(GW.robotOnGoal(gameWorld));
		}catch (Exception e){
			fail();
		}
	}
}
