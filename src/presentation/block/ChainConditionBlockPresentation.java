package presentation.block;

import java.awt.Graphics;

import domain.block.abstract_classes.ChainConditionBlock;
import domain.game_world.Vector;

public class ChainConditionBlockPresentation extends PresentationBlock{
	
	ChainConditionBlock block;
	
	public ChainConditionBlockPresentation(Vector position, ChainConditionBlock block) {
		super(position);
		this.block = block;
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
