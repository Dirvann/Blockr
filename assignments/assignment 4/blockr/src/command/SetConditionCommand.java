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
public class SetConditionCommand implements Command {
	ImplementationBlock BF = new ImplementationBlock();
	ConditionBlock blockToConnect;
	SurroundingBlock surroundingBlock;
	ConditionBlock nextBlock;
	GameController GC;

	/**
	 * Makes a Command used to set the condition of a surrounding block.
	 * 
	 * @param blockToConnectTo 
	 * 		  Block before group of blocks connected.
	 * @param blockToConnect   
	 * 		  First block of group of blocks that gets connected.
	 * @param nextBlock        
	 * 		  Block after group of blocks connected.
	 * @post  The objects surroundingBlock, blockToConnect, nextBlock and GC are
	 *        stored in this Command for later use.
	 *       | new.blockToConnect == blockToConnect
	 * 		 | new.surroundingBlock == surroundingBlock
	 * 	  	 | new.nextBlock == nextBlock
	 * 		 | new.GC == GC
	 */
	public SetConditionCommand(SurroundingBlock surroundingBlock, ConditionBlock blockToConnect,
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
