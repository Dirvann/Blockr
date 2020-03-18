package domain.block.abstract_classes;

import domain.GameController;
import domain.block.block_types.Block;
import domain.block.block_types.SequenceBlock;
import domain.game_world.Vector;
import presentation.ProgramAreaPresentation;
import presentation.block.ActionBlockPresentation;

public abstract class ActionBlock extends SequenceBlock{
	
	abstract public void performAction(GameController gameController);
	
	public ActionBlock() {
	}
	
	@Override
	public Block execute(GameController gameController) throws Exception {
		performAction(gameController);
		
		if (this.getNextBlock() == null) {
			if (this.getSurroundingBlock() == null) {
				return null;
			}
			
			return this.getSurroundingBlock().getNextAfterLoop();
		}
		return this.getNextBlock();
	}
	
	
	public void removeFromProgramAreaPresentationRecursively(ProgramAreaPresentation programAreaP) {
		programAreaP.removeBlock(this.getPresentationBlock());
		programAreaP.increaseBlocksLeft();
		
		Block nextBlock = this.getNextBlock();
		
		if (nextBlock != null) {
			nextBlock.removeFromProgramAreaPresentationRecursively(programAreaP);
		}
		
	}
}
