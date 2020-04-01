package presentation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
import domain.game_world.Direction;
import domain.game_world.GameWorld;
import domain.game_world.ImplementationGameWorld;
import domain.game_world.Vector;
import presentation.block.*;

public class Presentation extends Canvas implements MouseListener, MouseMotionListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1109041362094124173L;
	static int width = 800;
	static int height = 600;
	static Canvas canvas;

	double panelProportion = 0.2;
	double codeProportion = 0.5;
	double worldProportion = 0.3;

	GameWorld gameWorld;
	int gameWorldWidth = 10;
	int gameWorldHeight = 10;

	Vector mouseDownStartPosition = new Vector(0, 0);
	boolean mouseDown = false;
	PresentationBlock<?> selectedBlock = null;
	Vector previousMousePos = null;
	String errorMessage = "The error message will appear here!";

	private ImplementationBlock BF = new ImplementationBlock();
	private ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();
	private ImplementationGameWorld GW = new ImplementationGameWorld();
	private ImplementationGameController GC = new ImplementationGameController(); 

	GameController gameController;
	PalettePresentation paletteP;
	ProgramAreaPresentation programAreaP;
	ImplementationBlock GA; // GameInterface

	public static void main(String[] args) {
		JFrame frame = new JFrame("Blockr");
		canvas = new Presentation();
		canvas.setSize(width, height);
		frame.add(canvas);
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public Presentation() {

		GA = new ImplementationBlock();

		gameController = GC.makeGameController();
		paletteP = new PalettePresentation();
		programAreaP = new ProgramAreaPresentation(GC.getProgramArea(gameController));
//    	Block bla = new MoveForward();
//    	gameController.addTopLevelBlock(bla);
//    	GA.connect(bla, new MoveForward());

//    	Vector[] vectors = new Vector[] {new Vector(0,0), new Vector(5,5), new Vector(4,5)};
//    	Cell[] cells = new Cell[] {new Wall(), new Goal(), new Wall()};
//    	Grid grid;
//		try {
//			grid = new Grid(10,10, vectors, cells);
//			Vector start = new Vector(4,4);
//	    	gameController.setGameWorld(new GameWorld(grid, start));
//	    	gameWorld = gameController.getGameWorld();
//	    	gameWorld.setRobot(new Robot(new Vector(6,7), Direction.DOWN));
//	    	
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		GC.setGameWorld(gameController,GW.makeRandomGameWorld(gameWorldWidth, gameWorldHeight));
		gameWorld = GC.getGameWorld(gameController);

		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);

	}

	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine((int) (panelProportion * canvas.getWidth()), 0, (int) (panelProportion * canvas.getWidth()),
				canvas.getHeight());
		g.drawLine(canvas.getWidth() - (int) (worldProportion * canvas.getWidth()), 0,
				canvas.getWidth() - (int) (worldProportion * canvas.getWidth()), canvas.getHeight());

		if (GC.getAmountOfBlocksLeft(gameController) > 0) { //TODO: getBlocksLeft
			paletteP.paint(g);
		}
		g.setFont(new Font("Arial", Font.PLAIN, (int) (getHeight() / 20)));
		g.drawString("" + GC.getAmountOfBlocksLeft(gameController), getWidth() / 18, 17 * getHeight() / 18);
		g.setFont(new Font("Arial", Font.PLAIN, (int) (getHeight() / 40)));
		g.drawString(errorMessage, getWidth() / 4, 17 * getHeight() / 18);
		programAreaP.paint(g);

		Block nextToExecute = GC.getNextBlockToExecute(gameController);
		if (nextToExecute != null) {
			BFP.highLight(BF.getPresentationBlock(nextToExecute), g);
		}

		drawWorld(g, GC.getGameWorld(gameController));
	}

	public void drawWorld(Graphics g, GameWorld gameWorld) {
		// drawing grid assuming proportions of with are larger than the area
		// TODO Calc in double then after change to int
		int worldWidth = (int) (canvas.getWidth() * worldProportion);
		int worldHeight = (int) (worldWidth / GW.getGridHeight(gameWorld) * GW.getGridWidth(gameWorld));
		int worldStartX = (canvas.getWidth() - worldWidth);
		int worldStartY = (canvas.getHeight() - worldHeight) / 2;
		int cellWidth = worldWidth / GW.getGridWidth(gameWorld);
		int cellHeight = worldHeight / GW.getGridHeight(gameWorld);

		// Vertical lines
		for (int i = 0; i < GW.getGridWidth(gameWorld); i++) {
			g.drawLine(worldStartX + cellWidth * i, worldStartY, worldStartX + cellWidth * i,
					worldStartY + worldHeight);
		}
		// Horizontal lines
		for (int i = 0; i < GW.getGridHeight(gameWorld) + 1; i++) {
			g.drawLine(worldStartX, worldStartY + cellHeight * i, worldStartX + worldWidth,
					worldStartY + cellHeight * i);
		}

		drawCells(g, gameWorld, cellWidth, cellHeight, worldStartX, worldStartY);
	}

	void drawCells(Graphics g, GameWorld gameWorld, int cellWidth, int cellHeight, int worldStartX, int worldStartY) {
		for (int x = 0; x < GW.getGridWidth(gameWorld); x++) {
			for (int y = 0; y < GW.getGridHeight(gameWorld); y++) {
				if (GW.isWall(gameWorld, x, y)) {
					g.setColor(Color.BLACK);
					g.fillRect(worldStartX + cellWidth * x, worldStartY + cellHeight * y, cellWidth, cellHeight);
				} else if (GW.isGoal(gameWorld, x, y)) {
					g.setColor(Color.GREEN);
					g.fillRect(worldStartX + cellWidth * x, worldStartY + cellHeight * y, cellWidth, cellHeight);
				}
			}
		}

	Vector robotPostition = GW.getRobotLocation(gameWorld);
	Direction robotDirection = GW.getRobotDirection(gameWorld);

	double circleRatio = 0.9;
	double rectWidth = 0.2;
	g.setColor(Color.RED);
	g.fillOval( worldStartX + cellWidth*robotPostition.getX() + (int) (cellWidth * (1-circleRatio)) ,worldStartY+cellHeight*robotPostition.getY()+(int)(cellHeight*(1-circleRatio)),(int)(cellWidth*circleRatio),(int)(cellHeight*circleRatio));
	g.setColor(Color.BLACK);switch(robotDirection)
	{
		case RIGHT:
			g.fillRect(worldStartX + cellWidth * robotPostition.getX() + cellWidth / 2,
					worldStartY + cellHeight * robotPostition.getY() + (int) (cellHeight * (1 - rectWidth) / 2),
					cellWidth / 2, (int) (cellHeight * rectWidth));
			break;
		case LEFT:
			g.fillRect(worldStartX + cellWidth * robotPostition.getX(),
					worldStartY + cellHeight * robotPostition.getY() + (int) (cellHeight * (1 - rectWidth) / 2),
					cellWidth / 2, (int) (cellHeight * rectWidth));
			break;
		case UP:
			g.fillRect(worldStartX + cellWidth * robotPostition.getX() + (int) (cellWidth * (1 - rectWidth) / 2),
					worldStartY + cellHeight * robotPostition.getY(), (int) (cellWidth * rectWidth), cellHeight / 2);
			break;
		case DOWN:
			g.fillRect(worldStartX + cellWidth * robotPostition.getX() + (int) (cellWidth * (1 - rectWidth) / 2),
					worldStartY + cellHeight * robotPostition.getY() + (int) (cellHeight * (1 - rectWidth) / 2),
					(int) (cellWidth * rectWidth), cellHeight / 2);
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		errorMessage = "";
		System.out.println(e.getX() + " " + e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		errorMessage = "";
		Vector mousePos = new Vector(e.getX(), e.getY());

		PresentationBlock<?> paletteBlockP = paletteP.GetClickedPaletteBlock(mousePos);
		// Clicked block in palette
		// Create functional copy of paletteBlock and add to programArea
		if (paletteBlockP != null && GC.getAmountOfBlocksLeft(gameController) >= 0) {
			PresentationBlock<?> presentationCopy = BFP.makeCopy(paletteBlockP);
			GC.addBlockToProgramArea(gameController, presentationCopy);
			selectedBlock = presentationCopy;
			System.out.println("New Block made of type: " + BF.getName(BFP.getBlock(selectedBlock) ));
		}

		PresentationBlock<?> programBlockP = programAreaP.getBlockAtPosition(mousePos);
		if (programBlockP != null) {
			selectedBlock = programBlockP;
			GA.disconnect(BFP.getBlock(selectedBlock));
		}

		previousMousePos = mousePos;
		this.mouseDown = true;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Vector mousePos = new Vector(e.getX(), e.getY());

		if (this.selectedBlock != null) {
			// Check for snapping
			// TODO: replace with new snapping code
			boolean snapped = programAreaP.snapBlock(selectedBlock);
			if (!snapped) {
				if (!GC.isTopLevelBlock(gameController, BFP.getBlock(selectedBlock))) {
					GC.addTopLevelBlock(gameController, BFP.getBlock(selectedBlock));
				}
			} else {
				if (GC.isTopLevelBlock(gameController, BFP.getBlock(selectedBlock))) {
					GC.removeTopLevelBlock(gameController, BFP.getBlock(selectedBlock));
				} 
			}

			// Delete if over palette
			int paletteBorder = (int) (panelProportion * canvas.getWidth());
			if (mousePos.getX() < paletteBorder) {
				GC.removeBlockFromProgramArea(gameController, selectedBlock);
				// programAreaP.removeBlock(selectedBlock);

				// TODO: recursively delete all connected blocks
			}

		}

		this.mouseDown = false;
		this.selectedBlock = null;
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (this.mouseDown && this.selectedBlock != null) {
			Vector moveDifference = new Vector(e.getX() - previousMousePos.getX(), e.getY() - previousMousePos.getY());
			BFP.addToPosition(selectedBlock, moveDifference);
			this.previousMousePos = new Vector(e.getX(), e.getY());
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case 27: // Esc
			GC.stopExecution(gameController);
			GW.resetGameWorld(gameWorld);
			break;

		case 115: // F4
			GC.stopExecution(gameController);
			break;

		case 116: // F5
			try {
				GC.execute(gameController);
			} catch (Exception e1) {
				errorMessage = e1.getMessage();
			}
			break;

		case 117: // F6
			GC.setGameWorld(gameController, GW.makeRandomGameWorld(gameWorldWidth, gameWorldHeight));
			break;

		default:
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println(e);

	}
}
