package presentation.block;

import java.awt.Color;
import java.awt.Graphics;

import domain.block.abstract_classes.ChainConditionBlock;
import domain.game_world.Vector;

public class ChainConditionBlockPresentation extends PresentationBlock<ChainConditionBlock> {
	
	public ChainConditionBlockPresentation(Vector pos, ChainConditionBlock block) {
		super(pos, block);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		Vector pos = getPosition();
		g.fillRect(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight());
		g.setColor(Color.BLACK);
		g.setFont(getFont());
		g.drawString(getPresentationName(),pos.getX(), pos.getY() + (int)(getBlockHeight() * 0.8));
	}

	@Override
	public PresentationBlock<ChainConditionBlock> getNewBlockOfThisType() {
		return new ChainConditionBlockPresentation(getPosition(), (ChainConditionBlock) getBlock().getNewBlockOfThisType());
	}

	@Override
	public Vector getPossibleSnapLocation() {
		return new Vector(getPosition().getX(), getPosition().getY() + Math.round(getBlockHeight()/2));
	}
}
