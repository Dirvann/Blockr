package simpleui.buttons;

import java.awt.Color;

import game_world.api.FacadeGameWorld;
import game_world.api.Vector;

public class NewGameWorldButton extends Button<Boolean> {

	public NewGameWorldButton(Vector pos) {
		super("NewGameWorld", pos, Color.GREEN);
	}

	@Override
	public Boolean execute(FacadeGameWorld iGameWorld) {
		iGameWorld.makeNewGameWorld();
		return true;
	}

}
