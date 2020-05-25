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
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

/**
 * PalettePresentation is intended to be used as a part of a BlockAreaCanvas.
 * PalettePresentation handles activities concerning the palette.
 * 
 * @version 4.0
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
	private PresentationBlock<FunctionDefinition> paletteFunction;
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
	/**
	 * Initialize all the Presentation Blocks in the palette.
	 * 
	 * @param list
	 * 		  List of all the blocks to be shown in the palette.
	 * @param iGameWorld
	 * 		  The given GameWorld Facade.
	 * @effect Add all Blocks to the palette.
	 */
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
		paletteFunction = iPresentationBlock.makeFunctionDefinitionBlock(0, new Vector(xOffset, yOffset+yOffsetIncrement*index++));
		list.add(paletteFunction);
		
	}
	/**
	 * Set the ID of the Block.
	 * 
	 * @param ID
	 * 		  The given ID to set iBlock to.
	 * @post  The id of the Block is equal to the given ID.
	 * 		  | iPresentationBlock.getBlock(this.paletteFunction) == ID
	 */
	public void setFunctionDefinitionId(int ID) {
		iBlock.setID((FunctionDefinition) iPresentationBlock.getBlock(this.paletteFunction), ID);
	}
	/**
	 * The current ID for FunctionDefinitionBlocks in the palette.
	 * 
	 * @return the current ID for FunctionDefinitionBlocks in the palette.
	 * 		   |iBlock.getID(iPresentationBlock.getBlock(paletteFunction))
	 */
	public int getFunctionDefinitionId() {
		return iBlock.getID((FunctionDefinition) iPresentationBlock.getBlock(this.paletteFunction));
	}
	/**
	 * Higher the ID of the FunctionDefinition PresentationBlock in the palette.
	 * Add a caller PresentationBlock to the  
	 * 
	 * @param definition
	 */
	public void addFunctionCallToPalette(FunctionDefinition definition) {
		setNextDefinition();
		paletteBlocks.add(iPresentationBlock.makeFunctionCallBlock(definition, new Vector(xOffset, yOffset+yOffsetIncrement*(index++))));
	}
	/**
	 * Remove all FunctionCall PresentationBlocks from the paletteBlock list
	 * with the same id as the given definition 
	 * 
	 * @param definition
	 * 		  The definition with the same id as the callers that are being removed.
	 * @post  There are no FuncionCallBlocks in paletteBlocks with the same id as the definition.
	 * 		  |For all blocks: !paletteBlocks.contains(blocks) && sameBlockAndID(block,iBlock.getID(definition))
	 */
	public void removeFunctionCallFromPalette(FunctionDefinition definition) {
		removeFunctionCallWithIDFromList(iBlock.getID(definition));
	}
	/**
	 * Remove all Function Calls from the paletteBlock list with the given id.
	 * 
	 * @param id
	 * 		  The ID of the FunctionCall blocks that need to be removed.
	 * @post  There are no FuncionCallBlocks in paletteBlocks with the given id
	 * 		  |For all blocks: !paletteBlocks.contains(blocks) && sameBlockAndID(block,id)
	 */
	public void removeFunctionCallWithIDFromList(int id) {
		for(int i=0;i<paletteBlocks.size();i++) {
			PresentationBlock<?> pb = paletteBlocks.get(i);
			if(sameBlockAndID(pb,id)) {
				paletteBlocks.remove(i);
				i--;
			}
		} 
	}
	/**
	 * Return True if the given block is of the same type and
	 * has the same ID as the given ID.
	 * 
	 * @param  presentationBlock
	 * 		   The given block to check the ID of.
	 * @param  idOtherBlock
	 * 		   The ID to compare to.
	 * @return True if the given block is of the same type and
	 * 		   has the same ID as the given ID.
	 */
	private boolean sameBlockAndID(PresentationBlock<?> presentationBlock,int idOtherBlock) {
		return presentationBlock instanceof FunctionCallBlockPresentation && idOtherBlock == iBlock.getID((FunctionCall) iPresentationBlock.getBlock(presentationBlock));
	}
	/**
	 * Set the Definition of the DefinitionBlock in te palette one higher.
	 * 
	 * @post The id of the functionBlock in the palette is one higher.
	 * 		 |iBlock.getID(iPresentationBlock.getBlock(paletteFunction)) ++
	 */
	private void setNextDefinition() {
		iBlock.setID((FunctionDefinition) iPresentationBlock.getBlock(paletteFunction), iBlock.getID((FunctionDefinition) iPresentationBlock.getBlock(paletteFunction)) + 1);
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
		int index = 0;
		for (PresentationBlock<?> pBlock: paletteBlocks) {
			iPresentationBlock.setPosition(pBlock, new Vector(xOffset, yOffset+yOffsetIncrement*index++));
			iPresentationBlock.draw(g, pBlock);;
		}
	}

	
}
