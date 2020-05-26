package simpleui.buttons;

import java.awt.Color;

import game_world.api.FacadeGameWorld;
import game_world.api.Snapshot;
import game_world.api.Vector;

public class CreateSnapshotButton extends Button<Snapshot> {

	public CreateSnapshotButton(Vector pos) {
		super("CreateSnapshot", pos, Color.DARK_GRAY);
	}

	@Override
	public Snapshot execute(FacadeGameWorld iGameWorld) {
		System.out.println("created snapshot");
		return iGameWorld.makeSnapshot();
	}

}
