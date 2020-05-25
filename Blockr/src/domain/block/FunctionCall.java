package domain.block;

import domain.GameController;
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
	
	protected void delete(GameController GC) {
		SequenceBlock prev = this.previous;
		SequenceBlock nextBlock = this.next;
		SurroundingBlock surr = this.surroundingBlock;
		FunctionDefinition funct = this.function;

		if (nextBlock != null) {
			GC.disconnect(this.next);
		}
		
		GC.disconnect(this);
		GC.removeBlockFromProgramArea(this.getPresentationBlock());
		if (prev == null) {
			if (surr == null) {
				if (funct != null) {
					GC.setBody(funct, nextBlock);
				}
			}
			else {
				GC.setBody(surr, nextBlock);
			}
		}
		else {
			GC.connect(prev, nextBlock);
		}
		
	}
	


	@Override
	protected Block getNewBlockOfThisType() {
		FunctionCall copy = new FunctionCall(definition);
		return copy;
	}

	@Override
	protected String getName() {
		return "Function: " + ID;
	}

}
