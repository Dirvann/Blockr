package presentation.block;

import java.awt.Graphics;

import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.game_world.Vector;

public class SingleSurroundBlockPresentation extends PresentationBlock<SingleSurroundingBlock> {
	
	public SingleSurroundBlockPresentation(Vector pos, SingleSurroundingBlock block) {
		super(pos,block);
	}

	@Override
	public void draw(Graphics g) {
		
	}

}
