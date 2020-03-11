package testsuites;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.GameController;
import domain.block.block_types.Block;
import domain.game_world.*;
import facade.*;

class TestGameController {
	static Implementation fi = new Implementation();
	
	static GameController gameController;
	static GameWorld gameWorld;
	static Block ifBlock;
	static Block forwardBlock;
	static Block notBlock;
	static Block turnLeftBlock;
	static Block turnRightBlock;
	static Block wallInFrontBlock;
	static Block whileBlock;
	
	public static void setup() {
		gameController = fi.makeGameController();
		gameWorld = fi.makeGameWorld();
		ifBlock = fi.makeIfBlock();
		forwardBlock = fi.makeMoveForwardBlock();
		forwardBlock = fi.makeMoveForwardBlock();
		notBlock = fi.makeNotBlock();
		turnLeftBlock = fi.makeTurnLeftBlock();
		turnRightBlock = fi.makeTurnRightBlock();
		wallInFrontBlock = fi.makeWallInFrontBlock();
		whileBlock = fi.makeWhileBlock();
	}
	
	@Test
	void test() {
		turnLeftBlock.execute(gameController);
		fi.connect(firstBlock, secondBlock)
		
		turnRightBlock
		fail("Not yet implemented");
	}
	
	
}
