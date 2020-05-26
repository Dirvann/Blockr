package domain;

import java.util.ArrayList;
import java.util.List;

import command.ExecutionCommand;
import domain.block.Block;
import domain.block.FunctionCallBlock;
import domain.block.FunctionDefinitionBlock;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import exceptions.domainExceptions.BlockColumnNotExecutableException;
import exceptions.domainExceptions.CantRunConditionException;
import exceptions.domainExceptions.NotOneStartingBlockException;
import game_world.api.Snapshot;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

/**
 * A class of ProgramArea that has a list of top level blocks. It also contains
 * the currently executed and next to execute blocks.
 *
 * @version 4.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum 
 * 		   Dirk Vanbeveren 
 * 		   Geert Wesemael
 */
public class ProgramArea {

	private int blocksLeft = 15;
	private List<Block> topLevelBlocks = new ArrayList<Block>();
	private List<FunctionDefinitionBlock> functionBlocks = new ArrayList<FunctionDefinitionBlock>();

	protected Block nextToExecute = null;
	protected Block currentExe = null;

	private ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();
	private ImplementationBlock BF = new ImplementationBlock();

	protected ExecutionCommand exeCmd = null;

	/**
	 * Creates a new ProgramArea
	 * 
	 * @post There are no top level blocks. 
	 * 		 | new.topLevelBlocks.size() == null
	 */
	protected ProgramArea() {
		this.topLevelBlocks = new ArrayList<Block>();
	}

	/**
	 * The number of top level blocks
	 * 
	 * @return number of top level blocks 
	 * 		   | result = topLevelBlocks.size()
	 */
	protected int nbTopLevelBlocks() {
		return topLevelBlocks.size();
	}

	/**
	 * Returns true if the program is running.
	 * 
	 * @return true if there is no next block to execute. 
	 * 		   | nextToExecute != null
	 */
	protected Boolean programInProgress() {
		return nextToExecute != null;
	}

	/**
	 * Set the next block to execute to the top level block. This only works if
	 * there is only one top level block and the block is a sequence block.
	 * 
	 * @post The next block to execute is the top level block 
	 * 		 |nextToExecute = topLevelBlocks.get(0)
	 * @post There are no currently executed blocks if the program wasn't running 
	 * 		 | if (!programInProgress()) 
	 * 		 | then currentExe = null && exeCmd = null
	 * @throws Exception When there is more than one top level block, the top level
	 *                   block is not a sequence block or the top level block is not
	 *                   a valid starting block.
	 */
	protected void startExecution() throws Exception {
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
			currentExe = null;
			exeCmd = null;
		}
	}

	/**
	 * Execute the next block to execute and set the next/previous/current execute
	 * to the right blocks.
	 * 
	 * @param  gameController 
	 * 		   The gameController to execute the next function in.
	 * @post   The currently executed block is equal to the previous executed block.
	 *         |new.previousExe = currentExe
	 * @post   The currently executed block is equal to the previous executed block.
	 *         |new.currentExe = nextToExecute
	 * @return The execution command of the executed command. 
	 * 		   | return = programArea.exeCmd
	 * @throws Exception When execute is not possible.
	 */
	protected ExecutionCommand executeNextBlock(GameController GC) throws Exception {
		// undo redo info collect
		this.exeCmd = null;
		Snapshot snapshot = GC.getGameWorldImplementation().makeSnapshot();
		Block previousExe = currentExe;
		currentExe = nextToExecute;
		// execute() will also make an empty ExecutionCommand in programArea.
		nextToExecute = BF.execute(nextToExecute, GC);

		// fill ExecutionCommand with needed info and return command.
		if (exeCmd == null)
			return null;
		this.exeCmd.setPrevious(previousExe);
		this.exeCmd.setCurrent(currentExe);
		this.exeCmd.setNext(nextToExecute);
		this.exeCmd.setSnapshot(snapshot);
		return exeCmd;
	}

	/**
	 * Stop the execution of the program.
	 * 
	 * @post There is no block to execute next. 
	 * 		 |nextToExecute == null
	 */
	protected void stopExecution() {
		if (programInProgress()) {
			nextToExecute = null;
		}
		for(int i = 0; i < functionBlocks.size(); i++) {
			BF.resetFunctionCallers(functionBlocks.get(i));
		}
	}

	/**
	 * Add a block to the top level blocks. The block is associated to the given
	 * presentationBlock.
	 * 
	 * @param pBlock 
	 * 		  The given presentationblock.
	 * @post  The list of top level blocks contains the block associated to the given
	 *        presentationblock.
	 *        | new.getTopBlocks().contains(BFP.getBlock(pBlock))
	 * @post  The amount of blocks left is lowered by one. 
	 * 		  | new.blocksLeft == blocksLeft - 1
	 */
	protected void addBlock(PresentationBlock<?> pBlock) {
		Block block = BFP.getBlock(pBlock);
		addTopLevelBlock(block);
		blocksLeft -= BF.getAllNextBlocks(block).size();
	}

	/**
	 * Remove a block from the top level blocks. The block is associated to the
	 * given presentationBlock.
	 * 
	 * @param pBlock 
	 * 		  The given presentationblock.
	 * @post  The list of top level blocks does not contain the block associated to
	 *        the given presentationblock.
	 *        | !new.getTopBlocks().contains(BFP.getBlock(pBlock))
	 * @post One is added to the amount of blocks left. 
	 * 		  | new.blocksLeft == blocksLeft + 1
	 */
	protected void removeBlock(Block block,GameController GC) {
		BF.disconnect(block);
		blocksLeft += BF.getAllNextBlocks(block).size();

		if (!(block instanceof FunctionDefinitionBlock)) {
			removeTopLevelBlock(block);
		} else {
			List<Block> blocksFunction = BF.getAllNextBlocks(block);
			for (Block blockOfFunction : blocksFunction) {
				if (blockOfFunction instanceof FunctionCallBlock
						&& BF.getID((FunctionCallBlock) blockOfFunction) == BF.getID((FunctionDefinitionBlock) block)) {
					blocksLeft--;
				}
			}
			BF.RemoveFunctionBlock((FunctionDefinitionBlock) block, GC);
			this.functionBlocks.remove(block);

		}
	}

	/**
	 * Add the given block to the list of top level blocks.
	 * 
	 * @param block
	 * 		  The block to add to topLevelBlocks.
	 * @post  The list of top level blocks contains the given block 
	 * 		  | new.getTopBlocks().contains(block)
	 */
	protected void addTopLevelBlock(Block block) {
		try {
			if (!topLevelBlocks.contains(block) && !functionBlocks.contains(block)) {
				if (block instanceof FunctionDefinitionBlock) {
					functionBlocks.add((FunctionDefinitionBlock) block);
				} else
					topLevelBlocks.add(block);
			}
		} catch (Exception e) {
		}
		;
	}

	/**
	 * Remove the given block from the list of top level blocks.
	 * 
	 * @param block The block to remove from topLevelBlocks
	 * @post  The list of top level blocks does not contain the given block 
	 * 		  | !new.getTopBlocks().contains(block)
	 */
	protected void removeTopLevelBlock(Block block) {
		if (topLevelBlocks.contains(block)) {
			topLevelBlocks.remove(block);
		}
	}

	/**
	 * Check if the given block is a top level block.
	 * 
	 * @param  block The block to check
	 * @return true if the list of top level blocks contains the given block.
	 *         | topLevelBlocks.contains(block)
	 */
	protected boolean isTopLevelBlock(Block block) {
		return topLevelBlocks.contains(block);
	}

	/**
	 * The next block to execute.
	 * 
	 * @return nextToExecute
	 */
	protected Block getNextBlockToExecute() {
		return this.nextToExecute;
	}

	/**
	 * The list of top level blocks.
	 * 
	 * @return topLevelBlocks
	 */
	protected List<Block> getTopBlocks() {
		List<Block> allTopBlocks = new ArrayList<Block>();
		allTopBlocks.addAll(this.topLevelBlocks);
		allTopBlocks.addAll(functionBlocks);
		return allTopBlocks;
	}

	/**
	 * A copy of all the blocks in the programArea
	 * 
	 * @return copy of topLevelBlocks
	 */
	protected List<Block> getCopyOfAllBlocks() {
		List<Block> list = new ArrayList<Block>();
		for (Block block : this.getTopBlocks()) {
			list.addAll(BF.getAllNextBlocks(block));
		}

		return list;
	}

	/**
	 * The amount of blocks you can add to the programArea.
	 * 
	 * @return blocksLeft
	 */
	protected int getBlocksLeft() {
		return blocksLeft;
	}

	/**
	 * The execution command of the current execution.
	 * 
	 * @return exeCmd
	 */
	protected ExecutionCommand getExecutionCommand() {
		return exeCmd;
	}

	protected List<FunctionCallBlock> getAllFunctionCallsWithID(int ID) {
		List<FunctionCallBlock> allCallers = new ArrayList<FunctionCallBlock>();
		for (Block block : this.getCopyOfAllBlocks()) {
			if (block instanceof FunctionCallBlock && BF.getID((FunctionCallBlock) block) == ID) {
				allCallers.add((FunctionCallBlock) block);
			}
		}
		return allCallers;

	}

	/**
	 * Set the execution command to the given execution command.
	 * 
	 * @param exeCmd The execution command
	 * @post The execution command is equal to the given execution command. 
	 * 		 | new.getExecutionCommand() = exeCmd
	 */
	protected void setExecutionCommand(ExecutionCommand exeCmd) {
		this.exeCmd = exeCmd;
	}

}
