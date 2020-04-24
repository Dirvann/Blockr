package simpleui.buttons;

import java.awt.Color;

import game_world.api.FacadeGameWorld;
import simpleui.Vector;

public class SnapshotButton extends Button<Boolean> {

	public SnapshotButton(String name, Vector pos) {
		super(name, pos, Color.GRAY);
	}

	@Override
	public Boolean execute(FacadeGameWorld iGameWorld) {
		iGameWorld.loadSnapshot(getName());
		return true;
	}

}
