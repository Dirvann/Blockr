package domain.block;

import java.util.List;

import domain.GameController;
import game_world.api.Action;
import game_world.api.Predicate;
import presentation.block.PresentationBlock;
/**
 * The Implementation of all the block classes.
 * 
 * @version 3.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
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
	public SingleConditionBlock makeSingleConditionBlock(Predicate predicate) {
		return new SingleConditionBlock(predicate);
	}
	
	@Override
	public ActionBlock makeActionBlock(Action action) {
		return new ActionBlock(action);
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
	public void setBodyBlock(SurroundingBlock surroundingBlock, SequenceBlock block) {
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


	@Override
	public FunctionCall makeCaller(FunctionDefinition function) {
		return new FunctionCall(function);
	}


	@Override
	public FunctionDefinition makeFunctionDefinition(int ID) {
		return new FunctionDefinition(ID);
	}


	@Override
	public void setBodyBlock(FunctionDefinition function, SequenceBlock bodyBlock) {
		function.setBodyBlock(bodyBlock);
		
	}


	@Override
	public void RemoveFunctionBlock(FunctionDefinition function) {
		function.removeFunctionDefinition();
		
	}
	@Override
	public SequenceBlock getBodyBlock(FunctionDefinition block) {
		return block.getBodyBlock();
	}


	public int getID(FunctionDefinition definition) {
		return definition.ID;
	}


	public int getID(FunctionCall block) {
		return block.getFunctionDefinition().ID;
		
	}



	
	
}