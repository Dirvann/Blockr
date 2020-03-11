package domain;

import java.util.List;

import domain.block.block_types.*;


public class ProgramArea {
	
	private List<Block> topLevelBlocks;
	private Block nextToExecute = null;
	
	

	
	public int nbTopLevelBlocks() {
		return topLevelBlocks.size();
	}
	
	public Boolean programInProgress() {
		return nextToExecute != null;
	}
	
	public Boolean canStartExecution() {
		return (!programInProgress() && nbTopLevelBlocks() == 1);
		// TODO: maybe check if program is valid
	}
	
	
	public void startExecution() {
		if (canStartExecution()) {
			nextToExecute = topLevelBlocks.get(0);
		} 
	}
	
	public void addTopLevelBlock(Block block) {
		topLevelBlocks.add(block);
	}
	
	public void removeTopLevelBlock(Block block) throws Exception {
		if (topLevelBlocks.contains(block)) {
			topLevelBlocks.remove(block);
		} else {
			throw new Exception("Block is not a top level block");
		}
	}
	
	
	public Block getNextBlockToExecute() {
		return this.nextToExecute;
	}
	
	public void finishBlockExecution() {
		try {
			nextToExecute = nextToExecute.execute();
		} catch (Exception e) {
			System.out.println("Block not executable");
			e.printStackTrace();
		}
	}
}
