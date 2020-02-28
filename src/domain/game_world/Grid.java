package domain.game_world;

import domain.game_world.cell.*;


// Grid contains cells for a GameWorld to use
public class Grid {

	private Cell[][] grid;
	private int width;
	private int height;

	public Grid(int height, int width) {
		this.width = width;
		this.height = height;
		this.grid = new Cell[height][width];
		
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				grid[y][x] = new EmptyCell();
			}
		}
	}
	
	public Grid(int height, int width,Vector[] locations, Cell[] cells) throws Exception {
		this(height,width);
		setCells(locations, cells);
	}
	
	// Default 5x5 empty grid with goal at (4, 4)
	public Grid() {
		this(5, 5);
		try {
			setCell(new Vector(4, 4), new Goal());
		} catch (Exception e) {
			System.out.println("Out of bounds thrown at placing of goal block in default grid.");
			System.out.println("This should never happen so something is very wrong.");
			e.printStackTrace();
		}
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void setCell(Vector location, Cell cell) throws Exception {
		if (isInBounds(location)) {
			grid[location.getY()][location.getX()] = cell;
		} else {
			throw new Exception();
		}
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

	public Cell getCell(Vector location) throws Exception {
		return getCell(location.getX(), location.getY());
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
	
}
