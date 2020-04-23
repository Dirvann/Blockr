package simpleui.buttons;
import java.awt.Color;


import impl.root.ImplementationGameWorld;
import game_world.api.ActionResult;
import simpleui.Vector;

public class ActionButton extends Button<ActionResult>{
	
	
	public ActionButton(String name, Vector pos) {
		super(name, pos, Color.CYAN);
	}
	
	@Override
	public ActionResult execute(ImplementationGameWorld iGameWorld) {
		return iGameWorld.executeAction(getName());
	}
	
}
