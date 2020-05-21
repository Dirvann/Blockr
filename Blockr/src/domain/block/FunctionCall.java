package domain.block;

import domain.GameController;

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


	@Override
	protected Block getNewBlockOfThisType() {
		return new FunctionCall(definition);
	}

	@Override
	protected String getName() {
		return "Function: " + ID;
	}

}
