package domain;

import java.util.ArrayList;
import java.util.List;

import domain.block.Block;
import domain.block.ImplementationBlock;


public class ProgramArea {
	
	private List<Block> topLevelBlocks;
	private Block nextToExecute = null;
	private ImplementationBlock blockFunctions = new ImplementationBlock();
	
	/**
	 * Creates a new ProgramArea
	 */
	public ProgramArea() {
		this.topLevelBlocks = new ArrayList<Block>();
	}

	/**
	 * 
	 * @return number of top level blocks
	 */
	public int nbTopLevelBlocks() {
		return topLevelBlocks.size();
	}
	
	/**
	 * 
	 * @return  nbTopLevelBlocks() == 1 && top level block can be executed
	 */
	public boolean hasValidTopLevelBlock() {
		if (nbTopLevelBlocks() == 1) {
			Block topLevelBlock = topLevelBlocks.get(0);

			return blockFunctions.isValidStartingBlock(topLevelBlock);
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @return nextToExecute != null
	 */
	public Boolean programInProgress() {
		return nextToExecute != null;
	}
	
	/**
	 * 
	 * @return true if program can start executing
	 */
	public Boolean canStartExecution() {
		return (!programInProgress() && hasValidTopLevelBlock());
		// TODO: maybe check if program is valid
	}
	
	/**
	 * @post nextToExecute != null
	 */
	public void startExecution() {
		if (canStartExecution()) {
			nextToExecute = topLevelBlocks.get(0);
		} 
	}
	
	/**
	 * 
	 * @param gameController gameController to execute the next function in
	 */
	public void executeNextBlock(GameController gameController) {
		try {
			nextToExecute = blockFunctions.execute(nextToExecute, gameController);
		} catch (Exception e) {
			System.out.println("Execution of next block failed in ProgramArea");
			e.printStackTrace();
		}
	}
	
	/**
	 * @post nextToExecute == null
	 */
	public void stopExecution() {
		if (programInProgress()) {
			nextToExecute = null;
		}
	}
	
	/**
	 * 
	 * @param block, block to add to topLevelBlocks
	 */
	public void addTopLevelBlock(Block block) {
		topLevelBlocks.add(block);
	}
	
	/**
	 * 
	 * @param block, block to remove from topLevelBlocks
	 * @throws Exception
	 * 			block is not a top level block
	 */
	public void removeTopLevelBlock(Block block) throws Exception {
		if (topLevelBlocks.contains(block)) {
			topLevelBlocks.remove(block);
		} else {
			throw new Exception("Block is not a top level block");
		}
	}
	
	/**
	 * 
	 * @param block block to check
	 * @return topLevelBlocks.contains(block)
	 */
	public boolean isTopLevelBlock(Block block) {
		return topLevelBlocks.contains(block);
	}
	
	/**
	 * 
	 * @return nextToExecute
	 */
	public Block getNextBlockToExecute() {
		return this.nextToExecute;
	}
	
	/**
	 * 
	 * @return topLevelBlocks
	 */
	public List<Block> getTopBlocks() {
		return this.topLevelBlocks;
	}
	
	/**
	 * 
	 * @return copy of topLevelBlocks
	 */
	public List<Block> getAllBlocks() {
		List<Block> list = new ArrayList<Block>();
		for(Block block: this.getTopBlocks()) {
			list.addAll(blockFunctions.getAllNextBlocks(block));
		}
		
		return list;
	}
	

}
