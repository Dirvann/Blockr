package command;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.ImplementationBlock;
import presentation.block.PresentationBlock;

public class MakeBlock implements Command {

	GameController GC;
	PresentationBlock<?> block;
	ImplementationGameController GCF = new ImplementationGameController();
	ImplementationBlock BF = new ImplementationBlock();

	/**
	 * Makes a Command for the creation of a block. This holds the info to undo and
	 * redo this action.
	 * 
	 * @param blockToConnectTo block before group of blocks connected
	 * @param blockToConnect   first block of group of blocks that gets connected.
	 * @Post the objects GC and block will be stored in this Command for later use.
	 */
	public MakeBlock(GameController GC, PresentationBlock<?> block) {
		this.GC = GC;
		this.block = block;
	}

	@Override
	public void execute() {
		GCF.addBlockToProgramArea(GC, block);

	}

	@Override
	public void undo() {
		GCF.removeBlockFromProgramArea(GC, block);

	}

}
