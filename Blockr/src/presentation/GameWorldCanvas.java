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
	private ImplementationGameController GC;
	private ImplementationGameWorld GW;
	
	public GameWorldCanvas(BlockrPanel blockrPanel) {
		GC = new ImplementationGameController();
		GW = new ImplementationGameWorld();
		this.blockrPanel = blockrPanel;
	}
	
	public void paint(Graphics g) {
		GW.drawGameWorld(g,GC.getGameWorld(blockrPanel.getGameController()), getWidth());
	}
}
