package simpleui.buttons;

import java.awt.Color;

import game_world.api.FacadeGameWorld;
import simpleui.Vector;

public class CreateSnapshotButton extends Button<String> {

	public CreateSnapshotButton(Vector pos) {
		super("CreateSnapshot", pos, Color.DARK_GRAY);
	}

	@Override
	public String execute(FacadeGameWorld iGameWorld) {
		System.out.println("created snapshot");
		return iGameWorld.makeSnapshot();
	}

}
