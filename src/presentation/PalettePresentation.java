package presentation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import domain.block.block_types.Block;
import domain.game_world.Vector;
import presentation.block.PresentationBlock;

public class PalettePresentation {

	private List<PresentationBlock<Block>> paletteBlocks;

	
	public PalettePresentation() {
		paletteBlocks = new ArrayList<PresentationBlock<Block>>();
		// TODO: fill List with a single instance of each presentationblock
	}
	
	
	public PresentationBlock<Block> GetClickedPaletteBlock(Vector position) {
		for (PresentationBlock<Block> pBlock: paletteBlocks) {
			if (pBlock.collidesWithPosition(position)) {
				return pBlock;
			}
		}
		
		return null;
	}
	
	
	public void paint(Graphics g) {
		for (PresentationBlock<Block> pBlock: paletteBlocks) {
			pBlock.draw(g);
		}
	}
	
	
}
