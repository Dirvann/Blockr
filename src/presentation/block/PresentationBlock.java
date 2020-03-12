package presentation.block;

import java.awt.Font;
import java.awt.Graphics;

import domain.block.block_types.Block;
import domain.game_world.Vector;

public abstract class PresentationBlock {
	
	//TODO Store safely, maybe elsewhere.
	private static final int blockWidth = 100;
	private static final int blockHeight = 20;
	private static final int blockSideWidth = 20;
	
	private Block block;
	
	protected Vector position;
	
	private static final Font font = new Font("Arial", Font.PLAIN, (int)(blockHeight * 0.7));
	private String presentationName = "Block";
	
	
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
	
	public int getTotalHeight() {
		return getBlockHeight();
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
	
	public Block getBlock() {
		return this.block;
	}
	
	public void setBlock(Block block) {
		this.block = block;
	}
	
	public abstract PresentationBlock getNewBlockOfThisType(Block block) throws Exception;

	public String getPresentationName() {
		return presentationName;
	}

	public void setPresentationName(String presentationName) {
		this.presentationName = presentationName;
	}

	public static Font getFont() {
		return font;
	}
	public abstract boolean conditionCanSnap(Vector v);
	abstract public int getHeight();
	protected abstract Vector getNextBlockPosition(PresentationBlock<?> presentationBlock);
	
	public abstract boolean sequenceBlockCanSnap(Vector v);
	
	
	
}
