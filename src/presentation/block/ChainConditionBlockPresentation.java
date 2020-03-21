package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.Arrays;
import java.util.List;

import domain.block.abstract_classes.ChainConditionBlock;
import domain.block.block_types.ConditionBlock;
import domain.game_world.Vector;

public class ChainConditionBlockPresentation extends PresentationBlock<ChainConditionBlock> {
	
	public ChainConditionBlockPresentation(Vector pos, ChainConditionBlock block) {
		super(pos, block);
	}

	@Override
	public void draw(Graphics gr) {
		
		Graphics2D g = (Graphics2D)gr;
		g.setColor(Color.RED);
		Vector pos = getPosition();
		Area a = new Area(new Rectangle(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight()));
		a.subtract(new Area(new Rectangle(pos.getX() + getBlockWidth() - getPlugHeight(), pos.getY() + getBlockHeight()/2 - getPlugWidth()/2, getPlugHeight(), getPlugWidth())));
		g.fill(a);
		g.fillRect(pos.getX() - getPlugHeight(), pos.getY() + getBlockHeight()/2 - getPlugWidth()/2, getPlugHeight(), getPlugWidth());
		g.setColor(Color.BLACK);
		g.setFont(getFont());
		g.drawString(getPresentationName(),pos.getX(), pos.getY() + (int)(getBlockHeight() * 0.8));
	}

	@Override
	public PresentationBlock<ChainConditionBlock> getNewBlockOfThisType() {
		ChainConditionBlock block = (ChainConditionBlock) getBlock().getNewBlockOfThisType();
		ChainConditionBlockPresentation blockPresentation= new ChainConditionBlockPresentation(getPosition(), block);
		block.setPresentationBlock(blockPresentation);
		if (block.getPresentationBlock() == null) {
			System.out.println("block.getPresentationBlock() == null in chainConditionBlockPresentation");
		}
		return blockPresentation;
	}

	@Override
	public Vector getPossibleSnapLocation() {
		return new Vector(getPosition().getX() - Math.round(getBlockWidth() / 2), getPosition().getY() + Math.round(getBlockHeight()/2));
	}
	
	@Override
	protected Vector getNextBlockPosition(PresentationBlock<?> presentationBlock) {
		Vector pos = getPosition();
		return new Vector(pos.getX() + getBlockWidth(), pos.getY() );
	}

	@Override
	public Vector getGivingSnapPoint() {
		Vector pos = getPosition();
		return new Vector(pos.getX(), pos.getY() + (int)(getBlockHeight()/2));
	}

	@Override
	public List<Vector> getReceivingSnapPoints() {
		Vector pos = getPosition();
		return Arrays.asList(new Vector(pos.getX() + (int)(getBlockWidth()), pos.getY() + (int)(getBlockHeight()/2)));
	}

	@Override
	public boolean snap(PresentationBlock<?> b) {
		if(b.getBlock() instanceof ConditionBlock 
				&&  b.getGivingSnapPoint().distanceTo(getReceivingSnapPoints().get(0)) <= getSnapDistance()) {
			getBlock().connectTo(b.getBlock());
			return true;
		}
		return false;
	}
}
