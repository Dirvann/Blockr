package testsuites;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import domain.block.Block;
import domain.block.ConditionBlock;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import domain.block.SurroundingBlock;
import exceptions.domainExceptions.InfiniteLoopWhileException;
import exceptions.domainExceptions.NoConditionBlockException;

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
	
	@Test
	void testSurroundingBlocks() throws Exception {
		clearAll();
		init1();

		SequenceBlock tL2 = BF.makeTurnLeftBlock();
		SequenceBlock tL3 = BF.makeTurnLeftBlock();
		BF.connect(tL2, tL3);
		BF.addBodyBlock(whileBlock1, tL2);
		assertEquals(tL2, BF.getBodyBlock(whileBlock1));
		assertEquals(whileBlock1, BF.getSurroundingBlock(tR1));
		assertEquals(whileBlock1, BF.getSurroundingBlock(tL2));
		assertEquals(whileBlock1, BF.getSurroundingBlock(mF1));
		assertTrue(BF.isValidStartingBlock(startBlock));
		BF.disconnect(mF1);

		assertEquals(null, BF.getNextBlock(tL3));
		assertEquals(null, BF.getSurroundingBlock(mF1));
		
		BF.disconnect(tL2);
		assertEquals(null, BF.getSurroundingBlock(tL2));
		assertEquals(null, BF.getBodyBlock(whileBlock1));
		//empty while -> false
		assertFalse(BF.isValidStartingBlock(startBlock));
		
		clearAll();
		
		BF.connect(startBlock, ifBlock1);
		BF.connect(ifBlock1, tL1);
		//this one doesnt do anything
		BF.connect(ifBlock1, iswall1);
		BF.setConditionBlock(ifBlock1, iswall1);
		BF.setConditionBlock(ifBlock1, not1);
		BF.addBodyBlock(ifBlock1, mF1);
		assertTrue(BF.isValidStartingBlock(startBlock));
		
		//test execute
		Block[] desiredResult = { startBlock, ifBlock1, mF1, tL1, null};
		Block current = startBlock;
		int i = 0;
		while (current != null && i < desiredResult.length) {
			assertEquals(desiredResult[i], current);
			current = BF.execute(current, null);
			i += 1;
		}
		//end execute
		
		
		BF.disconnect(mF1);
		assertTrue(BF.isValidStartingBlock(startBlock));
		List<Block> allBlocks = BF.getAllNextBlocks(startBlock);
		assertTrue(allBlocks.contains(startBlock));
		assertTrue(allBlocks.contains(ifBlock1));
		assertTrue(allBlocks.contains(iswall1));
		assertFalse(allBlocks.contains(mF1));
		assertTrue(allBlocks.contains(not1));
		
		//test execute
		Block[] desiredResult1 = { startBlock, ifBlock1, tL1, null};
		Block current1 = startBlock;
		int j = 0;
		while (current1 != null && j < desiredResult1.length) {
			assertEquals(desiredResult1[j], current1);
			current1 = BF.execute(current1, null);
			j += 1;
		}
		//end execute

		BF.disconnect(iswall1);
		assertFalse(BF.isValidStartingBlock(startBlock));
		try {
			BF.execute(ifBlock1, null);
			throw new Exception("ifBlock should not be able to execute");
		} catch (NoConditionBlockException e) {
		}
		BF.disconnect(not1);
		try {
			BF.execute(ifBlock1, null);
			throw new Exception("ifBlock should not be able to execute");
		} catch (NoConditionBlockException e) {
		}
		
		
		//clear all connections to try exceptions while
		clearAll();
		try {
			BF.execute(whileBlock1, null);
			throw new Exception("whileBlock should not be able to execute");
		} catch (NoConditionBlockException e) {
		}
		BF.setConditionBlock(whileBlock1, not1);
		try {
			BF.execute(whileBlock1, null);
			throw new Exception("whileBlock should not be able to execute");
		} catch (NoConditionBlockException e) {
		}
		BF.connect(not1, iswall1);
		try {
			BF.execute(whileBlock1, null);
			throw new Exception("whileBlock should not be able to execute");
		} catch (InfiniteLoopWhileException e) {
		}
		BF.addBodyBlock(whileBlock1, mF1);
		assertEquals(mF1, BF.execute(whileBlock1, null));
		BF.disconnect(iswall1);
		BF.setConditionBlock(whileBlock1, iswall1);
		
		assertNull(BF.execute(whileBlock1, null));
		
		clearAll();
		try {
			BF.execute(ifBlock1, null);
			throw new Exception("ifBlock should not be able to execute");
		} catch (NoConditionBlockException e) {
		}
		BF.setConditionBlock(ifBlock1, not1);
		try {
			BF.execute(ifBlock1, null);
			throw new Exception("ifBlock should not be able to execute");
		} catch (NoConditionBlockException e) {
		}
		BF.connect(not1, iswall1);
		BF.connect(not1, BF.makeNotBlock());
		assertEquals(null, BF.execute(ifBlock1, null));
	}
	
	@Test
	void testName() {
		assertEquals("Turn Left", BF.getName(tL1));
		assertEquals("Turn Right", BF.getName(tR1));
		assertEquals("Move Forward", BF.getName(mF1));
		assertEquals("If", BF.getName(ifBlock1));
		assertEquals("While", BF.getName(whileBlock1));
		assertEquals("Not", BF.getName(not1));
		assertEquals("Wall In Front", BF.getName(iswall1));
	}


}