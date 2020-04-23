package domain.block;

import java.util.List;

import domain.GameController;
import impl.root.ImplementationGameWorld;
import presentation.block.PresentationBlock;

public class ImplementationBlock implements FacadeBlock{

	@Override
	public SingleSurroundingBlock makeIfBlock() {
		return new IfBlock();
	}


	@Override
	public ChainConditionBlock makeNotBlock() {
		return new NotBlock();
	}


	@Override
	public SingleSurroundingBlock makeWhileBlock() {
		return new WhileBlock();
	}
	
	@Override
	public SingleConditionBlock makeSingleConditionBlock(String name) {
		return new SingleConditionBlock(name);
	}
	
	@Override
	public ActionBlock makeActionBlock(String name) {
		return new ActionBlock(name);
	}

	@Override
	public boolean connect(Block firstBlock, Block secondBlock) {
		return firstBlock.setNextBlock(secondBlock);
		
	}

	@Override
	public void disconnect(Block blockToDisconnect) {
		if (blockToDisconnect != null)
			blockToDisconnect.disconnect();
	}


	@Override
	public void addBodyBlock(SurroundingBlock surroundingBlock, SequenceBlock block) {
		if (block != null) {
			surroundingBlock.setBodyBlock(block);
		}
		else {
			surroundingBlock.removeBodyBlock();
		}
	}

	@Override
	public void setConditionBlock(SurroundingBlock surroundingBlock, ConditionBlock conditionBlock) {
		if (conditionBlock == null) {
			surroundingBlock.removeConditionBlock();
		}
		else {
			surroundingBlock.setConditionBlock(conditionBlock);
		}
	}

	@Override
	public boolean isValidStartingBlock(Block block) {
		return block.hasValidExecutionColumn();
	}

	@Override
	public Block execute(Block block, GameController GC) throws Exception {
		return block.execute(GC);
	}

	@Override
	public Block getNextBlock(Block block) {
		return block.getNextBlock();
	}

	@Override
	public Block getPreviousBlock(Block block) {
		return block.getPreviousBlock();
	}

	@Override
	public ConditionBlock getConditionBlock(SurroundingBlock block) {
		return block.getConditionBlock();
	}

	@Override
	public SequenceBlock getBodyBlock(SurroundingBlock block) {
		return block.getBodyBlock();
	}

	@Override
	public PresentationBlock<?> getPresentationBlock(Block block) {
		return block.getPresentationBlock();
	}

	@Override
	public void setPresentationBlock(Block block, PresentationBlock<?> presentationBlock) {
		block.setPresentationBlock(presentationBlock);		
	}

	@Override
	public String getName(Block block) {
		return block.getName();
	}

	@Override
	public Block makeNewBlockOfThisType(Block block) {
		return block.getNewBlockOfThisType();
	}

	@Override
	public List<Block> getAllNextBlocks(Block block) {
		return block.getAllNextBlocks();
	}

	@Override
	public SurroundingBlock getSurroundingBlock(Block block) {
		return block.getSurroundingBlock();
	}

	@Override
	public Block getLastBlock(Block block) {
		return block.getLastBlock();
	}



	
	
}