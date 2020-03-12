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
		int height = getHeight();
		// top
		g.fillRect(pos.getX(), pos.getY(), getBlockWidth(), getBlockHeight());
		// side
		g.fillRect(pos.getX(), pos.getY() + getBlockHeight(), getBlockSideWidth(), height-2*getBlockHeight());
		// bottom
		g.fillRect(pos.getX(), pos.getY() + height - getBlockHeight(), getBlockWidth(), getBlockHeight());
	
		g.setColor(Color.BLACK);
		g.setFont(getFont());
		g.drawString(getBlock().getDisplayName(), pos.getX(), pos.getY() + (int)(getBlockHeight() * 0.8));
	
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
		
		if(presentationBlock.getBlock() == getBlock().getConditionBlock()) {
			Vector pos = getPosition();
			return new Vector(pos.getX() + getBlockWidth(), pos.getY());
		}
		
		return null;
	}
	
	@Override
	public int getHeight() {
		int h = 0;
		if(getBlock().getBodyBlock() != null) {
			List<Block> blocks = getBlock().getBodyBlock().getAllNextBlocks();
			
			for(Block b: blocks) {
				if(b instanceof SequenceBlock) {
					h += b.getPresentationBlock().getHeight();
				}
			}
		}
		
		h += 2 * getBlockHeight();
		return h;
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
