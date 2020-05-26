package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;

import domain.block.FunctionCallBlock;
import domain.block.ImplementationBlock;
import game_world.api.Vector;

/**
 * Handles the UI drawing and interaction for the FunctionCallBlock.
 * 
 * @version 4.0
 * @author Andreas Awouters 
 * 	       Thomas Van Erum 
 * 		   Dirk Vanbeveren 
 * 		   Geert Wesemael
 *
 */
public class FunctionCallBlockPresentation extends PresentationBlock<FunctionCallBlock> {
	public FunctionCallBlockPresentation(Vector pos, FunctionCallBlock block) {
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
	protected PresentationBlock<FunctionCallBlock> makeCopyWithoutConnections() {
		ImplementationBlock BF = new ImplementationBlock();
		return new FunctionCallBlockPresentation(getPosition(), (FunctionCallBlock) BF.makeNewBlockOfThisType(getBlock())) ;
	}

}