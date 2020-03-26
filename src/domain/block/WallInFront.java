package domain.block;

import domain.GameController;
import domain.block.abstract_classes.SingleConditionBlock;
import domain.block.block_types.Block;
import domain.game_world.Vector;

public class WallInFront extends SingleConditionBlock {
	
	
	public WallInFront() {
		super();
	}

	public boolean evaluate(GameController gameController) {

		if (gameController == null) {
			return false;
		}		
		else
			return gameController.IGW.robotWallInFront();
	}

	@Override
	public Block getNewBlockOfThisType() {
		return new WallInFront();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Wall In Front";
	}

	public void setConnectedBlockPositionRecursivelyByDifference(Vector deltaPos) {
		// Intentionally blank
	}
	
	
}
