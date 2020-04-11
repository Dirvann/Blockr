package domain;

import java.util.ArrayList;
import command.ExecutionCommand;
import domain.game_world.ImplementationGameWorld;

public class ExecutionProcessor {

	ImplementationGameController GCF = new ImplementationGameController();
	int cursor;
	ArrayList<ExecutionCommand> undoStack = new ArrayList<>();
	ImplementationGameWorld GWF = new ImplementationGameWorld();
	int nbCommandsUndone;

	public void undo() {
		if (undoStack.size() > nbCommandsUndone)
			undoStack.get(undoStack.size() - ++nbCommandsUndone).undo();
	}

	public void redo() {
		if (nbCommandsUndone > 0)
			undoStack.get(undoStack.size() - nbCommandsUndone--).execute();
	}

	void execute(ExecutionCommand command) {
		for (; nbCommandsUndone > 0; nbCommandsUndone--)
			undoStack.remove(undoStack.size() - 1);
		undoStack.add(command);
		//already executed in game;
		// command.execute();
	}

	public void addExecutionStep(ExecutionCommand exeCmd) {
		if (exeCmd != null)
			this.execute(exeCmd);
	}
}
