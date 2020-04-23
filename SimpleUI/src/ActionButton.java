import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;

import game_world.ImplementationGameWorld;
import game_world.api.ActionResult;

public class ActionButton {
	
	private String name;
	private final Vector position;
	final static  int width = 170;
	final static int height = 40;
	private static final Font font = new Font("Arial", Font.PLAIN, (int) (height * 0.7));
	
	public ActionButton(String name, Vector pos) {
		this.name = name;
		this.position = pos;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Vector getPos() {
		return this.position;
	}
	
	public void draw(Graphics gr) {
		Graphics2D g = (Graphics2D)gr;
		g.setColor(Color.GREEN);
		g.fill(new Area(new Rectangle(position.getX(), position.getY(), ActionButton.width, ActionButton.height)));

		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString(this.name,position.getX(), position.getY() + (int)(ActionButton.height * 0.8));
	}
	
	public ActionResult execute(ImplementationGameWorld iGameWorld) {
		return iGameWorld.executeAction(this.name);
	}
	
	public boolean collidesWith(Vector pos) {
		if(pos.getX() < position.getX()) return false;
		if(pos.getX() > position.getX() + width) return false;
		if(pos.getY() < position.getY()) return false;
		if(pos.getY() > position.getY() + height) return false;
		return true;
	}
}
