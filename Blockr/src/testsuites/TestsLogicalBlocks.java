package testsuites;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import domain.block.Block;
import domain.block.ConditionBlock;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import domain.block.SurroundingBlock;

public class TestsLogicalBlocks {
	private static ImplementationBlock BF = new ImplementationBlock();

	static SequenceBlock startBlock = BF.makeMoveForwardBlock();
	static SurroundingBlock ifBlock1 = BF.makeIfBlock();
	static SurroundingBlock whileBlock1 = BF.makeWhileBlock();
	static SequenceBlock mF1 = BF.makeMoveForwardBlock();
	static SequenceBlock mF2 = BF.makeMoveForwardBlock();
	static SequenceBlock tR1 = BF.makeTurnRightBlock();
	static SequenceBlock tL1 = BF.makeTurnLeftBlock();
	static ConditionBlock not1 = BF.makeNotBlock();
	static ConditionBlock iswall1 = BF.makeWallInFrontBlock();

	public static void clearAll() {
		startBlock = BF.makeMoveForwardBlock();
		ifBlock1 = BF.makeIfBlock();
		whileBlock1 = BF.makeWhileBlock();
		mF1 = BF.makeMoveForwardBlock();
		mF2 = BF.makeMoveForwardBlock();
		tR1 = BF.makeTurnRightBlock();
		tL1 = BF.makeTurnLeftBlock();
		not1 = BF.makeNotBlock();
		iswall1 = BF.makeWallInFrontBlock();
	}

//	
//	//General tests to see if execute, if and while works in a normal case
//
	public static void init1() {
		clearAll();

		// simple test: connect 2 actionblocks
		BF.connect(startBlock, whileBlock1);
		assertEquals(whileBlock1, BF.getNextBlock(startBlock));
		assertEquals(startBlock, BF.getPreviousBlock(whileBlock1));

		// simple test: squeeze actionBlock between 2 actionblocks
		BF.connect(mF1, tR1);
		BF.connect(mF1, tL1);
		assertEquals(tL1, BF.getNextBlock(mF1));
		assertEquals(tR1, BF.getNextBlock(tL1));
		assertEquals(mF1, BF.getPreviousBlock(tL1));
		assertEquals(tL1, BF.getPreviousBlock(tR1));

		// simple test: connect condition to while
		BF.setConditionBlock(whileBlock1, not1);
		BF.connect(not1, iswall1);
		assertEquals(not1, BF.getConditionBlock(whileBlock1));
		assertEquals(whileBlock1, BF.getSurroundingBlock(not1));
		assertEquals(whileBlock1, BF.getSurroundingBlock(iswall1));

		// Simple test: addBodyBlock
		BF.addBodyBlock(whileBlock1, mF1);
		assertEquals(mF1, BF.getBodyBlock(whileBlock1));
		assertEquals(whileBlock1, BF.getSurroundingBlock(tR1));
		/*
		 * moveForward() 
		 * while(not istWall())
		 * 	{	 moveForward() 
		 * 		turnLeft() 
		 * 		turnRight() 
		 * }
		 */
	}

	// initial code works -> before all && check execution
	@Test
	public void executeInit() throws Exception {
		System.out.printf(
				"\n test init \n" + "____________________________________________________________" + "\n" + "\n");
		init1();
		Block[] desiredResult = { startBlock, whileBlock1, mF1, tL1, tR1, whileBlock1, mF1, tL1, tR1 };
		Block current = startBlock;
		int i = 0;
		while (current != null && i < desiredResult.length) {
			assertEquals(desiredResult[i], current);
			current = BF.execute(current, null);
			i += 1;
		}

	}

	@Test
	public void addEmptyIfFalse() throws Exception {
		System.out.printf(
				"\n test if false \n" + "____________________________________________________________" + "\n" + "\n");
		init1();
		BF.connect(mF1, ifBlock1);
		BF.setConditionBlock(ifBlock1, iswall1);
		BF.addBodyBlock(ifBlock1, mF2);
		;

		Block[] desiredResult = { startBlock, whileBlock1, mF1, ifBlock1, tL1, tR1, whileBlock1, mF1, ifBlock1, tL1,
				tR1 };
		Block current = startBlock;
		int i = 0;
		while (current != null && i < desiredResult.length) {
			assertEquals(desiredResult[i], current);
			current = BF.execute(current, null);
			i += 1;
		}
		assertEquals(desiredResult.length, i);

	}

	@Test
	public void addEmptyIfTrue() throws Exception {
		System.out.printf(
				"\n test if true \n" + "____________________________________________________________" + "\n" + "\n");
		init1();
		BF.connect(mF1, ifBlock1);
		ConditionBlock not2 = BF.makeNotBlock();
		ConditionBlock isWall2 = BF.makeWallInFrontBlock();
		BF.connect(not2, isWall2);
		BF.setConditionBlock(ifBlock1, not2);

		Block[] desiredResult = { startBlock, whileBlock1, mF1, ifBlock1, tL1, tR1, whileBlock1, mF1, ifBlock1, tL1,
				tR1 };
		Block current = startBlock;
		int i = 0;
		while (current != null && i < desiredResult.length) {
			assertEquals(desiredResult[i], current);
			current = BF.execute(current, null);
			i += 1;
		}
		assertEquals(desiredResult.length, i);
	}

	// Testing add/remove for simple blocks

	@Test
	public void addRemoveSimpleBlock() {
		clearAll(); // remove all links of the blocks
		BF.connect(startBlock, ifBlock1);
		BF.connect(not1, iswall1);
		BF.setConditionBlock(ifBlock1, not1);
		BF.addBodyBlock(ifBlock1, mF1);
		BF.connect(tR1, tL1);
		BF.connect(ifBlock1, mF2);

		assertEquals(ifBlock1, BF.getNextBlock(startBlock));
		assertEquals(mF2, BF.getNextBlock(ifBlock1));
		assertEquals(mF1, BF.getBodyBlock(ifBlock1));
		try {
			assertEquals(mF2, BF.execute(mF1, null));
		} catch (Exception e) {
			assertNull(5);
		}
		assertEquals(tL1, BF.getNextBlock(tR1));

		BF.disconnect(BF.getNextBlock(mF1));

		assertNull(BF.getNextBlock(mF1));
		assertEquals(tL1, BF.getNextBlock(tR1));
		assertNull(BF.getSurroundingBlock(tR1)); // this is not in an if or while block anymore

		BF.disconnect(BF.getNextBlock(ifBlock1));
		BF.disconnect(BF.getNextBlock(startBlock));

	}
//	
//	@Test 
//	void previousAndNext() {
//		System.out.printf("\n test prev and next \n" + "____________________________________________________________" + "\n" + "\n");
//		init1();
//		assertNull( startBlock.getPreviousBlock()); 
//		assertEquals(whileBlock1, startBlock.getNextBlock());
//		
//		assertNull(whileBlock1.getNextBlock()); 
//		assertEquals(startBlock, whileBlock1.getPreviousBlock());
//		
//		assertNull(mF1.getPreviousBlock());
//		assertEquals(tL1, mF1.getNextBlock());
//
//		assertEquals(tR1, tL1.getNextBlock());
//		assertEquals(mF1, tL1.getPreviousBlock());
//		
//		mF1.removeNextBlock();
//		
//		assertNull(mF1.getNextBlock());
//		assertNull(tL1.getPreviousBlock());
//	}
//	
//	
//	@Test
//	void testIf() throws Exception {
//		System.out.printf("\n test if loop \n" + "____________________________________________________________" + "\n" + "\n");
//
//		init1();
//		/*
//		 * moveForward() 
//		 * while(not istWall()){ 
//		 * 		moveForward()
//		 * 		turnLeft() 
//		 * 		turnRight() 
//		 *  }
//		 */
//		
//		mF1.setNextBlock(ifBlock1);
//		ifBlock1.setBodyBlock(mF2);
//		ifBlock1.setConditionBlock(not1); //still not iswall
//		
//		Block[] desiredResult = {startBlock, whileBlock1, mF1, ifBlock1, mF2, tL1, tR1,  whileBlock1, mF1, ifBlock1, mF2, tL1, tR1};
//		Block current = startBlock;
//		int i = 0;
//		while (current != null && i < desiredResult.length) {
//			assertEquals(desiredResult[i], current);
//			current = current.execute(null);
//			
//			i += 1;
//		}
//		assertEquals(desiredResult.length, i);
//		
//	}

}