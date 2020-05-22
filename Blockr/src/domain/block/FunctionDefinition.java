package domain.block;

import java.util.ArrayList;
import java.util.List;

import domain.GameController;
import domain.ImplementationGameController;
import domain.ProgramArea;

public class FunctionDefinition extends Block{
	//the ID of the function
	protected int ID;
	//The history of calls
	protected ArrayList<SequenceBlock> callStack = new ArrayList<SequenceBlock>();
	//The body of the function
	protected SequenceBlock body;
	
	
	protected FunctionDefinition(int ID) {
		this.ID = ID;
	}

	protected SequenceBlock getNextAfterFunction() {
		if (callStack.size() == 0) {
			return null;
		}
		return callStack.remove(callStack.size() - 1).getNextToExecute(); //returns and removes last element in callstack and gets the block after the caller
	}
	
	/**
	 * Prepares the function definition to be deleted.
	 * @Post All the callers will be removed from the program. The function will not be called again.
	 * 
	 */
	protected void removeFunctionDefinition(ProgramArea programArea) {
		ImplementationGameController GCF = new ImplementationGameController();
		List<FunctionCall> allCallers = GCF.getAllFunctionCallsOfID(this.ID, programArea);
		for (int i = 0; i < allCallers.size(); i++) {
			FunctionCall caller = allCallers.get(i);
			caller.delete(programArea);
		}
	}

	@Override
	protected Block execute(GameController GC) throws Exception {
		throw new Exception("A function definition can't be executed");
	}

	@Override
	protected Block getNewBlockOfThisType() {
		return new FunctionDefinition(this.ID);
		
	}

	@Override
	protected String getName() {
		return "Definition function: " + ID;
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
	

	public void removeBodyBlock() {
		if (this.body != null)
			this.body.setFunctionBlock(null);
		this.body = null;
		
	}

	public void addCaller(FunctionCall functionCall) {
		this.callStack.add(functionCall);
		
	}
	
	@Override
	protected List<Block> getAllNextBlocks() {
		List<Block> allBlocks = super.getAllNextBlocks();
		if (this.body != null)
			allBlocks.addAll(this.body.getAllNextBlocks());
		return allBlocks;
	}

	public SequenceBlock getBodyBlock() {
		return body;
	}

	@Override
	protected Block getPrevious() {
		return null;
	}

}
