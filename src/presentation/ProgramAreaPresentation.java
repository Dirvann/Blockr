package presentation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import domain.game_world.Vector;
import presentation.block.PresentationBlock;

public class ProgramAreaPresentation {

	private List<PresentationBlock> programAreaBlocks;
	
	public ProgramAreaPresentation() {
		programAreaBlocks = new ArrayList<PresentationBlock>();
	}
	
	public void paint(Graphics g) {
		for (PresentationBlock pBlock: programAreaBlocks) {
			pBlock.draw(g);
		}
	}
	
	public void addBlock(PresentationBlock presentationCopy) {
		programAreaBlocks.add(presentationCopy);
	}
	
	public void removeBlock(PresentationBlock pBlock) {
		programAreaBlocks.remove(pBlock);
	}
	
	public PresentationBlock getBlockAtPosition(Vector position) {
		for (PresentationBlock pBlock: programAreaBlocks) {
			if (pBlock.collidesWithPosition(position)) {
				return pBlock;
			}
		}
		
		return null;
	}
	
	
}
