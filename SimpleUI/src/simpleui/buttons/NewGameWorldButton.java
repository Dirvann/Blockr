package simpleui.buttons;

import java.awt.Color;

import game_world.ImplementationGameWorld;
import simpleui.Vector;

public class NewGameWorldButton extends Button<Boolean> {

	public NewGameWorldButton(Vector pos) {
		super("NewGameWorld", pos, Color.GREEN);
	}

	@Override
	public Boolean execute(ImplementationGameWorld iGameWorld) {
		iGameWorld.makeNewGameWorld();
		return true;
	}

}
