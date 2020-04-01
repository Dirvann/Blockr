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
	protected ProgramArea() {
		this.topLevelBlocks = new ArrayList<Block>();
	}

	/**
	 * 
	 * @return number of top level blocks
	 */
	protected int nbTopLevelBlocks() {
		return topLevelBlocks.size();
	}

	/**
	 * 
	 * @return nbTopLevelBlocks() == 1 && top level block can be executed
	 */
	protected boolean hasValidTopLevelBlock() {
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
	protected Boolean programInProgress() {
		return nextToExecute != null;
	}

	/**
	 * 
	 * @return true if program can start executing
	 */
	protected Boolean canStartExecution() {
		return (!programInProgress() && hasValidTopLevelBlock());
		// TODO: maybe check if program is valid
	}

	/**
	 * @post nextToExecute != null
	 */
	protected void startExecution() {
		if (canStartExecution()) {
			nextToExecute = topLevelBlocks.get(0);
		}
	}

	/**
	 * 
	 * @param gameController gameController to execute the next function in
	 * @throws Exception when execute is not possible
	 */
	protected void executeNextBlock(GameController gameController) throws Exception {
		nextToExecute = BF.execute(nextToExecute, gameController);
	}

	/**
	 * @post nextToExecute == null
	 */
	protected void stopExecution() {
		if (programInProgress()) {
			nextToExecute = null;
		}
	}

	protected void addBlock(PresentationBlock<?> pBlock) {
		addTopLevelBlock(BFP.getBlock(pBlock));
		blocksLeft -= BF.getAllNextBlocks(BFP.getBlock(pBlock)).size();
	}

	protected void removeBlock(PresentationBlock<?> pBlock) {
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
	protected void addTopLevelBlock(Block block) {
		topLevelBlocks.add(block);
	}

	/**
	 * 
	 * @param block, block to remove from topLevelBlocks
	 * @throws Exception block is not a top level block
	 */
	protected void removeTopLevelBlock(Block block) {
		if (topLevelBlocks.contains(block)) {
			topLevelBlocks.remove(block);
		}
	}

	/**
	 * 
	 * @param block block to check
	 * @return topLevelBlocks.contains(block)
	 */
	protected boolean isTopLevelBlock(Block block) {
		return topLevelBlocks.contains(block);
	}

	/**
	 * 
	 * @return nextToExecute
	 */
	protected Block getNextBlockToExecute() {
		return this.nextToExecute;
	}

	/**
	 * 
	 * @return topLevelBlocks
	 */
	protected List<Block> getTopBlocks() {
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
	
	protected void connect(Block firstBlock, Block secondBlock) {
		BF.connect(firstBlock, secondBlock);
	}

	protected int getBlocksLeft() {
		return blocksLeft;
	}

	protected void setBlocksLeft(int blocksLeft) {
		this.blocksLeft = blocksLeft;
	}

	protected void increaseBlocksLeft() {
		blocksLeft += 1;
	}

	protected void decreaseBlocksLeft() {
		blocksLeft -= 1;
	}

}
