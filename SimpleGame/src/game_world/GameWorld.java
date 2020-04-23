package game_world;

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
	
	private GameWorld(int width, int height,Player p, ArrayList<FallingBlock> block) {
		this.blocks = block;
		this.width = width;
		this.player = p;
		this.height = height;
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
	public GameWorld createCopy() {
		ArrayList<FallingBlock> list = new ArrayList<FallingBlock>();
		for(FallingBlock b : blocks) {
			list.add(b.createCopy());
		}
		return new GameWorld(width, height,new Player(new Position(this.player.getPosition().getX(), this.player.getPosition().getY())), list);
	}
	
}
