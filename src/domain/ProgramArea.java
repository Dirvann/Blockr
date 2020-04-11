package domain;

import java.util.ArrayList;
import java.util.List;

import domain.block.Block;
import domain.block.ConditionBlock;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import exceptions.domainExceptions.BlockColumnNotExecutableException;
import exceptions.domainExceptions.CantRunConditionException;
import exceptions.domainExceptions.NotOneStartingBlockException;
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
	 * @throws NotOneStartingBlockException 
	 * @throws CantRunConditionException 
	 */
	protected boolean hasValidTopLevelBlock() throws NotOneStartingBlockException, CantRunConditionException {
		if (nbTopLevelBlocks() == 1) {
			Block topLevelBlock = topLevelBlocks.get(0);
			if (BF.isValidStartingBlock(topLevelBlock)) {
				return true;
			}
			else {
				throw new CantRunConditionException();}
		} else {
			throw new NotOneStartingBlockException();
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
	 * @throws Exception 
	 */
	protected Boolean canStartExecution() throws Exception {
		return (!programInProgress() && hasValidTopLevelBlock());
		// TODO: maybe check if program is valid
	}

	/**
	 * @throws Exception 
	 * @post nextToExecute != null
	 */
	protected void startExecution() throws Exception {
		// programInProgress() should in theory never return true, but it's there to make sure;
		if (programInProgress()) {
			return;
		}
		
		if (this.nbTopLevelBlocks() != 1) {
			throw new NotOneStartingBlockException();
		}
		
		Block topLevelBlock = topLevelBlocks.get(0);
		
		if (!(topLevelBlock instanceof SequenceBlock)) {
			throw new CantRunConditionException();
		}
		
		if (!BF.isValidStartingBlock(topLevelBlock)) {
			throw new BlockColumnNotExecutableException();
		}
		
		else {
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
			System.out.println("a block is correctly removed");
		} catch (Exception e) {
			System.out.println("Something is not right, a bug may happen soon.");
		}
	}

	/**
	 * 
	 * @param block, block to add to topLevelBlocks
	 */
	protected void addTopLevelBlock(Block block) {
		try {
			if (!topLevelBlocks.contains(block))
				topLevelBlocks.add(block);
		}
		catch (Exception e) {};
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
	protected List<Block> getAllBlocks() {
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
