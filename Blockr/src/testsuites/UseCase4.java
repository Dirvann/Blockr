package testsuites;

import static org.junit.jupiter.api.Assertions.*;

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
 * ## Use Case 4: Move Program Block
 * 
 * ### Main Success Scenario
 * 
 * 1. The user moves the mouse cursor over a block in the Program Area,then presses the left mouse key,
 * 	  then moves the mouse cursor to another place in the Program Area, and then releases the left mouse key.
 * 
 * 2. The Program Block gets removed from the old spot and added to the new spot in the Program Area.
 * 
 * ### Extensions
 * 
 * 1a. There are blocks connected in the same cavity below the current block
 *     1. These blocks are moved together with the current block.
 *  
 * 1b. When there is a block connected above the current block.
 *     1. The connection between these blocks is broken apart.
 *   
 * 1c. There are blocks connected to the right of the current block
 *     1. These blocks are moved together with the current block.
 *    
 * 1d. When there is a block connected to the left of the current block.
 *     1. The connection between these blocks is broken apart.
 *     
 * 1e. Programming Block is initially inside a While or If block.
 *     1. The system detaches the Programming Block and updates the size of the If/While Block.
 *       
 * 2a. The block is released over the Palette
 *     1. The block and the connected blocks are removed 
 */
class UseCase4 {
	
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
	void moveBlock() {
		setup();
		blockAreaCanvas.handleMousePressed(11, 11);
		blockAreaCanvas.handleMouseDragged(500, 150);
		blockAreaCanvas.handleMouseReleased(500, 150);
		assertEquals(new Vector(499,149),IPB.getPosition(IB.getPresentationBlock(GC.getCopyOfAllBlocks(gc).get(0))));
		//draging block
		blockAreaCanvas.handleMousePressed(500, 150);
		blockAreaCanvas.handleMouseDragged(400, 200);
		List<Block> topLevelBlocks = GC.getCopyOfAllBlocks(gc);
		//assertEquals(0,topLevelBlocks.size());
		blockAreaCanvas.handleMouseDragged(355, 40);
		blockAreaCanvas.handleMouseDragged(200, 40);
		blockAreaCanvas.handleMouseDragged(400, 250);
		blockAreaCanvas.handleMouseReleased(400, 250);
		topLevelBlocks = GC.getCopyOfAllBlocks(gc);
		assertEquals(1,topLevelBlocks.size());
		assertEquals("MoveForward",IB.getName(topLevelBlocks.get(0)));
		assertEquals(null,GC.getNextBlockToExecute(gc));
		assertEquals(new Vector(399,249),IPB.getPosition(IB.getPresentationBlock(GC.getCopyOfAllBlocks(gc).get(0))));
	}
	
	@Test
	void moveMultipleBlocks() {
		setup();
		blockAreaCanvas.handleMousePressed(11, 71);
		blockAreaCanvas.handleMouseDragged(500, 70);
		blockAreaCanvas.handleMouseReleased(500, 70);
		Block block = GC.getCopyOfAllBlocks(gc).get(0);
		blockAreaCanvas.handleMousePressed(11, 131);
		blockAreaCanvas.handleMouseDragged(500, 90);
		blockAreaCanvas.handleMouseReleased(500, 90);
		Block nextBlock = IB.getNextBlock(block);
//		assertTrue(GC.isTopLevelBlock(gc, block));
//		assertFalse(GC.isTopLevelBlock(gc, nextBlock));
		assertEquals(new Vector(499,69),IPB.getPosition(IB.getPresentationBlock(block)));
		assertEquals(new Vector(499,89),IPB.getPosition(IB.getPresentationBlock(nextBlock)));
		//draging block
		blockAreaCanvas.handleMousePressed(500, 70);
		blockAreaCanvas.handleMouseDragged(400, 250);
		blockAreaCanvas.handleMouseReleased(400, 250);
		assertEquals(null,GC.getNextBlockToExecute(gc));
		assertEquals(new Vector(399,249),IPB.getPosition(IB.getPresentationBlock(block)));
		assertEquals(nextBlock,IB.getNextBlock(block));
		assertEquals(new Vector(399,269),IPB.getPosition(IB.getPresentationBlock(nextBlock)));
//		assertTrue(GC.isTopLevelBlock(gc, block));
//		assertFalse(GC.isTopLevelBlock(gc, nextBlock));
	}
	
	@Test
	void breakConnection() {
		setup();
		blockAreaCanvas.handleMousePressed(11, 71);
		blockAreaCanvas.handleMouseDragged(500, 70);
		blockAreaCanvas.handleMouseReleased(500, 70);
		Block block = GC.getCopyOfAllBlocks(gc).get(0);
		blockAreaCanvas.handleMousePressed(11, 131);
		blockAreaCanvas.handleMouseDragged(500, 90);
		blockAreaCanvas.handleMouseReleased(500, 90);
		Block nextBlock = IB.getNextBlock(block);
		assertEquals("TurnRight",IB.getName(nextBlock));
		assertEquals(new Vector(499,69),IPB.getPosition(IB.getPresentationBlock(block)));
		assertEquals(new Vector(499,89),IPB.getPosition(IB.getPresentationBlock(nextBlock)));
//		assertTrue(GC.isTopLevelBlock(gc, block));
//		assertFalse(GC.isTopLevelBlock(gc, nextBlock));
		//draging block
		blockAreaCanvas.handleMousePressed(500, 90);
		blockAreaCanvas.handleMouseDragged(400, 250);
		blockAreaCanvas.handleMouseReleased(400, 250);
//		assertTrue(GC.isTopLevelBlock(gc, block));
//		assertTrue(GC.isTopLevelBlock(gc, nextBlock));
		assertEquals(null,IB.getNextBlock(block));
		assertEquals(new Vector(499,69),IPB.getPosition(IB.getPresentationBlock(block)));
		assertEquals(new Vector(399,249),IPB.getPosition(IB.getPresentationBlock(nextBlock)));
	}
	
	@Test
	void moveMultipleConditionBlocks() {
		setup();
		//drop not block
		blockAreaCanvas.handleMousePressed(11, 251);
		blockAreaCanvas.handleMouseDragged(500, 70);
		blockAreaCanvas.handleMouseReleased(500, 70);
		Block notBlock = GC.getCopyOfAllBlocks(gc).get(0);
		//drop wall in front block
		blockAreaCanvas.handleMousePressed(11, 191);
		blockAreaCanvas.handleMouseDragged(600, 70);
		blockAreaCanvas.handleMouseReleased(600, 70);
		Block wallInFrontBlock = IB.getNextBlock(notBlock);
		assertEquals(new Vector(499,69),IPB.getPosition(IB.getPresentationBlock(notBlock)));
		assertEquals(new Vector(599,69),IPB.getPosition(IB.getPresentationBlock(wallInFrontBlock)));
		//draging block
		blockAreaCanvas.handleMousePressed(500, 70);
		blockAreaCanvas.handleMouseDragged(400, 250);
		blockAreaCanvas.handleMouseReleased(400, 250);
		assertEquals(null,GC.getNextBlockToExecute(gc));
		assertEquals(new Vector(399,249),IPB.getPosition(IB.getPresentationBlock(notBlock)));
		assertEquals(wallInFrontBlock,IB.getNextBlock(notBlock));
		assertEquals(new Vector(499,249),IPB.getPosition(IB.getPresentationBlock(wallInFrontBlock)));
//		assertTrue(GC.isTopLevelBlock(gc, notBlock));
//		assertFalse(GC.isTopLevelBlock(gc, wallInFrontBlock));
	}
	
	@Test
	void breakConnectionConditionBlocks() {
		setup();
		//drop not block
		blockAreaCanvas.handleMousePressed(11, 251);
		blockAreaCanvas.handleMouseDragged(500, 70);
		blockAreaCanvas.handleMouseReleased(500, 70);
		Block notBlock = GC.getCopyOfAllBlocks(gc).get(0);
		//drop wall in front block
		blockAreaCanvas.handleMousePressed(11, 191);
		blockAreaCanvas.handleMouseDragged(600, 70);
		blockAreaCanvas.handleMouseReleased(600, 70);
		Block wallInFrontBlock = IB.getNextBlock(notBlock);
		assertEquals(new Vector(499,69),IPB.getPosition(IB.getPresentationBlock(notBlock)));
		assertEquals(new Vector(599,69),IPB.getPosition(IB.getPresentationBlock(wallInFrontBlock)));
//		assertTrue(GC.isTopLevelBlock(gc, notBlock));
//		assertFalse(GC.isTopLevelBlock(gc, wallInFrontBlock));
		//draging block
		blockAreaCanvas.handleMousePressed(600, 70);
		blockAreaCanvas.handleMouseDragged(400, 250);
		blockAreaCanvas.handleMouseReleased(400, 250);
		assertEquals(null,GC.getNextBlockToExecute(gc));
		assertEquals(new Vector(499,69),IPB.getPosition(IB.getPresentationBlock(notBlock)));
		assertEquals(new Vector(399,249),IPB.getPosition(IB.getPresentationBlock(wallInFrontBlock)));
//		assertTrue(GC.isTopLevelBlock(gc, notBlock));
//		assertTrue(GC.isTopLevelBlock(gc, wallInFrontBlock));
	}
	
	@Test
	void surroundingBlockMovement() {
		setup();
		//drop if block
		blockAreaCanvas.handleMousePressed(11, 311);
		blockAreaCanvas.handleMouseDragged(500, 70);
		blockAreaCanvas.handleMouseReleased(500, 70);
		Block ifBlock = GC.getCopyOfAllBlocks(gc).get(0);
		//drop Turn Left block
		blockAreaCanvas.handleMousePressed(11, 71);
		blockAreaCanvas.handleMouseDragged(520, 90);
		blockAreaCanvas.handleMouseReleased(520, 90);
		Block leftBlock = IB.getBodyBlock((SurroundingBlock) ifBlock);
		assertEquals("TurnLeft",IB.getName(leftBlock));
		assertEquals(new Vector(499,69),IPB.getPosition(IB.getPresentationBlock(ifBlock)));
		assertEquals(new Vector(519,89),IPB.getPosition(IB.getPresentationBlock(leftBlock)));
//		assertTrue(GC.isTopLevelBlock(gc, ifBlock));
//		assertFalse(GC.isTopLevelBlock(gc, leftBlock));
		//draging blocks togheter
		blockAreaCanvas.handleMousePressed(500, 70);
		blockAreaCanvas.handleMouseDragged(400, 250);
		blockAreaCanvas.handleMouseReleased(400, 250);
		assertEquals(new Vector(399,249),IPB.getPosition(IB.getPresentationBlock(ifBlock)));
		assertEquals(leftBlock,IB.getBodyBlock((SurroundingBlock) ifBlock));
		assertEquals(new Vector(419,269),IPB.getPosition(IB.getPresentationBlock(leftBlock)));
		//dragging block out of if
		blockAreaCanvas.handleMousePressed(420, 270);
		blockAreaCanvas.handleMouseDragged(350, 100);
		blockAreaCanvas.handleMouseReleased(350, 100);
		assertEquals(null,IB.getBodyBlock((SurroundingBlock) ifBlock));
		assertEquals(new Vector(349,99),IPB.getPosition(IB.getPresentationBlock(leftBlock)));
		assertEquals(new Vector(399,249),IPB.getPosition(IB.getPresentationBlock(ifBlock)));
//		assertTrue(GC.isTopLevelBlock(gc, ifBlock));
//		assertTrue(GC.isTopLevelBlock(gc, leftBlock));
		//dragging block into if
		blockAreaCanvas.handleMousePressed(350, 100);
		blockAreaCanvas.handleMouseDragged(420, 270);
		blockAreaCanvas.handleMouseReleased(420,270);
		assertEquals(new Vector(419,269),IPB.getPosition(IB.getPresentationBlock(leftBlock)));
		assertEquals(leftBlock,IB.getBodyBlock((SurroundingBlock) ifBlock));
		assertEquals(new Vector(399,249),IPB.getPosition(IB.getPresentationBlock(ifBlock)));
//		assertTrue(GC.isTopLevelBlock(gc, ifBlock));
//		assertFalse(GC.isTopLevelBlock(gc, leftBlock));
	}
	
	@Test
	void removeByDraggingToPalette(){
		setup();
		blockAreaCanvas.handleMousePressed(11, 11);
		blockAreaCanvas.handleMouseDragged(500, 150);
		blockAreaCanvas.handleMouseReleased(500, 150);
		assertEquals(new Vector(499,149),IPB.getPosition(IB.getPresentationBlock(GC.getCopyOfAllBlocks(gc).get(0))));
		blockAreaCanvas.handleMousePressed(500, 150);
		blockAreaCanvas.handleMouseReleased(11, 11);
		assertEquals(0,GC.getCopyOfAllBlocks(gc).size());
	}
	
	@Test
	void removeFullSequenceBlock(){
		setup();
		//drop if block
		blockAreaCanvas.handleMousePressed(11, 311);
		blockAreaCanvas.handleMouseDragged(500, 70);
		blockAreaCanvas.handleMouseReleased(500, 70);
		Block ifBlock = GC.getCopyOfAllBlocks(gc).get(0);
		//drop Turn Left block
		blockAreaCanvas.handleMousePressed(11, 71);
		blockAreaCanvas.handleMouseDragged(520, 90);
		blockAreaCanvas.handleMouseReleased(520, 90);
		Block leftBlock = IB.getBodyBlock((SurroundingBlock) ifBlock);
		assertEquals("TurnLeft",IB.getName(leftBlock));
		assertEquals(new Vector(499,69),IPB.getPosition(IB.getPresentationBlock(ifBlock)));
		assertEquals(new Vector(519,89),IPB.getPosition(IB.getPresentationBlock(leftBlock)));
//		assertTrue(GC.isTopLevelBlock(gc, ifBlock));
//		assertFalse(GC.isTopLevelBlock(gc, leftBlock));
		//drop Turn Right block
		blockAreaCanvas.handleMousePressed(11, 131);
		blockAreaCanvas.handleMouseDragged(520, 90);
		blockAreaCanvas.handleMouseReleased(520, 90);
		Block rightBlock = IB.getBodyBlock((SurroundingBlock) ifBlock);
		assertEquals("TurnRight",IB.getName(rightBlock));
		assertEquals(new Vector(499,69),IPB.getPosition(IB.getPresentationBlock(ifBlock)));
		assertEquals(new Vector(519,89),IPB.getPosition(IB.getPresentationBlock(rightBlock)));
		assertEquals(new Vector(519,109),IPB.getPosition(IB.getPresentationBlock(leftBlock)));
		//add wallInFront condition
		blockAreaCanvas.handleMousePressed(11, 191);
		blockAreaCanvas.handleMouseDragged(600, 70);
		blockAreaCanvas.handleMouseReleased(600, 70);
		Block wallInFrontBlock = IB.getConditionBlock((SurroundingBlock) ifBlock);
//		assertTrue(GC.isTopLevelBlock(gc, ifBlock));
//		assertFalse(GC.isTopLevelBlock(gc, leftBlock));
//		assertFalse(GC.isTopLevelBlock(gc, rightBlock));
//		assertFalse(GC.isTopLevelBlock(gc, wallInFrontBlock));
		//draging blocks togheter
		blockAreaCanvas.handleMousePressed(500, 70);
		blockAreaCanvas.handleMouseDragged(400, 250);
		blockAreaCanvas.handleMouseReleased(400, 250);
		assertEquals(new Vector(399,249),IPB.getPosition(IB.getPresentationBlock(ifBlock)));
		assertEquals(new Vector(419,269),IPB.getPosition(IB.getPresentationBlock(rightBlock)));
		assertEquals(new Vector(419,289),IPB.getPosition(IB.getPresentationBlock(leftBlock)));
		assertEquals(new Vector(499,249),IPB.getPosition(IB.getPresentationBlock(wallInFrontBlock)));

		//removing all blocks via palette
		blockAreaCanvas.handleMousePressed(400, 250);
		blockAreaCanvas.handleMouseDragged(11, 11);
		blockAreaCanvas.handleMouseReleased(11,11);
		assertEquals(0,GC.getCopyOfAllBlocks(gc).size());
	}
}
