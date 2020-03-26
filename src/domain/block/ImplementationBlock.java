package domain.block;

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
	public Block execute(Block block, GameController gameController) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Block getNextBlock(Block block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Block getPreviousBlock(Block block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConditionBlock getConditionBlock(SurroundingBlock block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SequenceBlock getBodyBlock(SurroundingBlock block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PresentationBlock<?> getPresentationBlock(Block block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPresentationBlock(Block block, PresentationBlock<?> presentationBlock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName(Block block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Block makeNewBlockOfThisType(Block block) {
		// TODO Auto-generated method stub
		return null;
	}



	
	
}