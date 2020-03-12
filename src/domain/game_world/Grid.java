package domain.game_world;

import java.util.Random;

import domain.game_world.cell.*;

/**
 * A class of the grid, containing the cells for the gameworld to use.
 * Each grid has a width, height and a matrix of cells.
 * 
 * @invar The location of each cell is inside the gird [height][width]
 *
 */
public class Grid {

	private Cell[][] grid;
	private int width;
	private int height;
	/**
	 * Initialize a new grid with given width and height, all cells are empty.
	 * 
	 * @param height
	 * @param width
	 * @param grid
	 * @post The height of this new Grid is equal to the given height.
	 * @post The width of this new Grid is equal to the given width.
	 * @post The grid is filled with empty cells.
	 */
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
	/**
	 * Initialize a new grid with given width and height. 
	 * set given cells in grid at given locations all other cells are empty.
	 * 
	 * @param height
	 * @param width
	 * @param locations
	 * @param cells
	 * @post The height of this new Grid is equal to the given height.
	 * @post The width of this new Grid is equal to the given width.
	 * @post The given cells are set in the grid at the corresponding locations
	 * 		 all the other cells are empty.
	 * @throws Exception
	 */
	public Grid(int height, int width,Vector[] locations, Cell[] cells) throws Exception {
		this(height,width);
		setCells(locations, cells);
	}
	
	/**
	 * Create a default empty 5x5 grid with the goal at (4,4).
	 * 
	 * @post The goal is located at grid[4][4]
	 * @post All cells except the goal cell are empty
	 */
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
	
	static public Grid randomGrid(int width, int height) {
		Grid result = new Grid(width, height);
		Random rand = new Random();
		
		for (int y = 0; y < result.getHeight(); y++) {
			for (int x = 0; x < result.getWidth(); x++) {
				if (rand.nextInt(100) < 30) {
					try {
						result.setCell(new Vector(x, y), new Wall());
					} catch (Exception e) {
						System.out.println("This exception should not happen. see randomGrid");
						e.printStackTrace();
					}
				}
			}
		}
		
		int goalX = rand.nextInt(width);
		int goalY = rand.nextInt(height);
		
		try {
			result.setCell(new Vector(goalX, goalY), new Goal());
		} catch (Exception e) {
			System.out.println("This exception also should not happen. See randomGrid");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * @return width of the grid.
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @return height of the grid
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Set the given cell at a given location.
	 * 
	 * @param location
	 * @param cell
	 * @post The given cell is set in the grid at the corresponding location.
	 * @throws Exception
	 * 
	 */
	public void setCell(Vector location, Cell cell) throws Exception {
		if (isInBounds(location)) {
			grid[location.getY()][location.getX()] = cell;
		} else {
			throw new Exception();
		}
	}
	/**
	 * Set the given cells at given locations.
	 * 
	 * @param locations
	 * @param cells
	 * @post The given cells are set in the grid at the corresponding locations.
	 * @throws Exception
	 * 
	 */
	public void setCells(Vector[] locations,Cell[] cells) throws Exception {
		if(locations.length != cells.length) {
			throw new Exception();
		} else {
			for (int i = 0; i < locations.length; i++) {
				setCell(locations[i],cells[i]);
			}
		}
		
	}
	/**
	 * Get the cell at given location.
	 * 
	 * @param location
	 * @return the cell at given location
	 * @throws Exception
	 */
	public Cell getCell(Vector location) throws Exception {
		return getCell(location.getX(), location.getY());
	}
	/**
	 * Get cell at given x,y coordinates.
	 * 
	 * @param x
	 * @param y
	 * @return the cell at given x,y coordinates
	 * @throws Exception
	 */
	public Cell getCell(int x, int y) throws Exception {
		// TODO create custom exception
		if (isInBounds(x, y)) {
			return grid[y][x];
		} else {
			throw new Exception();
		}
	}
	/**
	 * Checks if the given location is in the boundaries of the grid.
	 * 
	 * @param location
	 * @return true if the location isInBounds()
	 */
	public boolean isInBounds(Vector location) {
		return isInBounds(location.getX(), location.getY());
	}
	/**
	 * Check if the given x,y coordinates are in boundaries of the grid.
	 * 
	 * @param x
	 * @param y
	 * @return false if x is lower than 0 or bigger then or equal to width of the grid.
	 * 		   false if y is lower than 0 or bigger then or equal to height of the grid.
	 * 		   otherwise return true.
	 */
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
