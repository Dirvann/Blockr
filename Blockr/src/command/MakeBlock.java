package command;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.ImplementationBlock;
import presentation.block.PresentationBlock;

public class MakeBlock implements Command{


	GameController GC;
	PresentationBlock<?> block;
	ImplementationGameController GCF = new ImplementationGameController();
	ImplementationBlock BF = new ImplementationBlock();
	
	/**
	 * 
	 * @param blockToConnectTo block before group of blocks connected
	 * @param blockToConnect first block of group of blocks that gets connected.
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
