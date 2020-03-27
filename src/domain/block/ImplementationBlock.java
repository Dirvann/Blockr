package domain.block;

import java.util.List;

import domain.GameController;
import presentation.block.PresentationBlock;

public class ImplementationBlock implements FacadeBlock{

	@Override
	public SingleSurroundingBlock makeIfBlock() {
		return new IfBlock();
	}

	@Override
	public ActionBlock makeMoveForwardBlock() {
		return new MoveForward();
	}

	@Override
	public ChainConditionBlock makeNotBlock() {
		return new NotBlock();
	}

	@Override
	public ActionBlock makeTurnLeftBlock() {
		return new TurnLeft();
	}

	@Override
	public ActionBlock makeTurnRightBlock() {
		return new TurnRight();
	}

	@Override
	public SingleConditionBlock makeWallInFrontBlock() {
		return new WallInFront();
	}

	@Override
	public SingleSurroundingBlock makeWhileBlock() {
		return new WhileBlock();
	}

	@Override
	public boolean connect(Block firstBlock, Block secondBlock) {
		return firstBlock.setNextBlock(secondBlock);
		
	}

	@Override
	public void disconnect(Block blockToDisconnect) {
		blockToDisconnect.disconnect();
	}


	@Override
	public void addBodyBlock(SurroundingBlock surroundingBlock, SequenceBlock block) {
		surroundingBlock.setBodyBlock(block);
	}

	@Override
	public void setConditionBlock(SurroundingBlock surroundingBlock, ConditionBlock conditionBlock) {
		surroundingBlock.setConditionBlock(conditionBlock);
		
	}

	@Override
	public boolean isValidStartingBlock(Block block) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Block execute(Block block, GameController gameController) throws Exception {
		return block.execute(gameController);
	}

	@Override
	public Block getNextBlock(Block block) {
		return block.getNextBlock();
	}

	@Override
	public Block getPreviousBlock(Block block) {
		return block.getPreviousBlock();
	}

	@Override
	public ConditionBlock getConditionBlock(SurroundingBlock block) {
		return block.getConditionBlock();
	}

	@Override
	public SequenceBlock getBodyBlock(SurroundingBlock block) {
		return block.getBodyBlock();
	}

	@Override
	public PresentationBlock<?> getPresentationBlock(Block block) {
		return block.getPresentationBlock();
	}

	@Override
	public void setPresentationBlock(Block block, PresentationBlock<?> presentationBlock) {
		block.setPresentationBlock(presentationBlock);		
	}

	@Override
	public String getName(Block block) {
		return block.getName();
	}

	@Override
	public Block makeNewBlockOfThisType(Block block) {
		return block.getNewBlockOfThisType();
	}

	@Override
	public List<Block> getAllNextBlocks(Block block) {
		return block.getAllNextBlocks();
	}



	
	
}