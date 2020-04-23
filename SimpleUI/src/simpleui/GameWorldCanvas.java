package simpleui;
import java.awt.Canvas;
import java.awt.Graphics;

import game_world.ImplementationGameWorld;

public class GameWorldCanvas extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ImplementationGameWorld iGameWorld;

	public GameWorldCanvas(ImplementationGameWorld iGameWorld) {
		this.iGameWorld = iGameWorld;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		iGameWorld.drawGameWorld(g, getWidth(), getHeight());
	}
}
