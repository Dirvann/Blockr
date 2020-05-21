package domain.block;

import domain.GameController;
import domain.ImplementationGameController;
import domain.ProgramArea;

public class FunctionCall extends SequenceBlock{
	
	private int ID;
	private FunctionDefinition definition;
	

	protected FunctionCall(FunctionDefinition def){
		this.ID = def.ID;
		this.definition = def;
	}
	
	protected FunctionDefinition getFunctionDefinition() {
		return this.definition;
	}
	
	@Override
	protected Block execute(GameController GC) throws Exception {
		if (definition != null) {
			definition.addCaller(this);
			return definition.body;
		}
		return super.execute(GC);
	}
	
	protected void delete(ProgramArea programArea) {
		ImplementationGameController IGC = new ImplementationGameController();
		SequenceBlock prev = this.previous;
		SequenceBlock nextBlock = this.next;
		SurroundingBlock surr = this.surroundingBlock;
		FunctionDefinition funct = this.function;

		if (nextBlock != null) {
			IGC.disconnect(this.next, programArea);
		}
		
		IGC.disconnect(this, programArea);
		IGC.removeBlockFromProgramArea(programArea, this);
		if (prev == null) {
			if (surr == null) {
				if (funct != null) {
					funct.setBodyBlock(nextBlock);
				}
			}
			else {
				surr.setBodyBlock(nextBlock);
			}
		}
		else {
			prev.setNextBlock(nextBlock);
		}
		
	}
	


	@Override
	protected Block getNewBlockOfThisType() {
		FunctionCall copy = new FunctionCall(definition);
		definition.allCallers.add(copy);
		return copy;
	}

	@Override
	protected String getName() {
		return "Function: " + ID;
	}

}
