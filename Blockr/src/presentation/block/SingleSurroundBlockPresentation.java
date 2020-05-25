package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import command.Command;
import command.ConnectCommand;
import command.AddToBodyCommand;
import command.SetConditionCommand;
import domain.GameController;
import domain.block.Block;
import domain.block.ConditionBlock;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import domain.block.SingleSurroundingBlock;
import game_world.api.Vector;

public class SingleSurroundBlockPresentation extends PresentationBlock<SingleSurroundingBlock> {
	
	ImplementationBlock BF = new ImplementationBlock();

	public SingleSurroundBlockPresentation(Vector pos, SingleSurroundingBlock block) {
		super(pos, block);
	}

	@Override
	public void draw(Graphics gr) {
		
		BlockDrawer b = new BlockDrawer(Color.LIGHT_GRAY, Color.BLACK);
		
		Vector pos = getPosition();
		int height = getTotalHeight();
		
		b.drawBlock(gr, pos, true, true, true, false, false, true);
		b.drawSide(gr, pos, height);
		b.drawBlock(gr, pos.add(new Vector(0,height - getBlockHeight())), true, true, false, false, true, false);
		b.drawString(gr, getPresentationName(), pos);
	}

	@Override
	public int getTotalHeight() {
		int totalHeight = 2 * getBlockHeight();
		Block current =  BF.getBodyBlock(getBlock());
		while (current != null) {
			totalHeight += BF.getPresentationBlock(current).getTotalHeight();
			current = BF.getNextBlock(current);
		}
		return totalHeight;
	}

	@Override
	public boolean collisionWithLowerPart(Vector position) {
		int xValueLowerPart = getPosition().getX();
		int yValueLowerPart = getPosition().getY() + getTotalHeight() - getBlockHeight();
		if (position.getX() > this.getPosition().getX() && position.getX() < (getBlockWidth() + xValueLowerPart)
				&& position.getY() > yValueLowerPart && position.getY() < (yValueLowerPart + getBlockHeight())) {
			return true;
		}
		return false;
	}

	@Override
	protected Vector getNextBlockPosition(PresentationBlock<?> presentationBlock) {
		if (presentationBlock.getBlock() == BF.getBodyBlock(getBlock())) {
			Vector pos = getPosition();
			return new Vector(pos.getX() + getBlockSideWidth(), pos.getY() + PresentationBlock.getBlockHeight());
		}

		if (presentationBlock.getBlock() == BF.getNextBlock(getBlock())) {
			Vector pos = getPosition();
			return new Vector(pos.getX(), pos.getY() + getTotalHeight());
		}

		if (presentationBlock.getBlock() == BF.getConditionBlock(getBlock())) {
			Vector pos = getPosition();
			return new Vector(pos.getX() + getBlockWidth(), pos.getY());
		}

		return null;
	}

	@Override
	public Vector getGivingSnapPoint() {
		Vector pos = getPosition();
		return new Vector(pos.getX() + (int) (getBlockWidth() / 2), pos.getY());
	}

	@Override
	public List<Vector> getReceivingSnapPoints() {
		List<Vector> snapPoints = new ArrayList<Vector>();
		snapPoints.add(getConditionSnapPoint());
		snapPoints.add(getBodySnapPoint());
		snapPoints.add(getSequenceSnapPoint());
		return snapPoints;
	}

	public Vector getConditionSnapPoint() {
		Vector pos = getPosition();
		return new Vector(pos.getX() + getBlockWidth(), pos.getY() + (int) (getBlockHeight() / 2));
	}

	public Vector getBodySnapPoint() {
		Vector pos = getPosition();
		return new Vector(pos.getX() + (int) (getBlockWidth() / 2 + getBlockSideWidth()),
				pos.getY() + getBlockHeight());
	}

	public Vector getSequenceSnapPoint() {
		Vector pos = getPosition();
		return new Vector(pos.getX() + (int) (getBlockWidth() / 2), pos.getY() + getTotalHeight());
	}

	@Override
	public Command canSnap(PresentationBlock<?> b, GameController GC) {
		
		Block next = BF.getNextBlock(getBlock());
		ConditionBlock condition = BF.getConditionBlock(getBlock());
		SequenceBlock body = BF.getBodyBlock(getBlock());
		
		if (canSnapToCondition(b)) {
			GC.setCondition(getBlock(), (ConditionBlock) b.getBlock());
			return new SetConditionCommand(getBlock(), (ConditionBlock) b.getBlock(), condition, GC);
		}
		if (canSnapToBody(b)) {
			GC.setBody(getBlock(), (SequenceBlock) b.getBlock());
			return new AddToBodyCommand(getBlock(), (SequenceBlock) b.getBlock(), body, GC);
		}
		if (canSnapToBottom(b)) {
			GC.connect(getBlock(), b.getBlock());
			return new ConnectCommand(getBlock(), b.getBlock(), next, GC);
		}
		return null;
	}
	
	private boolean canSnapToCondition(PresentationBlock<?> b) {
		return b.getBlock() instanceof ConditionBlock && b.getGivingSnapPoint().distanceTo(getConditionSnapPoint()) <= getSnapDistance();
	}
	
	private boolean canSnapToBody(PresentationBlock<?> b) {
		return b.getBlock() instanceof SequenceBlock && b.getGivingSnapPoint().distanceTo(getBodySnapPoint()) <= getSnapDistance();
	}
	
	private boolean canSnapToBottom(PresentationBlock<?> b) {
		return b.getBlock() instanceof SequenceBlock && b.getGivingSnapPoint().distanceTo(getSequenceSnapPoint()) <= getSnapDistance();
	}

	@Override
	protected PresentationBlock<SingleSurroundingBlock> makeCopyWithoutConnections() {
		ImplementationBlock BF = new ImplementationBlock();
		return new SingleSurroundBlockPresentation(getPosition(), (SingleSurroundingBlock) BF.makeNewBlockOfThisType(getBlock())) ;
	}

}
