package command;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.ImplementationBlock;
import presentation.block.PresentationBlock;

public class DeleteBlock implements Command{
	GameController GC;
	PresentationBlock<?> block;
	ImplementationGameController GCF = new ImplementationGameController();
	ImplementationBlock BF = new ImplementationBlock();
	
	/**
	 * 
	 * @param GC the gamecontroller where the block is deleted.
	 * @param block the block that gets deleted.
	 */
	public DeleteBlock(GameController GC, PresentationBlock<?> block) {
		this.GC = GC;
		this.block = block;
	}

	@Override
	public void execute() {
		GCF.removeBlockFromProgramArea(GC, block);
		
	}

	@Override
	public void undo() {
		GCF.addBlockToProgramArea(GC, block);
		
	}

}
