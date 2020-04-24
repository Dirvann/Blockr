package simpleui;

public abstract class Button {
	
	private String name;
	private Vector position;
	
	public Button(String name, Vector pos) {
		this.name = name;
		this.position = pos;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Vector getPos() {
		return this.position;
	}
	

}
