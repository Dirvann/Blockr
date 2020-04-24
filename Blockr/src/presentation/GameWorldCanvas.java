package presentation;

import java.awt.Canvas;
import java.awt.Graphics;

import game_world.api.FacadeGameWorld;


public class GameWorldCanvas extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5278879530185183350L;
	
	private FacadeGameWorld iGameWorld;
	
	public GameWorldCanvas(BlockrPanel blockrPanel, FacadeGameWorld iGameWorld) {
		this.iGameWorld = iGameWorld;
	}
	
	public void paint(Graphics g) {
		iGameWorld.drawGameWorld(g,getWidth(),getHeight());
	}
}
