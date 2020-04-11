package presentation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import domain.Vector;
import presentation.block.*;

public class PalettePresentation {

	private List<PresentationBlock<?>> paletteBlocks;
	private ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();

	
	public PalettePresentation() {
		paletteBlocks = new ArrayList<PresentationBlock<?>>();
		
		initialisePaletteBlocksList(paletteBlocks);
	}
	
	private void initialisePaletteBlocksList(List<PresentationBlock<?>> list) {
		final int xOffset = 10;
		final int yOffset = 10;
		final int yOffsetIncrement = 60;
		
		// Move Forward
		list.add(BFP.makeMoveForwardBlock(new Vector(xOffset, yOffset)));
		// Turn Left
		list.add(BFP.makeTurnLeftBlock(new Vector(xOffset, yOffset+yOffsetIncrement)));		
		// Turn Right
		list.add(BFP.makeTurnRightBlock(new Vector(xOffset, yOffset+yOffsetIncrement*2)));
		// If
		list.add(BFP.makeIfBlock(new Vector(xOffset, yOffset+yOffsetIncrement*3)));
		// While
		list.add(BFP.makeWhileBlock(new Vector(xOffset, yOffset+yOffsetIncrement*4)));
		// Not
		list.add(BFP.makeNotBlock(new Vector(xOffset, yOffset+yOffsetIncrement*5)));
		// Wall In Front
		list.add(BFP.makeWallInFrontBlock(new Vector(xOffset, yOffset+yOffsetIncrement*6)));
	}
	
	
	public PresentationBlock<?> GetClickedPaletteBlock(Vector position) {
		for (PresentationBlock<?> pBlock: paletteBlocks) {
			if (BFP.collidesWithPosition(position, pBlock)) {
				return pBlock;
			}
		}
		
		return null;
	}
	
	
	public void paint(Graphics g) {
		for (PresentationBlock<?> pBlock: paletteBlocks) {
			BFP.draw(g, pBlock);;
		}
	}

	
}
