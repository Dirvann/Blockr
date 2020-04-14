package presentation;

import java.awt.Color;
import java.awt.Panel;

import domain.GameController;
import domain.ImplementationGameController;
import domain.game_world.ImplementationGameWorld;


public class BlockrPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3195415750744263154L;
	
	private BlockAreaCanvas blockAreaC;
	private GameWorldCanvas gameWorldC;
	
	private final double worldProportion = 0.3;
	
	private GameController gameController;
	private ImplementationGameController GC;
	private ImplementationGameWorld GW;
	
	private int preferredGameWorldWidth = 10;
	private int preferredGameWorldHeight = 10;
	
	public BlockrPanel() {
		// Set size of panel
		this.setSize(1280, 720);
		this.setBackground(Color.LIGHT_GRAY);
		
		// Set variables for game functions
		GC = new ImplementationGameController();
		GW = new ImplementationGameWorld();
		gameController = GC.makeGameController();
		GC.setGameWorld(gameController, GW.makeRandomGameWorld(preferredGameWorldWidth, preferredGameWorldHeight));
		
		// Define panel borders
		int worldPanelStart = (int) (this.getWidth() * (1 - worldProportion));
		int worldPanelWidth = (int) (this.getWidth() * worldProportion);
		
		// Add block Area Canvas
		this.blockAreaC = new BlockAreaCanvas(this);
		this.add(blockAreaC);
		blockAreaC.setBounds(0, 0, worldPanelStart, this.getHeight());
		blockAreaC.setBackground(Color.WHITE);
		
		// Add gameWorld Canvas
		this.gameWorldC = new GameWorldCanvas(this);
		this.add(gameWorldC);
		gameWorldC.setBounds(worldPanelStart, 0, worldPanelWidth, this.getHeight());
		gameWorldC.setBackground(Color.WHITE);
		
	}
	
//	public Implementation getGameInterface() {
//		return this.GI;
//	}
	
	public GameController getGameController() {
		return this.gameController;
	}
	
	public int getPreferredGameWorldWidth() {
		return this.preferredGameWorldWidth;
	}
	
	public int getPreferredGameWorldHeight() {
		return this.preferredGameWorldHeight;
	}
	
	public void redrawGameWorld() {
		gameWorldC.repaint();
	}
}