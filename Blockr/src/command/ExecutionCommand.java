package command;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.Block;

public class ExecutionCommand {
	protected ImplementationGameController GCF = new ImplementationGameController();
	protected Block previouslyExecuted;
	protected Block currentlyExecuted;
	protected Block nextToExecute;
	protected GameController GC;

	
	public ExecutionCommand(Block previouslyExecuted, Block currentlyExecuted, Block nextToExecute, GameController GC) {
		this.previouslyExecuted = previouslyExecuted;
		this.currentlyExecuted = currentlyExecuted;
		this.nextToExecute = nextToExecute;
		this.GC = GC;

	}
	
	public void undo() {
		GCF.setNewExecution(previouslyExecuted, currentlyExecuted, GC);
	}
	public void execute() {
		try {
			GCF.execute(GC);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void setPrevious(Block block) {
		this.previouslyExecuted = block;
	}
	public void setCurrent(Block block) {
		this.currentlyExecuted = block;
	}
	public void setNext(Block block) {
		this.nextToExecute = block;
	}
}
