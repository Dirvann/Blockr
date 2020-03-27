package presentation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import domain.game_world.Direction;
import domain.game_world.GameWorld;
import domain.game_world.Grid;
import domain.game_world.Vector;
import domain.game_world.cell.Cell;
import domain.game_world.cell.Goal;
import domain.game_world.cell.Wall;

public class GameWorldCanvas extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5278879530185183350L;
	
	private BlockrPanel blockrPanel;
	
	public GameWorldCanvas(BlockrPanel blockrPanel) {
		this.blockrPanel = blockrPanel;
	}
	
	public void paint(Graphics g) {
		drawGameWorld(g, blockrPanel.getGameController().getGameWorld());
	}
	
	public void drawGameWorld(Graphics g, GameWorld gameWorld) {
		Grid grid = gameWorld.getGrid();
		
		int worldWidth = this.getWidth();
		int worldHeight = worldWidth / grid.getWidth() * grid.getHeight();
		
		int cellWidth = worldWidth / grid.getWidth();
		int cellHeight = worldHeight /grid.getHeight();
		
		
		// Vertical lines
		for (int i = 0; i < grid.getWidth() +1; i++) {
			g.drawLine(i * cellWidth, 0, i * cellWidth, worldHeight);
		}
		
		// Horizontal lines
		for (int i = 0; i < grid.getHeight() +1; i++) {
			g.drawLine(0, i * cellHeight, worldWidth, i * cellHeight);
		}
		
		drawCells(g, gameWorld, cellWidth, cellHeight);
	}
	
	private void drawCells(Graphics g, GameWorld gameWorld, int cellWidth, int cellHeight) {
		Grid grid = gameWorld.getGrid();
		for (int x = 0; x < grid.getWidth(); x++) {
			for (int y = 0; y < grid.getHeight(); y++) {
				try {	
					if (grid.getCell(x, y) != null) {
						Cell c = grid.getCell(x, y);
						if (c instanceof Wall) {
							g.setColor(Color.BLACK);
							g.fillRect(cellWidth * x, cellHeight * y, cellWidth, cellHeight);
						} else if (c instanceof Goal) {
							g.setColor(Color.GREEN);
							g.fillRect(cellWidth * x +1, cellHeight * y +1, cellWidth -1, cellHeight -1);
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
		g.fillOval(cellWidth * robotPostition.getX() + (int) (cellWidth * (1 - circleRatio)),
				 cellHeight * robotPostition.getY() + (int) (cellHeight * (1 - circleRatio)),
				(int) (cellWidth * circleRatio), (int) (cellHeight * circleRatio));
		g.setColor(Color.BLACK);
		switch (robotDirection) {
		case RIGHT:
			g.fillRect(cellWidth * robotPostition.getX() + cellWidth / 2,
					cellHeight * robotPostition.getY() + (int) (cellHeight * (1 - rectWidth) / 2),
					cellWidth / 2, (int) (cellHeight * rectWidth));
			break;
		case LEFT:
			g.fillRect(cellWidth * robotPostition.getX(),
					cellHeight * robotPostition.getY() + (int) (cellHeight * (1 - rectWidth) / 2),
					cellWidth / 2, (int) (cellHeight * rectWidth));
			break;
		case UP:
			g.fillRect(cellWidth * robotPostition.getX() + (int) (cellWidth * (1 - rectWidth) / 2),
					cellHeight * robotPostition.getY(), (int) (cellWidth * rectWidth), cellHeight / 2);
			break;
		case DOWN:
			g.fillRect(cellWidth * robotPostition.getX() + (int) (cellWidth * (1 - rectWidth) / 2),
					cellHeight * robotPostition.getY() + (int) (cellHeight * (1 - rectWidth) / 2),
					(int) (cellWidth * rectWidth), cellHeight / 2);
			break;
		}
	}

}
