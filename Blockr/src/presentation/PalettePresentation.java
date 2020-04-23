package presentation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import game_world.ImplementationGameWorld;
import game_world.Vector;
import presentation.block.*;

public class PalettePresentation {

	private List<PresentationBlock<?>> paletteBlocks;
	private ImplementationPresentationBlock iPresentationBlock = new ImplementationPresentationBlock();

	
	public PalettePresentation() {
		paletteBlocks = new ArrayList<PresentationBlock<?>>();

		
		initialisePaletteBlocksList(paletteBlocks);
	}
	
	private void initialisePaletteBlocksList(List<PresentationBlock<?>> list) {
		final int xOffset = 10;
		final int yOffset = 10;
		final int yOffsetIncrement = 60;
		
		ImplementationGameWorld iGameWorld = new ImplementationGameWorld();
		
		List<String> actionList = iGameWorld.getAllActions();
		List<String> predicateList = iGameWorld.getAllPRedicates();
		
		int index = 0;
		for (int i = 0; i < actionList.size(); i++) {
			list.add(iPresentationBlock.makeActionBlock(actionList.get(i),new Vector(xOffset, yOffset + yOffsetIncrement*index)));
			index++;
		}
		for (int i = 0; i < predicateList.size(); i++) {
			list.add(iPresentationBlock.makeSingleConditionBlock(predicateList.get(i),new Vector(xOffset, yOffset + yOffsetIncrement*index)));
			index++;
		}
		
		
		list.add(iPresentationBlock.makeNotBlock(new Vector(xOffset, yOffset+yOffsetIncrement*index++)));
		
		list.add(iPresentationBlock.makeIfBlock(new Vector(xOffset, yOffset+yOffsetIncrement*index++)));
		// While
		list.add(iPresentationBlock.makeWhileBlock(new Vector(xOffset, yOffset+yOffsetIncrement*index++)));
		
		/*
		// Move Forward
		list.add(BFP.makeMoveForwardBlock(new Vector(xOffset, yOffset)));
		// Turn Left
		list.add(BFP.makeTurnLeftBlock(new Vector(xOffset, yOffset+yOffsetIncrement)));		
		// Turn Right
		list.add(BFP.makeTurnRightBlock(new Vector(xOffset, yOffset+yOffsetIncrement*2)));
		// If
		
		// Not
		
		// Wall In Front
		list.add(BFP.makeWallInFrontBlock(new Vector(xOffset, yOffset+yOffsetIncrement*6)));
		*/
	}
	
	
	public PresentationBlock<?> GetClickedPaletteBlock(Vector position) {
		for (PresentationBlock<?> pBlock: paletteBlocks) {
			if (iPresentationBlock.collidesWithPosition(position, pBlock)) {
				return pBlock;
			}
		}
		
		return null;
	}
	
	
	public void paint(Graphics g) {
		for (PresentationBlock<?> pBlock: paletteBlocks) {
			iPresentationBlock.draw(g, pBlock);;
		}
	}

	
}
