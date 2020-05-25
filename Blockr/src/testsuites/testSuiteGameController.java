package testsuites;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import actions.MoveForwardAction;
import actions.TurnLeftAction;
import actions.TurnRightAction;
import domain.GameController;
import domain.Vector;
import domain.block.ActionBlock;
import domain.block.Block;
import domain.block.ChainConditionBlock;
import domain.block.ConditionBlock;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import domain.block.SingleConditionBlock;
import domain.block.SingleSurroundingBlock;
import domain.block.SurroundingBlock;
import exceptions.domainExceptions.BlockColumnNotExecutableException;
import exceptions.domainExceptions.CantRunConditionException;
import exceptions.domainExceptions.NotOneStartingBlockException;
import impl.root.ImplementationGameWorld;
import predicates.WallInFrontPredicate;
import presentation.BlockAreaCanvas;
import presentation.BlockrPanel;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

public class testSuiteGameController {
	
	static BlockrPanel blockrPanel;
	static BlockAreaCanvas blockAreaCanvas;
	static GameController GC;
	static ImplementationBlock IB = new ImplementationBlock();
	static ImplementationPresentationBlock IPB = new ImplementationPresentationBlock();	
	static ImplementationGameWorld IGW = new ImplementationGameWorld();

	static Vector[] locations = { new Vector(1, 1), new Vector(1, 0), new Vector(2, 2) };
	
	static PresentationBlock<ActionBlock> left,right, forward, forward2;
	static PresentationBlock<SingleSurroundingBlock> ifB, whileB;
	static PresentationBlock<SingleConditionBlock> wallinfront;
	static PresentationBlock<ChainConditionBlock> not;
	int total = 0;
	int score = 0;
	

	private static void setup() {
		try {
			try {
				blockrPanel = new BlockrPanel(ImplementationGameWorld.class);
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GC = blockrPanel.getGameController();
			blockAreaCanvas = blockrPanel.getBlockAreaCanvas();	
			left = IPB.makeActionBlock(new TurnLeftAction(), null); // create blocks
			right = IPB.makeActionBlock(new TurnRightAction(), null);
			forward = IPB.makeActionBlock(new MoveForwardAction(), null);
			forward2 = IPB.makeActionBlock(new MoveForwardAction(), null);
			wallinfront = IPB.makeSingleConditionBlock(new WallInFrontPredicate(), null);
			not = IPB.makeNotBlock(null);
			ifB = IPB.makeIfBlock(null);
			whileB = IPB.makeWhileBlock(null);
			GC = new GameController();
		} catch (Exception e) {
			System.out.println("Testclass failed in setup phase.");
			e.printStackTrace();
		}
	}
	
	@Test
	public void simpleExecution() throws Exception {
		setup();
		GC.addBlockToProgramArea( forward);
		assertEquals(14, GC.getAmountOfBlocksLeft());
		GC.execute();
		assertEquals(IPB.getBlock(forward), GC.getNextBlockToExecute());
	}
	
	@Test
	public void restartExecution() throws Exception {
		setup();
		GC.addBlockToProgramArea( forward);
		GC.addBlockToProgramArea( left);
		GC.connect(IPB.getBlock(forward), IPB.getBlock(left));
		assertEquals(13, GC.getAmountOfBlocksLeft());
		assertEquals(2, GC.getCopyOfAllBlocks().size());
		GC.execute();
		assertEquals(IPB.getBlock(forward), GC.getNextBlockToExecute());
		try {
			GC.execute();
		} catch (Exception e) {
			return;
		}
		assertEquals(IPB.getBlock(left), GC.getNextBlockToExecute());
		GC.stopExecution();
		assertEquals(null, GC.getNextBlockToExecute());
		GC.execute();
		assertEquals(IPB.getBlock(forward), GC.getNextBlockToExecute());
	}
	
	@Test
	public void multipleStartBlocks() throws Exception {
		setup();
		GC.addBlockToProgramArea( forward);
		GC.addBlockToProgramArea( left);
		try {
			GC.execute();
		} catch (NotOneStartingBlockException e) {
		}
		GC.removeBlockFromProgramArea( forward);
		GC.execute();
		assertEquals(IPB.getBlock(left), GC.getNextBlockToExecute());
		
	}
	
	@Test
	public void robotRunsIntoWallReset() throws Exception {
		setup();
		GC.addBlockToProgramArea( whileB);
		GC.addBlockToProgramArea( not);
		GC.addBlockToProgramArea( wallinfront);
		GC.addBlockToProgramArea( forward);
		GC.addBlockToProgramArea( forward2);
		GC.addBlockToProgramArea( left);
		assertEquals(9, GC.getAmountOfBlocksLeft());
		
		GC.setBody((SurroundingBlock) IPB.getBlock(whileB), (SequenceBlock) IPB.getBlock(forward));
		GC.connect(IPB.getBlock(not), IPB.getBlock(wallinfront));
		GC.setCondition((SurroundingBlock) IPB.getBlock(whileB), (ConditionBlock) IPB.getBlock(not));
		GC.connect(IPB.getBlock(whileB), IPB.getBlock(forward2));
		GC.connect(IPB.getBlock(forward2), IPB.getBlock(left));
		
		GC.execute();
		Block executingBlock = GC.getNextBlockToExecute();
		while (executingBlock != IPB.getBlock(forward2)) {
			GC.execute();
			executingBlock = GC.getNextBlockToExecute();
		}
		try {
			GC.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
		assertEquals(null, GC.getNextBlockToExecute());
		assertFalse(GC.isExecuting());
	}
	
	@Test
	public void invalidBlocks() throws Exception {
		setup();
		GC.addBlockToProgramArea( whileB);
		GC.addBlockToProgramArea( not);
		GC.addBlockToProgramArea( forward);
		GC.addBlockToProgramArea( forward2);
		GC.addBlockToProgramArea( left);
		assertEquals(10, GC.getAmountOfBlocksLeft());
		
		GC.setBody((SurroundingBlock) IPB.getBlock(whileB), (SequenceBlock) IPB.getBlock(forward));
		GC.setCondition((SurroundingBlock) IPB.getBlock(whileB), (ConditionBlock) IPB.getBlock(not));
		GC.connect(IPB.getBlock(whileB), IPB.getBlock(forward2));
		GC.connect(IPB.getBlock(forward2), IPB.getBlock(left));
		
		try {
			GC.execute();
			throw new Exception("test invalid block failed");
		} catch (BlockColumnNotExecutableException e) {
			// TODO: handle exception
		}
	
	}
	@Test
	public void invalidBlocksAdvanced() throws Exception {
		setup();
		GC.addBlockToProgramArea( whileB);
		GC.addBlockToProgramArea( not);
		GC.addBlockToProgramArea( forward);
		GC.addBlockToProgramArea( forward2);
		GC.addBlockToProgramArea( left);
		assertEquals(10, GC.getAmountOfBlocksLeft());
		
		GC.setBody((SurroundingBlock) IPB.getBlock(whileB), (SequenceBlock) IPB.getBlock(forward));
		GC.setCondition((SurroundingBlock) IPB.getBlock(whileB), (ConditionBlock) IPB.getBlock(not));
		GC.connect(IPB.getBlock(whileB), IPB.getBlock(forward2));
		GC.connect(IPB.getBlock(forward2), IPB.getBlock(left));
		
		GC.addBlockToProgramArea( ifB);
		PresentationBlock<SingleConditionBlock> isWall2 = IPB.makeSingleConditionBlock(new WallInFrontPredicate(), null);
		GC.setCondition((SurroundingBlock) IPB.getBlock(ifB), (ConditionBlock) IPB.getBlock(isWall2));
		GC.setBody((SurroundingBlock) IPB.getBlock(ifB), (SequenceBlock) IPB.getBlock(whileB));
		
		try {
			GC.execute();
			throw new Exception("test invalid block failed");
		} catch (BlockColumnNotExecutableException e) {
			// TODO: handle exception
		}
	
	}
	
	@Test
	public void topLevelBlockIsCondition() throws Exception {
		setup();
		GC.addBlockToProgramArea( wallinfront);
		try {
			GC.execute();
		} catch (CantRunConditionException e) {
			// TODO: handle exception
		}
	}
	
	@Test
	public void disconnectBlocks() throws Exception {
		setup();
		GC.addBlockToProgramArea( whileB);
		GC.addBlockToProgramArea( not);
		GC.addBlockToProgramArea( forward);
		GC.addBlockToProgramArea( forward2);
		GC.addBlockToProgramArea( left);
		assertEquals(10, GC.getAmountOfBlocksLeft());
		
		GC.setBody((SurroundingBlock) IPB.getBlock(whileB), (SequenceBlock) IPB.getBlock(forward));
		GC.setCondition((SurroundingBlock) IPB.getBlock(whileB), (ConditionBlock) IPB.getBlock(not));
		GC.connect(IPB.getBlock(whileB), IPB.getBlock(forward2));
		GC.connect(IPB.getBlock(forward2), IPB.getBlock(left));
		
		GC.addBlockToProgramArea( ifB);
		PresentationBlock<SingleConditionBlock> isWall2 = IPB.makeSingleConditionBlock(new WallInFrontPredicate(), null);
		GC.addBlockToProgramArea( isWall2);
		GC.setCondition((SurroundingBlock) IPB.getBlock(ifB), (ConditionBlock) IPB.getBlock(isWall2));
		GC.setBody((SurroundingBlock) IPB.getBlock(ifB), (SequenceBlock) IPB.getBlock(whileB));
		
		GC.disconnect(IPB.getBlock(whileB));
		try {
			GC.execute();
		} catch (NotOneStartingBlockException e) {
			// TODO: handle exception
		}
		
		GC.removeBlockFromProgramArea( whileB);
		assertEquals(13, GC.getAmountOfBlocksLeft());
		GC.execute(); 
		assertEquals(IPB.getBlock(ifB), GC.getNextBlockToExecute());
	}
	
	@Test
	public void removeBeforeDisconnect() throws Exception {

		setup();
		GC.addBlockToProgramArea( whileB);
		GC.addBlockToProgramArea( not);
		GC.addBlockToProgramArea( forward);
		GC.addBlockToProgramArea( forward2);
		GC.addBlockToProgramArea( left);
		assertEquals(10, GC.getAmountOfBlocksLeft());
		
		GC.setBody((SurroundingBlock) IPB.getBlock(whileB), (SequenceBlock) IPB.getBlock(forward));
		GC.setCondition((SurroundingBlock) IPB.getBlock(whileB), (ConditionBlock) IPB.getBlock(not));
		GC.connect(IPB.getBlock(whileB), IPB.getBlock(forward2));
		GC.connect(IPB.getBlock(forward2), IPB.getBlock(left));
		
		GC.addBlockToProgramArea( ifB);
		PresentationBlock<SingleConditionBlock> isWall2 = IPB.makeSingleConditionBlock(new WallInFrontPredicate(), null);
		GC.addBlockToProgramArea( isWall2);
		GC.setCondition((SurroundingBlock) IPB.getBlock(ifB), (ConditionBlock) IPB.getBlock(isWall2));
		GC.setBody((SurroundingBlock) IPB.getBlock(ifB), (SequenceBlock) IPB.getBlock(whileB));
		
		GC.removeBlockFromProgramArea( whileB);
		assertEquals(13, GC.getAmountOfBlocksLeft());
		GC.execute(); 
		assertEquals(IPB.getBlock(ifB), GC.getNextBlockToExecute());
	}
	
}
