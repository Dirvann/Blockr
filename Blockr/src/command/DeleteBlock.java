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
