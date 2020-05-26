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
public class ImplementationBlock {

	
	public SingleSurroundingBlock makeIfBlock() {
		return new IfBlock();
	}


	
	public ChainConditionBlock makeNotBlock() {
		return new NotBlock();
	}


	
	public SingleSurroundingBlock makeWhileBlock() {
		return new WhileBlock();
	}
	
	
	public SingleConditionBlock makeSingleConditionBlock(Predicate predicate) {
		return new SingleConditionBlock(predicate);
	}
	
	
	public ActionBlock makeActionBlock(Action action) {
		return new ActionBlock(action);
	}

	
	public boolean connect(Block firstBlock, Block secondBlock) {
		if (firstBlock != null)
			return firstBlock.setNextBlock(secondBlock);
		return false;
		
	}

	
	public void disconnect(Block blockToDisconnect) {
		if (blockToDisconnect != null)
			blockToDisconnect.disconnect();
	}


	
	public void setBodyBlock(SurroundingBlock surroundingBlock, SequenceBlock block) {
		if (block != null) {
			surroundingBlock.setBodyBlock(block);
		}
		else {
			surroundingBlock.removeBodyBlock();
		}
	}

	
	public void setConditionBlock(SurroundingBlock surroundingBlock, ConditionBlock conditionBlock) {
		if (conditionBlock == null) {
			surroundingBlock.removeConditionBlock();
		}
		else {
			surroundingBlock.setConditionBlock(conditionBlock);
		}
	}

	
	public boolean isValidStartingBlock(Block block) {
		return block.hasValidExecutionColumn();
	}

	
	public Block execute(Block block, GameController GC) throws Exception {
		return block.execute(GC);
	}

	
	public Block getNextBlock(Block block) {
		return block.getNextBlock();
	}

	
	public Block getBlockAbove(Block block) {
		return block.getBlockAbove();
	}
	
	public Block getPreviousBlock(Block block) {
		return block.getPrevious();
	}

	
	public ConditionBlock getConditionBlock(SurroundingBlock block) {
		return block.getConditionBlock();
	}

	
	public SequenceBlock getBodyBlock(SurroundingBlock block) {
		return block.getBodyBlock();
	}

	
	public PresentationBlock<?> getPresentationBlock(Block block) {
		return block.getPresentationBlock();
	}

	
	public void setPresentationBlock(Block block, PresentationBlock<?> presentationBlock) {
		block.setPresentationBlock(presentationBlock);		
	}

	
	public String getName(Block block) {
		return block.getName();
	}

	
	public Block makeNewBlockOfThisType(Block block) {
		return block.getNewBlockOfThisType();
	}

	
	public List<Block> getAllNextBlocks(Block block) {
		return block.getAllNextBlocks();
	}

	
	public SurroundingBlock getSurroundingBlock(Block block) {
		return block.getSurroundingBlock();
	}

	
	public Block getLastBlock(Block block) {
		return block.getLastBlock();
	}


	
	public FunctionCallBlock makeCaller(FunctionDefinitionBlock function) {
		return new FunctionCallBlock(function);
	}


	
	public FunctionDefinitionBlock makeFunctionDefinition(int ID) {
		return new FunctionDefinitionBlock(ID);
	}


	
	public void setBodyBlock(FunctionDefinitionBlock function, SequenceBlock bodyBlock) {
		function.setBodyBlock(bodyBlock);
		
	}


	
	public void RemoveFunctionBlock(FunctionDefinitionBlock function, GameController GC) {
		function.removeFunctionDefinition(GC);
		
	}
	
	public SequenceBlock getBodyBlock(FunctionDefinitionBlock block) {
		return block.getBodyBlock();
	}

	
	public int getID(FunctionDefinitionBlock definition) {
		return definition.ID;
	}

	
	public int getID(FunctionCallBlock block) {
		return block.getFunctionDefinition().ID;
		
	}

	
	public FunctionDefinitionBlock getFunctionBlock(Block block) {
		if (!(block instanceof SequenceBlock)) return null;
		return ((SequenceBlock) block).function;
	}


	
	public void resetFunctionCallers(FunctionDefinitionBlock functionDefinition) {
		functionDefinition.callStack = new ArrayList<SequenceBlock>();
	}

	
	public void setID(FunctionDefinitionBlock function, int id) {
		function.ID = id;
		
	}
	
	public void deleteFunctionCall(FunctionCallBlock functionCall, GameController GC) {
		functionCall.delete(GC);
	}

	
	
}