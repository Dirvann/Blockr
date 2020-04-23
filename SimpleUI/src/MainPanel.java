import java.awt.Color;

import javax.swing.JPanel;

import game_world.ImplementationGameWorld;

public class MainPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GameWorldCanvas gameWorldC;
	private CommandCanvas commandC;
	private ImplementationGameWorld iGameWorld;
	private final static String originalSnapshotName = "original";
	
	private double worldProportion = 0.4;
	
	public MainPanel() {
		// set size of the panel
		this.setSize(1280, 720);
		this.setBackground(Color.LIGHT_GRAY);
		
		// gameworld init
		iGameWorld = new ImplementationGameWorld();
		iGameWorld.makeNewGameWorld();
		iGameWorld.makeSnapshot(MainPanel.originalSnapshotName);
		
		// panel borders
		int worldPanelStart = (int) (this.getWidth() * (1 - worldProportion));
		int worldPanelWidth = (int) (this.getWidth() * worldProportion);
		
		// add sub panels
		gameWorldC = new GameWorldCanvas(iGameWorld);
		commandC = new CommandCanvas(iGameWorld, gameWorldC);
		
		this.add(commandC);
		commandC.setBounds(0,0,worldPanelStart, this.getHeight());
		commandC.setBackground(Color.WHITE);
		
		this.add(gameWorldC);
		gameWorldC.setBounds(worldPanelStart, 0, worldPanelWidth, this.getHeight());
		gameWorldC.setBackground(Color.WHITE);
	}
	

}