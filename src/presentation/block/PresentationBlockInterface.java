package presentation.block;

import domain.block.ActionBlock;
import domain.block.Block;
import domain.block.ChainConditionBlock;
import domain.block.SingleConditionBlock;
import domain.block.SingleSurroundingBlock;
import domain.game_world.Vector;

public interface PresentationBlockInterface {
	
	// Make The Blocks:
		// _________________________________________________________________________________________________________\\

		/**
		 * 
		 * @return A block that can be used as an if statement with an empty condition
		 *         and body.
		 */
		public PresentationBlock<SingleSurroundingBlock> makeIfBlock(Vector pos);

		/**
		 * 
		 * @return A block that is able to perform the action Move Forward.
		 */
		public PresentationBlock<ActionBlock> makeMoveForwardBlock(Vector pos);

		/**
		 * 
		 * @return A not condition block.
		 */
		public PresentationBlock<ChainConditionBlock> makeNotBlock(Vector pos);

		/**
		 * 
		 * @return A block that is able to perform the action Turn Left.
		 */
		public PresentationBlock<ActionBlock> makeTurnLeftBlock(Vector pos);

		/**
		 * 
		 * @return A block that is able to perform the action Turn Right.
		 */
		public PresentationBlock<ActionBlock> makeTurnRightBlock(Vector pos);

		/**
		 * 
		 * @return A block that is able to check if there is a wall in front of the
		 *         players avatar..
		 */
		public PresentationBlock<SingleConditionBlock> makeWallInFrontBlock(Vector pos);

		/**
		 * 
		 * @return A block that can be used as a while loop with an empty condition and
		 *         body.
		 */
		public PresentationBlock<SingleSurroundingBlock> makeWhileBlock(Vector pos);

		/**
		 * 
		 * @param firstBlock  The block where the secondBlock needs to get connected to.
		 * @param secondBlock The block which gets connected to the firstBlock
		 * @Post The secondBlock is connected to the first block.
		 */
		
		//______________________________________________________________________________________//
		

}
