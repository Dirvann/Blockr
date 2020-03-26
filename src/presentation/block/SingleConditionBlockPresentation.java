package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import domain.block.SingleConditionBlock;
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
		g.fillRect(pos.getX() - getPlugHeight(), pos.getY() + getBlockHeight()/2 - getPlugWidth()/2, getPlugHeight(), getPlugWidth());
		g.setColor(Color.BLACK);
		g.setFont(getFont());
		g.drawString(getPresentationName(),pos.getX(), pos.getY() + (int)(getBlockHeight() * 0.8));
	}

//	@Override
//	public PresentationBlock<SingleConditionBlock> getNewBlockOfThisType() {
//		SingleConditionBlock block =  (SingleConditionBlock) getBlock().getNewBlockOfThisType();
//		SingleConditionBlockPresentation blockPresentation = new SingleConditionBlockPresentation(getPosition(), block);
//		block.setPresentationBlock(blockPresentation);
//		return blockPresentation;
//	}

	
	@Override
	protected Vector getNextBlockPosition(PresentationBlock<?> presentationBlock) {
		Vector pos = getPosition();
		return new Vector(pos.getX() + getBlockWidth(), pos.getY());
	}
	
	@Override
	public Vector getGivingSnapPoint() {
		Vector pos = getPosition();
		return new Vector(pos.getX(), pos.getY() + (int)(getBlockHeight()/2));
	}

	@Override
	public List<Vector> getReceivingSnapPoints() {
		return new ArrayList<Vector>();	
	}

}
