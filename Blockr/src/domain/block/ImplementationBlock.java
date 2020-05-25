package domain.block;

import java.util.ArrayList;
import java.util.List;

import domain.GameController;
import game_world.api.Action;
import game_world.api.Predicate;
import presentation.block.PresentationBlock;
/**
 * The Implementation of all the block classes.
 * 
 * @version 4.0
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
		if (firstBlock != null)
			return firstBlock.setNextBlock(secondBlock);
		return false;
		
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
	public Block getBlockAbove(Block block) {
		return block.getBlockAbove();
	}
	
	public Block getPreviousBlock(Block block) {
		return block.getPrevious();
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
	public FunctionCallBlock makeCaller(FunctionDefinitionBlock function) {
		return new FunctionCallBlock(function);
	}


	@Override
	public FunctionDefinitionBlock makeFunctionDefinition(int ID) {
		return new FunctionDefinitionBlock(ID);
	}


	@Override
	public void setBodyBlock(FunctionDefinitionBlock function, SequenceBlock bodyBlock) {
		function.setBodyBlock(bodyBlock);
		
	}


	@Override
	public void RemoveFunctionBlock(FunctionDefinitionBlock function, GameController GC) {
		function.removeFunctionDefinition(GC);
		
	}
	@Override
	public SequenceBlock getBodyBlock(FunctionDefinitionBlock block) {
		return block.getBodyBlock();
	}

	@Override
	public int getID(FunctionDefinitionBlock definition) {
		return definition.ID;
	}

	@Override
	public int getID(FunctionCallBlock block) {
		return block.getFunctionDefinition().ID;
		
	}

	@Override
	public FunctionDefinitionBlock getFunctionBlock(Block block) {
		if (!(block instanceof SequenceBlock)) return null;
		return ((SequenceBlock) block).function;
	}


	@Override
	public void resetFunctionCallers(FunctionDefinitionBlock functionDefinition) {
		functionDefinition.callStack = new ArrayList<SequenceBlock>();
	}

	@Override
	public void setID(FunctionDefinitionBlock function, int id) {
		function.ID = id;
		
	}
	@Override
	public void deleteFunctionCall(FunctionCallBlock functionCall, GameController GC) {
		functionCall.delete(GC);
	}

	
	
}