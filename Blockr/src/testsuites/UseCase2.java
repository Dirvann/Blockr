package testsuites;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.event.KeyEvent;
import org.junit.jupiter.api.Test;
import domain.GameController;
import domain.ImplementationGameController;
import domain.block.ImplementationBlock;
import impl.root.ImplementationGameWorld;
import presentation.BlockAreaCanvas;
import presentation.BlockrPanel;
/**
 * ## Use Case 2: Run Program
 * 
 * ### Main Success Scenario
 * 
 * 1. User presses F5
 * 
 * 2. Next block is highlighted
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
		try {
			blockrPanel = new BlockrPanel(ImplementationGameWorld.class);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		//IGW.robotTurnLeft(GC.getGameWorld(gc));
		//Direction expectedDir = IGW.getRobotDirection(GC.getGameWorld(gc));
		//IGW.robotTurnRight(GC.getGameWorld(gc));
		KeyEvent a = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		KeyEvent b = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(a);
		assertEquals("TurnLeft",IB.getName(GC.getNextBlockToExecute(gc)));
		blockAreaCanvas.handleKeyPressed(b);
		//assertEquals(expectedDir,IGW.getRobotDirection(GC.getGameWorld(gc)));
		assertEquals(null,GC.getNextBlockToExecute(gc));
	}
	
	@Test
	void connectedBlocksRunProgram() {
		setup();
		//Move Forward / Turn Left / Turn Right
		blockAreaCanvas.handleMousePressed(11, 11);
		blockAreaCanvas.handleMouseDragged(500, 50);
		blockAreaCanvas.handleMouseReleased(500, 50);
		blockAreaCanvas.handleMousePressed(11, 71);
		blockAreaCanvas.handleMouseDragged(500, 70);
		blockAreaCanvas.handleMouseReleased(500, 70);
		blockAreaCanvas.handleMousePressed(11, 131);
		blockAreaCanvas.handleMouseDragged(500, 90);
		blockAreaCanvas.handleMouseReleased(500, 90);
		//key events
		KeyEvent a = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		KeyEvent b = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		KeyEvent c = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		KeyEvent d = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		//check if running works
		blockAreaCanvas.handleKeyPressed(a);
		assertEquals("MoveForward",IB.getName(GC.getNextBlockToExecute(gc)));
		blockAreaCanvas.handleKeyPressed(b);
		assertEquals("TurnLeft",IB.getName(GC.getNextBlockToExecute(gc)));
		blockAreaCanvas.handleKeyPressed(c);
		assertEquals("TurnRight",IB.getName(GC.getNextBlockToExecute(gc)));
		blockAreaCanvas.handleKeyPressed(d);
		assertEquals(null,GC.getNextBlockToExecute(gc));
	}
	
	@Test
	void oneConditionBlockRunProgram() {
		setup();
		blockAreaCanvas.handleMousePressed(11, 191);
		blockAreaCanvas.handleMouseDragged(500, 50);
		blockAreaCanvas.handleMouseReleased(500, 50);
		KeyEvent a = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(a);
		assertEquals(null,GC.getNextBlockToExecute(gc));
	}
	
	@Test
	void surroundingBlockNoConditionFail() {
		setup();
		blockAreaCanvas.handleMousePressed(11, 311);
		blockAreaCanvas.handleMouseDragged(500, 50);
		blockAreaCanvas.handleMouseReleased(500, 50);
		KeyEvent a = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(a);
		assertEquals(null,GC.getNextBlockToExecute(gc));		
	}
	
	@Test
	void moreBlocksProgramFail() {
		setup();
		blockAreaCanvas.handleMousePressed(11, 11);
		blockAreaCanvas.handleMouseDragged(500, 50);
		blockAreaCanvas.handleMouseReleased(500, 50);
		blockAreaCanvas.handleMousePressed(11, 11);
		blockAreaCanvas.handleMouseDragged(500, 30);
		blockAreaCanvas.handleMouseReleased(500, 30);
		KeyEvent a = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(a);
		assertEquals(null,GC.getNextBlockToExecute(gc));	
	}
}
