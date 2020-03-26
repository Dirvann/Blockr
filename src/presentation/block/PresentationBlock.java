package presentation.block;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import domain.block.Block;
import domain.block.ImplementationBlock;
import domain.game_world.Vector;
import presentation.ProgramAreaPresentation;

public abstract class PresentationBlock<T extends Block> {

	// TODO Store safely, maybe elsewhere.
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

	protected PresentationBlock(Vector pos, T block) {
		this.position = pos;
		this.block = block;

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
			return this.position;
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
	 * @return
	 */
	public boolean snap(PresentationBlock<?> b) {
		if (b.getGivingSnapPoint().distanceTo(getReceivingSnapPoints().get(0)) <= getSnapDistance()
				&& blockFunctions.connect(b.getBlock(), getBlock())) {
			return true;
		}
		return false;
	}

}
