package domain.block;

import domain.GameController;
/**
 * An class of FunctionCalls that extends SequenceBlock.
 * It has a specified ID and definition.
 * 
 * @version 4.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class FunctionCall extends SequenceBlock{
	
	private int ID;
	private FunctionDefinition definition;
	
	/**
	 * Initialize a Function Call Block. The definition of this block is 
	 * executed when this block is executed.
	 * 
	 * @param def
	 * 		  The definition connected to this FunctionCall.
	 * @post  The FunctionDefinition is equal to the given def.
	 * 		  | new.definition = def
	 * @post  The ID is equal to the ID of the given def.
	 * 		  | new.ID = def.ID
	 */
	protected FunctionCall(FunctionDefinition def){
		this.ID = def.ID;
		this.definition = def;
	}
	/**
	 * The definition executed when this FunctionCall is executed.
	 * 
	 * @return the definition connected to this FunctionCall.
	 */
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
