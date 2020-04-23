package implementation;

import gameWorld.Position;

public class Main {

	public static void main(String[] args) {
		Position pos1 = new Position(5, 6);
		Position pos2 = new Position(5, 6);
		
		System.out.println(pos1.equals(pos2));

	}

}
