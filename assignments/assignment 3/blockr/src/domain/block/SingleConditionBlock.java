package domain.block;

import game_world.api.FacadeGameWorld;
import game_world.api.PredicateResult;
/**
 * An class of SingleConditionBlocks that extends ConditionBlock.
 * It has a specified evaluation and a name.
 * 
 * @version 3.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class SingleConditionBlock extends ConditionBlock {

	private String name;
	/**
	 * Initialize an Single Condition Block.
	 */
	public SingleConditionBlock(String name) {
		this.name = name;
	}

	@Override
	protected boolean isValidCondition() {
		return true;
	}

	@Override
	protected void removeNextBlock() {
		return;
	}

	@Override
	protected Block getNextBlock() {
		return null;
	}

	@Override
	protected boolean setNextBlock(Block block) {
		return false;
	}

	@Override
	protected boolean evaluate(FacadeGameWorld iGameWorld) {
		if (iGameWorld == null) {
			return false;
		} else {
			PredicateResult p = iGameWorld.evaluatePredicate(getName());
			if (p == PredicateResult.True) {
				return true;
			} else if(p == PredicateResult.False) {
				return false;
			} else if(p == PredicateResult.BadPredicate) {
				// TODO correct type of error
				throw new Error("bad predicate");
			}
			throw new Error("bad predicate");
			
		}

	}

	@Override
	protected Block getNewBlockOfThisType() {
		return new SingleConditionBlock(getName());
	}

	@Override
	protected String getName() {
		return this.name;
	}

}
