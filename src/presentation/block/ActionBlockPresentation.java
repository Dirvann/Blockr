package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.Arrays;
import java.util.List;

import domain.block.ActionBlock;
import domain.block.ImplementationBlock;
import domain.game_world.Vector;

public class ActionBlockPresentation extends PresentationBlock<ActionBlock> {
	public ActionBlockPresentation(Vector pos, ActionBlock block) {
		super(pos, block);
	}

	@Override
	public void draw(Graphics gr) {
		
		Graphics2D g = (Graphics2D)gr;
		g.setColor(Color.GREEN);
		Vector pos = getPosition();
		Area a = new Area(new Rectangle(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight()));
		a.subtract(new Area(new Rectangle(pos.getX() + getBlockWidth()/2 - getPlugWidth()/2, pos.getY() + getBlockHeight() - getPlugHeight(), getPlugWidth(), getPlugHeight())));
		g.fill(a);
		g.fillRect(pos.getX() + getBlockWidth()/2 - getPlugWidth()/2, pos.getY() - getPlugHeight(), getPlugWidth(), getPlugHeight());
		g.setColor(Color.BLACK);
		g.setFont(getFont());
		g.drawString(getPresentationName(),pos.getX(), pos.getY() + (int)(getBlockHeight() * 0.8));
	}

//	@Override
//	public PresentationBlock<ActionBlock> getNewBlockOfThisType() {
//		// TODO Auto-generated method stub
//		ActionBlock block = (ActionBlock) getBlock().getNewBlockOfThisType();
//		ActionBlockPresentation blockPresentation = new ActionBlockPresentation(getPosition(), block);
//		
//		
//		block.setPresentationBlock(blockPresentation);
//		if (block.getPresentationBlock() == null) {
//			System.out.println("block.getPresentationBlock() == null in actionBlockPresentation");
//		}
//		return blockPresentation;
//	}

	
	@Override
	protected Vector getNextBlockPosition(PresentationBlock<?> presentationBlock) {
		Vector pos = getPosition();
		return new Vector(pos.getX(), pos.getY() + PresentationBlock.getBlockHeight());
	}

	@Override
	public Vector getGivingSnapPoint() {
		Vector pos = getPosition();
		return new Vector(pos.getX() + (int)(getBlockWidth()/2), pos.getY());
	}

	@Override
	public List<Vector> getReceivingSnapPoints() {
		Vector pos = getPosition();
		return Arrays.asList(new Vector(pos.getX() + (int)(getBlockWidth()/2), pos.getY() + getBlockHeight()));
	}

	@Override
	protected PresentationBlock<ActionBlock> makeCopyWithoutConnections() {
		ImplementationBlock BF = new ImplementationBlock();
		return new ActionBlockPresentation(getPosition(), (ActionBlock) BF.makeNewBlockOfThisType(getBlock())) ;
	}

}