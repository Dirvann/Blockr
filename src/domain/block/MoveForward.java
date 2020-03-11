package domain.block;

import domain.block.abstract_classes.ActionBlock;
import domain.game_world.Vector;

public class MoveForward extends ActionBlock {

	public MoveForward(Vector pos) {
		super(pos);
	}
	
	public void performAction() {
		//TODO
		System.out.println("Moved Forward");
	}

}
