package presentation;

import java.awt.Canvas;
import java.awt.Graphics;

import game_world.api.FacadeGameWorld;

/**
 * Canvas object used to draw the GameWorld on.
 * 
 * @version 3.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class GameWorldCanvas extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5278879530185183350L;
	
	private FacadeGameWorld iGameWorld;
	
	/**
	 * Create a new instance of the gameWorldCanvas
	 * 
	 * @param blockrPanel
	 * 		  | Panel to attach this gameWorldCanvas to
	 * @param iGameWorld
	 *        | Interface used by the panel
	 */
	public GameWorldCanvas(BlockrPanel blockrPanel, FacadeGameWorld iGameWorld) {
		this.iGameWorld = iGameWorld;
	}
	
	/**
	 * Draw the gameWorld onto this canvas
	 */
	public void paint(Graphics g) {
		iGameWorld.drawGameWorld(g,getWidth(),getHeight());
	}
}
