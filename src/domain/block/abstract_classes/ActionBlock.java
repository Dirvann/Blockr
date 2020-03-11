package domain.block.abstract_classes;

import domain.block.block_types.Block;
import domain.block.block_types.SequenceBlock;

public abstract class ActionBlock extends SequenceBlock{
	
	public void performAction() {
		
	}
	
	public Block execute() throws Exception {
		this.performAction();
		if (this.getNextBlock() == null) {
			if (this.getSurroundingBlock() == null) {
				return null; //TODO Misschien al een end_block toevoegen in Backend Programming
				
			}
			
			return this.getSurroundingBlock().getNextAfterLoop();
		}
		return this.getNextBlock();
	}
}
