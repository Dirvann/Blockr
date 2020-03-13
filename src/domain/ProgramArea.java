package domain;

import java.util.ArrayList;
import java.util.List;

import domain.block.block_types.*;


public class ProgramArea {
	
	private List<Block> topLevelBlocks;
	private Block nextToExecute = null;
	
	
	public ProgramArea() {
		this.topLevelBlocks = new ArrayList<Block>();
	}

	
	public int nbTopLevelBlocks() {
		return topLevelBlocks.size();
	}
	
	public boolean hasValidTopLevelBlock() {
		if (nbTopLevelBlocks() == 1) {
			return (topLevelBlocks.get(0) instanceof SequenceBlock);
		} else {
			return false;
		}
	}
	
	public Boolean programInProgress() {
		return nextToExecute != null;
	}
	
	public Boolean canStartExecution() {
		return (!programInProgress() && hasValidTopLevelBlock());
		// TODO: maybe check if program is valid
	}
	
	
	public void startExecution() {
		if (canStartExecution()) {
			nextToExecute = topLevelBlocks.get(0);
		} 
	}
	
	public void executeNextBlock(GameController gameController) {
		try {
			nextToExecute = nextToExecute.execute(gameController);
		} catch (Exception e) {
			System.out.println("Execution of next block failed in ProgramArea");
			e.printStackTrace();
		}
	}
	
	public void stopExecution() {
		if (programInProgress()) {
			nextToExecute = null;
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
	
	public boolean isTopLevelBlock(Block block) {
		return topLevelBlocks.contains(block);
	}
	
	
	public Block getNextBlockToExecute() {
		return this.nextToExecute;
	}
	
	public List<Block> getTopBlocks() {
		return this.topLevelBlocks;
	}
	
	public List<Block> getAllBlocks() {
		List<Block> list = new ArrayList<Block>();
		for(Block block: this.getTopBlocks()) {
			list.addAll(block.getAllNextBlocks());
		}
		
		return list;
	}
	

}
