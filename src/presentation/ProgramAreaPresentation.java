package presentation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import domain.block.block_types.Block;
import domain.game_world.Vector;
import presentation.block.PresentationBlock;

public class ProgramAreaPresentation {

	private List<PresentationBlock<Block>> programAreaBlocks;
	
	public ProgramAreaPresentation() {
		programAreaBlocks = new ArrayList<PresentationBlock<Block>>();
	}
	
	public void paint(Graphics g) {
		for (PresentationBlock<Block> pBlock: programAreaBlocks) {
			pBlock.draw(g);
		}
	}
	
	public void addBlock(PresentationBlock<Block> pBlock) {
		programAreaBlocks.add(pBlock);
	}
	
	public void removeBlock(PresentationBlock<Block> pBlock) {
		programAreaBlocks.remove(pBlock);
	}
	
	public PresentationBlock<Block> getClickedBlock(Vector position) {
		for (PresentationBlock<Block> pBlock: programAreaBlocks) {
			if (pBlock.collidesWithPosition(position)) {
				return pBlock;
			}
		}
		
		return null;
	}
	
	
	
}
