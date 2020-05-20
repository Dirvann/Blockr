package presentation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import domain.Vector;
import game_world.api.Action;
import game_world.api.FacadeGameWorld;
import game_world.api.Predicate;
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
		final int xOffset = 10;
		final int yOffset = 10;
		final int yOffsetIncrement = 60;
		
		
		List<Action> actionList = iGameWorld.getAllActions();
		List<Predicate> predicateList = iGameWorld.getAllPRedicates();
		
		int index = 0;
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
		//functioncall test
		list.add(iPresentationBlock.makeFunctionDefinitionBlock(new Vector(xOffset, yOffset+yOffsetIncrement*index++)));
		//list.add(iPresentationBlock.makeFunctionCallBlock(new Vector(xOffset, yOffset+yOffsetIncrement*index++)));
		
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
