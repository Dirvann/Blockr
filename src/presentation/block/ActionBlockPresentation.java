package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import domain.game_world.Vector;

public abstract class ActionBlockPresentation extends SequenceBlockPresentation {
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		Vector pos = getPosition();
		g.fillRect(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight());
		g.setColor(Color.BLACK);
		g.setFont(getFont());
		g.drawString(getPresentationName(),pos.getX(), pos.getY() + (int)(getBlockHeight() * 0.8));
	}

}