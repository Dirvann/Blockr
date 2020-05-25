package domain.block;

import command.ExecutionCommand;
import domain.GameController;
import exceptions.domainExceptions.NoConditionBlockException;
/**
 * An class of IfBlocks that extends SingleSurroundingBlock.
 * It has a specified execution and a name.
 * 
 * @version 3.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
class IfBlock extends SingleSurroundingBlock {
	/**
	 * Initialize an If-block.
	 */
	protected IfBlock() {
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
	protected IfBlock getNewBlockOfThisType() {
		return new IfBlock();
	}


	@Override
	protected String getName() {
		return "If";
	}

}
