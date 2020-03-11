package domain.block;

import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.block.block_types.Block;
import domain.block.block_types.SequenceBlock;

public class WhileBlock extends SingleSurroundingBlock {
	
	public WhileBlock() {
		
	}
	
	public Block execute() throws Exception {
		if (!this.hasValidCondition()) {
			throw new Exception("If-Block does not have a complete condition");
		}
		if (this.evaluateCondition()) {
			return this.getBodyBlock();
		}
		return this.getNextBlock();
	}
	
	public SequenceBlock getNextAfterLoop() {
		if (this.hasValidCondition()) {
			if (this.evaluateCondition()) {
				return this.getBodyBlock();
			}
			return this.getNextBlock();
			
		}
		return null;
	}

}
