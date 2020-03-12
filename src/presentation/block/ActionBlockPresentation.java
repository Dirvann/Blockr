package presentation.block;

import java.awt.Color;
import java.awt.Graphics;

import domain.block.abstract_classes.ActionBlock;
import domain.game_world.Vector;

public class ActionBlockPresentation extends PresentationBlock<ActionBlock> {
	

	public ActionBlockPresentation(Vector pos, ActionBlock block) {
		super(pos,block);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		Vector pos = getPosition();
		g.fillRect(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight());
		g.setColor(Color.BLACK);
		g.setFont(getFont());
		g.drawString(getBlock().getDisplayName(), pos.getX(), pos.getY() + (int)(getBlockHeight() * 0.8));
	}

	@Override
	public boolean conditionCanSnap(Vector v) {
		return false;
	}

	/**
	 * # = snapping area
	 * 	0,25|   0,75|
	 *   ___|_______|___ 
	 *  |   |       |   |
	 *  | Block     |___|___0,51
	 *  |   #########   |
	 *  |___#########___|
	 *      #########
	 *      #########_______1,49
	 *      |       |

	 */
	@Override
	public boolean sequenceBlockCanSnap(Vector v) {
		int x=v.getX();
		int y=v.getY();
		int xB = this.getPosition().getX();
		int yB = this.getPosition().getY();
		int wB = PresentationBlock.getBlockWidth();
		int hB = PresentationBlock.getBlockHeight();
		if(xB + 0.25*wB < x && x < xB + 0.75*wB && yB + 0.51*hB < y && y < yB +1.49*hB) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected Vector getNextBlockPosition(PresentationBlock<?> presentationBlock) {
		Vector pos = getPosition();
		return new Vector(pos.getX(), pos.getY() + PresentationBlock.getBlockHeight());
	}

	@Override
	public int getHeight() {
		return getBlockHeight();
	}
	
	
	

}