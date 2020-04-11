package command;

import domain.Vector;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

public class DraggingCommand implements Command{
	
	ImplementationPresentationBlock BPF = new ImplementationPresentationBlock();
	Command preCommand;
	Command postCommand;
	Vector oldPos;
	Vector newPos;
	PresentationBlock<?> block;
	public DraggingCommand(Vector oldPos, Vector newPos, PresentationBlock<?> block, Command preCommand, Command postCommand) {
		this.preCommand = preCommand;
		this.postCommand = postCommand;
		this.oldPos = oldPos;
		this.newPos = newPos;
		this.block = block;
	}
	

	@Override
	public void execute() {
		if (preCommand != null) {
			preCommand.execute();
		}
		BPF.setPosition(block, newPos);
		if (postCommand != null) {
			postCommand.execute();
		}
		
		
	}

	@Override
	public void undo() {
		if (postCommand != null) {
			postCommand.undo();
		}
		BPF.setPosition(block, oldPos);
		if (preCommand != null) {
			preCommand.undo();
		}
		
	}

}
