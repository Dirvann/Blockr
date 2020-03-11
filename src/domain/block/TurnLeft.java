package domain.block;

import domain.block.abstract_classes.ActionBlock;
import domain.game_world.Vector;

public class TurnLeft extends ActionBlock {
	
	public TurnLeft(Vector pos) {
		super(pos);
	}
	
	public void performAction() {
		//TODO
		System.out.println("Turned Left");
	}

}
