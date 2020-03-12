package presentation.block;

import domain.game_world.Vector;

public abstract class SequenceBlockPresentation extends PresentationBlock {
	
	public Vector getPossibleSnapLocation() {
		Vector ownPosition = this.getPosition();
		int heightOffset = -15;
		int widthOffset = SequenceBlockPresentation.getBlockWidth()/2;
		
		return new Vector(ownPosition.getX() + widthOffset, ownPosition.getY() + heightOffset);
	}
	
}
