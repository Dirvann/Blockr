package domain.block;

import java.util.List;

import domain.GameController;
import presentation.ProgramAreaPresentation;
import presentation.block.PresentationBlock;

abstract class Block {
	
	protected PresentationBlock<?> presentationBlock;

	/**
	 * 
	 * @return next block in sequence
	 * @ executes every necessary command in the block.
	 */
	protected Block execute(GameController gameController) throws Exception{
		return null;
	}
	
	protected PresentationBlock<?> getPresentationBlock() {
		return this.presentationBlock;
	}
	
	protected void setPresentationBlock(PresentationBlock<?> presentationBlock) {
		this.presentationBlock = presentationBlock;
	}
	
	protected abstract void removeFromProgramAreaPresentationRecursively(ProgramAreaPresentation programAreaP);
	
	protected abstract Block getNewBlockOfThisType();

	protected abstract List<Block> getAllNextBlocks();
	
	protected abstract String getName();
	

	protected abstract Block getPreviousBlock();
	
	protected abstract boolean disconnect();
	
	protected abstract void connectTo(Block b);
	
	protected abstract Block getLastBlock();
}
