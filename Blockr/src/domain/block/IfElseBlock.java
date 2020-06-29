package domain.block;

import command.ExecutionCommand;
import domain.GameController;
import exceptions.domainExceptions.NoConditionBlockException;

public class IfElseBlock extends SurroundingBlock{
	protected IfElseBlock() {
		super();
	}

	@Override
	protected Block execute(GameController GC) throws Exception {
		if (getConditionBlock() == null || !getConditionBlock().isValidCondition()) {
			throw new NoConditionBlockException();
		}
		if (this.getBodyBlock() != null) {
			if (getConditionBlock().evaluate(GC.getGameWorldImplementation())) {
				GC.setExecutionCommand(new ExecutionCommand(null, null, null, GC));
				return this.getBodyBlock();
			}
			else {
				GC.setExecutionCommand(new ExecutionCommand(null, null, null, GC));
				return this.getLowerBodyBlock();
			}
		}
		if (this.getNextBlock() == null) {
			if (this.getSurroundingBlock() == null) {
				return null;
			}
			return this.getSurroundingBlock().getNextAfterLoop();
		}
		GC.setExecutionCommand(new ExecutionCommand(null, null, null, GC));
		return this.getNextBlock();
	}

	@Override
	protected SequenceBlock getNextAfterLoop() {
		return this.getNextToExecute();
	}

	@Override
	protected IfElseBlock getNewBlockOfThisType() {
		return new IfElseBlock();
	}


	@Override
	protected String getName() {
		return "If/Else";
	}
	
	//NEW
	protected SequenceBlock lowerBodyBlock = null;

	protected void setLowerBodyBlock(SequenceBlock block) {
		if (this.lowerBodyBlock != null) {
			SequenceBlock last = block;
			while (last.getNextBlock() != null) {
				last = last.getNextBlock();
			}
			last.setNextBlock(this.bodyBlock);
		}
		this.lowerBodyBlock = block;
		if (block != null) {
			block.setSurroundingBlock(this);
			block.setFunctionBlock(this.function);
			block.previous = null;
		}
	}

	protected SequenceBlock getLowerBodyBlock() {
		SequenceBlock copy = this.lowerBodyBlock; // TODO: decent copy
		return copy;
	}

	protected void removeBodyBlock() {
		if (this.bodyBlock != null) {
			this.bodyBlock.setSurroundingBlock(null);
			this.bodyBlock.setFunctionBlock(null);
			this.bodyBlock = null;
		}
	}

	public void removeLowerBodyBlock() {
		if (this.lowerBodyBlock != null) {
			this.lowerBodyBlock.setSurroundingBlock(null);
			this.lowerBodyBlock.setFunctionBlock(null);
			this.lowerBodyBlock = null;
		}
	}
}
