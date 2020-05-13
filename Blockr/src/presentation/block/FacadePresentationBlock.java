package presentation.block;

import java.awt.Graphics;

import command.Command;
import domain.GameController;
import domain.Vector;
import domain.block.ActionBlock;
import domain.block.Block;
import domain.block.ChainConditionBlock;
import domain.block.SingleConditionBlock;
import domain.block.SingleSurroundingBlock;
import game_world.api.Action;
import game_world.api.Predicate;

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
		 * @return A not condition block.
		 */
		public PresentationBlock<ChainConditionBlock> makeNotBlock(Vector pos);


		/**
		 * 
		 * @return A block that can be used as a while loop with an empty condition and
		 *         body.
		 */
		public PresentationBlock<SingleSurroundingBlock> makeWhileBlock(Vector pos);
		
		
		public PresentationBlock<ActionBlock> makeActionBlock(Action action, Vector pos);
		
		public PresentationBlock<SingleConditionBlock> makeSingleConditionBlock(Predicate predicate, Vector pos);
		

		
		public boolean collidesWithPosition(Vector pos, PresentationBlock<?> block);
		
		public void draw(Graphics g, PresentationBlock<?> block);
		
		public void highLight(PresentationBlock<?> block, Graphics g);
		
		public Block getBlock(PresentationBlock<?> block);
		
		public PresentationBlock<?> makeCopy(PresentationBlock<?> block);

		public Command canSnap(PresentationBlock<?> firstBlock, PresentationBlock<?> secondBlock, GameController GC);
		
		public void setPosition(PresentationBlock<?> block, Vector position);
		
		public Vector getPosition(PresentationBlock<?> block);
		
		public void addToPosition(PresentationBlock<?> block, Vector deltaPos);
		
		//______________________________________________________________________________________//
		

}
