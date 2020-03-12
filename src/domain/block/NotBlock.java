package domain.block;

import domain.GameController;
import domain.block.abstract_classes.ChainConditionBlock;
import domain.game_world.Vector;

public class NotBlock extends ChainConditionBlock {

	private final String displayName = "NOT";

	public NotBlock(Vector pos) {
		super(pos);
	}

	public boolean evaluate(GameController gamecontroller) {
		if (this.getNextCondition() == null) {
			return false;
		}
		return !this.getNextCondition().evaluate(gamecontroller);
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}
}
