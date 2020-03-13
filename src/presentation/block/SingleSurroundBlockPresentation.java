package presentation.block;

import java.awt.Color;
import java.awt.Graphics;

import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.block.block_types.Block;
import domain.block.block_types.SequenceBlock;
import domain.game_world.Vector;

public class SingleSurroundBlockPresentation extends PresentationBlock<SingleSurroundingBlock> {

	public SingleSurroundBlockPresentation(Vector pos, SingleSurroundingBlock block) {
		super(pos, block);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		Vector pos = getPosition();
		g.fillRect(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight());
		g.setColor(Color.BLACK);
		g.setFont(getFont());
		g.drawString(getPresentationName(), pos.getX(), pos.getY() + (int) (getBlockHeight() * 0.8));
	}

	@Override
	public PresentationBlock<SingleSurroundingBlock> getNewBlockOfThisType() {
		SingleSurroundingBlock block = (SingleSurroundingBlock) getBlock().getNewBlockOfThisType();
		SingleSurroundBlockPresentation blockPresentation = new SingleSurroundBlockPresentation(getPosition(), block);
		block.setPresentationBlock(blockPresentation);
		return blockPresentation;
	}

	@Override
	public Vector getPossibleSnapLocation() {
		return new Vector(getPosition().getX() + Math.round(getBlockWidth()/2), getPosition().getY() - Math.round(getBlockHeight() / 2));
	}
	
	@Override
	public int getTotalHeight(){
		int totalHeight = 2 * getBlockHeight();
		SequenceBlock current = getBlock().getBodyBlock();
		while (current != null) {
			totalHeight += current.getPresentationBlock().getTotalHeight();
			current = current.getNextBlock();
		}
		return totalHeight;
	}
	@Override
	public boolean collisionWithLowerPart(Vector position) {
		int xValueLowerPart = getPosition().getX();
		int yValueLowerPart = getPosition().getY() + getTotalHeight() - getBlockHeight();
		if(position.getX() > this.getPosition().getX() && 
				position.getX() < (getBlockWidth() + xValueLowerPart) && 
				position.getY() > yValueLowerPart && 
				position.getY() < (yValueLowerPart + getBlockHeight())) {
			return true;
		}
		return false;
	}
}
