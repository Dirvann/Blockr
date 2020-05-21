package presentation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import domain.Vector;
import domain.block.FunctionCall;
import domain.block.FunctionDefinition;
import domain.block.ImplementationBlock;
import game_world.api.Action;
import game_world.api.FacadeGameWorld;
import game_world.api.Predicate;
import presentation.block.FunctionCallBlockPresentation;
import presentation.block.FunctionDefinitionBlockPresentation;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

/**
 * PalettePresentation is intended to be used as a part of a BlockAreaCanvas.
 * PalettePresentation handles activities concerning the palette.
 * 
 * @version 3.0
 * @author Andreas Awouters
 * 		   Thomas Van Erum
 * 		   Dirk Vanbeveren
 * 		   Geert Wesemael
 *
 */
public class PalettePresentation {

	private List<PresentationBlock<?>> paletteBlocks;
	private ImplementationPresentationBlock iPresentationBlock = new ImplementationPresentationBlock();
	private ImplementationBlock iBlock = new ImplementationBlock();

	private int index = 0;
	private final int xOffset = 10;
	private final int yOffset = 10;
	private final int yOffsetIncrement = 60;
	private int functionID = 0;
	/**
	 * Create a new instance of PalettePresentation
	 * 
	 * @param iGameWorld
	 *  	  | Interface used by the BlockAreaCanvas
	 * 
	 */
	public PalettePresentation(FacadeGameWorld iGameWorld) {
		paletteBlocks = new ArrayList<PresentationBlock<?>>();

		
		initialisePaletteBlocksList(paletteBlocks, iGameWorld);
	}
	
	private void initialisePaletteBlocksList(List<PresentationBlock<?>> list, FacadeGameWorld iGameWorld) {
		
		index = 0;
		List<Action> actionList = iGameWorld.getAllActions();
		List<Predicate> predicateList = iGameWorld.getAllPRedicates();
		
		for (int i = 0; i < actionList.size(); i++) {
			list.add(iPresentationBlock.makeActionBlock(actionList.get(i),new Vector(xOffset, yOffset + yOffsetIncrement*index)));
			index++;
		}
		for (int i = 0; i < predicateList.size(); i++) {
			list.add(iPresentationBlock.makeSingleConditionBlock(predicateList.get(i),new Vector(xOffset, yOffset + yOffsetIncrement*index)));
			index++;
		}
		
		
		list.add(iPresentationBlock.makeNotBlock(new Vector(xOffset, yOffset+yOffsetIncrement*index++)));
		
		list.add(iPresentationBlock.makeIfBlock(new Vector(xOffset, yOffset+yOffsetIncrement*index++)));
		// While
		list.add(iPresentationBlock.makeWhileBlock(new Vector(xOffset, yOffset+yOffsetIncrement*index++)));
		// FunctionDefinition
		list.add(iPresentationBlock.makeFunctionDefinitionBlock(functionID, new Vector(xOffset, yOffset+yOffsetIncrement*index++)));
		
	}
	
	
	public void addFunctionCallToPalette(FunctionDefinition definition,FacadeGameWorld iGameWorld) {
		paletteBlocks.add(iPresentationBlock.makeFunctionCallBlock(definition, new Vector(xOffset, yOffset+yOffsetIncrement*(index++))));
		setNextDefinition();
	}
	
	public void removeFunctionCallFromPalette(FunctionDefinition definition,FacadeGameWorld iGameWorld) {
		int id = iBlock.getID(definition);
		removeFunctionCallWithIDFromList(id);
	}
	
	private void removeFunctionCallWithIDFromList(int id) {
		for(int i=0;i<paletteBlocks.size();i++) {
			PresentationBlock<?> pb = paletteBlocks.get(i);
			if(pb instanceof FunctionCallBlockPresentation && id == iBlock.getID((FunctionCall) iPresentationBlock.getBlock(pb))) {
				paletteBlocks.remove(i);
				i--;
			}
		} 
	}
	
	private void setNextDefinition() {
		Vector position = null;
		for(int i=0;i<paletteBlocks.size();i++) {
			PresentationBlock<?> pb = paletteBlocks.get(i);
			if(pb instanceof FunctionDefinitionBlockPresentation && functionID == iBlock.getID((FunctionDefinition) iPresentationBlock.getBlock(pb))) {
				position = iPresentationBlock.getPosition(pb);
				paletteBlocks.remove(i);
				break;
			}
		} 
		if(position != null) {
			functionID++;
			paletteBlocks.add(iPresentationBlock.makeFunctionDefinitionBlock(functionID,position));			
		}
	}
	
	/**
	 * Returns the presentationBlock from the palette if the presentationBlock covers this position
	 * 
	 * @param position
	 *  	  | position to get the block from
	 * @return
	 * 		  | paletteBlock at given position
	 * 		  | null if no block exists at given position
	 */
	public PresentationBlock<?> GetClickedPaletteBlock(Vector position) {
		for (PresentationBlock<?> pBlock: paletteBlocks) {
			if (iPresentationBlock.collidesWithPosition(position, pBlock)) {
				return pBlock;
			}
		}
		
		return null;
	}
	
	/**
	 * Draw the palette on the given Graphics object
	 * 
	 * @param g
	 * 	      | Graphics object to draw palette on
	 */
	public void paint(Graphics g) {
		for (PresentationBlock<?> pBlock: paletteBlocks) {
			iPresentationBlock.draw(g, pBlock);;
		}
	}

	
}
