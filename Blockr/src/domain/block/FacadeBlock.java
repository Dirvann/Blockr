package domain.block;

import java.util.List;

import domain.GameController;
import domain.ProgramArea;
import game_world.api.Action;
import game_world.api.Predicate;
import presentation.block.PresentationBlock;
/**
 * The Facade of the ImplemenationBlock class.
 * 
 * @version 3.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public interface FacadeBlock {

	// Make The Blocks:
	// _________________________________________________________________________________________________________\\

	/**
	 * 
	 * @return A block that can be used as an if statement with an empty condition
	 *         and body.
	 */
	public SingleSurroundingBlock makeIfBlock();


	/**
	 * 
	 * @return A not condition block.
	 */
	public ChainConditionBlock makeNotBlock();

	/**
	 * 
	 * @return A block that can be used as a while loop with an empty condition and
	 *         body.
	 */
	public SingleSurroundingBlock makeWhileBlock();
	
	/**
	 * 
	 * @param function The function definition block of the caller.
	 * @return A block that executes the function as a functionCall
	 */
	public FunctionCall makeCaller(FunctionDefinition function);
	
	/**
	 * 
	 * @param ID The ID of the new function definition.
	 * @return A new function with an empty body and as Id the given ID.
	 */
	public FunctionDefinition makeFunctionDefinition(int ID);

	/**
	 * 
	 * @param block
	 * @return A block of the same type with no connections at all.
	 */
	public Block makeNewBlockOfThisType(Block block);
	
	

	// ________________________________________________________________________________________//
	// Connection of the blocks
	// ________________________________________________________________________________________//

	/**
	 * 
	 * @param firstBlock  The block where the secondBlock needs to get connected to.
	 * @param secondBlock The block which gets connected to the firstBlock
	 * @Post The secondBlock is connected to the first block.
	 */
	public boolean connect(Block firstBlock, Block secondBlock);

	/**
	 * 
	 * @param block
	 * @Post Block is disconnected to its previous block. This can be a horizontal
	 *       connection (condition) or a vertical connection (sequence). This
	 *       creates a new group of blocks where block is the first one.
	 */
	public void disconnect(Block block);

	/**
	 * 
	 * @param surroundingBlock Is a block of the type {@link SurroundingBlock}.
	 * @param bodyBlock        Is a block of the type {@link SequenceBlock}.
	 * @throws Exception If bodyBlock is not the first one of a group of blocks.
	 * @Post The group of blocks where bodyBlock is the first one will be added in
	 *       front of the current blocks of the body of the surroundingBlock.
	 * 
	 */
	public void setBodyBlock(SurroundingBlock surroundingBlock, SequenceBlock bodyBlock);
	
	
	/**
	 * 
	 * @param function The desired function where you will set the body.
	 * @param bodyBlock The desired body of the function.
	 * @throws Exception If bodyBlock is not the first one of a group of blocks.
	 * @Post The body of the function will be set to the parameter bodyBlock.
	 */
	public void setBodyBlock(FunctionDefinition function, SequenceBlock bodyBlock);

	/**
	 * 
	 * @param surroundingBlock
	 * @param conditionBlock
	 * @Post conditionBlock will be set as the condition of the surrounding block.
	 *       If possible, the original condition will be placed after
	 *       conditionBlock.
	 */
	public void setConditionBlock(SurroundingBlock surroundingBlock, ConditionBlock conditionBlock);

	/**
	 * @param block
	 * @return The block that is connected after this block (on the right side for
	 *         conditionBlocks or on the down side of sequenceBlocks).
	 */
	public Block getNextBlock(Block block);

	/**
	 * @param block
	 * @return The block that is connected before this block (on the left side for
	 *         conditionBlocks or on the upper side of sequenceBlocks).
	 */
	public Block getBlockAbove(Block block);
	
	/**
	 * 
	 * @param block
	 * @return the last block of the group of blocks where block is a part of.
	 */
	public Block getLastBlock(Block block);

	/**
	 * 
	 * @param block
	 * @return Returns the first condition block (might be of a sequence accessible
	 *         by block.next()).
	 */
	public ConditionBlock getConditionBlock(SurroundingBlock block);

	/**
	 * 
	 * @param block
	 * @return The first block which is surrounded by this if block
	 */
	public SequenceBlock getBodyBlock(SurroundingBlock block);

	// ________________________________________________________________________________________________//
	// execution functions
	// ________________________________________________________________________________________________//
	/**
	 * 
	 * @param block
	 * @return True if block is a possible block to start the program with.
	 */
	public boolean isValidStartingBlock(Block block);

	/**
	 * @param gameController
	 * @param block
	 * @return next block in sequence
	 * @post every necessary command in the block is executed.
	 * @throws Exception if a block in the sequence is not executable.
	 */
	public Block execute(Block block, GameController gameController) throws Exception;

	// ________________________________________________________________________________________________//
	// presentation functions
	// ________________________________________________________________________________________________//

	/**
	 * 
	 * @return The presentation of this block. Returns null if none has been set.
	 */
	public PresentationBlock<?> getPresentationBlock(Block block);

	/**
	 * 
	 * @param presentationBlock The desired presentation for this Block.
	 */
	public void setPresentationBlock(Block block, PresentationBlock<?> presentationBlock);

	/**
	 * 
	 * @param block
	 * @return the name of the block.
	 */
	public String getName(Block block);

	/**
	 * 
	 * @param block
	 * @return All the blocks connected beneath or to the right of this block.
	 */
	public List<Block> getAllNextBlocks(Block block);

	/**
	 * 
	 * @param block
	 * @return the surrounding block where block is positioned in or (in case block
	 *         is a condition) connected to.
	 */
	public SurroundingBlock getSurroundingBlock(Block block);


	public SingleConditionBlock makeSingleConditionBlock(Predicate predicate);
	
	public void RemoveFunctionBlock(FunctionDefinition function, ProgramArea programArea);


	public ActionBlock makeActionBlock(Action action);


	public SequenceBlock getBodyBlock(FunctionDefinition functionDefinition);


	FunctionDefinition getFunctionBlock(Block block);


	void resetFunctionCallers(FunctionDefinition functionDefinition);


}