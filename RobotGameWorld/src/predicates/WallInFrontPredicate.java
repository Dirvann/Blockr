package predicates;

import game_world.GameWorld;
import game_world.api.Predicate;
import game_world.api.PredicateResult;

public class WallInFrontPredicate extends PredicateEvaluation {

	@Override
	public String getName() {
		return "Wall In Front";
	}

	@Override
	public PredicateResult evaluate(GameWorld gameWorld) {
		if (gameWorld.robotWallInFront()) {
			return PredicateResult.True;
		} else {
			return PredicateResult.False;
		}
	}

}
