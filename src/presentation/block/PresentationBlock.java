package presentation.block;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import domain.block.block_types.Block;
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

	public PresentationBlock(Vector pos, T block) {
		this.position = pos;
		this.block = block;

	}
	
	public static int getPlugHeight() {
		return plugHeight;
	}
	
	public static int getPlugWidth() {
		return plugWidth;
	}
	
	public static Color getBackgroundColor() {
		return backgroundColor;
	}

	

	private static final Font font = new Font("Arial", Font.PLAIN, (int) (blockHeight * 0.7));

	public Vector getPosition() {
		// check if it has a previous block
		if (getBlock().getPreviousBlock() == null) {
			return this.position;
		}

		// get the position of the block from the upper block

		this.setPosition(getBlock().getPreviousBlock().getPresentationBlock().getNextBlockPosition(this));

		return this.position;
	}

	public void setPosition(Vector pos) {
		this.position = pos;
	}

	public void setPositionByDifference(Vector deltaPos) {
		this.setPosition(getPosition().add(deltaPos));
	}


	public void removeFromProgramAreaPresentationRecursively(ProgramAreaPresentation programAreaP) {

	}

	abstract public void draw(Graphics g);

	public void highLight(Graphics g) {
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

	public static int getBlockWidth() {
		return blockWidth;
	}
	
	public static double getSnapDistance() {
		return snapDistance;
	}

	public static int getBlockHeight() {
		return blockHeight;
	}

	public boolean collidesWithPosition(Vector pos) {
		if (pos.getX() > this.getPosition().getX() && pos.getX() < (getBlockWidth() + this.getPosition().getX())
				&& pos.getY() > this.getPosition().getY()
				&& pos.getY() < (this.getPosition().getY() + getBlockHeight())) {
			return true;
		}
		return false;
	}

	public boolean collisionWithLowerPart(Vector position) {
		return false;
	}

	public T getBlock() {
		return this.block;
	}

	public void setBlock(T block) {
		this.block = block;
	}

	public abstract PresentationBlock<T> getNewBlockOfThisType();

	public String getPresentationName() {
		return getBlock().getName();
	}

	public static Font getFont() {
		return font;
	}

	public int getTotalHeight() {
		return getBlockHeight();
	}
	
	protected abstract Vector getNextBlockPosition(PresentationBlock<?> presentationBlock);
	
	public int getBlockSideWidth() {
		return blockSideWidth;
	}
	
	public int getHeight() {
		return blockHeight;
	}
	/**
	 * Returns the position of the snap point of the block that can snap into another.
	 * @return snap point as vector
	 */
	public abstract Vector getGivingSnapPoint();
	
	/**
	 * Return a list of snap points that can be snapped into
	 * @return list of snap points as vectors
	 */
	public abstract List<Vector> getReceivingSnapPoints();
	
	/**
	 * Check if the given block can snap into this presentationBlock
	 * @param b
	 * @return
	 */
	public abstract boolean snap(PresentationBlock<?> b);

}
