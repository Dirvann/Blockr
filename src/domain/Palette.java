package domain;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import domain.block.IfBlock;
import domain.block.MoveForward;
import domain.block.NotBlock;
import domain.block.TurnLeft;
import domain.block.TurnRight;
import domain.block.WallInFront;
import domain.block.WhileBlock;
import domain.block.block_types.Block;
import domain.game_world.Vector;

public class Palette {

	private List<Block> paletteBlocks;

	
	public Palette() {
		paletteBlocks = new ArrayList<Block>();
		
		initialisePaletteBlocksList(paletteBlocks);
	}
	
	private void initialisePaletteBlocksList(List<Block> list) {
		final int xOffset = 10;
		final int yOffset = 10;
		final int yOffsetIncrement = 60;
		
		// Move Forward
		list.add(new MoveForward(new Vector(xOffset,yOffset)));
		// Turn Left
		list.add(new TurnLeft(new Vector(xOffset, yOffset+yOffsetIncrement)));
		// Turn Right
		list.add(new TurnRight(new Vector(xOffset, yOffset+yOffsetIncrement*2)));
		// If
		list.add(new IfBlock(new Vector(xOffset, yOffset+yOffsetIncrement*3)));
		// While
		list.add(new WhileBlock(new Vector(xOffset, yOffset+yOffsetIncrement*4)));
		// Not
		list.add(new NotBlock(new Vector(xOffset, yOffset+yOffsetIncrement*5)));
		// Wall In Front
		list.add(new WallInFront(new Vector(xOffset, yOffset+yOffsetIncrement*6)));
	}
	
	
	public Block GetClickedPaletteBlock(Vector position) {
		for (Block block: paletteBlocks) {
			if (block.getPresentationBlock().collidesWithPosition(position)) {
				return block;
			}
		}
		
		return null;
	}
	
	
	public void paint(Graphics g) {
		for (Block block: paletteBlocks) {
			block.getPresentationBlock().draw(g);
		}
	}

	
}
