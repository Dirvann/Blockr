package domain.block;

class Implementation implements FacadeBlock{
	
	protected Implementation() {};

	@Override
	public Block makeIfBlock() {
		Block block = new IfBlock();
		return block;
	}

	@Override
	public Block makeMoveForwardBlock() {
		Block block = new MoveForward();
		return block;
	}

	@Override
	public Block makeNotBlock() {
		Block block = new NotBlock();
		return block;
	}

	@Override
	public Block makeTurnLeftBlock() {
		Block block = new TurnLeft();
		return block;
	}

	@Override
	public Block makeTurnRightBlock() {
		Block block = new TurnRight();
		return block;
	}

	@Override
	public Block makeWallInFrontBlock() {
		Block block = new WallInFront();
		return block;
	}

	@Override
	public Block makeWhileBlock() {
		Block block = new WhileBlock();
		return block;
	}

	@Override
	public boolean connect(Block firstBlock, Block secondBlock) {
		if (firstBlock instanceof SequenceBlock && secondBlock instanceof SequenceBlock) {
			((SequenceBlock)firstBlock).setNextBlock((SequenceBlock)secondBlock);
			return true;
		}
		else if (firstBlock instanceof ChainConditionBlock && secondBlock instanceof ConditionBlock) {
			((ChainConditionBlock)firstBlock).addCondition(((ConditionBlock)secondBlock));
			return true;
		}
		else if (firstBlock instanceof SurroundingBlock && secondBlock instanceof ConditionBlock) {
			((SurroundingBlock)firstBlock).setConditionBlock(((ConditionBlock)secondBlock));
			return true;
		}
		return false;
		
	}

	@Override
	public boolean disconnect(Block blockToDisconnect) {
		return blockToDisconnect.disconnect();
	}

	@Override
	public void deleteBlock(Block block) {
		disconnect(block);
		block = null;
		//TODO counter adding
		
	}


	@Override
	public boolean addBodyBlock(SurroundingBlock surroundingBlock, SequenceBlock block) {
		// TODO Auto-generated method stub
		return false;
	}


	
	
}