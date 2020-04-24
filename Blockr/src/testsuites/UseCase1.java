package testsuites;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import domain.GameController;
import domain.ImplementationGameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
import impl.root.ImplementationGameWorld;
import presentation.BlockAreaCanvas;
import presentation.BlockrPanel;
import presentation.block.ImplementationPresentationBlock;

/**
 * ## Use Case 1: Add Program Block
 * 
 * ### Main Success Scenario
 * 
 * 1. The user moves the mouse cursor over a block in the Palette,then presses the left mouse key,
 *    then moves the mouse cursor to the Program Area, and then releases the left mouse key.
 * 
 * 2. The system adds a new block of the same type to the Program Area.
 * 
 * ### Extensions
 * 
 * 1a. When the user releases the mouse key, one of the blocks
 *     connectors is near a compatible opposite connector of another block.
 *    1. The system adds a new block of the same type to the ProgramArea; 
 *       the new block is inserted into an existing group of connected blocks at the matching connection point.
 *    
 * 2a. Maximum number of blocks is reached.
 *    1. All blocks disappear from the Palette.
 */
class UseCase1 {
	
	private static BlockrPanel blockrPanel;
	private ImplementationGameController GC;
	private GameController gc;
	static ImplementationBlock IB = new ImplementationBlock();
	static ImplementationPresentationBlock IPB = new ImplementationPresentationBlock();
	BlockAreaCanvas blockAreaCanvas;
	
	private void setup() {
		//JFrame frame = new JFrame("Blockr");
		try {
			blockrPanel = new BlockrPanel(ImplementationGameWorld.class);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//frame.add(blockrPanel);
		//frame.pack();
		//frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//frame.setVisible(true);
		GC = new ImplementationGameController();
		gc = blockrPanel.getGameController();
		blockAreaCanvas = blockrPanel.getBlockAreaCanvas();	
	}
	
	@Test
	void addBlockToProgramArea() {
		setup();
		blockAreaCanvas.handleMousePressed(11, 11);
		blockAreaCanvas.handleMouseDragged(30, 30);
		blockAreaCanvas.handleMouseDragged(80, 40);
		blockAreaCanvas.handleMouseDragged(200, 40);
		blockAreaCanvas.handleMouseDragged(500, 150);
		blockAreaCanvas.handleMouseReleased(500, 150);
		System.out.println(IPB.getPosition(IB.getPresentationBlock(GC.getCopyOfAllBlocks(gc).get(0))).getX() + " "+ IPB.getPosition(IB.getPresentationBlock(GC.getCopyOfAllBlocks(gc).get(0))).getY());
		List<Block> topLevelBlocks = GC.getCopyOfAllBlocks(gc);
		assertEquals(1,topLevelBlocks.size());
		assertEquals("MoveForward",IB.getName(topLevelBlocks.get(0)));
		assertEquals(null,GC.getNextBlockToExecute(gc));
	}
	
	@Test
	void connectingBlocks() {
		setup();
		blockAreaCanvas.handleMousePressed(11, 11);
		blockAreaCanvas.handleMouseDragged(500, 50);
		blockAreaCanvas.handleMouseReleased(500, 50);
		Block block = GC.getCopyOfAllBlocks(gc).get(0);
		//Add another block
		blockAreaCanvas.handleMousePressed(11, 191);
		blockAreaCanvas.handleMouseDragged(600, 550);
		blockAreaCanvas.handleMouseReleased(600, 550);
		assertEquals(2,GC.getCopyOfAllBlocks(gc).size());
		//Add connecting block
		blockAreaCanvas.handleMousePressed(11, 71);
		blockAreaCanvas.handleMouseDragged(500, 70);
		blockAreaCanvas.handleMouseReleased(500, 70);
		assertEquals(3,GC.getCopyOfAllBlocks(gc).size());
		System.out.println(GC.getCopyOfAllBlocks(gc));
		assertEquals("TurnLeft",IB.getName(IB.getNextBlock(block)));
		//Snap in between
		blockAreaCanvas.handleMousePressed(11, 131);
		blockAreaCanvas.handleMouseDragged(501, 71);
		blockAreaCanvas.handleMouseReleased(501, 71); //not exactly, close enough
		assertEquals(4,GC.getCopyOfAllBlocks(gc).size());
		assertEquals("TurnRight",IB.getName(IB.getNextBlock(block)));
		assertEquals("TurnLeft",IB.getName(IB.getNextBlock(IB.getNextBlock(block))));
	}
	
	@Test
	void maxNumberOfBlocksReached() {
		setup();
		for (int i=0;i<16;i++) {
			blockAreaCanvas.handleMousePressed(11, 11);
			blockAreaCanvas.handleMouseDragged(500, 50);
			blockAreaCanvas.handleMouseReleased(500, 50);
		}
		assertEquals(15,GC.getCopyOfAllBlocks(gc).size());
	}
}