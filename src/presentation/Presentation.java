package presentation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import domain.GameController;
import domain.block.MoveForward;
import domain.block.abstract_classes.ActionBlock;
import domain.block.abstract_classes.ChainConditionBlock;
import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.block.block_types.Block;
import domain.block.block_types.ConditionBlock;
import domain.block.block_types.SequenceBlock;
import domain.game_world.Direction;
import domain.game_world.GameWorld;
import domain.game_world.Grid;
import domain.game_world.Robot;
import domain.game_world.Vector;
import domain.game_world.cell.Cell;
import domain.game_world.cell.Goal;
import domain.game_world.cell.Wall;
import facade.Implementation;
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

	Vector mouseDownStartPosition = new Vector(0, 0);
	boolean mouseDown = false;
	PresentationBlock<?> selectedBlock = null;
	Vector previousMousePos = null;

	GameController gameController;
	PalettePresentation paletteP;
	ProgramAreaPresentation programAreaP;
	Implementation GA; // GameInterface

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

		GA = new Implementation();

		gameController = GA.makeGameController();
		paletteP = new PalettePresentation();
		programAreaP = new ProgramAreaPresentation();
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

		gameController.setGameWorld(new GameWorld(10, 10));
		gameWorld = gameController.getGameWorld();

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

		// If (!gameController.maxNumberBlocksReached()) {
		paletteP.paint(g);
		programAreaP.paint(g);
		
		Block nextToExecute = gameController.getNextBlockToExecute();
		if (nextToExecute != null) {
			nextToExecute.getPresentationBlock().highLight(g);
		}

		drawWorld(g, gameController.getGameWorld());
	}

	public void drawWorld(Graphics g, GameWorld gameWorld) {
		// drawing grid assuming proportions of with are larger than the area
		// TODO Calc in double then after change to int
		int worldWidth = (int) (canvas.getWidth() * worldProportion);
		Grid grid = gameWorld.getGrid();
		int worldHeight = (int) (worldWidth / grid.getWidth() * grid.getHeight());

		int worldStartX = (canvas.getWidth() - worldWidth);
		int worldStartY = (canvas.getHeight() - worldHeight) / 2;
		int cellWidth = worldWidth / grid.getWidth();
		int cellHeight = worldHeight / grid.getHeight();

		// Vertical lines
		for (int i = 0; i < grid.getWidth(); i++) {
			g.drawLine(worldStartX + cellWidth * i, worldStartY, worldStartX + cellWidth * i,
					worldStartY + worldHeight);
		}
		// Horizontal lines
		for (int i = 0; i < grid.getHeight() + 1; i++) {
			g.drawLine(worldStartX, worldStartY + cellHeight * i, worldStartX + worldWidth,
					worldStartY + cellHeight * i);
		}

		drawCells(g, gameWorld, cellWidth, cellHeight, worldStartX, worldStartY);
	}

	void drawCells(Graphics g, GameWorld gameWorld, int cellWidth, int cellHeight, int worldStartX, int worldStartY) {
		Grid grid = gameWorld.getGrid();
		for (int x = 0; x < grid.getWidth(); x++) {
			for (int y = 0; y < grid.getHeight(); y++) {
				try {
					if (grid.getCell(x, y) != null) {
						Cell c = grid.getCell(x, y);
						if (c instanceof Wall) {
							g.setColor(Color.BLACK);
							g.fillRect(worldStartX + cellWidth * x, worldStartY + cellHeight * y, cellWidth,
									cellHeight);
						} else if (c instanceof Goal) {
							g.setColor(Color.GREEN);
							g.fillRect(worldStartX + cellWidth * x, worldStartY + cellHeight * y, cellWidth,
									cellHeight);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		Vector robotPostition = gameWorld.getRobot().getLocation();
		Direction robotDirection = gameWorld.getRobot().getDirection();

		double circleRatio = 0.9;
		double rectWidth = 0.2;
		g.setColor(Color.RED);
		g.fillOval(worldStartX + cellWidth * robotPostition.getX() + (int) (cellWidth * (1 - circleRatio)),
				worldStartY + cellHeight * robotPostition.getY() + (int) (cellHeight * (1 - circleRatio)),
				(int) (cellWidth * circleRatio), (int) (cellHeight * circleRatio));
		g.setColor(Color.BLACK);
		switch (robotDirection) {
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
		Vector mousePos = new Vector(e.getX(), e.getY());

		PresentationBlock<?> paletteBlockP = paletteP.GetClickedPaletteBlock(mousePos);
		// Clicked block in palette
		// Create functional copy of paletteBlock and add to programArea
		if (paletteBlockP != null) {
			PresentationBlock<?> presentationCopy;
			presentationCopy = paletteBlockP.getNewBlockOfThisType();
			programAreaP.addBlock(presentationCopy);
			selectedBlock = presentationCopy;
			System.out.println("presentationCopy.getBlock == null: " + presentationCopy.getBlock() == null);
		}

		PresentationBlock<?> programBlockP = programAreaP.getBlockAtPosition(mousePos);
		if (programBlockP != null) {
			selectedBlock = programBlockP;
			GA.disconnect(selectedBlock.getBlock());
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
			boolean snapped = false;
			Vector snapLocation = selectedBlock.getPossibleSnapLocation();
			System.out.println("Checking location (" + snapLocation.getX() + ", " + snapLocation.getY() + ")");
			PresentationBlock blockToGetSnappedTo = programAreaP.snappebleBlock(snapLocation);
			if (blockToGetSnappedTo != null) {
				System.out.println("SNAP!");
				if (selectedBlock.getBlock() instanceof ConditionBlock) {
					if (blockToGetSnappedTo.getBlock() instanceof SingleSurroundingBlock
							&& blockToGetSnappedTo.collidesWithPosition(snapLocation)) {
						((SingleSurroundingBlock) blockToGetSnappedTo.getBlock())
								.setConditionBlock((ConditionBlock) selectedBlock.getBlock());
						snapped = true;
					} else if (blockToGetSnappedTo.getBlock() instanceof ChainConditionBlock) {
						((ChainConditionBlock) blockToGetSnappedTo.getBlock())
								.setNextCondition((ConditionBlock) selectedBlock.getBlock());
						snapped = true;
					}
				} else if (selectedBlock.getBlock() instanceof SequenceBlock) {
					if (blockToGetSnappedTo instanceof ActionBlockPresentation) {
						((ActionBlock) blockToGetSnappedTo.getBlock())
								.setNextBlock((SequenceBlock) selectedBlock.getBlock());
						snapped = true;
					} else if (blockToGetSnappedTo instanceof SingleSurroundBlockPresentation) {
						if (blockToGetSnappedTo.collidesWithPosition(snapLocation)) {
							((SingleSurroundingBlock) blockToGetSnappedTo.getBlock())
									.setBodyBlock((SequenceBlock) selectedBlock.getBlock());
							snapped = true;
						} else {
							((SingleSurroundingBlock) blockToGetSnappedTo.getBlock())
									.setNextBlock((SequenceBlock) selectedBlock.getBlock());
							snapped = true;
						}
					}
				}
			}

			if (!snapped) {
				if (!gameController.isTopLevelBlock(selectedBlock.getBlock())) {
					gameController.addTopLevelBlock(selectedBlock.getBlock());
				}
			}

			// Delete if over palette
			int paletteBorder = (int) (panelProportion * canvas.getWidth());
			if (mousePos.getX() < paletteBorder) {
				if (gameController.isTopLevelBlock(selectedBlock.getBlock())) {
					gameController.removeTopLevelBlock(selectedBlock.getBlock());
				}

				selectedBlock.getBlock().removeFromProgramAreaPresentationRecursively(programAreaP);
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
			// selectedBlock.setPositionByDifference(moveDifference);
			selectedBlock.setPositionRecursivelyByDifference(moveDifference);
			this.previousMousePos = new Vector(e.getX(), e.getY());
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case 27: 	// Esc
			gameController.stopExecution();
			gameController.resetWorld();
			break;

		case 115: 	// F4
			gameController.stopExecution();
			break;

		case 116: 	// F5
			try {
				gameController.execute();
			} catch (Exception e1) {
				System.out.println("Execute in keyPressed failed");
			}
			break;

		case 117: // F6
			gameController.setGameWorld(new GameWorld(15, 10));
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
