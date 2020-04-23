package presentation;

import java.awt.Graphics;
import java.util.List;

import command.Command;
import domain.GameController;
import domain.ImplementationGameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
import game_world.Vector;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

public class ProgramAreaPresentation {
	
	private ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();
	private ImplementationBlock BF = new ImplementationBlock();
	private ImplementationGameController GC = new ImplementationGameController();
	private GameController gameController;
	
	public ProgramAreaPresentation(GameController gameController) {
		this.gameController = gameController;
	}
	
	public void paint(Graphics g) {
		List<Block> programAreaBlocks = GC.getCopyOfAllBlocks(gameController);
		for (Block pBlock: programAreaBlocks) {
			BFP.draw(g, BF.getPresentationBlock(pBlock));
		}
	}
	
	
	
	public PresentationBlock<?> getBlockAtPosition(Vector position) {
		for (Block block:  GC.getCopyOfAllBlocks(gameController)) {
			if (BFP.collidesWithPosition(position, BF.getPresentationBlock(block))) {
				return BF.getPresentationBlock(block);
			}
		}
		
		return null;
	}
	
	public Command snapBlock(PresentationBlock<?> block){
		Command cmd = null;
		for (Block blockListElement:  GC.getCopyOfAllBlocks(gameController)) {
			PresentationBlock<?> pBlock = BF.getPresentationBlock(blockListElement);
			cmd = BFP.canSnap(pBlock, block, this.gameController);
			if (cmd != null) {
				return cmd;
			}
		}
		return cmd;
	}
	

	
	
}
