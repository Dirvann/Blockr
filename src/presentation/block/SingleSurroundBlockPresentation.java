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
	
	@Override
	public int getTotalHeight() {
		int totalHeight = this.getHeight() * 2;
		SequenceBlock current	= 	this.getBlock().getBodyBlock();
		while (current != null) {
			totalHeight += current.getPresentationBlock().getTotalHeight();
			current = current.getNextBlock();
		}
		return totalHeight;
	}


	@Override
	public boolean conditionCanSnap(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sequenceBlockCanSnap(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

}
