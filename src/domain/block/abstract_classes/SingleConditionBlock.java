package domain.block.abstract_classes;

import domain.block.block_types.Block;
import domain.block.block_types.ConditionBlock;
import presentation.ProgramAreaPresentation;

public abstract class SingleConditionBlock extends ConditionBlock{
	
	public SingleConditionBlock() {

	}
	
	public boolean isValidCondition() {
		return true;
	}
	
	public void removeFromProgramAreaPresentationRecursively(ProgramAreaPresentation programAreaP) {
		programAreaP.removeBlock(getPresentationBlock());
		programAreaP.increaseBlocksLeft();
	}
	
	@Override
	public void connectTo(Block block) {
		return;
	}
	
}
