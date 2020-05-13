package domain.block;

import java.util.ArrayList;

import command.ExecutionCommand;
import domain.GameController;
import domain.ImplementationGameController;

public class FunctionDefinition extends Block{
	//the ID of the function
	protected int ID;
	//The history of calls
	private ArrayList<SequenceBlock> callStack = new ArrayList<SequenceBlock>();
	//The body of the function
	protected SequenceBlock body;
	
	
	protected FunctionDefinition(int ID) {
		this.ID = ID;
	}

	protected SequenceBlock getNextAfterFunction() {
		if (callStack.size() == 0) {
			return null;
		}
		return callStack.remove(callStack.size() - 1).next; //returns and removes last element in callstack and gets the block after the caller
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
	protected Block getPreviousBlock() {
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
		this.body.setFunctionBlock(null);;
		
	}

	public void addCaller(FunctionCall functionCall) {
		this.callStack.add(functionCall);
		
	}

}
