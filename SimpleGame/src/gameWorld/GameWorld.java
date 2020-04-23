package gameWorld;

import java.util.ArrayList;
import java.util.List;

import exceptions.OutOfBoundsException;

public class GameWorld {

	private int width;
	private int height;
	
	private Player player;
	
	private List<FallingBlock> blocks;
	
	
	public GameWorld(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.player = new Player(width/2, height-1);
		
		this.blocks = new ArrayList<FallingBlock>();
	}
	
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void movePlayerLeft() throws OutOfBoundsException {
		if (getPlayer().getPosition().getX() <= 0) {
			throw new OutOfBoundsException();
		} else {
			getPlayer().moveLeft();
		}
	}
	
	public void movePlayerRight() throws OutOfBoundsException {
		if (getPlayer().getPosition().getX() >= getWidth()) {
			throw new OutOfBoundsException();
		} else {
			getPlayer().moveRight();
		}
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public List<FallingBlock> getAllBlocks() {
		return this.blocks;
	}
	
	public void clearAllBlocks() {
		blocks.clear();
	}
	
	public void addBlock(int x) {
		this.blocks.add(new FallingBlock(x, 0));
	}
	
}
