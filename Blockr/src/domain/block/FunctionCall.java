package domain.block;

import domain.GameController;

public class FunctionCall extends SequenceBlock{
	
	private int ID;
	private FunctionDefinition definition;
	

	protected FunctionCall(FunctionDefinition def){
		this.ID = def.ID;
		this.definition = def;
		this.definition.allCallers.add(this);
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
	
	protected void delete() {
		SequenceBlock prev = this.previous;
		SequenceBlock nextBlock = this.next;
		SurroundingBlock surr = this.surroundingBlock;
		FunctionDefinition funct = this.function;
		
		this.disconnect();

		if (nextBlock != null) {
			nextBlock.disconnect();
		}
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
		return new FunctionCall(definition);
	}

	@Override
	protected String getName() {
		return "Function: " + ID;
	}

}
