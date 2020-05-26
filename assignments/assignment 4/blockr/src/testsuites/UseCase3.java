package testsuites;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.event.KeyEvent;
import org.junit.jupiter.api.Test;
import domain.GameController;
import domain.block.ImplementationBlock;
import impl.root.ImplementationGameWorld;
import presentation.BlockAreaCanvas;
import presentation.BlockrPanel;
/**
 * ## Use Case 3: Reset Game World
 * 
 * ### Main Success Scenario
 * 
 * 1. User presses Escape
 * 
 * 2. The program stops running
 * 
 * 3. The game world resets to the original state
 * 
 * ### Extensions
 * 
 * 1a. User modifies program while it's running
 *     1. The program stops running
 *     2. The game world resets to the original state
 */
class UseCase3 {
	
	private static BlockrPanel blockrPanel;
	private GameController GC;
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
		GC = blockrPanel.getGameController();
		blockAreaCanvas = blockrPanel.getBlockAreaCanvas();	
	}
	@Test
	void resetGameOneBlock() {
		setup();
		//start program: TurnLeft
		blockAreaCanvas.handleMousePressed(11, 71);
		blockAreaCanvas.handleMouseDragged(500, 150);
		blockAreaCanvas.handleMouseReleased(500, 150);
		assertEquals(null,GC.getNextBlockToExecute());
		KeyEvent a = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		KeyEvent b = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(a);
		assertEquals("Turn Left",IB.getName(GC.getNextBlockToExecute()));
		blockAreaCanvas.handleKeyPressed(b);
		assertEquals(null,GC.getNextBlockToExecute());
	}
	
	@Test
	void resetGameMoreBlocks() {
		setup();
		//start program: TurnLeft / Turn Right
		blockAreaCanvas.handleMousePressed(11, 71);
		blockAreaCanvas.handleMouseDragged(500, 70);
		blockAreaCanvas.handleMouseReleased(500, 70);
		blockAreaCanvas.handleMousePressed(11, 131);
		blockAreaCanvas.handleMouseDragged(500, 90);
		blockAreaCanvas.handleMouseReleased(500, 90);
		assertEquals(null,GC.getNextBlockToExecute());
		//stop after 1 execute
		KeyEvent a = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		KeyEvent b = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(a);
		assertEquals("Turn Left",IB.getName(GC.getNextBlockToExecute()));
		blockAreaCanvas.handleKeyPressed(b);
		assertEquals(null,GC.getNextBlockToExecute());
		//stop after 2 execute
		KeyEvent c = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		KeyEvent d = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		KeyEvent e = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(c);
		assertEquals("Turn Left",IB.getName(GC.getNextBlockToExecute()));
		blockAreaCanvas.handleKeyPressed(d);
		assertEquals("Turn Right",IB.getName(GC.getNextBlockToExecute()));
		blockAreaCanvas.handleKeyPressed(e);
		assertEquals(null,GC.getNextBlockToExecute());
	}
	
	@Test
	void modifiesProgramWhileRunning() {
		setup();
		//start program: TurnLeft
		blockAreaCanvas.handleMousePressed(11, 71);
		blockAreaCanvas.handleMouseDragged(500, 150);
		blockAreaCanvas.handleMouseReleased(500, 150);
		assertEquals(null,GC.getNextBlockToExecute());
		KeyEvent a = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(a);
		assertEquals("Turn Left",IB.getName(GC.getNextBlockToExecute()));
		//modify program: TurnLeft / TurnRight
		blockAreaCanvas.handleMousePressed(11, 131);
		blockAreaCanvas.handleMouseDragged(500, 170);
		blockAreaCanvas.handleMouseReleased(500, 1700);
		assertEquals(null,GC.getNextBlockToExecute());
		KeyEvent b = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		KeyEvent c = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(b);
		assertEquals("Turn Left",IB.getName(GC.getNextBlockToExecute()));
		blockAreaCanvas.handleKeyPressed(c);
		assertEquals("Turn Right",IB.getName(GC.getNextBlockToExecute()));
		blockAreaCanvas.handleMousePressed(11, 71);
		blockAreaCanvas.handleMouseDragged(500, 150);
		blockAreaCanvas.handleMouseReleased(500, 150);
		assertEquals(null,GC.getNextBlockToExecute());
	}
}
