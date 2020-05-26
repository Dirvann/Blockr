package simpleui.buttons;

import java.awt.Color;

import game_world.api.FacadeGameWorld;
import game_world.api.Vector;

public class ResetGameWorldButton extends Button<Boolean> {

	public ResetGameWorldButton(Vector pos) {
		super("ResetGameWorld", pos, Color.RED);
	}

	@Override
	public Boolean execute(FacadeGameWorld iGameWorld) {
		iGameWorld.resetGameWorld();
		return true;
	}

}
