package domain.block;

import domain.GameController;
import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.block.block_types.Block;

public class WhileBlock extends SingleSurroundingBlock {
	
	private final String displayName = "While";
	
	public WhileBlock() {
		super();
	}

	public Block execute(GameController gameController) throws Exception {
		if (getConditionBlock() == null || !getConditionBlock().isValidCondition()) {
			throw new Exception("While-Block does not have a complete condition");
		}
		if (getConditionBlock().evaluate(gameController)) {
			if (this.getBodyBlock() == null) throw new Exception("infinite loop in while");
			return this.getBodyBlock();
		}
		return this.getNextBlock();
	}

	@Override
	public Block getNewBlockOfThisType() {
		return new WhileBlock();
	}

}
