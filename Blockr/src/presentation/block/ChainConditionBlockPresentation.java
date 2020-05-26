package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;

import domain.block.ChainConditionBlock;
import domain.block.ImplementationBlock;
import game_world.api.Vector;

/**
 * Handles the UI drawing and interaction for the ChainConditionBlock.
 * 
 * @version 4.0
 * @author Andreas Awouters 
 * 	       Thomas Van Erum 
 * 		   Dirk Vanbeveren 
 * 		   Geert Wesemael
 *
 */
public class ChainConditionBlockPresentation extends PresentationBlock<ChainConditionBlock> {
	
	public ChainConditionBlockPresentation(Vector pos, ChainConditionBlock block) {
		super(pos, block);
	}

	@Override
	public void draw(Graphics gr) {
		
		Vector pos = getPosition();
		BlockDrawer b = new BlockDrawer(Color.RED, Color.BLACK);
		b.drawBlock(gr, pos, false, false, true, true, false, false);
		
		b.drawString(gr, getPresentationName(), pos);
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
	protected PresentationBlock<ChainConditionBlock> makeCopyWithoutConnections() {
		ImplementationBlock BF = new ImplementationBlock();
		return new ChainConditionBlockPresentation(getPosition(), (ChainConditionBlock) BF.makeNewBlockOfThisType(getBlock())) ;
	}
}
