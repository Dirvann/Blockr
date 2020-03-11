package domain.block;

import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.block.block_types.Block;
import domain.block.block_types.SequenceBlock;
import domain.game_world.Vector;

public class WhileBlock extends SingleSurroundingBlock {
	
	public WhileBlock(Vector pos) {
		super(pos);
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
