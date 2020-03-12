package presentation.block;

import domain.game_world.Vector;

public abstract class ConditionBlockPresentation extends PresentationBlock {

	public Vector getPossibleSnapLocation() {
		Vector ownPosition = this.getPosition();
		int heightOffset = ConditionBlockPresentation.getBlockHeight()/2;
		int widthOffset = -15;
		
		return new Vector(ownPosition.getX() + widthOffset, ownPosition.getY() + heightOffset);
	}
	
}

