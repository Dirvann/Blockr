package presentation.block;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import command.Command;
import command.ConnectCommand;
import command.addToBodyCommand;
import command.setConditionCommand;
import domain.GameController;
import domain.ImplementationGameController;
import domain.block.Block;
import domain.block.ConditionBlock;
import domain.block.FunctionDefinition;
import domain.block.ImplementationBlock;
import domain.block.SequenceBlock;
import presentation.PalettePresentation;
import domain.Vector;

public class FunctionDefinitionBlockPresentation extends PresentationBlock<FunctionDefinition> {
	
	ImplementationBlock BF = new ImplementationBlock();

	public FunctionDefinitionBlockPresentation(Vector pos, FunctionDefinition block) {
		super(pos, block);
	}

	@Override
	public void draw(Graphics gr) {
		
		Graphics2D g = (Graphics2D)gr;
		g.setColor(Color.BLUE);
		Vector pos = getPosition();
		int height = getTotalHeight();
		
		Area top = new Area(new Rectangle(pos.getX(), pos.getY(), getBlockWidth(), getBlockHeight()));
		// top bottom plug
		top.subtract(new Area(new Rectangle(pos.getX() + getBlockSideWidth() + getBlockWidth()/2 - getPlugWidth()/2, pos.getY() + getBlockHeight() - getPlugHeight(), getPlugWidth(), getPlugHeight())));
		g.fill(top);
		
		//???
		g.fillRect(pos.getX() + getBlockWidth() - getPlugHeight(), pos.getY() + getBlockHeight()/2 + getPlugWidth()/2, getPlugHeight(), getBlockHeight()/2 - getPlugWidth()/2);
		
		// side
		g.fillRect(pos.getX(), pos.getY() + getBlockHeight(), getBlockSideWidth(), height - 2 * getBlockHeight());
		
		// bottom
		Area bottom = new Area(new Rectangle(pos.getX(), pos.getY() + height - getBlockHeight(), getBlockWidth(), getBlockHeight()));
		g.fill(bottom);
		
		// bottom top plug
		g.fillRect(pos.getX() + getBlockWidth()/2 - getPlugWidth()/2 + getBlockSideWidth(), pos.getY() - getPlugHeight() + height - getBlockHeight(), getPlugWidth(), getPlugHeight());
		
		
		g.setColor(Color.BLACK);
		g.setFont(getFont());
		g.drawString(getPresentationName(), pos.getX(), pos.getY() + (int) (getBlockHeight() * 0.8));
		
		
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

	public Vector getBodySnapPoint() {
		Vector pos = getPosition();
		return new Vector(pos.getX() + (int) (getBlockWidth() / 2 + getBlockSideWidth()),
				pos.getY() + getBlockHeight());
	}


	@Override
	public Command canSnap(PresentationBlock<?> b, GameController GC) {
		
		ImplementationGameController IGC = new ImplementationGameController();
		
		if (b.getBlock() instanceof SequenceBlock) {
			if (b.getGivingSnapPoint().distanceTo(getBodySnapPoint()) <= getSnapDistance()) {
				IGC.setBody(getBlock(), (SequenceBlock) b.getBlock(), GC);
				return null; //TODO: dis
			}
		}
		return null;
	}

	@Override
	protected PresentationBlock<FunctionDefinition> makeCopyWithoutConnections() {
		ImplementationBlock BF = new ImplementationBlock();
		return new FunctionDefinitionBlockPresentation(getPosition(), (FunctionDefinition) BF.makeNewBlockOfThisType(getBlock())) ;
	}

}
