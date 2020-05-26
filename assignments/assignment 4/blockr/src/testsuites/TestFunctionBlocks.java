package testsuites;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.KeyEvent;
import org.junit.jupiter.api.Test;

import domain.GameController;
import domain.block.ImplementationBlock;
import impl.root.ImplementationGameWorld;
import presentation.BlockAreaCanvas;
import presentation.BlockrPanel;
import presentation.block.ImplementationPresentationBlock;

public class TestFunctionBlocks {

	private static BlockrPanel blockrPanel;
	private GameController GC;
	static ImplementationBlock IB = new ImplementationBlock();
	static ImplementationPresentationBlock IPB = new ImplementationPresentationBlock();
	static ImplementationGameWorld IGW = new ImplementationGameWorld();
	BlockAreaCanvas blockAreaCanvas;

	static KeyEvent execute;
	static KeyEvent undo;
	static KeyEvent redo;

	private void setup() {
		try {
			blockrPanel = new BlockrPanel(ImplementationGameWorld.class);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GC = blockrPanel.getGameController();
		blockAreaCanvas = blockrPanel.getBlockAreaCanvas();

		execute = new KeyEvent(blockAreaCanvas, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_F5,
				KeyEvent.CHAR_UNDEFINED);
		undo = new KeyEvent(blockAreaCanvas, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), KeyEvent.CTRL_DOWN_MASK,
				KeyEvent.VK_Z, 'z');
		redo = new KeyEvent(blockAreaCanvas, KeyEvent.KEY_PRESSED, System.currentTimeMillis(),
				KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK, KeyEvent.VK_Z, 'z');
	}

	@Test
	void RemoveFunctionDefinitionBlock() {
		setup();

		// function def 0
		blockAreaCanvas.handleMousePressed(11, 431);
		blockAreaCanvas.handleMouseDragged(500, 400);
		blockAreaCanvas.handleMouseReleased(500, 400);
		assertEquals(14, GC.getAmountOfBlocksLeft());

		// function call 0
		blockAreaCanvas.handleMousePressed(11, 491);
		blockAreaCanvas.handleMouseDragged(600, 400);
		blockAreaCanvas.handleMouseReleased(600, 400);
		assertEquals(13, GC.getAmountOfBlocksLeft());

		// make turn left & connect to function call0
		blockAreaCanvas.handleMousePressed(11, 71);
		blockAreaCanvas.handleMouseDragged(600, 410);
		blockAreaCanvas.handleMouseReleased(600, 410);
		assertEquals(12, GC.getAmountOfBlocksLeft());

		// make turn right & put in body function def 0
		blockAreaCanvas.handleMousePressed(11, 131);
		blockAreaCanvas.handleMouseDragged(520, 410);
		blockAreaCanvas.handleMouseReleased(520, 410);
		assertEquals(11, GC.getAmountOfBlocksLeft());

		// test execute
		String[] desiredResult = { "Function: 0", "Turn Right", "Turn Left" };
		// execute()

		blockAreaCanvas.handleKeyPressed(execute);

		int i = 0;
		while (GC.isExecuting()) {
			assertEquals(desiredResult[i], IB.getName(GC.getNextBlockToExecute()));
			blockAreaCanvas.handleKeyPressed(execute);
			i++;

		}
		assertEquals(3, i);
		// end execute

		// remove function definition
		// function def 0
		blockAreaCanvas.handleMousePressed(500, 400);
		blockAreaCanvas.handleMouseDragged(10, 10);
		blockAreaCanvas.handleMouseReleased(10, 10);
		assertEquals(14, GC.getAmountOfBlocksLeft());

		// test execute
		String[] desiredResult1 = { "Turn Left" };
		// execute()
		blockAreaCanvas.handleKeyPressed(execute);

		i = 0;
		while (GC.isExecuting()) {
			assertEquals(desiredResult1[i], IB.getName(GC.getNextBlockToExecute()));
			blockAreaCanvas.handleKeyPressed(execute);
			i++;

		}
		assertEquals(1, i);
		// end execute

		// _________________________________//
		// undo remove function definition
		// _________________________________//
		blockAreaCanvas.handleKeyPressed(undo);

		assertEquals(11, GC.getAmountOfBlocksLeft());

		// test execute
		// execute()

		blockAreaCanvas.handleKeyPressed(execute);

		i = 0;
		while (GC.isExecuting()) {
			assertEquals(desiredResult[i], IB.getName(GC.getNextBlockToExecute()));
			blockAreaCanvas.handleKeyPressed(execute);
			i++;

		}
		assertEquals(3, i);
		// end execute

		
		
		// _________________________________//
		// redo remove function definition
		// _________________________________//
		blockAreaCanvas.handleKeyPressed(redo);

		assertEquals(14, GC.getAmountOfBlocksLeft());

		// test execute
		// execute()

		blockAreaCanvas.handleKeyPressed(execute);

		i = 0;
		while (GC.isExecuting()) {
			assertEquals(desiredResult1[i], IB.getName(GC.getNextBlockToExecute()));
			blockAreaCanvas.handleKeyPressed(execute);
			i++;

		}
		assertEquals(1, i);
		// end execute
		
		
		

	}

}
