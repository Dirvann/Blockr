package simpleui.buttons;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;

import game_world.ImplementationGameWorld;
import game_world.api.PredicateResult;
import simpleui.Vector;

public class PredicateButton extends Button<PredicateResult>{
	
	public PredicateButton(String name, Vector pos) {
		super(name, pos, Color.GRAY);
	}

	@Override
	public PredicateResult execute(ImplementationGameWorld iGameWorld) {
		return iGameWorld.evaluatePredicate(getName());
	}
	
}
