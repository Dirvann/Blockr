package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import domain.block.ImplementationBlock;
import domain.block.SingleConditionBlock;
import game_world.api.Vector;

/**
 * Handles the UI drawing and interaction for the SingleConditionBlock.
 * 
 * @version 4.0
 * @author Andreas Awouters 
 * 	       Thomas Van Erum 
 * 		   Dirk Vanbeveren 
 * 		   Geert Wesemael
 *
 */
public class SingleConditionBlockPresentation extends PresentationBlock<SingleConditionBlock> {
	
	public SingleConditionBlockPresentation(Vector pos, SingleConditionBlock block) {
		super(pos, block);
	}

	@Override
	public void draw(Graphics g) {
		Vector pos = getPosition();
		BlockDrawer b = new BlockDrawer(Color.ORANGE, Color.BLACK);
		b.drawBlock(g, pos, false, false, false, true, false, false);
		b.drawString(g, getPresentationName(), pos);
	}
	
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

	@Override
	protected PresentationBlock<SingleConditionBlock> makeCopyWithoutConnections() {
		ImplementationBlock BF = new ImplementationBlock();
		return new SingleConditionBlockPresentation(getPosition(), (SingleConditionBlock) BF.makeNewBlockOfThisType(getBlock())) ;
	}

}
