package presentation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import domain.game_world.Direction;
import domain.game_world.GameWorld;
import domain.game_world.Grid;
import domain.game_world.Robot;
import domain.game_world.Vector;
import domain.game_world.cell.Cell;
import domain.game_world.cell.Goal;
import domain.game_world.cell.Wall;

public class Presentation extends Canvas implements MouseListener {
	
	static int width = 800;
	static int height = 600;
	static Canvas canvas;
	
	double panelProportion = 0.2;
	double codeProportion = 0.5;
	double worldProportion = 0.3;
	
	GameWorld gameWorld;
	
	
	
    public static void main(String[] args) {
        JFrame frame = new JFrame("Blockr");
        canvas = new Presentation();
        canvas.setSize(width, height);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }
    
    public Presentation() {
    	Vector[] vectors = new Vector[] {new Vector(0,0), new Vector(5,5), new Vector(4,5)};
    	Cell[] cells = new Cell[] {new Wall(), new Goal(), new Wall()};
    	Grid grid;
		try {
			grid = new Grid(10,10, vectors, cells);
			Vector start = new Vector(4,4);
	    	gameWorld = new GameWorld(grid, start);
	    	gameWorld.setRobot(new Robot(new Vector(6,7), Direction.DOWN));
	    	addMouseListener(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    public void paint(Graphics g) {
    	g.setColor(Color.BLACK);
        g.drawLine((int)(panelProportion * canvas.getWidth()), 0,(int) (panelProportion * canvas.getWidth()), canvas.getHeight());
        g.drawLine(canvas.getWidth() - (int) (worldProportion * canvas.getWidth()), 0, canvas.getWidth() - (int) (worldProportion * canvas.getWidth()), canvas.getHeight());
        
        drawWorld(g, this.gameWorld);
        
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
	public void mouseClicked(MouseEvent arg0) {
		System.out.println(arg0.getX() + " " + arg0.getY());
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
