package simpleui.buttons;
import java.awt.Color;



import game_world.api.ActionResult;
import game_world.api.FacadeGameWorld;
import game_world.api.Action;
import game_world.api.Vector;

public class ActionButton extends Button<ActionResult>{
	
	Action action;
	
	public ActionButton(Action action, Vector pos) {
		super(action.getName(), pos, Color.CYAN);
		this.action = action;
	}
	
	@Override
	public ActionResult execute(FacadeGameWorld iGameWorld) {
		return iGameWorld.executeAction(this.action);
	}
	
}
