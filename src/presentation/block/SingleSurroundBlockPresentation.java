package presentation.block;

import java.awt.Color;
import java.awt.Graphics;

import domain.block.abstract_classes.SingleSurroundingBlock;
import domain.game_world.Vector;

public abstract class SingleSurroundBlockPresentation extends PresentationBlock {

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		Vector pos = getPosition();
		g.fillRect(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight());
	
	}

}
