package domain.block;

import domain.GameController;

public abstract class ActionBlock extends SequenceBlock{
	
	/**
	 * 
	 * @param gameController The gameController where the block is in.
	 * @throws Exception If action is not possible.
	 * @post The action of the block will be performed
	 */
	abstract protected void performAction(GameController gameController) throws Exception;
	
	protected ActionBlock() {
	}
	
	@Override
	protected Block execute(GameController gameController) throws Exception {
		performAction(gameController);
		
		if (this.getNextBlock() == null) {
			if (this.getSurroundingBlock() == null) {
				return null;
			}
			
			return this.getSurroundingBlock().getNextAfterLoop();
		}
		return this.getNextBlock();
	}
	
	@Override
	protected boolean hasValidExecutionColumn() {
		if (this.getNextBlock() != null) {
			return this.getNextBlock().hasValidExecutionColumn();
		} else {
			return true;
		}
	}
}
