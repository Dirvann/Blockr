package presentation.block;

import java.awt.Graphics;

import domain.block.abstract_classes.SingleConditionBlock;
import domain.game_world.Vector;

public class SingleConditionBlockPresentation extends PresentationBlock<SingleConditionBlock>{

	public SingleConditionBlockPresentation(Vector pos, SingleConditionBlock block) {
		super(pos,block);
	}

	@Override
	public void draw(Graphics g) {
		
	}

}