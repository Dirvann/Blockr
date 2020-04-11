package command;

import domain.GameController;
import domain.block.Block;
import domain.game_world.ImplementationGameWorld;

public class turnRightCommand extends ExecutionCommand{
	ImplementationGameWorld GWF = new ImplementationGameWorld();

	public turnRightCommand(Block previouslyExecuted, Block currentlyExecuted, Block nextToExecute, GameController GC) {
		super(previouslyExecuted, currentlyExecuted, nextToExecute, GC);

	}

	@Override
	public void undo() {
		GWF.robotTurnLeft(GCF.getGameWorld(GC));
		super.undo();
	}
}
