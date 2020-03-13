package domain.block.abstract_classes;

import domain.block.block_types.ConditionBlock;
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
	}
	
}
