package presentation;

import java.awt.Canvas;
import java.awt.Graphics;

import domain.ImplementationGameController;
import game_world.ImplementationGameWorld;


public class GameWorldCanvas extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5278879530185183350L;
	
	private BlockrPanel blockrPanel;
	private ImplementationGameWorld iGameWorld;
	
	public GameWorldCanvas(BlockrPanel blockrPanel, ImplementationGameWorld iGameWorld) {
		this.iGameWorld = iGameWorld;
		this.blockrPanel = blockrPanel;
	}
	
	public void paint(Graphics g) {
		iGameWorld.drawGameWorld(g,getWidth(),getHeight());
	}
}
