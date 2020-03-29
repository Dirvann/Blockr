package presentation;

import java.awt.Graphics;
import java.util.List;

import domain.ProgramArea;
import domain.block.Block;
import domain.block.ImplementationBlock;
import domain.game_world.Vector;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

public class ProgramAreaPresentation {
	
	private ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();
	private ImplementationBlock BF = new ImplementationBlock();
	private ProgramArea programArea;
	
	public ProgramAreaPresentation(ProgramArea programArea) {
		this.programArea = programArea;
	}
	
	public void paint(Graphics g) {
		List<Block> programAreaBlocks = programArea.getAllBlocks();
		for (Block pBlock: programAreaBlocks) {
			BFP.draw(g, BF.getPresentationBlock(pBlock));
		}
	}
	
	
	
	public PresentationBlock<?> getBlockAtPosition(Vector position) {
		for (Block block: programArea.getAllBlocks()) {
			if (BFP.collidesWithPosition(position, BF.getPresentationBlock(block))) {
				return BF.getPresentationBlock(block);
			}
		}
		
		return null;
	}
	
	public boolean snapBlock(PresentationBlock<?> block){
		
		for (Block blockListElement: programArea.getAllBlocks()) {
			if (BFP.snap(BF.getPresentationBlock(blockListElement), block)) {
				return true;
			}
		}
		return false;
	}
	

	
	
}
