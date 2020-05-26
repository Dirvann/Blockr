package presentation.block;

import java.awt.Graphics;

import command.Command;
import domain.GameController;
import game_world.api.Vector;
import domain.block.ActionBlock;
import domain.block.Block;
import domain.block.ChainConditionBlock;
import domain.block.FunctionCallBlock;
import domain.block.FunctionDefinitionBlock;
import domain.block.ImplementationBlock;
import domain.block.SingleConditionBlock;
import domain.block.SingleSurroundingBlock;
import game_world.api.Action;
import game_world.api.Predicate;

/**
 * Handles all the function of the presentation blocks.
 * 
 * @version 4.0
 * @author Andreas Awouters 
 * 	       Thomas Van Erum 
 * 		   Dirk Vanbeveren 
 * 		   Geert Wesemael
 *
 */
public class ImplementationPresentationBlock {
	private ImplementationBlock BF = new ImplementationBlock();
	
	/**
	 * Creates a new if block presentation on a given position.
	 * @param pos
	 *        Position of the block.
	 * @return The if block presentation
	 */
	public PresentationBlock<SingleSurroundingBlock> makeIfBlock(Vector pos) {
		SingleSurroundingBlock block = BF.makeIfBlock();
		PresentationBlock<SingleSurroundingBlock> presentation = new SingleSurroundBlockPresentation(pos, block);
		return presentation;
	}

	/**
	 * Creates a new not block presentation on a given position.
	 * @param pos
	 *        Position of the block.
	 * @return The not block presentation
	 */
	public PresentationBlock<ChainConditionBlock> makeNotBlock(Vector pos) {
		ChainConditionBlock block = BF.makeNotBlock();
		PresentationBlock<ChainConditionBlock> presentation = new ChainConditionBlockPresentation(pos, block);
		return presentation;
	}

	/**
	 * Creates a new while block presentation on a given position.
	 * @param pos
	 *        Position of the block.
	 * @return The while block presentation.
	 */
	public PresentationBlock<SingleSurroundingBlock> makeWhileBlock(Vector pos) {
		SingleSurroundingBlock block = BF.makeWhileBlock();
		PresentationBlock<SingleSurroundingBlock> presentation = new SingleSurroundBlockPresentation(pos, block);
		return presentation;
	}

	
	public boolean collidesWithPosition(Vector pos, PresentationBlock<?> block) {
		return block.collidesWithPosition(pos);
	}

	public void draw(Graphics g, PresentationBlock<?> block) {
		block.draw(g);
		
	}

	public void highLight(PresentationBlock<?> block, Graphics g) {
		block.highLight(g);
		
	}

	public Block getBlock(PresentationBlock<?> block) {
		return block.getBlock();
	}

	public PresentationBlock<?> makeCopy(PresentationBlock<?> block) {
		return block.makeCopyWithoutConnections();
	}

	public Command canSnap(PresentationBlock<?> firstBlock, PresentationBlock<?> secondBlock, GameController GC) {
		return firstBlock.canSnap(secondBlock, GC);
	}

	public void setPosition(PresentationBlock<?> block, Vector position) {
		block.setPosition(position);
		
	}

	public Vector getPosition(PresentationBlock<?> block) {
		return block.getPosition();
	}

	public void addToPosition(PresentationBlock<?> block, Vector deltaPos) {
		block.setPositionByDifference(deltaPos);
		
	}

	/**
	 * Creates a new action block presentation with given position and action.
	 * @param action
	 *        The action of the action block.
	 * @param pos
	 *        Position of the block.
	 * @return The action block presentation.
	 */
	public PresentationBlock<ActionBlock> makeActionBlock(Action action, Vector pos) {
		ActionBlock block = BF.makeActionBlock(action);
		PresentationBlock<ActionBlock> presentation = new ActionBlockPresentation(pos, block);
		return presentation;
	}

	/**
	 * Creates a new single condition block presentation on a given position with given predicate.
	 * @param predicate
	 *        The predicate of the condition.
	 * @param pos
	 *        Position of the block.
	 * @return The single condition block presentation.
	 */
	public PresentationBlock<SingleConditionBlock> makeSingleConditionBlock(Predicate predicate, Vector pos) {
		SingleConditionBlock block = BF.makeSingleConditionBlock(predicate);
		PresentationBlock<SingleConditionBlock> presentation = new SingleConditionBlockPresentation(pos, block);
		return presentation;
	}

	/**
	 * Creates a new function call block presentation with a given position and function definition block.
	 * @param definition
	 *        The function definition block to link to.
	 * @param pos
	 *        Position of the block.
	 * @return The function call block presentation.
	 */
	public PresentationBlock<FunctionCallBlock> makeFunctionCallBlock(FunctionDefinitionBlock definition,Vector pos) {
		FunctionCallBlock block = BF.makeCaller(definition);
		PresentationBlock<FunctionCallBlock> presentation = new FunctionCallBlockPresentation(pos, block);
		return presentation;
	}

	/**
	 * Creates a new function definition block presentation with a given position and id.
	 * @param id
	 *        The id of the function definition block.
	 * @param pos
	 *        Position of the block.
	 * @return The while block presentation.
	 */
	public PresentationBlock<FunctionDefinitionBlock> makeFunctionDefinitionBlock(int id,Vector pos) {
		FunctionDefinitionBlock block = BF.makeFunctionDefinition(id);
		PresentationBlock<FunctionDefinitionBlock> presentation = new FunctionDefinitionBlockPresentation(pos, block);
		return presentation;
	}

}
