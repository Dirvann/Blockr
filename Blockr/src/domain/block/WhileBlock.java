package domain.block;

import command.ExecutionCommand;
import domain.GameController;
import domain.ImplementationGameController;
import exceptions.domainExceptions.InfiniteLoopWhileException;
import exceptions.domainExceptions.NoConditionBlockException;
/**
 * An class of WhileBlocks that extends SingleSurroundingBlock.
 * It has a specified execution and a name.
 * 
 * @version 3.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
class WhileBlock extends SingleSurroundingBlock {
	
	/**
	 * Initialize a While-block.
	 */
	protected WhileBlock() {
		super();
	}

	@Override
	protected boolean hasValidExecutionColumn() {
		if (this.getBodyBlock() == null) return false;
		return super.hasValidExecutionColumn();
	}
	@Override
	protected Block execute(GameController GC) throws Exception {
		if (getConditionBlock() == null || !getConditionBlock().isValidCondition()) {
			throw new NoConditionBlockException();
		}
		if (getConditionBlock().evaluate(GC.getGameWorldImplementation())) {
			if (this.getBodyBlock() == null) throw new InfiniteLoopWhileException();
			
			GC.setExecutionCommand(new ExecutionCommand(null, null, null, GC));
			return this.getBodyBlock();
		}
		GC.setExecutionCommand(new ExecutionCommand(null, null, null, GC));
		
		
		return this.getNextToExecute();
	}

	@Override
	protected WhileBlock getNewBlockOfThisType() {
		return new WhileBlock();
	}

	@Override
	protected String getName() {
		return "While";
	}

	@Override
	protected SequenceBlock getNextAfterLoop() {
		return this;
	}

}
