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
import domain.Vector;

public class ActionBlockPresentation extends PresentationBlock<ActionBlock> {
	public ActionBlockPresentation(Vector pos, ActionBlock block) {
		super(pos, block);
	}

	@Override
	public void draw(Graphics gr) {
		
		BlockDrawer b = new BlockDrawer(Color.GREEN, Color.BLACK);
		
		Vector pos = getPosition();
		
		b.drawBlock(gr, pos, true, true, false, false, false, false);
		b.drawString(gr, getPresentationName(), pos);
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