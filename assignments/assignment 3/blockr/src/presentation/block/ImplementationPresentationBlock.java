package presentation.block;

import java.awt.Graphics;

import command.Command;
import domain.GameController;
import domain.block.*;
import domain.Vector;

public class ImplementationPresentationBlock implements FacadePresentationBlock{
	private ImplementationBlock BF = new ImplementationBlock();

	@Override
	public PresentationBlock<SingleSurroundingBlock> makeIfBlock(Vector pos) {
		SingleSurroundingBlock block = BF.makeIfBlock();
		PresentationBlock<SingleSurroundingBlock> presentation = new SingleSurroundBlockPresentation(pos, block);
		return presentation;
	}

	@Override
	public PresentationBlock<ChainConditionBlock> makeNotBlock(Vector pos) {
		ChainConditionBlock block = BF.makeNotBlock();
		PresentationBlock<ChainConditionBlock> presentation = new ChainConditionBlockPresentation(pos, block);
		return presentation;
	}




	@Override
	public PresentationBlock<SingleSurroundingBlock> makeWhileBlock(Vector pos) {
		SingleSurroundingBlock block = BF.makeWhileBlock();
		PresentationBlock<SingleSurroundingBlock> presentation = new SingleSurroundBlockPresentation(pos, block);
		return presentation;
	}

	@Override
	public boolean collidesWithPosition(Vector pos, PresentationBlock<?> block) {
		return block.collidesWithPosition(pos);
	}

	@Override
	public void draw(Graphics g, PresentationBlock<?> block) {
		block.draw(g);
		
	}

	@Override
	public void highLight(PresentationBlock<?> block, Graphics g) {
		block.highLight(g);
		
	}

	@Override
	public Block getBlock(PresentationBlock<?> block) {
		return block.getBlock();
	}

	@Override
	public PresentationBlock<?> makeCopy(PresentationBlock<?> block) {
		return block.makeCopyWithoutConnections();
	}

	@Override
	public Command canSnap(PresentationBlock<?> firstBlock, PresentationBlock<?> secondBlock, GameController GC) {
		return firstBlock.canSnap(secondBlock, GC);
	}

	@Override
	public void setPosition(PresentationBlock<?> block, Vector position) {
		block.setPosition(position);
		
	}

	@Override
	public Vector getPosition(PresentationBlock<?> block) {
		return block.getPosition();
	}

	@Override
	public void addToPosition(PresentationBlock<?> block, Vector deltaPos) {
		block.setPositionByDifference(deltaPos);
		
	}

	@Override
	public PresentationBlock<ActionBlock> makeActionBlock(String name, Vector pos) {
		ActionBlock block = BF.makeActionBlock(name);
		PresentationBlock<ActionBlock> presentation = new ActionBlockPresentation(pos, block);
		return presentation;
	}

	@Override
	public PresentationBlock<SingleConditionBlock> makeSingleConditionBlock(String name, Vector pos) {
		SingleConditionBlock block = BF.makeSingleConditionBlock(name);
		PresentationBlock<SingleConditionBlock> presentation = new SingleConditionBlockPresentation(pos, block);
		return presentation;
	}

}
