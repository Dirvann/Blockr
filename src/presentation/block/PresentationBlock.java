package presentation.block;

import java.awt.Graphics;

import domain.game_world.Vector;

public abstract class PresentationBlock {
	
	//TODO Store safely, maybe elsewhere.
	public static int blockWidth = 140;
	public static int blockHeight = 100;
	
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
