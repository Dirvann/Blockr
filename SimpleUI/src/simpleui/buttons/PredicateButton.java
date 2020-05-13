package simpleui.buttons;
import java.awt.Color;

import game_world.api.FacadeGameWorld;
import game_world.api.PredicateResult;
import game_world.api.Predicate;
import simpleui.Vector;

public class PredicateButton extends Button<PredicateResult>{
	
	Predicate predicate;
	
	public PredicateButton(Predicate predicate, Vector pos) {
		super(predicate.getName(), pos, Color.MAGENTA);
		this.predicate = predicate;
	}

	@Override
	public PredicateResult execute(FacadeGameWorld iGameWorld) {
		return iGameWorld.evaluatePredicate(this.predicate);
	}
	
}
