package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import command.AddToBodyFunctionDefinitionCommand;
import command.Command;
import domain.GameController;
import domain.block.Block;
import domain.block.FunctionDefinitionBlock;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import game_world.api.Vector;

/**
 * Handles the UI drawing and interaction for the FunctionDefinitionBlock.
 * 
 * @version 4.0
 * @author Andreas Awouters 
 * 	       Thomas Van Erum 
 * 		   Dirk Vanbeveren 
 * 		   Geert Wesemael
 *
 */
public class FunctionDefinitionBlockPresentation extends PresentationBlock<FunctionDefinitionBlock> {
	
	ImplementationBlock BF = new ImplementationBlock();

	public FunctionDefinitionBlockPresentation(Vector pos, FunctionDefinitionBlock block) {
		super(pos, block);
	}

	@Override
	public void draw(Graphics gr) {
		
		int height = getTotalHeight();
		Vector pos = getPosition();
		BlockDrawer b = new BlockDrawer(Color.CYAN, Color.BLACK);
		b.drawBlock(gr, pos, false, true, false, false, false, true);
		b.drawSide(gr, pos,height);
		b.drawBlock(gr, pos.add(new Vector(0,height - getBlockHeight())), true, false, false, false, true, false);
		b.drawString(gr, getPresentationName(), pos);
		
	}

	@Override
	public int getTotalHeight() {
		int totalHeight = 2 * getBlockHeight();
		Block current =  BF.getBodyBlock(getBlock());
		while (current != null) {
			totalHeight += BF.getPresentationBlock(current).getTotalHeight();
			current = BF.getNextBlock(current);
		}
		return totalHeight;
	}

	@Override
	protected Vector getNextBlockPosition(PresentationBlock<?> presentationBlock) {
		if (presentationBlock.getBlock() == BF.getBodyBlock(getBlock())) {
			Vector pos = getPosition();
			return new Vector(pos.getX() + getBlockSideWidth(), pos.getY() + PresentationBlock.getBlockHeight());
		}
		return null;
	}

	@Override
	public Vector getGivingSnapPoint() {
		return null;
	}

	@Override
	public List<Vector> getReceivingSnapPoints() {
		List<Vector> snapPoints = new ArrayList<Vector>();
		snapPoints.add(getBodySnapPoint());
		return snapPoints;
	}
	/**
	 * Get the receiving snapping point in the hollow part of the block.
	 * @return The body snap point.
	 */
	private Vector getBodySnapPoint() {
		Vector pos = getPosition();
		return new Vector(pos.getX() + (int) (getBlockWidth() / 2 + getBlockSideWidth()),
				pos.getY() + getBlockHeight());
	}


	@Override
	public Command canSnap(PresentationBlock<?> b, GameController GC) {
		
		SequenceBlock body = BF.getBodyBlock(getBlock());
		
		if (canSnapBody(b)) {
			GC.setBody(getBlock(), (SequenceBlock) b.getBlock());
			return new AddToBodyFunctionDefinitionCommand(getBlock(), (SequenceBlock) b.getBlock(), body, GC);
		}
		return null;
	}
	
	private boolean canSnapBody(PresentationBlock<?> b) {
		return (b.getBlock() instanceof SequenceBlock && b.getGivingSnapPoint().distanceTo(getBodySnapPoint()) <= getSnapDistance());
	}

	@Override
	protected PresentationBlock<FunctionDefinitionBlock> makeCopyWithoutConnections() {
		ImplementationBlock BF = new ImplementationBlock();
		return new FunctionDefinitionBlockPresentation(getPosition(), (FunctionDefinitionBlock) BF.makeNewBlockOfThisType(getBlock())) ;
	}

}
