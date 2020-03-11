package domain.block.block_types;

import domain.GameController;

public abstract class Block {
	private int ID;

	/**
	 * 
	 * @return next block in sequence
	 * @ executes every necessary command in the block.
	 */
	public Block execute(GameController gameController) throws Exception{
		return null;
	}
	
	public void setID(int newID) {
		this.ID = newID;
	}
	

	public int getID(int newID) {
		return this.ID;
	}
}
