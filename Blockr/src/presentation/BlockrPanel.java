package presentation;

import java.awt.Color;
import java.awt.Panel;

import client.main.ClientMainClass;
import domain.GameController;
import domain.ImplementationGameController;
import game_world.api.FacadeGameWorld;


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
	private FacadeGameWorld iGameWorld;
	
	private int preferredGameWorldWidth = 10;
	private int preferredGameWorldHeight = 10;
	
	public final static String originalSnapshotName = "original";
	
	/**
	 * Create a new BlockrPanel
	 * This will in turn create a canvas for the block Area
	 * and a canvas for the gameWorld to be drawn on.
	 * Also creates the necessary objects for the program to function.
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public BlockrPanel() throws InstantiationException, IllegalAccessException {
		// Set size of panel
		this.setSize(1280, 720);
		this.setBackground(Color.LIGHT_GRAY);
		
		// Set variables for game functions
		GC = new ImplementationGameController();
		iGameWorld = FacadeGameWorld.newInstance(ClientMainClass.getImplementationClass());
		// TODO gameworld init.
		iGameWorld.makeNewGameWorld();
		iGameWorld.makeSnapshot(BlockrPanel.originalSnapshotName);
		gameController = GC.makeGameController();
		GC.setGameWorldImplementation(gameController, iGameWorld);
		
		// Define panel borders
		int worldPanelStart = (int) (this.getWidth() * (1 - worldProportion));
		int worldPanelWidth = (int) (this.getWidth() * worldProportion);
		
		// Add block Area Canvas
		this.blockAreaC = new BlockAreaCanvas(this, iGameWorld);
		this.add(blockAreaC);
		blockAreaC.setBounds(0, 0, worldPanelStart, this.getHeight());
		blockAreaC.setBackground(Color.WHITE);
		
		// Add gameWorld Canvas
		this.gameWorldC = new GameWorldCanvas(this, iGameWorld);
		this.add(gameWorldC);
		gameWorldC.setBounds(worldPanelStart, 0, worldPanelWidth, this.getHeight());
		gameWorldC.setBackground(Color.WHITE);
		
	}
	
//	public Implementation getGameInterface() {
//		return this.GI;
//	}
	
	/**
	 * get the gameController used by this panel
	 * 
	 * @return gameController associated with this BlockrPanel
	 */
	public GameController getGameController() {
		return this.gameController;
	}
	
	/**
	 * 
	 * 
	 * @return width to be used by the gameWorld
	 */
	public int getPreferredGameWorldWidth() {
		return this.preferredGameWorldWidth;
	}
	
	/**
	 * 
	 * 
	 * @return height to be used by the gameWorld
	 */
	public int getPreferredGameWorldHeight() {
		return this.preferredGameWorldHeight;
	}
	
	/**
	 * Redraw the gameWorldPanel
	 */
	public void redrawGameWorld() {
		gameWorldC.repaint();
	}
	
	/**
	 * 
	 * 
	 * @return block Area Canvas used by this panel
	 */
	public BlockAreaCanvas getBlockAreaCanvas() {
		return blockAreaC;
	}
}
