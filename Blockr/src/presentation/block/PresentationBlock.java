package presentation.block;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import command.Command;
import command.ConnectCommand;
import domain.GameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
import game_world.api.Vector;

/**
 * Handles the UI drawing and interaction for a given block type.
 * 
 * @version 4.0
 * @author Andreas Awouters 
 * 	       Thomas Van Erum 
 * 		   Dirk Vanbeveren 
 * 		   Geert Wesemael
 *
 */
public abstract class PresentationBlock<T extends Block> {

	private static final int blockWidth = 100;
	private static final int blockHeight = 20;

	private static final int blockSideWidth = 20;

	private static double snapDistance = 14;

	private static int plugHeight = 4;
	private static int plugWidth = 8;

	private static Color backgroundColor = Color.WHITE;
	
	private static final Font font = new Font("Arial", Font.PLAIN, (int) (blockHeight * 0.7));

	private T block;

	private Vector position;

	private ImplementationBlock blockFunctions = new ImplementationBlock();
	
	protected PresentationBlock(Vector pos, T block) {
		this.position = pos;
		this.block = block;
		blockFunctions.setPresentationBlock(block, this);

	}
	
	/**
	 * Return the height of a plug.
	 * @return The plug height.
	 */
	protected static int getPlugHeight() {
		return plugHeight;
	}
	/**
	 * Return the with of a plug.
	 * @return The plug width.
	 */
	protected static int getPlugWidth() {
		return plugWidth;
	}
	/**
	 * Return the background color.
	 * @return background color.
	 */
	protected static Color getBackgroundColor() {
		return backgroundColor;
	}
	
	/**
	 * Return the position of the block.
	 * @post  Sets the position relative to the parent block if connected.
	 * @return The position of the block.
	 */
	protected Vector getPosition() {
		// check if it has a previous block
		if (blockFunctions.getBlockAbove(getBlock()) == null) {
			return new Vector(position.getX(), position.getY());
		}

		// get the position of the block from the upper block
		Block previous = blockFunctions.getBlockAbove(getBlock());

		this.setPosition(blockFunctions.getPresentationBlock(previous).getNextBlockPosition(this));

		return this.position;
	}
	
	/**
	 * Set the position of the block to the given position.
	 * @param pos
	 * 		  The position to set the block position to.
	 */
	protected void setPosition(Vector pos) {
		this.position = pos;
	}
	
	/**
	 * Add a given vector the the position of the block.
	 * @param deltaPos
	 * 		  The position to add to the block position.
	 */
	protected void setPositionByDifference(Vector deltaPos) {
		this.setPosition(getPosition().add(deltaPos));
	}
	
	/**
	 * Draw the block on the given graphics object
	 * @param g
	 * 	      The graphics object to draw to.
	 */
	abstract protected void draw(Graphics g);
	
	/**
	 * Highlight this block.
	 * @param g
	 * 	      Graphics object to draw to.
	 */
	protected void highLight(Graphics g) {
		g.setColor(Color.black);
		int left = this.getPosition().getX();
		int right = this.getPosition().getX() + PresentationBlock.getBlockWidth();
		int top = this.getPosition().getY();
		int bottom = this.getPosition().getY() + this.getTotalHeight();

		// Horizontal line top
		g.drawLine(left, top, right, top);
		// Horizontal line bottom
		g.drawLine(left, bottom, right, bottom);
		// Vertical line left
		g.drawLine(left, top, left, bottom);
		// Vertical line right
		g.drawLine(right, top, right, bottom);
	}
	
	/**
	 * Get the width of a block.
	 * @return The block width.
	 */
	protected static int getBlockWidth() {
		return blockWidth;
	}
	
	/**
	 * Get the maximum distance to snap to.
	 * @return The snap distance.
	 */
	protected static double getSnapDistance() {
		return snapDistance;
	}
	/**
	 * Get the height of the block.
	 * @return The block height.
	 */
	protected static int getBlockHeight() {
		return blockHeight;
	}

	/**
	 * Check if the block collides with the given position.
	 * @param pos
	 * @return Checks if the position is in the block
	 */
	protected boolean collidesWithPosition(Vector pos) {
		if (pos.getX() > this.getPosition().getX() && pos.getX() < (getBlockWidth() + this.getPosition().getX())
				&& pos.getY() > this.getPosition().getY()
				&& pos.getY() < (this.getPosition().getY() + getBlockHeight())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Get the logic block of this presentation block.
	 * @return The logic block.
	 */
	protected T getBlock() {
		return this.block;
	}
	
	/**
	 * Sets the logic block of this presentation block.
	 * @param block
	 * 		  The logic block to set.
	 */
	protected void setBlock(T block) {
		this.block = block;
	}

	/**
	 * Get the name of the block.
	 * @return The nam eof the block.
	 */
	protected String getPresentationName() {
		return blockFunctions.getName(getBlock());
	}
	/**
	 * Get the font of the block.
	 */
	protected static Font getFont() {
		return font;
	}
	/**
	 * Get the total Height of the block. This includes inner blocks.
	 * @return The height of the block.
	 */
	protected int getTotalHeight() {
		return getBlockHeight();
	}
	
	/**
	 * Return the position where the given block should be.
	 * @param presentationBlock
	 * @return The position of the next block.
	 */
	protected abstract Vector getNextBlockPosition(PresentationBlock<?> presentationBlock);
	
	/**
	 * Return the with of the block side.
	 * @return The block side width.
	 */
	protected static int getBlockSideWidth() {
		return blockSideWidth;
	}
	/**
	 * Get the height of the block.
	 * @return
	 */
	protected int getHeight() {
		return blockHeight;
	}

	/**
	 * Returns the position of the snap point of the block that can snap into
	 * another.
	 * 
	 * @return snap point as vector
	 */
	protected abstract Vector getGivingSnapPoint();

	/**
	 * Return a list of snap points that can be snapped into
	 * 
	 * @return list of snap points as vectors
	 */
	protected abstract List<Vector> getReceivingSnapPoints();

	/**
	 * Check if the given block can snap into this presentationBlock
	 * 
	 * @param b
	 *        The block to snap.
	 * @return true if b can connect to this block, false if not.
	 * @post b is connected to this if possible.
	 */
	protected Command canSnap(PresentationBlock<?> b, GameController GC) {
		List<Vector> receivers = getReceivingSnapPoints();
		if (receivers.size() == 0)
			return null;
		// redo undo info collect
		Block next = blockFunctions.getNextBlock(getBlock());

		if (canSnapToPoint(b,GC)) {
			return new ConnectCommand(getBlock(), b.getBlock(), next, GC);
		}
		return null;
	}
	/**
	 * Check if the given block can snap to this block.
	 * @param b
	 *        Block to check.
	 * @param GC
	 * 		  GameController
	 * @return True if the block can snap else false.
	 */
	private boolean canSnapToPoint(PresentationBlock<?> b, GameController GC) {
		Vector givingSnappoint = b.getGivingSnapPoint();
		Vector receivingSnapPoint = getReceivingSnapPoints().get(0);
		return (givingSnappoint != null && receivingSnapPoint != null 
				&& givingSnappoint.distanceTo(receivingSnapPoint) <= getSnapDistance()
				&& GC.connect(getBlock(), b.getBlock()));
	}
	
	/**
	 * Return a copy of the block.
	 * @return A copy of the block.
	 */
	abstract protected PresentationBlock<T> makeCopyWithoutConnections();

}
