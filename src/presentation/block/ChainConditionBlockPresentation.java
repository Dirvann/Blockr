package presentation.block;

import java.awt.Color;
import java.awt.Graphics;

import domain.block.abstract_classes.ActionBlock;
import domain.block.abstract_classes.ChainConditionBlock;
import domain.game_world.Vector;

public class ChainConditionBlockPresentation extends PresentationBlock<ChainConditionBlock>{
	
	public ChainConditionBlockPresentation(Vector position, ChainConditionBlock block) {
		super(position, block);
		
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		Vector pos = getPosition();
		g.fillRect(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight());
	
	}
	
	
	@Override
	protected Vector getNextBlockPosition(PresentationBlock<?> presentationBlock) {
		Vector pos = getPosition();
		return new Vector(pos.getX() + PresentationBlock.getBlockWidth(), pos.getY() );
	}
	
	@Override
	public int getHeight() {
		return getBlockHeight();
	}

	/**
	 * # = snapping area
	 * 		0,51|           1,49|
	 *   _______|_______        |
	 *  |       |       |       |____0,25
	 *  | Block |#######|#######|
	 *  |       |#######|#######|____0,75
	 *  |_______|_______|       |
	 *          |               |
	 */
	@Override
	public boolean conditionCanSnap(int x, int y) {
		int xB = this.getPosition().getX();
		int yB = this.getPosition().getY();
		int wB = PresentationBlock.getBlockWidth();
		int hB = PresentationBlock.getBlockHeight();
		if(xB + 0.51*wB < x && x < xB + 1.49*wB && yB + 0.25*hB < y && y < yB + 0.75*hB) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean sequenceBlockCanSnap(int x, int y) {
		return false;
	}

}
