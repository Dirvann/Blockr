package domain.block;

import domain.block.abstract_classes.ActionBlock;
import domain.game_world.Vector;

public class TurnRight extends ActionBlock {
	
	public TurnRight(Vector pos) {
		super(pos);
	}
	
	public void performAction() {
		//TODO
		System.out.println("Turned Right");
	}

}
