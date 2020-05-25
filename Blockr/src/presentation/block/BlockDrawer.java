package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;

import game_world.api.Vector;

public class BlockDrawer {

	private Color c1;
	private Color c2;

	BlockDrawer(Color c1, Color c2) {
		this.c1 = c1;
		this.c2 = c2;
	}

	public void drawBlock(Graphics gr, Vector pos, boolean top, boolean bottom, boolean right, boolean left,
			boolean topOffset, boolean bottomOffset) {
		Graphics2D g = (Graphics2D) gr;
		g.setColor(this.c1);

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

	public void drawString(Graphics gr, String str, Vector pos) {
		Graphics2D g = (Graphics2D) gr;
		g.setColor(this.c2);
		g.setFont(PresentationBlock.getFont());
		g.drawString(str, pos.getX(), pos.getY() + (int) (PresentationBlock.getBlockHeight() * 0.8));
	}

	public void drawSide(Graphics gr, Vector pos, int innerHeight) {
		Graphics2D g = (Graphics2D) gr;
		g.setColor(this.c1);
		g.fillRect(pos.getX(), pos.getY() + PresentationBlock.getBlockHeight(), PresentationBlock.getBlockSideWidth(), innerHeight - 2 * PresentationBlock.getBlockHeight());
		
	}

}
