package presentation;

import java.awt.Canvas;
import java.awt.Graphics;

import domain.ImplementationGameController;
import impl.root.ImplementationGameWorld;


public class GameWorldCanvas extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5278879530185183350L;
	
	private BlockrPanel blockrPanel;
	private ImplementationGameWorld iGameWorld;
	
	/**
	 * Create a new instance of the gameWorldCanvas
	 * 
	 * @param blockrPanel
	 * 		  | Panel to attach this gameWorldCanvas to
	 * @param iGameWorld
	 *        | Interface used by the panel
	 */
	public GameWorldCanvas(BlockrPanel blockrPanel, ImplementationGameWorld iGameWorld) {
		this.iGameWorld = iGameWorld;
		this.blockrPanel = blockrPanel;
	}
	
	/**
	 * Draw the gameWorld onto this canvas
	 */
	public void paint(Graphics g) {
		iGameWorld.drawGameWorld(g,getWidth(),getHeight());
	}
}
