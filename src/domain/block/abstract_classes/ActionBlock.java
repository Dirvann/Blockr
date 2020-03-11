package domain.block.abstract_classes;

import domain.GameController;
import domain.block.block_types.Block;
import domain.block.block_types.SequenceBlock;

public abstract class ActionBlock extends SequenceBlock{
	
	abstract public void performAction(GameController gameController);
	
	@Override
	public Block execute(GameController gameController) throws Exception {
		performAction(gameController);
		
		if (this.getNextBlock() == null) {
			if (this.getSurroundingBlock() == null) {
				return null; //TODO Misschien al een end_block toevoegen in Backend Programming
			}
			
			return this.getSurroundingBlock();
		}
		return this.getNextBlock();
	}
	
	/**
	 * 
	 * @param block The first block (of a group of blocks) which will be added
	 *              between this and this.getNextBlock(). The surrounding block is
	 *              adjusted in the added blocks. If the block to be added is an
	 *              empty surrounding block (like if and while), all sequence blocks
	 *              after 'block' will be added to the body of this surrounding
	 *              block. If the 'block' is a non-empty surrounding block, its will
	 *              be added between 'block' and 'block.next'.
	 * 
	 */
	public void setNextBlock(SequenceBlock block) {

		block.setSurroundingBlock(this.getSurroundingBlock());

		if (this.next == null) {
			this.next = block;// TODO: verify that there are no loops -> cant be in game
		} else {
			SequenceBlock last = block.getLastBlock();
			last.setNextBlock(this.next);
			this.next = block;

		}
	}
}
