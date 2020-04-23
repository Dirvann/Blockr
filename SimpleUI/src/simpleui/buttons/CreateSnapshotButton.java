package simpleui.buttons;

import java.awt.Color;

import impl.root.ImplementationGameWorld;
import simpleui.Vector;

public class CreateSnapshotButton extends Button<String> {

	public CreateSnapshotButton(Vector pos) {
		super("CreateSnapshot", pos, Color.DARK_GRAY);
	}

	@Override
	public String execute(ImplementationGameWorld iGameWorld) {
		System.out.println("created snapshot");
		return iGameWorld.makeSnapshot();
	}

}
