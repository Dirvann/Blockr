package presentation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import domain.GameController;
import domain.ProgramArea;
import domain.block.IfBlock;
import domain.block.MoveForward;
import domain.block.TurnLeft;
import domain.block.TurnRight;
import domain.block.block_types.Block;
import domain.block.block_types.SequenceBlock;
import domain.game_world.Direction;
import domain.game_world.GameWorld;
import domain.game_world.Grid;
import domain.game_world.Robot;
import domain.game_world.Vector;
import domain.game_world.cell.Cell;
import domain.game_world.cell.Goal;
import domain.game_world.cell.Wall;
import presentation.block.PresentationBlock;

public class Presentation extends Canvas implements MouseListener, MouseMotionListener {
	
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
	
	Block[] blockList;
	
	GameWorld gameWorld;
	
	Vector mouseDownStartPosition = new Vector(0,0);
	boolean mouseDown = false;
	Block selectedBlock = null;
	Vector previousMousePos = null;
	GameController gameController;
	ProgramArea programArea;
	
	
	
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
    	
    	
    	gameController = new GameController();
    	
    	programArea = gameController.getProgramArea();
    	
    	SequenceBlock left = new TurnLeft(new Vector(300,300));
    	
    	left.setNextBlock(new TurnRight(new Vector(300,300)));
    	
    	//programArea.addTopLevelBlock(left);
    	
    	
    	IfBlock ifBlock = new IfBlock(new Vector(200,200));
    	
    	ifBlock.setBodyBlock(left);
    	
    	programArea.addTopLevelBlock(ifBlock);
    	
    	
    	
    	//programArea.addTopLevelBlock(new MoveForward(new Vector(100,300)));
    	//programArea.addTopLevelBlock(new IfBlock(new Vector(300,100)));
    	
    	blockList = new Block[] {new TurnLeft(new Vector(300,300))};
    	
    	
    	Vector[] vectors = new Vector[] {new Vector(0,0), new Vector(5,5), new Vector(4,5)};
    	Cell[] cells = new Cell[] {new Wall(), new Goal(), new Wall()};
    	Grid grid;
		try {
			grid = new Grid(10,10, vectors, cells);
			Vector start = new Vector(4,4);
	    	gameController.setGameWorld(new GameWorld(grid, start));
	    	gameWorld = gameController.getGameWorld();
	    	gameWorld.setRobot(new Robot(new Vector(6,7), Direction.DOWN));
	    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addMouseListener(this);
    	addMouseMotionListener(this);
    	
    }

    public void paint(Graphics g) {
    	g.setColor(Color.BLACK);
        g.drawLine((int)(panelProportion * canvas.getWidth()), 0,(int) (panelProportion * canvas.getWidth()), canvas.getHeight());
        g.drawLine(canvas.getWidth() - (int) (worldProportion * canvas.getWidth()), 0, canvas.getWidth() - (int) (worldProportion * canvas.getWidth()), canvas.getHeight());
        
        drawWorld(g, this.gameWorld);
        
        
        
     
        drawBlocks(g, gameController.getProgramArea().getAllBlocks());
    }
    
    public void drawWorld(Graphics g, GameWorld gameWorld) {
    	// drawing grid assuming proportions of with are larger than the area
    	//TODO Calc in double then after change to int
    	int worldWidth = (int)(canvas.getWidth() * worldProportion);
    	Grid grid = gameWorld.getGrid();
    	int worldHeight = (int)(worldWidth / grid.getWidth() * grid.getHeight());
    	
    	int worldStartX = (canvas.getWidth() - worldWidth);
    	int worldStartY = (canvas.getHeight()- worldHeight)/2;
    	int cellWidth = worldWidth/grid.getWidth();
    	int cellHeight = worldHeight/grid.getHeight();
    	
    	// Vertical lines
    	for(int i = 0; i < grid.getWidth() ; i++) {
    		g.drawLine(worldStartX + cellWidth*i, worldStartY, worldStartX + cellWidth * i, worldStartY + worldHeight);
    	}
    	// Horizontal lines
    	for(int i = 0; i < grid.getHeight() + 1; i++) {
    		g.drawLine(worldStartX, worldStartY + cellHeight * i, worldStartX + worldWidth, worldStartY + cellHeight*i);
    	}
    	
    	drawCells(g, gameWorld, cellWidth, cellHeight, worldStartX, worldStartY);
    	
    	
    }
    
    private void drawBlocks(Graphics g, List<Block> blockList) {
		for(Block b: blockList) {
			b.getPresentationBlock().draw(g);
		}
		
	}

	void drawCells(Graphics g, GameWorld gameWorld, int cellWidth, int cellHeight, int worldStartX, int worldStartY) {
    	Grid grid = gameWorld.getGrid();
    	for(int x = 0; x < grid.getWidth(); x++) {
    		for(int y = 0; y < grid.getHeight(); y++) {
    			try {
					if(grid.getCell(x,y) != null) {
						Cell c = grid.getCell(x,y);
						if(c instanceof Wall) {
							g.setColor(Color.BLACK);
							g.fillRect(worldStartX + cellWidth*x, worldStartY + cellHeight*y, cellWidth, cellHeight);
						} else if (c instanceof Goal) {
							g.setColor(Color.GREEN);
							g.fillRect(worldStartX + cellWidth*x, worldStartY + cellHeight*y, cellWidth, cellHeight);
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
    	g.fillOval(worldStartX + cellWidth * robotPostition.getX() + (int)(cellWidth * (1-circleRatio)), worldStartY + cellHeight * robotPostition.getY() + (int)(cellHeight *(1-circleRatio)), (int)(cellWidth*circleRatio), (int)(cellHeight*circleRatio));
    	g.setColor(Color.BLACK);
    	switch(robotDirection) {
    	case RIGHT:
    		g.fillRect(worldStartX + cellWidth * robotPostition.getX() + cellWidth / 2, worldStartY + cellHeight * robotPostition.getY() + (int)(cellHeight *(1-rectWidth)/2), cellWidth/2, (int)(cellHeight * rectWidth));
    		break;
    	case LEFT:
    		g.fillRect(worldStartX + cellWidth * robotPostition.getX(), worldStartY + cellHeight * robotPostition.getY() + (int)(cellHeight *(1-rectWidth)/2), cellWidth/2, (int)(cellHeight * rectWidth));
    		break;
    	case UP:
    		g.fillRect(worldStartX + cellWidth * robotPostition.getX() + (int)(cellWidth *(1-rectWidth)/2), worldStartY + cellHeight * robotPostition.getY(), (int)(cellWidth * rectWidth), cellHeight/2);
    		break;
    	case DOWN:
    		g.fillRect(worldStartX + cellWidth * robotPostition.getX() + (int)(cellWidth *(1-rectWidth)/2), worldStartY + cellHeight * robotPostition.getY() + (int)(cellHeight *(1-rectWidth)/2), (int)(cellWidth * rectWidth), cellHeight/2);
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
		Vector p = this.blockList[0].getPresentationBlock().getPosition();
		int width = PresentationBlock.getBlockWidth();
		int height = PresentationBlock.getBlockHeight();
		
		if(e.getX() > p.getX() && e.getX() < (width + p.getX()) && e.getY() > p.getY() && e.getY() < (p.getY() + height)) {
			this.selectedBlock = this.blockList[0];
		}
		
		Vector mousePos = new Vector(e.getX(), e.getY());
		
		for(Block block: gameController.getProgramArea().getAllBlocks()) {
			if(block.getPresentationBlock().collidesWithPosition(mousePos)) {
				this.selectedBlock = block;
				break;
			}
		}
		previousMousePos = mousePos;
		this.mouseDown = true;
		
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.mouseDown = false;
		this.selectedBlock = null;
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(this.mouseDown && this.selectedBlock != null) {
			PresentationBlock<?> pres = this.selectedBlock.getPresentationBlock();
			Vector newPos = new Vector(pres.getPosition().getX() + e.getX() - this.previousMousePos.getX(),pres.getPosition().getY() + e.getY() - this.previousMousePos.getY());
			pres.setPosition(newPos);
			this.previousMousePos = new Vector(e.getX(), e.getY());
			repaint();
		}
	}
}
