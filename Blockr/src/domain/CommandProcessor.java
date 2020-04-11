package domain;

import java.util.ArrayList;

import command.Command;
import command.DraggingCommand;
import domain.block.ImplementationBlock;
import presentation.block.PresentationBlock;

public class CommandProcessor {
	GameController gameController;
	ImplementationBlock BF  = new ImplementationBlock();
	int cursor;
	ArrayList<Command> undoStack = new ArrayList<>();
	int nbCommandsUndone;

	public void undo() {
		if (undoStack.size() > nbCommandsUndone)
			undoStack.get(undoStack.size() - ++nbCommandsUndone).undo();
	}

	public void redo() {
		if (nbCommandsUndone > 0)
			undoStack.get(undoStack.size() - nbCommandsUndone--).execute();
	}

	void executed(Command command) {
		for (; nbCommandsUndone > 0; nbCommandsUndone--)
			undoStack.remove(undoStack.size() - 1);
		undoStack.add(command);
		//first execution happens in program itself, this is because dragging needs 3 commands (disconnect, drag and connect for example)
		//command.execute();
	}

	/**
	 * 
	 * @param firstBlock
	 * @param secondBlock
	 * @param lastOfSecondBlock
	 */
	public void dragCommand(Vector oldPos, Vector newPos, PresentationBlock<?> block, Command preCommand, Command postCommand) {
		
		executed(new DraggingCommand(oldPos, newPos, block, preCommand, postCommand));
	}
	

	void positionClicked(int position) {
		cursor = position;
	}
}
