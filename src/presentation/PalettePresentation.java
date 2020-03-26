package presentation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import domain.block.*;
import domain.game_world.Vector;
import presentation.block.*;

public class PalettePresentation {

	private List<PresentationBlock> paletteBlocks;
	private ImplementationBlock BF = new ImplementationBlock();
	private ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();

	
	public PalettePresentation() {
		paletteBlocks = new ArrayList<PresentationBlock>();
		
		initialisePaletteBlocksList(paletteBlocks);
	}
	
	private void initialisePaletteBlocksList(List<PresentationBlock> list) {
		final int xOffset = 10;
		final int yOffset = 10;
		final int yOffsetIncrement = 60;
		
		// Move Forward
		list.add(BFP.makeMoveForwardBlock(new Vector(xOffset, yOffset)););
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
		WallInFront wall = new WallInFront();
		SingleConditionBlockPresentation wallPresentation = new SingleConditionBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*6), wall);
		wall.setPresentationBlock(wallPresentation);
		list.add(BFP.makeWallInFrontBlock(new Vector(xOffset, yOffset+yOffsetIncrement*6)));
	}
	
	
	public PresentationBlock GetClickedPaletteBlock(Vector position) {
		for (PresentationBlock pBlock: paletteBlocks) {
			if (pBlock.collidesWithPosition(position)) {
				return pBlock;
			}
		}
		
		return null;
	}
	
	
	public void paint(Graphics g) {
		for (PresentationBlock pBlock: paletteBlocks) {
			pBlock.draw(g);
		}
	}

	
}
