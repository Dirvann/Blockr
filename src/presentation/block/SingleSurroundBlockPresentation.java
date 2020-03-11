package presentation.block;

import java.awt.Graphics;

import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.game_world.Vector;

public class SingleSurroundBlockPresentation extends PresentationBlock {
	
	private SingleSurroundingBlock block;
	
	public SingleSurroundBlockPresentation(Vector pos, SingleSurroundingBlock block) {
		super(pos);
		this.setBlock(block);
	}

	@Override
	public void draw(Graphics g) {
		
	}

	public SingleSurroundingBlock getBlock() {
		return block;
	}

	public void setBlock(SingleSurroundingBlock block) {
		this.block = block;
	}

}
