package domain.block;

import domain.GameController;
import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.block.block_types.*;

public class IfBlock extends SingleSurroundingBlock {
	
	

	public IfBlock() {
		super();
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
		if (this.getNextBlock() == null) {
			if (this.getSurroundingBlock() == null) {
				return null;
			}
			return this.getSurroundingBlock().getNextAfterLoop();
		}
		return this.getNextBlock();
	}

	@Override
	public SequenceBlock getNextAfterLoop() {
		if (this.getNextBlock() == null) {
			if (this.getSurroundingBlock() == null) {
				return null;
			}
			
			return this.getSurroundingBlock().getNextAfterLoop();
		}
		return this.getNextBlock();
	}


	
	@Override
	public Block getNewBlockOfThisType() {
		return new IfBlock();
	}


	@Override
	public String getName() {
		return "if";
	}

}
