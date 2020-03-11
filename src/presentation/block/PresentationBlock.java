package presentation.block;

import java.awt.Graphics;

import domain.game_world.Vector;

public abstract class PresentationBlock {
	
	private Vector position;
	
	
	public PresentationBlock(Vector pos) {
		this.position = pos;
	}
	
	
	public Vector getPosition() {
		return this.position;
	}
	
	public void setPosition(Vector pos) {
		this.position = pos;
	}
	
	abstract public void draw(Graphics g);
	
	
}
