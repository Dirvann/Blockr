package presentation.block;

import java.awt.Color;
import java.awt.Graphics;

import domain.block.abstract_classes.ActionBlock;
import domain.game_world.Vector;

public class ActionBlockPresentation extends PresentationBlock {
	
	private ActionBlock block;

	public ActionBlockPresentation(Vector pos, ActionBlock block) {
		super(pos);
		this.setBlock(block);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		Vector pos = getPosition();
		g.fillRect(pos.getX(), pos.getY(), PresentationBlock.blockWidth, PresentationBlock.blockHeight);
	}

	public ActionBlock getBlock() {
		return block;
	}

	public void setBlock(ActionBlock block) {
		this.block = block;
	}
	
	

}