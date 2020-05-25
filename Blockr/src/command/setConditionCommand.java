package command;

import domain.GameController;
import domain.block.ConditionBlock;
import domain.block.ImplementationBlock;
import domain.block.SurroundingBlock;

/**
 * A class that holds all the information about the action where a block got
 * connected as a condition to a surroundingBlock. This information consists of
 * the objects blockToConnect, surroundingBlock and nextBlock (the block that
 * was previously connected as a condition). The class also specifies what must
 * happen to undo and execute this command.
 * 
 * @version 3.0
 * @author Andreas Awouters, Thomas Van Erum, Dirk Vanbeveren, Geert Wesemael
 *
 */
public class setConditionCommand implements Command {
	ImplementationBlock BF = new ImplementationBlock();

	// first block of group of blocks that gets connected.
	ConditionBlock blockToConnect;
	// the surrounding block blockToConnect will be connected to.
	SurroundingBlock surroundingBlock;
	// block after group of blocks connected
	ConditionBlock nextBlock;
	// The gamecontroller wher the blocks exist
	GameController GC;

	/**
	 * Makes a Command used to set the condition of a surrounding block.
	 * 
	 * @param blockToConnectTo block before group of blocks connected
	 * @param blockToConnect   first block of group of blocks that gets connected.
	 * @param nextBlock        block after group of blocks connected
	 * 
	 * @post the objects surroundingBlock, blockToConnect, nextBlock and GC are
	 *       stored in this Command for later use.
	 */
	public setConditionCommand(SurroundingBlock surroundingBlock, ConditionBlock blockToConnect,
			ConditionBlock nextBlock, GameController GC) {
		this.blockToConnect = blockToConnect;
		this.surroundingBlock = surroundingBlock;
		this.nextBlock = nextBlock;
		this.GC = GC;
	}

	@Override
	public void execute() {
		GC.setCondition(surroundingBlock, blockToConnect);
	}

	@Override
	public void undo() {
		GC.disconnect(blockToConnect);
		GC.disconnect(nextBlock);
		GC.setCondition(surroundingBlock, nextBlock);
	}
}
