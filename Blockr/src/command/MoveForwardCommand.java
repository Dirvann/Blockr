package command;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.Block;
import game_world.ImplementationGameWorld;

public class MoveForwardCommand extends ExecutionCommand {
	ImplementationGameController GCF = new ImplementationGameController();
	ImplementationGameWorld GWF = new ImplementationGameWorld();

	/**
	 * 
	 * @param previouslyExecuted The block that has been executed just before.
	 * @param currentlyExecuted The block that is currently being executed.
	 * @param nextToExecute The next block to execute.
	 * @param GC The gameController where the blocks are stored in.
	 */
	public MoveForwardCommand(Block previouslyExecuted, Block currentlyExecuted, Block nextToExecute, GameController GC) {
		super(previouslyExecuted, currentlyExecuted, nextToExecute, GC);

	}

	@Override
	public void undo() {
		GWF.robotTurnLeft(GCF.getGameWorld(GC));
		GWF.robotTurnLeft(GCF.getGameWorld(GC));
		try {
			GWF.robotStepForwards(GCF.getGameWorld(GC));
		} catch (Exception e) {
			// TODO: handle exception
		}
		GWF.robotTurnLeft(GCF.getGameWorld(GC));
		GWF.robotTurnLeft(GCF.getGameWorld(GC));

		super.undo();
	}

}
