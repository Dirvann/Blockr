package simpleui.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;

import game_world.ImplementationGameWorld;
import simpleui.Vector;

public abstract class Button<T> {
	private String name;
	private final Vector position;
	final public static  int width = 200;
	final public static int height = 35;
	private static final Font font = new Font("Arial", Font.PLAIN, (int) (height * 0.7));
	private Color color;
	
	public Button(String name, Vector pos, Color c) {
		this.name = name;
		this.position = pos;
		this.color = c;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Vector getPos() {
		return this.position;
	}
	
	public void draw(Graphics gr) {
		Graphics2D g = (Graphics2D)gr;
		g.setColor(this.color);
		g.fill(new Area(new Rectangle(position.getX(), position.getY(), Button.width, Button.height)));

		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString(this.name,position.getX(), position.getY() + (int)(Button.height * 0.8));
	}
	
	
	public boolean collidesWith(Vector pos) {
		if(pos.getX() < position.getX()) return false;
		if(pos.getX() > position.getX() + width) return false;
		if(pos.getY() < position.getY()) return false;
		if(pos.getY() > position.getY() + height) return false;
		return true;
	}
	
	public abstract T execute(ImplementationGameWorld iGameWorld);
}
