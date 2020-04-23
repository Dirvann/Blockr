package simpleui.buttons;

import java.awt.Color;

import game_world.ImplementationGameWorld;
import simpleui.Vector;

public class ResetGameWorldButton extends Button<Boolean> {

	public ResetGameWorldButton(Vector pos) {
		super("ResetGameWorld", pos, Color.RED);
	}

	@Override
	public Boolean execute(ImplementationGameWorld iGameWorld) {
		iGameWorld.resetGameWorld();
		return true;
	}

}