package predicates;

import game_world.GameWorld;
import game_world.api.Predicate;
import game_world.api.PredicateResult;

public abstract class PredicateEvaluation implements Predicate {
	public abstract PredicateResult evaluate(GameWorld gameWorld);
}
