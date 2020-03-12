package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import domain.game_world.Vector;

public abstract class ChainConditionBlockPresentation extends PresentationBlock {

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		Vector pos = getPosition();
		g.fillRect(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight());
		g.setColor(Color.BLACK);
		g.setFont(getFont());
		g.drawString(getPresentationName(),pos.getX(), pos.getY() + (int)(getBlockHeight() * 0.8));
	}
	
	
	@Override
	protected Vector getNextBlockPosition(PresentationBlock<?> presentationBlock) {
		Vector pos = getPosition();
		return new Vector(pos.getX() + getBlockWidth(), pos.getY() );
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
	public boolean conditionCanSnap(Vector v) {
		int x=v.getX();
		int y=v.getY();
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
	public boolean sequenceBlockCanSnap(Vector v) {
		return false;
	}

}
