package presentation.block;

import java.awt.Graphics;

import domain.game_world.Vector;

public abstract class PresentationBlock<T> {
	
	//TODO Store safely, maybe elsewhere.
	private static final int blockWidth = 100;
	private static final int blockHeight = 20;
	
	private T block;
	
	private Vector position;
	
	
	public PresentationBlock(Vector pos, T block) {
		this.position = pos;
		this.setBlock(block);
	}
	
	
	public Vector getPosition() {
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
	
	
}
