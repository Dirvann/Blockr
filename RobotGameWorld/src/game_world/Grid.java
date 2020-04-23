package game_world;

import java.util.Random;

import exceptions.domainExceptions.OutOfBoundsException;
import game_world.cell.*;

/**
 * A class of the Grid, containing the cells for the gameworld to use.
 * Each Grid has a width, height and a matrix of cells.
 * 
 * @invar The location of each cell is inside the Gird [height][width]
 *
 */
public class Grid {

	private Cell[][] grid;
	private int width;
	private int height;
	/**
	 * Initialize a new Grid with given width and height, all cells are empty.
	 * 
	 * @param height
	 * 		  The height for this new Grid.
	 * @param width
	 * 		  The width for this new Grid.
	 * @post  The height of this new Grid is equal to the given height.
	 * 		  | new.getHeight() = height
	 * @post  The width of this new Grid is equal to the given width.
	 * 		  | new.getWidth() = width
	 * @post  The Grid is filled with empty cells.
	 * 		  | if (isInBounds(vector))
	 * 		  |   then getCell(vector) instanceof Emptycell
	 */
	protected Grid(int width, int height) {
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
	 * Initialize a new Grid with given width and height. 
	 * set given cells in Grid at given locations all other cells are empty.
	 * 
	 * @param height
	 * 		  The height for this new Grid.
	 * @param width
	 * 		  The width for this new Grid.
	 * @param locations
	 * 		  The list of locations associated with the given cells.
	 * @param cells
	 * 		  The list of cells with associated locations.
	 * @post  The height of this new Grid is equal to the given height.
	 * 		  | new.getHeight() = height
	 * @post  The width of this new Grid is equal to the given width.
	 * 		  | new.getWidth() = width
	 * @post  The given cells are set in the Grid at the corresponding locations
	 * 		  all the other cells are empty.
	 * 		  | if (isInBounds(vector) && !locations.contains(vector))
	 * 		  |   then getCell(vector) instanceof Emptycell 
	 * @throws Exception
	 */
	protected Grid(int height, int width,Vector[] locations, Cell[] cells) throws Exception {
		this(height,width);
		setCells(locations, cells);
	}
	
	/**
	 * Create a default empty 5x5 Grid with the goal at (4,4).
	 * 
	 * @post The height of this new Grid is equal to 5.
	 * 	     | new.getHeight() = 5
	 * @post The width of this new Grid is equal to 5.
	 * 		 | new.getWidth() = 5
	 * @post The goal is located at Grid[4][4].
	 * 		 | getCell(4,4) instanceof Goal
	 * @post All cells except the goal cell are empty
	 * 		 | if (isInBounds(vector) && !(getCell(vector) instanceof Goal))
	 * 		 |   then getCell(vector) instanceof Emptycell
	 */
	protected Grid() {
		this(5, 5);
		try {
			setCell(new Vector(4, 4), new Goal());
		} catch (OutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Return a new Grid with given width and height.
	 * Some cells are empty or a wall. One cell in the Grid is a Goal.
	 * 
	 * @param height
	 * 		  The height for this new Grid.
	 * @param width
	 * 		  The width for this new Grid.
	 * @post  The height of this new Grid is equal to the given height.
	 * 		  | new.getHeight() = height
	 * @post  The width of this new Grid is equal to the given width.
	 * 		  | new.getWidth() = width
	 * @post  There are no cells outside of the width and height.
	 * 		  | if (isInBounds(vector))
	 * 		  |   then getCell(vector) instanceof Cell 
	 * @post  The border cells of new Grid are Walls.
	 * 		  | getCell(0,_) instance of Wall 
	 * 		  | getCell(_,0) instance of Wall
	 * 		  | getCell(getWidth-1,_) instance of Wall
	 * 		  | getCell(_,getHeight-1) instance of Wall
	 * @return a new Grid with random cells
	 */
	static protected Grid randomGrid(int width, int height) {
		Grid result = new Grid(width, height);
		Random rand = new Random();
		
		for (int y = 0; y < result.getHeight(); y++) {
			for (int x = 0; x < result.getWidth(); x++) {
				if (rand.nextInt(100) < 25) {
					try {
						result.setCell(new Vector(x, y), new Wall());
					} catch (OutOfBoundsException e) {
						System.out.println("This exception should not happen. see randomGrid");
						e.printStackTrace();
					}
				}
			}
		}
		
		result.setEdgesToWall();
		
		int goalX = 1 + rand.nextInt(width - 2);
		int goalY = 1 + rand.nextInt(height - 2);
		
		try {
			result.setCell(new Vector(goalX, goalY), new Goal());
		} catch (OutOfBoundsException e) {
			System.out.println("This exception also should not happen. See randomGrid");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * The width of the Grid.
	 * 
	 * @return width of the Grid.
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * The height of the Grid.
	 * 
	 * @return height of the Grid.
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Set the given cell at a given location.
	 * 
	 * @param location
	 * 		  The location in the Grid where the cell should be set.
	 * @param cell
	 * 		  The cell to be placed in a given location.
	 * @post  The given cell is set in the Grid at the corresponding location.
	 * 		  | new.getCell(location) = cell
	 * @throws Exception
	 * 		   The given location is out of the Grid.
	 */
	protected void setCell(Vector location, Cell cell) throws OutOfBoundsException {
		if (isInBounds(location)) {
			grid[location.getY()][location.getX()] = cell;
		} else {
			throw new OutOfBoundsException();
		}
	}
	/**
	 * Set the given cells at given locations.
	 * 
	 * @param locations
	 * 		  The list of locations in the Grid where the associated cells should be set.
	 * @param cells
	 * 		  The cells to be placed at the associated locations.
	 * @post  The given cells are set in the Grid at the corresponding locations.
	 * 		  | new.getCell(location[x]) = cell[x]
	 * @throws Exception
	 * 		   One of the given locations is out of the Grid.
	 */
	protected void setCells(Vector[] locations,Cell[] cells) throws Exception {
		if(locations.length != cells.length) {
			throw new Exception();
		} else {
			for (int i = 0; i < locations.length; i++) {
				setCell(locations[i],cells[i]);
			}
		}
		
	}
	/**
	 * The cell at given location.
	 * 
	 * @param  location
	 * 		   The location of the requested cell.
	 * @return the cell at given location
	 * 		   | result = getCell(location.getX(),location.getY())
	 * @throws Exception
	 * 		   The given vector lies out of the Grid.
	 */
	protected Cell getCell(Vector location) throws OutOfBoundsException {
		return getCell(location.getX(), location.getY());
	}
	/**
	 * The cell at given x,y coordinates.
	 * 
	 * @param x
	 * 		  The x coordinate of the vector.
	 * @param y
	 * 		  The y coordinate of the vector.
	 * @return the cell at given x,y coordinates
	 * 		   | result = getCell(x,y)
	 * @throws Exception
	 * 		   The given x and y values don't lie inside of the Grid.
	 */
	public Cell getCell(int x, int y) throws OutOfBoundsException {
		if (isInBounds(x, y)) {
			return grid[y][x];
		} else {
			throw new OutOfBoundsException();
		}
	}
	/**
	 * Checks if the given location is in the boundaries of the Grid.
	 * 
	 * @param  location
	 * 		   The given location to check.
	 * @return true if the location isInBounds()
	 * 		   | result = isInBounds(location.getX(), location.getY())
	 */
	protected boolean isInBounds(Vector location) {
		return isInBounds(location.getX(), location.getY());
	}
	/**
	 * Check if the given x,y coordinates are in boundaries of the Grid.
	 * 
	 * @param x
	 * 		  The given x coordinate to check.
	 * @param y
	 * 		  The given y coordinate to check.
	 * @return false if x is lower than 0 or bigger then or equal to width of the Grid.
	 * 		   false if y is lower than 0 or bigger then or equal to height of the Grid.
	 * 		   otherwise return true.
	 *		   | result = !((x < 0 || x >= getWidth()) && (y < 0 || y >= getHeight()))
	 */
	protected boolean isInBounds(int x, int y) {
		if (x < 0 || x >= getWidth()) {
			return false;
		}
		if (y < 0 || y >= getHeight()) {
			return false;
		}
		return true;
	}
	/**
	 * Set all cells at the border of the Grid to Walls.
	 * 
	 * @post  The border cells of the Grid are Walls.
	 * 		  | getCell(0,_) instance of Wall 
	 * 		  | getCell(_,0) instance of Wall
	 * 		  | getCell(getWidth-1,_) instance of Wall
	 * 		  | getCell(_,getHeight-1) instance of Wall
	 */
	public void setEdgesToWall() {
		try {
			// Set top and bottom horizontal edges
			for (int i = 0; i < this.getWidth(); i++) {
				// Top row
				this.setCell(new Vector(i, 0), new Wall());
				// Bottom row
				this.setCell(new Vector(i, this.getHeight() -1), new Wall());
			}
			
			// Set left and right vertical edges
			for (int i = 0; i < this.getHeight(); i++) {
				// Left column
				this.setCell(new Vector(0, i), new Wall());
				// Right column
				this.setCell(new Vector(this.getWidth() -1, i), new Wall());
			}
			
		} catch (OutOfBoundsException e) {
			e.printStackTrace();
		}
	}
}
