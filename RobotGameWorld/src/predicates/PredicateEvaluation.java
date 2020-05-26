package predicates;

import game_world.GameWorld;
import game_world.api.Predicate;
import game_world.api.PredicateResult;

/**
 * Class responsible for predicate evaluation.
 * 
 * @version 4.0
 * @author Andreas Awouters 
 * 	       Thomas Van Erum 
 * 		   Dirk Vanbeveren 
 * 		   Geert Wesemael
 *
 */
public abstract class PredicateEvaluation implements Predicate {
	/**
	 * Evaluate this predicate on the given game world.
	 * @param gameWorld
	 * 		  The gameworld to evaluate the predicate on.
	 * @return A predicate result.
	 */
	public abstract PredicateResult evaluate(GameWorld gameWorld);
}
