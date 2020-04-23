package simpleui.buttons;

import java.awt.Color;

import impl.root.ImplementationGameWorld;
import simpleui.Vector;

public class SnapshotButton extends Button<Boolean> {

	public SnapshotButton(String name, Vector pos) {
		super(name, pos, Color.GRAY);
	}

	@Override
	public Boolean execute(ImplementationGameWorld iGameWorld) {
		iGameWorld.loadSnapshot(getName());
		return true;
	}

}
