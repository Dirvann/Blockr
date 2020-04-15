package presentation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import domain.ImplementationGameController;
import domain.Vector;
import gameworld.Direction;
import gameworld.GameWorld;
import gameworld.ImplementationGameWorld;


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
		drawGameWorld(g, GC.getGameWorld(blockrPanel.getGameController()));
	}
	
	public void drawGameWorld(Graphics g, GameWorld gameWorld) {
		int worldWidth = this.getWidth();
		int worldHeight = worldWidth / GW.getGridWidth(gameWorld) * GW.getGridHeight(gameWorld);
		
		int cellWidth = worldWidth / GW.getGridWidth(gameWorld);
		int cellHeight = worldHeight /GW.getGridHeight(gameWorld);
		
		
		// Vertical lines
		for (int i = 0; i < GW.getGridWidth(gameWorld) +1; i++) {
			g.drawLine(i * cellWidth, 0, i * cellWidth, worldHeight);
		}
		
		// Horizontal lines
		for (int i = 0; i < GW.getGridHeight(gameWorld) +1; i++) {
			g.drawLine(0, i * cellHeight, worldWidth, i * cellHeight);
		}
		
		drawCells(g, gameWorld, cellWidth, cellHeight);
	}
	
	void drawCells(Graphics g, GameWorld gameWorld, int cellWidth, int cellHeight) {
		for (int x = 0; x < GW.getGridWidth(gameWorld); x++) {
			for (int y = 0; y < GW.getGridHeight(gameWorld); y++) {
				if (GW.isWall(gameWorld, x, y)) {
					g.setColor(Color.BLACK);
					g.fillRect(cellWidth * x, cellHeight * y, cellWidth, cellHeight);
				} else if (GW.isGoal(gameWorld, x, y)) {
					g.setColor(Color.GREEN);
					g.fillRect(cellWidth * x, cellHeight * y, cellWidth, cellHeight);
				}
			}
		}
		
		Vector robotPostition = GW.getRobotLocation(gameWorld);
		Direction robotDirection = GW.getRobotDirection(gameWorld);

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
