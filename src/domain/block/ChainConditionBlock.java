package domain.block;

import presentation.ProgramAreaPresentation;

abstract class ChainConditionBlock extends ConditionBlock{
	
	protected ChainConditionBlock() {

	}
	
	/**
	 * 
	 * @param block The block which gets connected to the current one.
	 */
	protected void addCondition(ConditionBlock block) {
		block.setPrevious(this);
		
		ConditionBlock last = block;
		while(last.getNextCondition() != null) {
			last = last.getNextCondition();
		}
		last.setNextCondition(getNextCondition());
		if (getNextCondition() != null) getNextCondition().setPrevious(last);
		setNextCondition(block);
	}
	
	protected void removeNextCondition() {
		if (getNextCondition() != null) {
			getNextCondition().setPrevious(null);
			setNextCondition(null);
		}
	}

	protected boolean isValidCondition() {
		if (getNextCondition() != null)
			return getNextCondition().isValidCondition();
		return false;
	}
	

	
	protected void removeFromProgramAreaPresentationRecursively(ProgramAreaPresentation programAreaP) {
		programAreaP.removeBlock(getPresentationBlock());
		programAreaP.increaseBlocksLeft();
		
		Block connectedCondition = this.getNextCondition();
		if (connectedCondition != null) {
			connectedCondition.removeFromProgramAreaPresentationRecursively(programAreaP);
		}
	}
	
	@Override
	protected void connectTo(Block block) {
		if(!(block instanceof ConditionBlock)) return;
		ConditionBlock b = (ConditionBlock) block;
		if(next != null) {
			ConditionBlock last = b.getLastBlock();
			if(last instanceof ChainConditionBlock) {
				last.setNextCondition(next);
			} else if(last instanceof SingleConditionBlock) {
				next.setPrevious(null);
			}
		}
		setNextCondition(b);
	}

}
