package presentation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import domain.block.*;
import domain.block.block_types.*;
import domain.game_world.Vector;
import presentation.block.*;;

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
		list.add(new ActionBlockPresentation(new Vector(xOffset, yOffset), new MoveForward()));
		// Turn Left
		list.add(new ActionBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement), new TurnLeft()));
		// Turn Right
		list.add(new ActionBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*2), new TurnRight()));
		// If
		list.add(new SingleSurroundBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*3), new IfBlock()));
		// While
		list.add(new SingleSurroundBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*4), new WhileBlock()));
		// Not
		list.add(new ChainConditionBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*5), new NotBlock()));
		// Wall In Front
		list.add(new SingleConditionBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*6), new WallInFront()));
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
