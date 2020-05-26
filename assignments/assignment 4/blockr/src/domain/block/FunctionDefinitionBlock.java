package domain.block;

import java.util.ArrayList;
import java.util.List;
import domain.GameController;
/**
 * An class of FunctionDefinition that extends Block.
 * It has a specified ID, a callstack and a body.
 * 
 * @version 4.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class FunctionDefinitionBlock extends Block{
	protected int ID;
	protected ArrayList<SequenceBlock> callStack = new ArrayList<SequenceBlock>();
	protected SequenceBlock body;
	
	/**
	 * Initialize a Function Definition Block.
	 * 
	 * @param ID
	 * 		  The ID of this Definition.
	 * @post  The ID is equal to the given ID.
	 * 		  | new.ID = def.ID
	 */
	protected FunctionDefinitionBlock(int ID) {
		this.ID = ID;
	}

	/**
	 * Gets the next block to be executed after the function definition body has been executed.
	 * 
	 * @return the block that has to be executed after this function. | nextToExecute(lastElement(callstack))
	 * @post last element of callstack is removed.
	 */
	protected SequenceBlock getNextAfterFunction() {
		if (callStack.size() == 0) {
			return null;
		}
		return callStack.remove(callStack.size() - 1).getNextToExecute(); //returns and removes last element in callstack and gets the block after the caller
	}
	
	/**
	 * Prepares the function definition to be deleted.
	 * 
	 * @post All the callers will be removed from the program. The function will not be called again.
	 * 
	 */
	protected void removeFunctionDefinition(GameController GC) {
		List<FunctionCallBlock> allCallers = GC.getAllFunctionCallsOfID(this.ID);
		for (int i = 0; i < allCallers.size(); i++) {
			FunctionCallBlock caller = allCallers.get(i);
			caller.delete(GC);
		}
	}

	@Override
	protected Block execute(GameController GC) throws Exception {
		throw new Exception("A function definition can't be executed");
	}

	@Override
	protected Block getNewBlockOfThisType() {
		return new FunctionDefinitionBlock(this.ID);
		
	}

	@Override
	protected String getName() {
		return "Function Def: " + ID;
	}

	@Override
	protected boolean hasValidExecutionColumn() {
		if (this.body == null) return true;
		return this.body.hasValidExecutionColumn();
	}

	@Override
	protected Block getNextBlock() {
		return null;
	}

	@Override
	protected boolean setNextBlock(Block block) {
		return false;
	}

	@Override
	protected void removeNextBlock() {		
	}

	@Override
	protected Block getBlockAbove() {
		return null;
	}

	@Override
	protected Block getLastBlock() {
		return this;
	}

	@Override
	protected void disconnect() {		
	}
	
	/**
	 * Set the given block as first body block.
	 * 
	 * @param block 
	 * 		  Sets this block as first (of a sequence) under the statement.
	 * 
	 * @post connect(block.last, this.body) && this.body = block
	 */
	protected void setBodyBlock(SequenceBlock block) {
		if (block == null) {
			this.removeBodyBlock();
			return;
		}
		if (this.body != null) {
			SequenceBlock last = block;
			while (last.getNextBlock() != null) {
				last = last.getNextBlock();
			}
			last.setNextBlock(this.body);
		}
		this.body = block;
		block.setFunctionBlock(this);
		block.previous = null;
	}
	

	/**
	 * removes the body block
	 * 
	 * @post The body of this function is empty.
	 */
	public void removeBodyBlock() {
		if (this.body != null)
			this.body.setFunctionBlock(null);
		this.body = null;
		
	}

	/**
	 * Adds a caller of the function.
	 * @param functionCall
	 * @post functionCall is added to callStack
	 */
	public void addCaller(FunctionCallBlock functionCall) {
		this.callStack.add(functionCall);
		
	}
	
	@Override
	protected List<Block> getAllNextBlocks() {
		List<Block> allBlocks = super.getAllNextBlocks();
		if (this.body != null)
			allBlocks.addAll(this.body.getAllNextBlocks());
		return allBlocks;
	}

	/**
	 * Gets the first block of the body of this function.
	 * @return The first block of the body of this function.
	 */
	public SequenceBlock getBodyBlock() {
		return body;
	}

	@Override
	protected Block getPrevious() {
		return null;
	}

}
