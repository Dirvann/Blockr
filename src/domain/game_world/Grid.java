package domain.game_world;

import domain.game_world.cell.Cell;
import domain.game_world.cell.EmptyCell;
import domain.game_world.cell.Wall;

public class Grid {

	private Cell[][] grid;
	private int width;
	private int height;
	private Robot robot;
	// always a robot on grid
	// robot is not a cell, but it is part of the grid
	// example: a cell can be empty with the robot on it
	public Grid(int height, int width, Robot robot) {
		this.width = width;
		this.height = height;
		this.grid = new Cell[height][width];
		
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				grid[y][x] = new EmptyCell();
			}
		}
		setRobot(robot);
	}
	
	public Grid(int height, int width, Robot robot,Vector[] locations, Cell[] cells) throws Exception {
		this(height,width,robot);
		setCells(locations, cells);
	}
	
	public void setCells(Vector[] locations,Cell[] cells) throws Exception {
		if(locations.length != cells.length) {
			throw new Exception();
		} else {
			for (int i = 0; i < locations.length; i++) {
				setCell(locations[i],cells[i]);
			}
		}
		
	}
	
	public void setCell(Vector location, Cell cell) throws Exception {
		if (isInBounds(location)) {
			grid[location.getY()][location.getX()] = cell;
		} else {
			throw new Exception();
		}
	}

	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}

	public Cell getCell(int x, int y) throws Exception {
		// TODO create custom exception
		if (isInBounds(x, y)) {
			return grid[y][x];
		} else {
			throw new Exception();
		}
	}
	
	public boolean isInBounds(Vector location) {
		return isInBounds(location.getX(), location.getY());
	}
	
	public boolean isInBounds(int x, int y) {
		if (x < 0 || x >= getWidth()) {
			return false;
		}
		if (y < 0 || y >= getHeight()) {
			return false;
		}
		return true;
	}
	
	public boolean isWall(int x, int y) throws Exception {
		return (getCell(x, y) instanceof Wall);
	}

	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}
	
	
}
