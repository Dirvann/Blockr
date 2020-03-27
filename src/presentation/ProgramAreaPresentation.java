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
	
	private int blocksLeft = 15;
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
	
	public void addBlock(PresentationBlock<?> pBlock) {		
		Block block = BFP.getBlock(pBlock);
		programArea.addTopLevelBlock(block);
		blocksLeft -= BF.getAllNextBlocks(block).size();
	}
	
	public void removeBlock(PresentationBlock<?> pBlock) {
		Block block = BFP.getBlock(pBlock);
		BF.disconnect(BFP.getBlock(pBlock));
		blocksLeft += BF.getAllNextBlocks(block).size();
		
		try {
			programArea.removeTopLevelBlock(block);
			System.out.println("a top-level block is removed");
		} catch (Exception e) {
			System.out.println("a non top-level block is removed");
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
	

	public int getBlocksLeft() {
		return blocksLeft;
	}

	public void setBlocksLeft(int blocksLeft) {
		this.blocksLeft = blocksLeft;
	}
	
	public void increaseBlocksLeft() {
		blocksLeft += 1;
	}
	
	public void decreaseBlocksLeft() {
		blocksLeft -= 1;
	}
	
	
}
