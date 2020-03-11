package domain.block;

import domain.GameController;
import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.block.block_types.*;

public class IfBlock extends SingleSurroundingBlock {

	public IfBlock() {
	}

	public Block execute(GameController gameController) throws Exception {
		if (getConditionBlock() == null || !getConditionBlock().isValidCondition()) {
			throw new Exception("If-Block does not have a complete condition");
		}
		if (this.getBodyBlock() != null) {
			if (getConditionBlock().evaluate(gameController)) {
				return this.getBodyBlock();
			}
		}
		return this.getNextBlock();
	}

}
