package simpleui.buttons;

import java.awt.Color;

import game_world.api.FacadeGameWorld;
import game_world.api.Snapshot;
import game_world.api.Vector;

public class SnapshotButton extends Button<Boolean> {
	Snapshot snapshot;

	public SnapshotButton(String name, Vector pos, Snapshot snapshot) {
		super(name, pos, Color.GRAY);
		this.snapshot = snapshot;
	}

	@Override
	public Boolean execute(FacadeGameWorld iGameWorld) {
		iGameWorld.loadSnapshot(this.snapshot);
		return true;
	}
	
	public Snapshot getSnapshot() {
		return snapshot;
	}

}
