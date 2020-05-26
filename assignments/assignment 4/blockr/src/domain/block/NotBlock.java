package domain.block;

import game_world.api.FacadeGameWorld;
/**
 * An class of NotBlocks that extends ChainConditionBlock.
 * It has a specified evaluation and a name.
 * 
 * @version 4.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
class NotBlock extends ChainConditionBlock {
	
	/**
	 * Initialize an Not-block.
	 */
	protected NotBlock() {
		super();
	}

	@Override
	protected boolean evaluate(FacadeGameWorld iGameWorld) {
		if (!this.isValidCondition()) {
			return false;
		}
		return !this.next.evaluate(iGameWorld);
	}

	@Override
	protected NotBlock getNewBlockOfThisType() {
		return new NotBlock();
	}

	@Override
	protected String getName() {
		return "Not";
	}
}
