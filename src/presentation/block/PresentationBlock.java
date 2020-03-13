package presentation.block;

import java.awt.Font;
import java.awt.Graphics;

import domain.block.block_types.Block;
import domain.game_world.Vector;

public abstract class PresentationBlock<T extends Block> {

	
	//TODO Store safely, maybe elsewhere.
	private static final int blockWidth = 100;
	private static final int blockHeight = 20;
	
	public PresentationBlock (Vector pos, T block){
		this.position = pos;
		this.block = block;
		
	}
	
	private T block;
	
	private Vector position;
	
	private static final Font font = new Font("Arial", Font.PLAIN, (int)(blockHeight * 0.7));
	
	
	public Vector getPosition() {
		return this.position;
	}
	
	public void setPosition(Vector pos) {
		this.position = pos;
	}
	
	public void setPositionByDifference(Vector deltaPos) {
		this.setPosition(getPosition().add(deltaPos));
	}
	
	public void setPositionRecursivelyByDifference(Vector deltaPos) {
		this.setPositionByDifference(deltaPos);
		this.getBlock().setConnectedBlockPositionRecursivelyByDifference(deltaPos);
		// this.getBlock().setPositionRecursivelyByDifference(deltaPos);
	}
	
	
	
	abstract public void draw(Graphics g);
	
	public static int getBlockWidth() {
		return blockWidth;
	}
	
	public static int getBlockHeight() {
		return blockHeight;
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
	
	public abstract PresentationBlock<T> getNewBlockOfThisType();
	

	public String getPresentationName() {
		return getBlock().getName();
	}

	public static Font getFont() {
		return font;
	}
	
	public int getTotalHeight() {
		return getBlockHeight();
	}

	public abstract Vector getPossibleSnapLocation();
	
}
