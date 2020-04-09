package domain.block;

import domain.GameController;

public abstract class ActionBlock extends SequenceBlock{
	
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
}
