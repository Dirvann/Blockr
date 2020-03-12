package presentation.block;

import java.awt.Color;
import java.awt.Graphics;

import domain.block.abstract_classes.SingleConditionBlock;
import domain.game_world.Vector;

public abstract class SingleConditionBlockPresentation extends PresentationBlock {
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		Vector pos = getPosition();
		g.fillRect(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight());
	
	}

}
