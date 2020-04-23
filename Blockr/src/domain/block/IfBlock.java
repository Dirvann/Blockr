package domain.block;

import command.ExecutionCommand;
import domain.GameController;
import domain.ImplementationGameController;
import exceptions.domainExceptions.NoConditionBlockException;
import game_world.ImplementationGameWorld;

class IfBlock extends SingleSurroundingBlock {
	
	

	protected IfBlock() {
		super();
	}

	@Override
	protected Block execute(ImplementationGameWorld iGameWorld) throws Exception {
		ImplementationGameController GCF = new ImplementationGameController();
		if (getConditionBlock() == null || !getConditionBlock().isValidCondition()) {
			throw new NoConditionBlockException();
		}
		if (this.getBodyBlock() != null) {
			if (getConditionBlock().evaluate(iGameWorld)) {
				//TODO impelemnt undo
				//GCF.setExecutionCommand(new ExecutionCommand(null, null, null, iGameWorld), iGameWorld);
				return this.getBodyBlock();
			}
		}
		if (this.getNextBlock() == null) {
			if (this.getSurroundingBlock() == null) {
				return null;
			}
			return this.getSurroundingBlock().getNextAfterLoop();
		}
		//GCF.setExecutionCommand(new ExecutionCommand(null, null, null, iGameWorld), iGameWorld);
		return this.getNextBlock();
	}

	@Override
	protected SequenceBlock getNextAfterLoop() {
		if (this.getNextBlock() == null) {
			if (this.getSurroundingBlock() == null) {
				return null;
			}
			
			return this.getSurroundingBlock().getNextAfterLoop();
		}
		return this.getNextBlock();
	}


	
	@Override
	protected IfBlock getNewBlockOfThisType() {
		return new IfBlock();
	}


	@Override
	protected String getName() {
		return "If";
	}

}
