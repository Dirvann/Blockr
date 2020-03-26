package presentation.block;

import domain.block.*;
import domain.game_world.Vector;

public class ImplementationPresentationBlock implements PresentationBlockInterface{
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

}
