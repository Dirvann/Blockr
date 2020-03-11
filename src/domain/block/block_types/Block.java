package domain.block.block_types;

import domain.GameController;

public abstract class Block {

	/**
	 * 
	 * @return next block in sequence
	 * @ executes every necessary command in the block.
	 */
	public Block execute(GameController gameController) throws Exception{
		return null;
	}
}
