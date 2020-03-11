package domain.block.abstract_classes;

import domain.GameController;
import domain.block.block_types.Block;
import domain.block.block_types.SequenceBlock;

public abstract class ActionBlock extends SequenceBlock{
	
	abstract public void performAction(GameController gameController);
	
	@Override
	public Block execute(GameController gameController) throws Exception {
		performAction(gameController);
		
		if (this.getNextBlock() == null) {
			if (this.getSurroundingBlock() == null) {
				return null; //TODO Misschien al een end_block toevoegen in Backend Programming
			}
			
			return this.getSurroundingBlock();
		}
		return this.getNextBlock();
	}
}
