package presentation.block;

import java.awt.Color;
import java.awt.Graphics;

import domain.block.abstract_classes.ChainConditionBlock;
import domain.game_world.Vector;

public class ChainConditionBlockPresentation extends PresentationBlock<ChainConditionBlock>{
	
	public ChainConditionBlockPresentation(Vector position, ChainConditionBlock block) {
		super(position, block);
		
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		Vector pos = getPosition();
		g.fillRect(pos.getX(), pos.getY(), PresentationBlock.getBlockWidth(), PresentationBlock.getBlockHeight());
	
	}

}
