package domain.block;

import domain.GameController;
import game_world.api.FacadeGameWorld;
/**
 * An abstract class of ConditionBlocks that extends Block and has a previous block.
 * 
 * @version 3.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public abstract class ConditionBlock extends Block {

	protected ChainConditionBlock previous = null;

	/**
	 * 
	 * @return true if it would be possible to evaluate.
	 */
	abstract protected boolean isValidCondition();

	/**
	 * 
	 * @param iGameWorld
	 * @return true if the condition is true;
	 */
	abstract protected boolean evaluate(FacadeGameWorld iGameWorld);

	@Override
	protected void disconnect() {
		Block prev = this.getPreviousBlock();
		if (prev instanceof SurroundingBlock) {
			((SurroundingBlock) prev).removeConditionBlock();
			return;
		}
		if (prev instanceof ChainConditionBlock) {
			((ChainConditionBlock) prev).removeNextBlock();
		}
	}

	@Override
	protected Block getPreviousBlock() {
		if (previous != null) {
			return previous;
		} else if (surroundingBlock != null) {
			return surroundingBlock;
		}
		return null;
	}

	@Override
	protected ConditionBlock getLastBlock() {
		return this;
	}
	
	@Override
	protected Block execute(GameController gameController) {
		return null;
	}
	
	@Override
	protected boolean hasValidExecutionColumn() {
		// ConditionBlocks can't be executed
		return false;
	}
	
}
