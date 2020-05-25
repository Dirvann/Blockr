package command;

import domain.GameController;
import domain.block.FunctionDefinition;
import domain.block.ImplementationBlock;
import presentation.PalettePresentation;
import presentation.block.FunctionDefinitionBlockPresentation;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

public class MakeFunctionCommand implements Command {
	/**
	 * A class that holds all the information about the action where a FunctionDefinitionBlock is
	 * made. This information consists of the objects block and
	 * GameController. The class also specifies what must happen to undo and execute this
	 * command.
	 * 
	 * @version 4.0
	 * @author Andreas Awouters 
	 * 		   Thomas Van Erum
	 * 		   Dirk Vanbeveren
	 * 		   Geert Wesemael
	 *
	 */
	GameController GC;
	PresentationBlock<FunctionDefinition> function;
	PalettePresentation palette;
	
	ImplementationBlock BF = new ImplementationBlock();
	ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();

	/**
	 * Makes a Command for the creation of a block. This holds the info to undo and
	 * redo this action.
	 * 
	 * @param blockToConnectTo 
	 * 		  Block before group of blocks connected.
	 * @param blockToConnect   
	 * 		  First block of group of blocks that gets connected.
	 * @post  The objects GC, function and palette will be stored in this Command for later use.
	 * 		  | new.GC == GC
	 * 		  | new.function == function
	 * 		  | new.palette == palette
	 */
	public MakeFunctionCommand(GameController GC, PresentationBlock<FunctionDefinition> function, PalettePresentation palette) {
		this.GC = GC;
		this.function = function;
		this.palette = palette;
	}

	@Override
	public void execute() {
		GC.addBlockToProgramArea(function);
		palette.addFunctionCallToPalette((FunctionDefinition) BFP.getBlock(function));

	}

	@Override
	public void undo() {
		int ID = BF.getID((FunctionDefinition) BFP.getBlock(function));
		palette.removeFunctionCallWithIDFromList(ID);
		palette.setFunctionDefinitionId(ID);
		GC.removeBlockFromProgramArea(function);

	}

}