package presentation.block;

import java.awt.Color;
import java.awt.Graphics;

import domain.block.abstract_classes.ActionBlock;
import domain.game_world.Vector;

public class ActionBlockPresentation extends PresentationBlock<ActionBlock> {
	public ActionBlockPresentation(Vector pos, ActionBlock block) {
		super(pos, block);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		Vector pos = getPosition();
		g.fillRect(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight());
		g.setColor(Color.BLACK);
		g.setFont(getFont());
		g.drawString(getPresentationName(),pos.getX(), pos.getY() + (int)(getBlockHeight() * 0.8));
	}

	@Override
	public PresentationBlock<ActionBlock> getNewBlockOfThisType() {
		// TODO Auto-generated method stub
		ActionBlock block = (ActionBlock) getBlock().getNewBlockOfThisType();
		ActionBlockPresentation blockPresentation = new ActionBlockPresentation(getPosition(), block);
		
		
		block.setPresentationBlock(blockPresentation);
		if (block.getPresentationBlock() == null) {
			System.out.println("block.getPresentationBlock() == null in actionBlockPresentation");
		}
		return blockPresentation;
	}

	@Override
	public Vector getPossibleSnapLocation() {
		return new Vector(getPosition().getX() + Math.round(getBlockWidth()/2), getPosition().getY() - Math.round(getBlockHeight() / 2));
	}
	
	@Override
	protected Vector getNextBlockPosition(PresentationBlock<?> presentationBlock) {
		Vector pos = getPosition();
		return new Vector(pos.getX(), pos.getY() + PresentationBlock.getBlockHeight());
	}

}