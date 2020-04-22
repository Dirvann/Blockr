package domain.block;

import domain.GameController;
import game_world.ImplementationGameWorld;
import game_world.api.PredicateResult;

public class SingleConditionBlock extends ConditionBlock {

	private String name;

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
	protected boolean evaluate(ImplementationGameWorld iGameWorld) {
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
