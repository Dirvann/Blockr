package presentation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import domain.block.*;
import domain.game_world.Vector;
import presentation.block.*;
import presentation.block.specific.*;;

public class PalettePresentation {

	private List<PresentationBlock> paletteBlocks;

	
	public PalettePresentation() {
		paletteBlocks = new ArrayList<PresentationBlock>();
		
		initialisePaletteBlocksList(paletteBlocks);
	}
	
	private void initialisePaletteBlocksList(List<PresentationBlock> list) {
		final int xOffset = 10;
		final int yOffset = 10;
		final int yOffsetIncrement = 60;
		
		// Move Forward
		list.add(new MoveForwardPresentation(new Vector(xOffset, yOffset), new MoveForward()));
		// Turn Left
		list.add(new TurnLeftPresentation(new Vector(xOffset, yOffset+yOffsetIncrement), new TurnLeft()));
		// Turn Right
		list.add(new TurnRightPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*2), new TurnRight()));
		// If
		list.add(new IfBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*3), new IfBlock()));
		// While
		list.add(new WhileBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*4), new WhileBlock()));
		// Not
		list.add(new NotBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*5), new NotBlock()));
		// Wall In Front
		list.add(new WallInfrontPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*6), new WallInFront()));
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
