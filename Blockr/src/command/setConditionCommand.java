package command;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.ConditionBlock;
import domain.block.ImplementationBlock;
import domain.block.SurroundingBlock;

public class setConditionCommand implements Command{
	ImplementationBlock BF = new ImplementationBlock();
	
	//first block of group of blocks that gets connected.
	ConditionBlock blockToConnect;
	//the surrounding block blockToConnect will be connected to.
	SurroundingBlock surroundingBlock;
	//block after group of blocks connected
	ConditionBlock nextBlock;
	//The gamecontroller wher the blocks exist
	GameController GC;
	ImplementationGameController GCF = new ImplementationGameController();
	
	/**
	 * 
	 * @param blockToConnectTo block before group of blocks connected
	 * @param blockToConnect first block of group of blocks that gets connected.
	 * @param nextBlock block after group of blocks connected
	 */
	public setConditionCommand(SurroundingBlock surroundingBlock, ConditionBlock blockToConnect, ConditionBlock nextBlock, GameController GC) {
		this.blockToConnect = blockToConnect;
		this.surroundingBlock = surroundingBlock;
		this.nextBlock = nextBlock;
		this.GC = GC;
	}

	@Override
	public void execute() {
		GCF.setCondition(surroundingBlock, blockToConnect, GC);	
	}

	@Override
	public void undo() {
		GCF.disconnect(blockToConnect,GC);
		GCF.disconnect(nextBlock, GC);
		GCF.setCondition(surroundingBlock, nextBlock, GC);
	}
}
