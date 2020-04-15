package command;

import domain.GameController;
import domain.block.Block;
import game_world.ImplementationGameWorld;

public class turnLeftCommand extends ExecutionCommand{
	ImplementationGameWorld GWF = new ImplementationGameWorld();

	public turnLeftCommand(Block previouslyExecuted, Block currentlyExecuted, Block nextToExecute, GameController GC) {
		super(previouslyExecuted, currentlyExecuted, nextToExecute, GC);
	}
	@Override
	public void undo() {
		GWF.robotTurnRight(GCF.getGameWorld(GC));
		super.undo();
	}
}
