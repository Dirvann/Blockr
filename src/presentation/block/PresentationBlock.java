package presentation.block;

import java.awt.Font;
import java.awt.Graphics;

import domain.block.block_types.Block;
import domain.game_world.Vector;

public abstract class PresentationBlock<T extends Block>{
	
	//TODO Store safely, maybe elsewhere.
	private static final int blockWidth = 100;
	private static final int blockHeight = 20;
	private static final int blockSideWidth = 20;
	
	private static final Font font = new Font("Arial", Font.PLAIN, (int)(blockHeight * 0.7));
	
	private T block;
	
	protected Vector position;
	
	
	public PresentationBlock(Vector pos, T block) {
		this.position = pos;
		this.setBlock(block);
	}
	
	
	public Vector getPosition() {
		// check if it has a previous block
		if(getBlock().getPreviousBlock() == null) {
			return this.position;
		} 
		
		// get the position of the block from the upper block
		
		this.setPosition(getBlock().getPreviousBlock().getPresentationBlock().getNextBlockPosition(this));
		
		
		return this.position;
	}
	
	public void setPosition(Vector pos) {
		this.position = pos;
	}
	
	abstract public void draw(Graphics g);
	
	public static int getBlockWidth() {
		return blockWidth;
	}
	
	public static int getBlockHeight() {
		return blockHeight;
	}
	
	public static int getBlockSideWidth() {
		return blockSideWidth;
	}
	
	public boolean collidesWithPosition(Vector pos) {
		if(pos.getX() > this.getPosition().getX() && 
				pos.getX() < (getBlockWidth() + this.getPosition().getX()) && 
				pos.getY() > this.getPosition().getY() && 
				pos.getY() < (this.getPosition().getY() + getBlockHeight())) {
			return true;
		}
		return false;
	}
	
	public T getBlock() {
		return this.block;
	}
	
	public void setBlock(T block) {
		this.block = block;
	}
	
	public Font getFont() {
		return font;
	}
	
	public abstract boolean conditionCanSnap(int x, int y);
	abstract public int getHeight();


	protected abstract Vector getNextBlockPosition(PresentationBlock<?> presentationBlock);
	
	public abstract boolean sequenceBlockCanSnap(int x, int y);
	
	
	
}
