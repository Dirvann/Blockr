package simpleui;
import java.awt.Canvas;
import java.awt.Graphics;

import game_world.api.FacadeGameWorld;
import game_world.api.GameWorldListener;

public class GameWorldCanvas extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FacadeGameWorld iGameWorld;
	private GameWorldListener listener;

	public GameWorldCanvas(FacadeGameWorld iGameWorld) {
		this.iGameWorld = iGameWorld;
		
		listener = new GameWorldListener() {

			@Override
			public void gameWorldChanged() {
				repaint();
			}
			
		};
		
		iGameWorld.addListener(listener);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		iGameWorld.drawGameWorld(g, getWidth(), getHeight());
	}
}
