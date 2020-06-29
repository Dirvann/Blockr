package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import command.AddToBodyCommand;
import command.AddToLowerBodyCommand;
import command.Command;
import command.ConnectCommand;
import command.SetConditionCommand;
import domain.GameController;
import domain.block.Block;
import domain.block.ConditionBlock;
import domain.block.IfElseBlock;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import domain.block.SingleSurroundingBlock;
import game_world.api.Vector;

public class IfElseBlockPresentation extends PresentationBlock<IfElseBlock> {

	ImplementationBlock BF = new ImplementationBlock();

	protected IfElseBlockPresentation(Vector pos, IfElseBlock block) {
		super(pos, block);
	}

	@Override
	protected void draw(Graphics gr) {
		BlockDrawer b = new BlockDrawer(Color.BLUE, Color.BLACK);
		
		Vector pos = getPosition();
		int height = getTotalHeight();
		
		b.drawBlock(gr, pos, true, true, true, false, false, true);
		b.drawSide(gr, pos, height);
		b.drawBlock(gr, pos.add(new Vector(0,height - getBlockHeight())), true, true, false, false, true, false);
		b.drawBlock(gr, pos.add(new Vector(0,getFirstHeight()- getBlockHeight())), true, true, false, false, true, true);
		b.drawString(gr, getPresentationName(), pos);
	}

	@Override
	public int getTotalHeight() {
		int totalHeight = 3 * getBlockHeight();
		Block current =  BF.getBodyBlock(getBlock());
		Block second = BF.getLowerBodyBlock(getBlock());
		while (current != null) {
			totalHeight += BF.getPresentationBlock(current).getTotalHeight();
			current = BF.getNextBlock(current);
		}
		while (second != null) {
			totalHeight += BF.getPresentationBlock(second).getTotalHeight();
			current = BF.getNextBlock(second);
		}
		return totalHeight;
	}
	
	public int getFirstHeight() {
		int firstHeight = 2 * getBlockHeight();
		Block current =  BF.getBodyBlock(getBlock());
		while (current != null) {
			firstHeight += BF.getPresentationBlock(current).getTotalHeight();
			current = BF.getNextBlock(current);
		}
		return firstHeight;
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
		
		if (presentationBlock.getBlock() == BF.getLowerBodyBlock(getBlock())) {
			Vector pos = getPosition();
			return new Vector(pos.getX() + getBlockSideWidth(), pos.getY() + getFirstHeight());
		}

		return null;
	}

	@Override
	protected Vector getGivingSnapPoint() {
		Vector pos = getPosition();
		return new Vector(pos.getX() + (int) (getBlockWidth() / 2), pos.getY());
	}

	@Override
	protected List<Vector> getReceivingSnapPoints() {
		List<Vector> snapPoints = new ArrayList<Vector>();
		snapPoints.add(getConditionSnapPoint());
		snapPoints.add(getBodySnapPoint());
		snapPoints.add(getLowerBodySnapPoint());
		snapPoints.add(getSequenceSnapPoint());
		return snapPoints;
	}
	
	private Vector getConditionSnapPoint() {
		Vector pos = getPosition();
		return new Vector(pos.getX() + getBlockWidth(), pos.getY() + (int) (getBlockHeight() / 2));
	}

	private Vector getBodySnapPoint() {
		Vector pos = getPosition();
		return new Vector(pos.getX() + (int) (getBlockWidth() / 2 + getBlockSideWidth()),
				pos.getY() + getBlockHeight());
	}
	
	private Vector getLowerBodySnapPoint() {
		Vector pos = getPosition();
		return new Vector(pos.getX() + (int) (getBlockWidth() / 2 + getBlockSideWidth()),
				pos.getY() + getFirstHeight());
	}
	
	private Vector getSequenceSnapPoint() {
		Vector pos = getPosition();
		return new Vector(pos.getX() + (int) (getBlockWidth() / 2), pos.getY() + getTotalHeight());
	}
	
	@Override
	public Command canSnap(PresentationBlock<?> b, GameController GC) {
		
		Block next = BF.getNextBlock(getBlock());
		ConditionBlock condition = BF.getConditionBlock(getBlock());
		SequenceBlock body = BF.getBodyBlock(getBlock());
		SequenceBlock lowerBody = BF.getLowerBodyBlock(getBlock());
		
		if (canSnapToCondition(b)) {
			GC.setCondition(getBlock(), (ConditionBlock) b.getBlock());
			return new SetConditionCommand(getBlock(), (ConditionBlock) b.getBlock(), condition, GC);
		}
		if (canSnapToBody(b)) {
			GC.setBody(getBlock(), (SequenceBlock) b.getBlock());
			return new AddToBodyCommand(getBlock(), (SequenceBlock) b.getBlock(), body, GC);
		}
		if (canSnapToLowerBody(b)) {
			GC.setLowerBody(getBlock(), (SequenceBlock) b.getBlock());
			return new AddToLowerBodyCommand(getBlock(), (SequenceBlock) b.getBlock(), lowerBody, GC);
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
	
	private boolean canSnapToLowerBody(PresentationBlock<?> b) {
		return b.getBlock() instanceof SequenceBlock && b.getGivingSnapPoint().distanceTo(getLowerBodySnapPoint()) <= getSnapDistance();
	}
	
	private boolean canSnapToBottom(PresentationBlock<?> b) {
		return b.getBlock() instanceof SequenceBlock && b.getGivingSnapPoint().distanceTo(getSequenceSnapPoint()) <= getSnapDistance();
	}

	
	@Override
	protected PresentationBlock<IfElseBlock> makeCopyWithoutConnections() {
		ImplementationBlock BF = new ImplementationBlock();
		return new IfElseBlockPresentation(getPosition(), (IfElseBlock) BF.makeNewBlockOfThisType(getBlock())) ;

	}

}
