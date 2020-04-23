package simpleui.buttons;
import java.awt.Color;


import impl.root.ImplementationGameWorld;
import game_world.api.PredicateResult;
import simpleui.Vector;

public class PredicateButton extends Button<PredicateResult>{
	
	public PredicateButton(String name, Vector pos) {
		super(name, pos, Color.MAGENTA);
	}

	@Override
	public PredicateResult execute(ImplementationGameWorld iGameWorld) {
		return iGameWorld.evaluatePredicate(getName());
	}
	
}
