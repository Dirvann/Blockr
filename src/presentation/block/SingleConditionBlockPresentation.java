package presentation.block;

import java.awt.Graphics;

import domain.block.abstract_classes.SingleConditionBlock;
import domain.game_world.Vector;

public class SingleConditionBlockPresentation extends PresentationBlock{
	
	private SingleConditionBlock block;

	public SingleConditionBlockPresentation(Vector pos, SingleConditionBlock block) {
		super(pos);
		this.setBlock(block);
	}

	@Override
	public void draw(Graphics g) {
		
	}

	public SingleConditionBlock getBlock() {
		return block;
	}

	public void setBlock(SingleConditionBlock block) {
		this.block = block;
	}

}
