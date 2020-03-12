package presentation.block;

import java.awt.Color;
import java.awt.Graphics;

import domain.block.abstract_classes.SingleConditionBlock;
import domain.game_world.Vector;

public class SingleConditionBlockPresentation extends PresentationBlock<SingleConditionBlock>{

	public SingleConditionBlockPresentation(Vector pos, SingleConditionBlock block) {
		super(pos,block);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		Vector pos = getPosition();
		g.fillRect(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight());
	}
	

	@Override
	protected Vector getNextBlockPosition(PresentationBlock<?> presentationBlock) {
		Vector pos = getPosition();
		return new Vector(pos.getX() + PresentationBlock.getBlockWidth(), pos.getY());
	}
	
	@Override
	public int getHeight() {
		return getBlockHeight();
	}

	@Override
	public boolean conditionCanSnap(int x, int y) {
		return false;
	}

	@Override
	public boolean sequenceBlockCanSnap(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

}
