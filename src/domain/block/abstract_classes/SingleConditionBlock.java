package domain.block.abstract_classes;

import domain.block.block_types.Block;
import domain.block.block_types.ConditionBlock;
import domain.block.block_types.SequenceBlock;
import presentation.ProgramAreaPresentation;
import presentation.block.PresentationBlock;
import presentation.block.SingleConditionBlockPresentation;

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
