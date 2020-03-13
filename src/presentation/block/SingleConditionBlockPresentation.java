package presentation.block;

import java.awt.Color;
import java.awt.Graphics;

import domain.block.abstract_classes.SingleConditionBlock;
import domain.game_world.Vector;

public class SingleConditionBlockPresentation extends PresentationBlock<SingleConditionBlock> {
	
	public SingleConditionBlockPresentation(Vector pos, SingleConditionBlock block) {
		super(pos, block);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		Vector pos = getPosition();
		g.fillRect(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight());
		g.setColor(Color.BLACK);
		g.setFont(getFont());
		g.drawString(getPresentationName(),pos.getX(), pos.getY() + (int)(getBlockHeight() * 0.8));
	}

	@Override
	public PresentationBlock<SingleConditionBlock> getNewBlockOfThisType() {
		return new SingleConditionBlockPresentation(getPosition(), (SingleConditionBlock) getBlock().getNewBlockOfThisType());
	}

	@Override
	public Vector getPossibleSnapLocation() {
		return new Vector(getPosition().getX(), getPosition().getY() + Math.round(getBlockHeight()/2));
	}

}
