package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;

import game_world.api.Vector;

/**
 * Draws the blocks with given settings
 * 
 * @version 4.0
 * @author Andreas Awouters 
 * 	       Thomas Van Erum 
 * 		   Dirk Vanbeveren 
 * 		   Geert Wesemael
 *
 */
public class BlockDrawer {

	private Color primaryColor;
	private Color textColor;

	/**
	 * Create a new drawer with given colors
	 * @param primaryColor
	 * 		  The primary color of the block.
	 * @param fontColor
	 *        The color of the font.
	 */
	BlockDrawer(Color primaryColor, Color fontColor) {
		this.primaryColor = primaryColor;
		this.textColor = fontColor;
	}
	
	/**
	 * Draws a block with given parameters
	 * @param gr
	 * 		  The graphics object to draw on.
	 * @param pos
	 * 		  The upper left position of the block to draw.
	 * @param top
	 * 		  Draw the to plug.
	 * @param bottom
	 *        Draw the bottom socket.
	 * @param right
	 * 		  Draw the right socket.
	 * @param left
	 * 		  Draw the left plug.
	 * @param topOffset
	 * 		  Add an offset from the side bar to the top plug.
	 * @param bottomOffset
	 * 		  Add an offset from the side bar to the bottom socket.
	 */
	public void drawBlock(Graphics gr, Vector pos, boolean top, boolean bottom, boolean right, boolean left,
			boolean topOffset, boolean bottomOffset) {
		Graphics2D g = (Graphics2D) gr;
		g.setColor(this.primaryColor);

		Area t = new Area(new Rectangle(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(),
				PresentationBlock.getBlockHeight()));
		// subtract
		if (right) {
			t.subtract(new Area(
					new Rectangle(pos.getX() + PresentationBlock.getBlockWidth() - PresentationBlock.getPlugHeight(),
							pos.getY() + PresentationBlock.getBlockHeight() / 2 - PresentationBlock.getPlugWidth() / 2,
							PresentationBlock.getPlugHeight(), PresentationBlock.getPlugWidth())));

		}

		if (bottom) {
			int offset = bottomOffset ? PresentationBlock.getBlockSideWidth() : 0;

			t.subtract(new Area(new Rectangle(
					pos.getX() + offset + PresentationBlock.getBlockWidth() / 2 - PresentationBlock.getPlugWidth() / 2,
					pos.getY() + PresentationBlock.getBlockHeight() - PresentationBlock.getPlugHeight(),
					PresentationBlock.getPlugWidth(), PresentationBlock.getPlugHeight())));

		}
		g.fill(t);

		// additive

		if (left) {
			g.fillRect(pos.getX() - PresentationBlock.getPlugHeight(),
					pos.getY() + PresentationBlock.getBlockHeight() / 2 - PresentationBlock.getPlugWidth() / 2,
					PresentationBlock.getPlugHeight(), PresentationBlock.getPlugWidth());

		}

		if (top) {
			int offset = topOffset ? PresentationBlock.getBlockSideWidth() : 0;
			g.fillRect(
					pos.getX() + PresentationBlock.getBlockWidth() / 2 - PresentationBlock.getPlugWidth() / 2 + offset,
					pos.getY() - PresentationBlock.getPlugHeight(),
					PresentationBlock.getPlugWidth(), PresentationBlock.getPlugHeight());

		}

	}

	/**
	 * Draw a given string on the given position.
	 * @param gr
	 * 	      The graphics object to draw on.
	 * @param str
	 * 		  The string to draw.
	 * @param pos
	 * 		  The upper left position where to draw.
	 */
	public void drawString(Graphics gr, String str, Vector pos) {
		Graphics2D g = (Graphics2D) gr;
		g.setColor(this.textColor);
		g.setFont(PresentationBlock.getFont());
		g.drawString(str, pos.getX(), pos.getY() + (int) (PresentationBlock.getBlockHeight() * 0.8));
	}
	
	/**
	 * Draw a side bar for the block
	 * @param gr
	 * 	      Graphics object to draw on.
	 * @param pos
	 * 		  Left upper position of the upper rectangle.
	 * @param totalHeight
	 *        The total height of the block including internal blocks, top rectangle and bottom rectangle.
	 * 		  
	 */
	public void drawSide(Graphics gr, Vector pos, int totalHeight) {
		Graphics2D g = (Graphics2D) gr;
		g.setColor(this.primaryColor);
		g.fillRect(pos.getX(), pos.getY() + PresentationBlock.getBlockHeight(), PresentationBlock.getBlockSideWidth(), totalHeight - 2 * PresentationBlock.getBlockHeight());
		
	}

}
