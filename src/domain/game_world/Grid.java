package domain.game_world;

import domain.game_world.cell.Cell;
import domain.game_world.cell.EmptyCell;
import domain.game_world.cell.Wall;

public class Grid {

	private Cell[][] grid;
	private int width;
	private int height;
	
	public Grid(int height, int width) {
		this.width = width;
		this.height = height;
		grid = new Cell[height][width];
		
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				grid[y][x] = new EmptyCell();
			}
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
		if (x < 0 || x >= getWidth()) {
			throw new Exception("getCell out of bounds (x greater than width)");
		}
		if (y < 0 || y >= getHeight()) {
			throw new Exception("getCell out of bounds (y greater than height)");
		}
		return grid[y][x];
	}
	
	public boolean isWall(int x, int y) throws Exception {
		return (getCell(x, y) instanceof Wall);
	}
	
	
}
