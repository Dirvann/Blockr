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
import impl.root.ImplementationGameWorld;
import domain.Vector;
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
		KeyEvent a = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK,KeyEvent.VK_Z, 'z');
		blockAreaCanvas.handleKeyPressed(a);
		topLevelBlocks = GC.getCopyOfAllBlocks(gc);
		assertEquals(1,topLevelBlocks.size());
		assertEquals("MoveForward",IB.getName(topLevelBlocks.get(0)));
		assertEquals(null,GC.getNextBlockToExecute(gc));
		assertEquals(new Vector(499,149),IPB.getPosition(IB.getPresentationBlock(topLevelBlocks.get(0))));
		//undo 2x
		KeyEvent b = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK,KeyEvent.VK_Z,'z');
		blockAreaCanvas.handleKeyPressed(b);
		topLevelBlocks = GC.getCopyOfAllBlocks(gc);
		assertEquals(0,topLevelBlocks.size());
		//redo 1x
		KeyEvent c = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK+KeyEvent.SHIFT_DOWN_MASK,KeyEvent.VK_Z, 'z');
		blockAreaCanvas.handleKeyPressed(c);
		topLevelBlocks = GC.getCopyOfAllBlocks(gc);
		assertEquals(1,topLevelBlocks.size());
		assertEquals("MoveForward",IB.getName(topLevelBlocks.get(0)));
		assertEquals(null,GC.getNextBlockToExecute(gc));
		assertEquals(new Vector(499,149),IPB.getPosition(IB.getPresentationBlock(topLevelBlocks.get(0))));
		//redo 2x
		KeyEvent d = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK+KeyEvent.SHIFT_DOWN_MASK,KeyEvent.VK_Z, 'z');
		blockAreaCanvas.handleKeyPressed(d);
		topLevelBlocks = GC.getCopyOfAllBlocks(gc);
		assertEquals(0,topLevelBlocks.size());
	}
	
	@Test
	void undoRedoMoveBlocks() {
		setup();
		blockAreaCanvas.handleMousePressed(11, 11);
		blockAreaCanvas.handleMouseDragged(500, 50);
		blockAreaCanvas.handleMouseReleased(500, 50);
		blockAreaCanvas.handleMousePressed(11, 71);
		blockAreaCanvas.handleMouseDragged(500, 70);
		blockAreaCanvas.handleMouseReleased(500, 70);
		List<Block> topLevelBlocks = GC.getCopyOfAllBlocks(gc);
		assertEquals(2,topLevelBlocks.size());
		Block moveBlock = topLevelBlocks.get(0);
		assertEquals("MoveForward",IB.getName(moveBlock));
		Block leftBlock = IB.getNextBlock(moveBlock);
		assertEquals("TurnLeft",IB.getName(leftBlock));
		//move
		blockAreaCanvas.handleMousePressed(500, 50);
		blockAreaCanvas.handleMouseDragged(300, 100);
		blockAreaCanvas.handleMouseReleased(300, 100);
		assertEquals(new Vector(299,99),IPB.getPosition(IB.getPresentationBlock(moveBlock)));
		assertEquals(new Vector(299,119),IPB.getPosition(IB.getPresentationBlock(leftBlock)));
		//undo
		KeyEvent a = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK,KeyEvent.VK_Z,'z');
		blockAreaCanvas.handleKeyPressed(a);
		assertEquals(new Vector(499,49),IPB.getPosition(IB.getPresentationBlock(moveBlock)));
		assertEquals(new Vector(499,69),IPB.getPosition(IB.getPresentationBlock(leftBlock)));
		//redo
		KeyEvent b = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK+KeyEvent.SHIFT_DOWN_MASK,KeyEvent.VK_Z, 'z');
		blockAreaCanvas.handleKeyPressed(b);
		assertEquals(new Vector(299,99),IPB.getPosition(IB.getPresentationBlock(moveBlock)));
		assertEquals(new Vector(299,119),IPB.getPosition(IB.getPresentationBlock(leftBlock)));
	}
	
	@Test
	void undoRedoRunProgram() {
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
		//check if running works
		KeyEvent a = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(a);
		assertEquals("MoveForward",IB.getName(GC.getNextBlockToExecute(gc)));
		KeyEvent b = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(b);
		assertEquals("TurnLeft",IB.getName(GC.getNextBlockToExecute(gc)));
		KeyEvent c = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(c);
		assertEquals("TurnRight",IB.getName(GC.getNextBlockToExecute(gc)));
		//stop at turnleft
		//undo 1x
		KeyEvent d = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK,KeyEvent.VK_Z,'z');
		blockAreaCanvas.handleKeyPressed(d);
		assertEquals("TurnLeft",IB.getName(GC.getNextBlockToExecute(gc)));
		//undo 2x
		KeyEvent e = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK,KeyEvent.VK_Z,'z');
		blockAreaCanvas.handleKeyPressed(e);
		assertEquals("MoveForward",IB.getName(GC.getNextBlockToExecute(gc)));
		//undo 3x, 4x
		KeyEvent f = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK,KeyEvent.VK_Z,'z');
		blockAreaCanvas.handleKeyPressed(f);
		assertEquals("MoveForward",IB.getName(GC.getNextBlockToExecute(gc)));
		KeyEvent g = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK,KeyEvent.VK_Z,'z');
		blockAreaCanvas.handleKeyPressed(g);
		assertEquals("MoveForward",IB.getName(GC.getNextBlockToExecute(gc)));
		//redo 1x
		KeyEvent h = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK+KeyEvent.SHIFT_DOWN_MASK,KeyEvent.VK_Z, 'z');
		blockAreaCanvas.handleKeyPressed(h);
		assertEquals("TurnLeft",IB.getName(GC.getNextBlockToExecute(gc)));
		//redo 2x
		KeyEvent i = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK+KeyEvent.SHIFT_DOWN_MASK,KeyEvent.VK_Z, 'z');
		blockAreaCanvas.handleKeyPressed(i);
		assertEquals("TurnRight",IB.getName(GC.getNextBlockToExecute(gc)));
	}
	
	@Test
	void undoRedoModifyAndRun() {
		setup();
		//Move Forward / Turn Left
		blockAreaCanvas.handleMousePressed(11, 11);
		blockAreaCanvas.handleMouseDragged(500, 50);
		blockAreaCanvas.handleMouseReleased(500, 50);
		blockAreaCanvas.handleMousePressed(11, 71);
		blockAreaCanvas.handleMouseDragged(500, 70);
		blockAreaCanvas.handleMouseReleased(500, 70);
		List<Block> topLevelBlocks = GC.getCopyOfAllBlocks(gc);
		Block moveBlock = topLevelBlocks.get(0);
		assertEquals("MoveForward",IB.getName(moveBlock));
		Block leftBlock = IB.getNextBlock(moveBlock);
		assertEquals("TurnLeft",IB.getName(leftBlock));
		assertEquals(new Vector(499,49),IPB.getPosition(IB.getPresentationBlock(moveBlock)));
		assertEquals(new Vector(499,69),IPB.getPosition(IB.getPresentationBlock(leftBlock)));
		//run
		KeyEvent a = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(a);
		assertEquals("MoveForward",IB.getName(GC.getNextBlockToExecute(gc)));
		KeyEvent b = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F5, KeyEvent.CHAR_UNDEFINED);
		blockAreaCanvas.handleKeyPressed(b);
		assertEquals("TurnLeft",IB.getName(GC.getNextBlockToExecute(gc)));
		//move a block + Stop run
		blockAreaCanvas.handleMousePressed(500, 50);
		blockAreaCanvas.handleMouseDragged(500, 50);
		blockAreaCanvas.handleMouseDragged(450, 70);
		blockAreaCanvas.handleMouseDragged(400, 100);
		blockAreaCanvas.handleMouseReleased(400, 100);
		assertEquals(new Vector(399,99),IPB.getPosition(IB.getPresentationBlock(moveBlock)));
		assertEquals(new Vector(399,119),IPB.getPosition(IB.getPresentationBlock(leftBlock)));
		//undo movement 
		KeyEvent c = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK,KeyEvent.VK_Z,'z');
		blockAreaCanvas.handleKeyPressed(c);
		assertEquals(new Vector(499,49),IPB.getPosition(IB.getPresentationBlock(moveBlock)));
		assertEquals(new Vector(499,69),IPB.getPosition(IB.getPresentationBlock(leftBlock)));
		//undo add before running
		KeyEvent e = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK,KeyEvent.VK_Z,'z');
		blockAreaCanvas.handleKeyPressed(e);
		assertEquals(1,GC.getCopyOfAllBlocks(gc).size());
		//redo movement before running
		KeyEvent f = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK+KeyEvent.SHIFT_DOWN_MASK,KeyEvent.VK_Z, 'z');
		blockAreaCanvas.handleKeyPressed(f);
		assertEquals(2,GC.getCopyOfAllBlocks(gc).size());
		//redo movement
		KeyEvent g = new KeyEvent(blockAreaCanvas,KeyEvent.KEY_PRESSED,System.currentTimeMillis(),KeyEvent.CTRL_DOWN_MASK+KeyEvent.SHIFT_DOWN_MASK,KeyEvent.VK_Z, 'z');
		blockAreaCanvas.handleKeyPressed(g);
		assertEquals(2,GC.getCopyOfAllBlocks(gc).size());
		assertEquals(new Vector(399,99),IPB.getPosition(IB.getPresentationBlock(moveBlock)));
		assertEquals(new Vector(399,119),IPB.getPosition(IB.getPresentationBlock(leftBlock)));
	}
}
