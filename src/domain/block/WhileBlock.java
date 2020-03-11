package domain.block;

import domain.GameController;
import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.block.block_types.Block;
import domain.game_world.Vector;

public class WhileBlock extends SingleSurroundingBlock {
	
	
	public WhileBlock(Vector pos) {
		super(pos);
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

}
