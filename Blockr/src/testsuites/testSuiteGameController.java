package testsuites;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import command.turnLeftCommand;
import domain.GameController;
import domain.ImplementationGameController;
import domain.block.ActionBlock;
import domain.block.Block;
import domain.block.ChainConditionBlock;
import domain.block.ConditionBlock;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import domain.block.SingleConditionBlock;
import domain.block.SingleSurroundingBlock;
import domain.block.SurroundingBlock;
import exceptions.domainExceptions.NotOneStartingBlockException;
import game_world.ImplementationGameWorld;
import game_world.Vector;
import game_world.cell.Cell;
import game_world.cell.Goal;
import game_world.cell.Wall;
import presentation.block.ChainConditionBlockPresentation;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

public class testSuiteGameController {
	static GameController gc;
	static ImplementationGameController IGC = new ImplementationGameController();
	static ImplementationBlock IB = new ImplementationBlock();
	static ImplementationPresentationBlock IPB = new ImplementationPresentationBlock();	
	static ImplementationGameWorld IGW = new ImplementationGameWorld();

	static Cell[] cells = { new Goal(), new Wall(), new Wall() };
	static Vector[] locations = { new Vector(1, 1), new Vector(1, 0), new Vector(2, 2) };
	
	static PresentationBlock<ActionBlock> left,right, forward, forward2;
	static PresentationBlock<SingleSurroundingBlock> ifB, whileB;
	static PresentationBlock<SingleConditionBlock> wallinfront;
	static PresentationBlock<ChainConditionBlock> not;
	int total = 0;
	int score = 0;
	

	private static void setup() {
		try {
			left = IPB.makeActionBlock("TurnLeft", null); // create blocks
			right = IPB.makeActionBlock("TurnRight", null);
			forward = IPB.makeActionBlock("MoveForward", null);
			forward2 = IPB.makeActionBlock("MoveForward", null);
			wallinfront = IPB.makeSingleConditionBlock("WallInFront", null);
			not = IPB.makeNotBlock(null);
			ifB = IPB.makeIfBlock(null);
			whileB = IPB.makeWhileBlock(null);
			IGW = new ImplementationGameWorld();
			gc = IGC.makeGameController(IGW);
		} catch (Exception e) {
			System.out.println("Testclass failed in setup phase.");
		}
	}
	
	@Test
	public void simpleExecution() throws Exception {
		setup();
		IGC.addBlockToProgramArea(gc, forward);
		assertEquals(14, IGC.getAmountOfBlocksLeft(gc));
		IGC.execute(gc);
		assertEquals(IPB.getBlock(forward), IGC.getNextBlockToExecute(gc));
	}
	
	@Test
	public void restartExecution() throws Exception {
		setup();
		IGC.addBlockToProgramArea(gc, forward);
		IGC.addBlockToProgramArea(gc, left);
		IGC.connect(IPB.getBlock(forward), IPB.getBlock(left), gc);
		assertEquals(13, IGC.getAmountOfBlocksLeft(gc));
		assertEquals(2, IGC.getCopyOfAllBlocks(gc).size());
		IGC.execute(gc);
		assertEquals(IPB.getBlock(forward), IGC.getNextBlockToExecute(gc));
		IGC.execute(gc);
		assertEquals(IPB.getBlock(left), IGC.getNextBlockToExecute(gc));
		IGC.stopExecution(gc);
		assertEquals(null, IGC.getNextBlockToExecute(gc));
		IGC.execute(gc);
		assertEquals(IPB.getBlock(forward), IGC.getNextBlockToExecute(gc));
	}
	
	@Test
	public void multipleStartBlocks() throws Exception {
		setup();
		IGC.addBlockToProgramArea(gc, forward);
		IGC.addBlockToProgramArea(gc, left);
		try {
			IGC.execute(gc);
		} catch (NotOneStartingBlockException e) {
		}
		IGC.removeBlockFromProgramArea(gc, forward);
		IGC.execute(gc);
		assertEquals(IPB.getBlock(left), IGC.getNextBlockToExecute(gc));
		
	}
	
	@Test
	public void robotRunsIntoWallReset() throws Exception {
		setup();
		IGC.addBlockToProgramArea(gc, whileB);
		IGC.addBlockToProgramArea(gc, not);
		IGC.addBlockToProgramArea(gc, wallinfront);
		IGC.addBlockToProgramArea(gc, forward);
		IGC.addBlockToProgramArea(gc, forward2);
		IGC.addBlockToProgramArea(gc, left);
		assertEquals(9, IGC.getAmountOfBlocksLeft(gc));
		
		IGC.setBody((SurroundingBlock) IPB.getBlock(whileB), (SequenceBlock) IPB.getBlock(forward), gc);
		IGC.connect(IPB.getBlock(not), IPB.getBlock(wallinfront), gc);
		IGC.setCondition((SurroundingBlock) IPB.getBlock(whileB), (ConditionBlock) IPB.getBlock(not), gc);
		IGC.connect(IPB.getBlock(whileB), IPB.getBlock(forward2), gc);
		IGC.connect(IPB.getBlock(forward2), IPB.getBlock(left), gc);
		
		IGC.execute(gc);
		Block executingBlock = IGC.getNextBlockToExecute(gc);
		while (executingBlock != IPB.getBlock(forward2)) {
			IGC.execute(gc);
			executingBlock = IGC.getNextBlockToExecute(gc);
		}
		
		IGC.execute(gc);
		assertEquals(null, IGC.getNextBlockToExecute(gc));
		assertFalse(IGC.isExecuting(gc));
	}
	
}
