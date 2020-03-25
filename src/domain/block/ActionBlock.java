package domain.block;

import domain.GameController;
import domain.game_world.Vector;
import presentation.ProgramAreaPresentation;
import presentation.block.ActionBlockPresentation;

abstract class ActionBlock extends SequenceBlock{
	
	abstract protected void performAction(GameController gameController);
	
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
	
	
	protected void removeFromProgramAreaPresentationRecursively(ProgramAreaPresentation programAreaP) {
		programAreaP.removeBlock(this.getPresentationBlock());
		programAreaP.increaseBlocksLeft();
		
		Block nextBlock = this.getNextBlock();
		
		if (nextBlock != null) {
			nextBlock.removeFromProgramAreaPresentationRecursively(programAreaP);
		}
		
	}
}
