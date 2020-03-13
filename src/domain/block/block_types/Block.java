package domain.block.block_types;

import java.util.List;

import domain.GameController;
import presentation.block.PresentationBlock;

public abstract class Block {
	
	private PresentationBlock<?> presentationBlock;

	/**
	 * 
	 * @return next block in sequence
	 * @ executes every necessary command in the block.
	 */
	public Block execute(GameController gameController) throws Exception{
		return null;
	}
	
	public PresentationBlock<?> getPresentationBlock() {
		return this.presentationBlock;
	}
	
	public void setPresentationBlock(PresentationBlock<?> presentationBlock) {
		this.presentationBlock = presentationBlock;
	}
	
	public abstract Block getNewBlockOfThisType();

	public abstract List<Block> getAllNextBlocks();
	
	public abstract String getName();
}
