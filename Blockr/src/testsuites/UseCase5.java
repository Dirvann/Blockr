package testsuites;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.util.List;

import org.junit.jupiter.api.Test;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
import domain.block.SurroundingBlock;
import game_world.ImplementationGameWorld;
import game_world.Vector;
import presentation.BlockAreaCanvas;
import presentation.BlockrPanel;
import presentation.block.ImplementationPresentationBlock;
/**
 * ## Use Case 5: Undo Redo
 * 
 * ### Main Succes Scenario
 * 
 * 1. The user presses CTRL+Z on the keyboard.
 * 
 * 2. The last action is reverted.
 * 
 * repeat steps 1 and 2 until there are no more actions to revert.
 * 
 * 3. The user presses CTRL+SHIFT+Z on the keyboard.
 * 
 * 4. The last reverted action is executed again.
 * 
 * repeat steps 3 and 4 until there are no more actions reverted actions.
 * 
 * ### Extensions
 * 
 * 1a. No actions are made.
 *     1. Nothing happens.
 *  
 * 1b. Program is running.
 *     2. Only execute actions (F5) can be reverted or executed again.
 *  
 * 1c. Program was running before the program got modified 
 *     2. Only block actions (mouse actions) can be reverted or executed again.
 *  
 * 3a. No actions are reverted.
 *     3. Nothing happens.
 */
class UseCase5 {
	
	private static BlockrPanel blockrPanel;
	private ImplementationGameController GC;
	private GameController gc;
	static ImplementationBlock IB = new ImplementationBlock();
	static ImplementationPresentationBlock IPB = new ImplementationPresentationBlock();
	static ImplementationGameWorld IGW = new ImplementationGameWorld();
	BlockAreaCanvas blockAreaCanvas;
	
	private void setup() {
		blockrPanel = new BlockrPanel();
		GC = new ImplementationGameController();
		gc = blockrPanel.getGameController();
		blockAreaCanvas = blockrPanel.getBlockAreaCanvas();	
	}

	@Test
	void undoRedoAddRemoveBlock() {
		setup();
		//add block
		blockAreaCanvas.handleMousePressed(11, 11);
		blockAreaCanvas.handleMouseDragged(30, 30);
		blockAreaCanvas.handleMouseDragged(80, 40);
		blockAreaCanvas.handleMouseDragged(500, 150);
		blockAreaCanvas.handleMouseReleased(500, 150);
		System.out.println(IPB.getPosition(IB.getPresentationBlock(GC.getCopyOfAllBlocks(gc).get(0))).getX() + " "+ IPB.getPosition(IB.getPresentationBlock(GC.getCopyOfAllBlocks(gc).get(0))).getY());
		List<Block> topLevelBlocks = GC.getCopyOfAllBlocks(gc);
		assertEquals(1,topLevelBlocks.size());
		assertEquals("MoveForward",IB.getName(topLevelBlocks.get(0)));
		assertEquals(null,GC.getNextBlockToExecute(gc));
		//remove block
		blockAreaCanvas.handleMousePressed(500, 150);
		blockAreaCanvas.handleMouseDragged(11, 11);
		blockAreaCanvas.handleMouseReleased(11, 11);
		topLevelBlocks = GC.getCopyOfAllBlocks(gc);
		assertEquals(0,topLevelBlocks.size());
		//undo 1x
		KeyEvent a = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK,KeyEvent.VK_CONTROL, KeyEvent.CHAR_UNDEFINED);
		KeyEvent b = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_Z, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(a);
		blockAreaCanvas.handleKeyPressed(b);
		topLevelBlocks = GC.getCopyOfAllBlocks(gc);
		assertEquals(1,topLevelBlocks.size());
		assertEquals("MoveForward",IB.getName(topLevelBlocks.get(0)));
		assertEquals(null,GC.getNextBlockToExecute(gc));
		assertEquals(new Vector(499,149),IPB.getPosition(IB.getPresentationBlock(topLevelBlocks.get(0))));
		//undo 2x
		KeyEvent c = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK,KeyEvent.VK_CONTROL, KeyEvent.CHAR_UNDEFINED);
		KeyEvent d = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_Z, KeyEvent.CHAR_UNDEFINED);
		topLevelBlocks = GC.getCopyOfAllBlocks(gc);
		assertEquals(0,topLevelBlocks.size());
	}
	
	@Test
	void undoRedoMoveBlocks() {
		setup();
	}
	
	@Test
	void undoRedoRunProgram() {
		setup();
	}
	
	@Test
	void undoRedoModifyAndRun() {
		setup();
	}
}
