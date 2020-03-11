package domain.block;

import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.block.block_types.*;

public class IfBlock extends SingleSurroundingBlock {

	public IfBlock() {
	}

	public Block execute() throws Exception {
		if (!this.hasValidCondition()) {
			throw new Exception("If-Block does not have a complete condition");
		}
		if (this.getBodyBlock() != null) {
			if (this.evaluateCondition()) {
				return this.getBodyBlock();
			}
		}
		return this.getNextBlock();
	}

}
