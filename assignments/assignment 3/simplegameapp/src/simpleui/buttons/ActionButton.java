package simpleui.buttons;
import java.awt.Color;


import client.main.ClientMainClass;
import game_world.api.ActionResult;
import game_world.api.FacadeGameWorld;
import simpleui.Vector;

public class ActionButton extends Button<ActionResult>{
	
	
	public ActionButton(String name, Vector pos) {
		super(name, pos, Color.CYAN);
	}
	
	@Override
	public ActionResult execute(FacadeGameWorld iGameWorld) {
		return iGameWorld.executeAction(getName());
	}
	
}
