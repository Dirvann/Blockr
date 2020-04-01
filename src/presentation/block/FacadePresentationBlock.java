package presentation.block;

import java.awt.Graphics;

import domain.Vector;
import domain.block.ActionBlock;
import domain.block.Block;
import domain.block.ChainConditionBlock;
import domain.block.SingleConditionBlock;
import domain.block.SingleSurroundingBlock;

public interface FacadePresentationBlock {
	
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

		
		public boolean collidesWithPosition(Vector pos, PresentationBlock<?> block);
		
		public void draw(Graphics g, PresentationBlock<?> block);
		
		public void highLight(PresentationBlock<?> block, Graphics g);
		
		public Block getBlock(PresentationBlock<?> block);
		
		public PresentationBlock<?> makeCopy(PresentationBlock<?> block);

		public boolean canSnap(PresentationBlock<?> firstBlock, PresentationBlock<?> secondBlock);
		
		public void setPosition(PresentationBlock<?> block, Vector position);
		
		public Vector getPosition(PresentationBlock<?> block);
		
		public void addToPosition(PresentationBlock<?> block, Vector deltaPos);
		
		//______________________________________________________________________________________//
		

}
