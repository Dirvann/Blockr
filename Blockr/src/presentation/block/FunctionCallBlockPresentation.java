package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.Arrays;
import java.util.List;

import domain.block.FunctionCall;
import domain.block.ImplementationBlock;
import domain.Vector;

public class FunctionCallBlockPresentation extends PresentationBlock<FunctionCall> {
	public FunctionCallBlockPresentation(Vector pos, FunctionCall block) {
		super(pos, block);
	}

	@Override
	public void draw(Graphics gr) {
		BlockDrawer b = new BlockDrawer(Color.CYAN, Color.BLACK);
		Vector pos = getPosition();
		b.drawBlock(gr, pos, true, true, false, false, false, false);
		b.drawString(gr, getPresentationName(), pos);
	}
	
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
	protected PresentationBlock<FunctionCall> makeCopyWithoutConnections() {
		ImplementationBlock BF = new ImplementationBlock();
		return new FunctionCallBlockPresentation(getPosition(), (FunctionCall) BF.makeNewBlockOfThisType(getBlock())) ;
	}

}