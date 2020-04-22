package testsuites;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.junit.jupiter.api.Test;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.ActionBlock;
import domain.block.Block;
import domain.block.ImplementationBlock;
import game_world.Direction;
import game_world.GameWorld;
import game_world.ImplementationGameWorld;
import presentation.BlockAreaCanvas;
import presentation.BlockrPanel;
/**
 * ## Use Case 2: Run Program
 * 
 * ### Main Success Scenario
 * 
 * 1. User presses F5
 * 
 * 2. Next block is higlighted
 * 
 * 3. User presses F5
 * 
 * 4. Highlighted block is executed
 * 
 * repeat steps 2, 3 and 4 until program is finished.
 * 
 * ### Extensions
 * 
 * 1a. Program Area does not contain exactly one connected block group.
 * 	   1. Execution does not start.
 * 
 * 1b. Program Area contains a condition block.
 *     1. Execution does not start.
 *  
 * 1b. Program Area contains a surrounding block without condition.
 *     1. Execution does not start
 */
class UseCase2 {
	
	private static BlockrPanel blockrPanel;
	private ImplementationGameController GC;
	private GameController gc;
	static ImplementationBlock IB = new ImplementationBlock();
	static ImplementationGameWorld IGW = new ImplementationGameWorld();
	BlockAreaCanvas blockAreaCanvas;
	
	private void setup() {
		blockrPanel = new BlockrPanel();
		GC = new ImplementationGameController();
		gc = blockrPanel.getGameController();
		blockAreaCanvas = blockrPanel.getBlockAreaCanvas();	
	}
	
	@Test
	void oneBlockRunProgram() {
		setup();
		blockAreaCanvas.handleMousePressed(11, 71);
		blockAreaCanvas.handleMouseReleased(500, 150);
		assertEquals(null,GC.getNextBlockToExecute(gc));
		IGW.robotTurnLeft(GC.getGameWorld(gc));
		Direction expectedDir = IGW.getRobotDirection(GC.getGameWorld(gc));
		IGW.robotTurnRight(GC.getGameWorld(gc));
		
		
		KeyEvent a = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		KeyEvent b = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(a);
		assertEquals("Turn Left",IB.getName(GC.getNextBlockToExecute(gc)));
		blockAreaCanvas.handleKeyPressed(b);
		assertEquals(expectedDir,IGW.getRobotDirection(GC.getGameWorld(gc)));
		assertEquals(null,GC.getNextBlockToExecute(gc));
	}
/*	
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
*/	
}
