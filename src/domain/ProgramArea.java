package domain;

import java.util.ArrayList;
import java.util.List;

import domain.block.Block;
import domain.block.ImplementationBlock;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

public class ProgramArea {

	private int blocksLeft = 15;
	private List<Block> topLevelBlocks;
	private Block nextToExecute = null;
	private ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();
	private ImplementationBlock BF = new ImplementationBlock();

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
	 * @return nbTopLevelBlocks() == 1 && top level block can be executed
	 */
	public boolean hasValidTopLevelBlock() {
		if (nbTopLevelBlocks() == 1) {
			Block topLevelBlock = topLevelBlocks.get(0);

			return BF.isValidStartingBlock(topLevelBlock);
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
	 * @throws Exception when execute is not possible
	 */
	public void executeNextBlock(GameController gameController) throws Exception {
		nextToExecute = BF.execute(nextToExecute, gameController);
	}

	/**
	 * @post nextToExecute == null
	 */
	public void stopExecution() {
		if (programInProgress()) {
			nextToExecute = null;
		}
	}

	public void addBlock(PresentationBlock<?> pBlock) {
		addTopLevelBlock(BFP.getBlock(pBlock));
		blocksLeft -= BF.getAllNextBlocks(BFP.getBlock(pBlock)).size();
	}

	public void removeBlock(PresentationBlock<?> pBlock) {
		Block block = BFP.getBlock(pBlock);
		BF.disconnect(BFP.getBlock(pBlock));
		blocksLeft += BF.getAllNextBlocks(block).size();

		try {
			removeTopLevelBlock(block);
			System.out.println("a top-level block is removed");
		} catch (Exception e) {
			System.out.println("a non top-level block is removed");
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
	 * @throws Exception block is not a top level block
	 */
	public void removeTopLevelBlock(Block block) {
		if (topLevelBlocks.contains(block)) {
			topLevelBlocks.remove(block);
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
		for (Block block : this.getTopBlocks()) {
			list.addAll(BF.getAllNextBlocks(block));
		}

		return list;
	}

	public int getBlocksLeft() {
		return blocksLeft;
	}

	public void setBlocksLeft(int blocksLeft) {
		this.blocksLeft = blocksLeft;
	}

	public void increaseBlocksLeft() {
		blocksLeft += 1;
	}

	public void decreaseBlocksLeft() {
		blocksLeft -= 1;
	}

}
