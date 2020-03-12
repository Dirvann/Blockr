package presentation.block;

import java.awt.Color;
import java.awt.Graphics;

import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.game_world.Vector;

public class SingleSurroundBlockPresentation extends PresentationBlock<SingleSurroundingBlock> {
	
	public SingleSurroundBlockPresentation(Vector pos, SingleSurroundingBlock block) {
		super(pos,block);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		Vector pos = getPosition();
		g.fillRect(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight());
	
	}

	@Override
	public boolean conditionCanSnap(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sequenceBlockCanSnap(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

}
