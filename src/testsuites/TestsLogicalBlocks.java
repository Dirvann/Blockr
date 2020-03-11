package testsuites;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import domain.block.*;
import domain.block.block_types.*;
import domain.block.abstract_classes.*;

public class TestsLogicalBlocks {
	static ActionBlock startBlock = new MoveForward();
	static IfBlock ifBlock1 = new IfBlock();
	static WhileBlock whileBlock1 = new WhileBlock();
	static MoveForward mF1 = new MoveForward();
	static MoveForward mF2 = new MoveForward();
	static TurnRight tR1 = new TurnRight();
	static TurnLeft tL1 = new TurnLeft();
	static ChainConditionBlock not1 = new NotBlock();
	static ConditionBlock iswall1 = new WallInFront();
	
	public static void clearAll() {

		startBlock = new MoveForward();
		ifBlock1 = new IfBlock();
		whileBlock1 = new WhileBlock();
		mF1 = new MoveForward();
		mF2 = new MoveForward();
		tR1 = new TurnRight();
		tL1 = new TurnLeft();
		not1 = new NotBlock();
		iswall1 = new WallInFront();
		
	}
	
	//General tests to see if exectute, if and while works in a normal case

	public static void init1() {
		clearAll();
		
		startBlock.setNextBlock(whileBlock1);
		mF1.setNextBlock(tR1);
		mF1.setNextBlock(tL1); // result mF1 - tL1 - tR1
		whileBlock1.setConditionBlock(not1);
		not1.setNextConditon(iswall1); // isWall returns false for now
		whileBlock1.setBodyBlock(mF1);
		/*
		 * moveForward() while(not istWall()){ moveForward() turnLeft() turnRight() }
		 */
	}

	// initial code works -> before all && check execution
	@Test
	public void executeInit() throws Exception {
		System.out.printf("\n test init \n" + "____________________________________________________________" + "\n" +  "\n");
		init1();
		Block[] desiredResult = { startBlock, whileBlock1, mF1, tL1, tR1, mF1, tL1, tR1 };
		Block current = startBlock;
		int i = 0;
		while (current != null && i < desiredResult.length) {
			assertEquals(desiredResult[i], current);
			current = current.execute();
			i += 1;
		}

	}

	//adding an empty while block will get filled with the blocks underneath it.
	@Test
	public void addEmptyIfFalse() throws Exception {
		System.out.printf("\n test if false \n" + "____________________________________________________________" + "\n" +  "\n");
		init1();
		mF1.setNextBlock(ifBlock1);
		ifBlock1.setConditionBlock(iswall1);
		
		Block[] desiredResult = {startBlock, whileBlock1, mF1, ifBlock1, mF1, ifBlock1};
		Block current = startBlock;
		int i = 0;
		while (current != null && i < desiredResult.length) {
			assertEquals(desiredResult[i], current);
			current = current.execute();
			i += 1;
		}		
		assertEquals(desiredResult.length, i);

	}
	
	@Test 
	public void addEmptyIfTrue() throws Exception {
		System.out.printf("\n test if true \n" + "____________________________________________________________" + "\n" + "\n");
		init1();
		mF1.setNextBlock(ifBlock1);
		ifBlock1.setConditionBlock(not1); //is linked to iswall1, see init()
		
		Block[] desiredResult = {startBlock, whileBlock1, mF1, ifBlock1,tL1, tR1,  mF1, ifBlock1,tL1, tR1};
		Block current = startBlock;
		int i = 0;
		while (current != null && i < desiredResult.length) {
			assertEquals(desiredResult[i], current);
			current = current.execute();
			i += 1;
		}
		assertEquals(desiredResult.length, i);
	}
	
	//Testing add/remove for simple blocks
	
	@Test
	public void addRemoveSimpleBlock() {
		clearAll(); // remove all links of the blocks
		startBlock.setNextBlock(ifBlock1);
		not1.setNextConditon(iswall1);
		ifBlock1.setConditionBlock(not1);
		ifBlock1.setBodyBlock(mF1);
		tR1.setNextBlock(tL1);
		mF1.setNextBlock(tR1);
		ifBlock1.setNextBlock(mF2);
		
		assertEquals(ifBlock1, startBlock.getNextBlock());
		assertEquals(mF2, ifBlock1.getNextBlock());
		assertEquals(mF1, ifBlock1.getBodyBlock());
		assertEquals(tR1, mF1.getNextBlock());
		assertEquals(tL1, tR1.getNextBlock());
		
		mF1.removeNextBlock();
		
		assertEquals(mF2, mF1.getNextBlock());
		assertEquals(tL1, tR1.getNextBlock());
		assertEquals(null, tR1.getSurroundingBlock()); // this is not in an if or while block anymore
		
		ifBlock1.removeNextBlock();
		startBlock.removeNextBlock();
		
		
	}

}