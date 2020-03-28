package presentation.block;

import java.awt.Graphics;

import domain.block.*;
import domain.game_world.Vector;

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
	public PresentationBlock<ActionBlock> makeTurnLeftBlock(Vector pos) {
		ActionBlock block = BF.makeTurnLeftBlock();
		PresentationBlock<ActionBlock> presentation = new ActionBlockPresentation(pos, block);
		return presentation;
	}

	@Override
	public PresentationBlock<ActionBlock> makeTurnRightBlock(Vector pos) {
		ActionBlock block = BF.makeTurnRightBlock();
		PresentationBlock<ActionBlock> presentation = new ActionBlockPresentation(pos, block);
		return presentation;
	}

	@Override
	public PresentationBlock<SingleConditionBlock> makeWallInFrontBlock(Vector pos) {
		SingleConditionBlock block = BF.makeWallInFrontBlock();
		PresentationBlock<SingleConditionBlock> presentation = new SingleConditionBlockPresentation(pos, block);
		return presentation;
	}

	@Override
	public PresentationBlock<SingleSurroundingBlock> makeWhileBlock(Vector pos) {
		SingleSurroundingBlock block = BF.makeWhileBlock();
		PresentationBlock<SingleSurroundingBlock> presentation = new SingleSurroundBlockPresentation(pos, block);
		return presentation;
	}

	@Override
	public PresentationBlock<ActionBlock> makeMoveForwardBlock(Vector pos) {
		ActionBlock block = BF.makeMoveForwardBlock();
		PresentationBlock<ActionBlock> presentation = new ActionBlockPresentation(pos, block);
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
	public boolean snap(PresentationBlock<?> firstBlock, PresentationBlock<?> secondBlock) {
		return firstBlock.snap(secondBlock);
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

}