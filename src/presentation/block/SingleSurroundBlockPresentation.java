package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.block.block_types.Block;
import domain.block.block_types.SequenceBlock;
import domain.game_world.Vector;

public class SingleSurroundBlockPresentation extends PresentationBlock<SingleSurroundingBlock> {
	
	public SingleSurroundBlockPresentation(Vector pos, SingleSurroundingBlock block) {
		super(pos,block);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		Vector pos = getPosition();
		g.fillRect(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight());
	
	}
	

	@Override
	protected Vector getNextBlockPosition(PresentationBlock<?> presentationBlock) {
		if(presentationBlock.getBlock() == getBlock().getBodyBlock()) {
			Vector pos = getPosition();
			return new Vector(pos.getX() + getBlockSideWidth(), pos.getY()  + PresentationBlock.getBlockHeight());
		}
		
		if(presentationBlock.getBlock() == getBlock().getNextBlock()) {
			Vector pos = getPosition();
			return new Vector(pos.getX(), pos.getY()  + getHeight());
		}
		
		return null;
	}
	
	@Override
	public int getHeight() {
		List<Block> blocks = getBlock().getBodyBlock().getAllNextBlocks();
		int h = 0;
		for(Block b: blocks) {
			if(b instanceof SequenceBlock) {
				h += b.getPresentationBlock().getHeight();
			}
		}
		h += 2 * getBlockHeight();
		return h;
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
	public boolean sequenceBlockCanSnap(int x, int y) {
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

}
