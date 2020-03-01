package testsuites;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.List;
import java.sql.Array;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import domain.block.*;
import domain.block.block_types.*;
import domain.block.abstract_classes.*;

public class TestsLogicalBlocks {
	static ActionBlock startBlock = new MoveForward();
	static IfBlock ifBlock1 = new IfBlock();
	static WhileBlock whileBlock1 = new WhileBlock();
	static MoveForward mF1 = new MoveForward();
	static TurnRight tR1 = new TurnRight();
	static TurnLeft tL1 = new TurnLeft();
	static ConditionBlock not1 = new NotBlock();
	static ConditionBlock iswall1 = new WallInFront();

	@BeforeAll
	public static void init() {
		startBlock = new MoveForward();
		ifBlock1 = new IfBlock();
		whileBlock1 = new WhileBlock();
		mF1 = new MoveForward();
		tR1 = new TurnRight();
		tL1 = new TurnLeft();
		not1 = new NotBlock();
		iswall1 = new WallInFront();
		
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
		System.out.printf("____________________________________________________________" + "\n" + "test init" + "\n");
		init();
		Block[] desiredResult = { startBlock, whileBlock1, mF1, tL1, tR1, mF1, tL1, tR1 };
		Block current = startBlock;
		int i = 0;
		while (current != null && i < desiredResult.length) {
			assertEquals(desiredResult[i], current);
			current = current.execute();
			i += 1;
		}

	}

	@Test
	public void addEmptyIfFalse() throws Exception {
		System.out.printf("____________________________________________________________" + "\n" + "test if false" + "\n");
		init();
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
		System.out.printf("____________________________________________________________" + "\n" + "test if true" + "\n");
		init();
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

}
