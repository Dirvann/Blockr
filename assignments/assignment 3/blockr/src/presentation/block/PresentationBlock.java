package presentation.block;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import command.Command;
import command.ConnectCommand;
import domain.GameController;
import domain.ImplementationGameController;
import domain.block.Block;
import domain.block.ImplementationBlock;
import domain.Vector;
import presentation.ProgramAreaPresentation;

public abstract class PresentationBlock<T extends Block> {

	private static final int blockWidth = 100;
	private static final int blockHeight = 20;

	private static final int blockSideWidth = 20;

	private static double snapDistance = 14;

	private static int plugHeight = 4;
	private static int plugWidth = 8;

	private static Color backgroundColor = Color.WHITE;

	private T block;

	private Vector position;

	private ImplementationBlock blockFunctions = new ImplementationBlock();
	private ImplementationGameController IGC = new ImplementationGameController();

	protected PresentationBlock(Vector pos, T block) {
		this.position = pos;
		this.block = block;
		blockFunctions.setPresentationBlock(block, this);

	}

	protected static int getPlugHeight() {
		return plugHeight;
	}

	protected static int getPlugWidth() {
		return plugWidth;
	}

	protected static Color getBackgroundColor() {
		return backgroundColor;
	}

	private static final Font font = new Font("Arial", Font.PLAIN, (int) (blockHeight * 0.7));

	protected Vector getPosition() {
		// check if it has a previous block
		if (blockFunctions.getPreviousBlock(getBlock()) == null) {
			return new Vector(position.getX(), position.getY());
		}

		// get the position of the block from the upper block
		Block previous = blockFunctions.getPreviousBlock(getBlock());

		this.setPosition(blockFunctions.getPresentationBlock(previous).getNextBlockPosition(this));

		return this.position;
	}

	protected void setPosition(Vector pos) {
		this.position = pos;
	}

	protected void setPositionByDifference(Vector deltaPos) {
		this.setPosition(getPosition().add(deltaPos));
	}

	protected void removeFromProgramAreaPresentationRecursively(ProgramAreaPresentation programAreaP) {

	}

	abstract protected void draw(Graphics g);

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

	protected static int getBlockWidth() {
		return blockWidth;
	}

	protected static double getSnapDistance() {
		return snapDistance;
	}

	protected static int getBlockHeight() {
		return blockHeight;
	}

	/**
	 * 
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

	protected boolean collisionWithLowerPart(Vector position) {
		return false;
	}

	protected T getBlock() {
		return this.block;
	}

	protected void setBlock(T block) {
		this.block = block;
	}

	// protected abstract PresentationBlock<T> getNewBlockOfThisType();

	protected String getPresentationName() {
		return blockFunctions.getName(getBlock());
	}

	protected static Font getFont() {
		return font;
	}

	protected int getTotalHeight() {
		return getBlockHeight();
	}

	protected abstract Vector getNextBlockPosition(PresentationBlock<?> presentationBlock);

	protected int getBlockSideWidth() {
		return blockSideWidth;
	}

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
	 * @return true if b can connect to this block, false if not.
	 * @Post b is connected to this if possible.
	 */
	protected Command canSnap(PresentationBlock<?> b, GameController GC) {
		List<Vector> receivers = getReceivingSnapPoints();
		if (receivers.size() == 0) return null;
		//redo undo info collect
		Block next = blockFunctions.getNextBlock(getBlock());
		
		if (b.getGivingSnapPoint().distanceTo(getReceivingSnapPoints().get(0)) <= getSnapDistance()
				&& IGC.connect(getBlock(), b.getBlock(), GC)) {
			return new ConnectCommand(getBlock(), b.getBlock(), next, GC);
		}
		return null;
	}
	
	abstract protected PresentationBlock<T> makeCopyWithoutConnections(); 

}
