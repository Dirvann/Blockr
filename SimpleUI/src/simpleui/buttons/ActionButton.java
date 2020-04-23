package simpleui.buttons;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;

import game_world.ImplementationGameWorld;
import game_world.api.ActionResult;
import simpleui.Vector;

public class ActionButton extends Button<ActionResult>{
	
	
	public ActionButton(String name, Vector pos) {
		super(name, pos, Color.GREEN);
	}
	
	@Override
	public ActionResult execute(ImplementationGameWorld iGameWorld) {
		return iGameWorld.executeAction(getName());
	}
	
}
