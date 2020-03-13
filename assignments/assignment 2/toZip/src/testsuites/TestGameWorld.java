package testsuites;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import domain.game_world.*;
import domain.game_world.cell.*;

public class TestGameWorld {
	static Grid testGrid;
	static Cell[] cells = {new Goal(),new Wall(), new Wall()};
	static Vector[] locations = {new Vector(1,1), new Vector(1,0), new Vector(2,2) };
	static GameWorld gameWorld;
	static Robot robot;
//robot draaien
//robot bewegen
//robot out of bounds
//robot op goal
//robot niet op goal
	/**
	 * [[Robot , Wall , ____ ],
	 *  [ ____ , Goal , ____ ],
	 *  [ ____ , ____ , Wall ]]
	 */
	public static void makeGameWorld() {
		try {
			testGrid = new Grid(3,3,locations,cells);
			gameWorld = new GameWorld(testGrid,new Vector(0,0));
			robot = gameWorld.getRobot();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}
	@Test
	public void gridFunctions() {
		Wall wall1 = new Wall();
		Vector v1 = new Vector(2,2);
		Wall wall2 = new Wall();
		Vector v2  = new Vector(2,3);
		EmptyCell empty1 = new EmptyCell();
		
		Grid grid1 = new Grid();
		try {
		//CREATE GRID
		grid1.setCells(new Vector[]{v1,v2},new Cell[] {wall1,wall2});
		assertEquals(grid1.getCell(v1),wall1);
		assertEquals(grid1.getCell(v2),wall2);
		//EDIT GRID
		grid1.setCell(v1, empty1);
		assertEquals(grid1.getCell(v1),empty1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		//OUT OF BOUND EXCEPTION
		try {
			grid1.setCell(new Vector(10,10),new Wall());
			fail();
		} catch (Exception e) {
			;
		}
		
	}
	@Test
	public void rotateRobot() {
		makeGameWorld();
		assertEquals(robot.getDirection(),Direction.UP);
		gameWorld.robotTurnRight();
		assertEquals(robot.getDirection(),Direction.RIGHT);
		gameWorld.robotTurnRight();
		assertEquals(robot.getDirection(),Direction.DOWN);
		
		try {
			assertEquals(robot.getPositionInFront(),new Vector(0,1));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		gameWorld.robotTurnRight();
		assertEquals(robot.getDirection(),Direction.LEFT);

	}
	
	@Test
	public void robotInFrontOfWall() {
		makeGameWorld();
		assertFalse(gameWorld.robotWallInFront());
		gameWorld.robotTurnRight();
		assertTrue(gameWorld.robotWallInFront());
	}
	
	@Test
	public void robotOnGoal() {
		makeGameWorld();
		assertFalse(gameWorld.robotOnGoal());
		robot.setLocation(new Vector(1,1));
		assertTrue(gameWorld.robotOnGoal());
	}
	
	@Test
	public void moveRobot() {
		makeGameWorld();
		gameWorld.robotTurnRight();
		gameWorld.robotTurnRight();
		gameWorld.robotStepForwards();
		assertEquals(robot.getLocation(),new Vector(0,1));
		gameWorld.robotStepForwards();
		assertEquals(robot.getLocation(),new Vector(0,2));
		gameWorld.robotTurnLeft();
		gameWorld.robotStepForwards();
		assertEquals(robot.getLocation(),new Vector(1,2));
		gameWorld.robotStepForwards();
		assertEquals(robot.getLocation(),new Vector(1,2));
		gameWorld.robotStepForwards();
		assertEquals(robot.getLocation(),new Vector(1,2));
		gameWorld.robotTurnLeft();
		gameWorld.robotStepForwards();
		assertEquals(robot.getLocation(),new Vector(1,1));
		assertTrue(gameWorld.robotOnGoal());
	}
}
