package presentation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import domain.block.*;
import domain.game_world.Vector;
import presentation.block.*;

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
		MoveForward move = new MoveForward();
		ActionBlockPresentation movepresentation = new ActionBlockPresentation(new Vector(xOffset, yOffset), move);
		move.setPresentationBlock(movepresentation);
		list.add(movepresentation);
		// Turn Left
		TurnLeft turnLeft = new TurnLeft();
		ActionBlockPresentation turnLeftPresentation = new ActionBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement), turnLeft);
		turnLeft.setPresentationBlock(turnLeftPresentation);
		list.add(turnLeftPresentation);		
		// Turn Right
		TurnRight turnRight = new TurnRight();
		ActionBlockPresentation turnRightPresentation = new ActionBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*2),turnRight);
		turnRight.setPresentationBlock(turnRightPresentation);
		list.add(turnRightPresentation);
		// If
		IfBlock ifBlock = new IfBlock();
		SingleSurroundBlockPresentation ifBlockPresentation = new SingleSurroundBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*3), ifBlock);
		ifBlock.setPresentationBlock(ifBlockPresentation);
		list.add(ifBlockPresentation);
		// While
		WhileBlock whileBlock = new WhileBlock();
		SingleSurroundBlockPresentation whileBlockPresentaton = new SingleSurroundBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*4), whileBlock);
		whileBlock.setPresentationBlock(whileBlockPresentaton);
		list.add(whileBlockPresentaton);
		// Not
		NotBlock not = new NotBlock();
		ChainConditionBlockPresentation notPresentation = new ChainConditionBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*5), not);
		not.setPresentationBlock(notPresentation);
		list.add(notPresentation);
		// Wall In Front
		WallInFront wall = new WallInFront();
		SingleConditionBlockPresentation wallPresentation = new SingleConditionBlockPresentation(new Vector(xOffset, yOffset+yOffsetIncrement*6), wall);
		wall.setPresentationBlock(wallPresentation);
		list.add(wallPresentation);
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
