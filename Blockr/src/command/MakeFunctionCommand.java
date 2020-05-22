package command;

import domain.GameController;
import domain.ImplementationGameController;
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
	 * @version 3.0
	 * @author Andreas Awouters, Thomas Van Erum, Dirk Vanbeveren, Geert Wesemael
	 *
	 */
	GameController GC;
	PresentationBlock<FunctionDefinition> function;
	PalettePresentation palette;
	
	ImplementationGameController GCF = new ImplementationGameController();
	ImplementationBlock BF = new ImplementationBlock();
	ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();

	/**
	 * Makes a Command for the creation of a block. This holds the info to undo and
	 * redo this action.
	 * 
	 * @param blockToConnectTo block before group of blocks connected
	 * @param blockToConnect   first block of group of blocks that gets connected.
	 * @Post the objects GC and block will be stored in this Command for later use.
	 */
	public MakeFunctionCommand(GameController GC, PresentationBlock<FunctionDefinition> function, PalettePresentation palette) {
		this.GC = GC;
		this.function = function;
		this.palette = palette;
	}

	@Override
	public void execute() {
		GCF.addBlockToProgramArea(GC, function);
		palette.addFunctionCallToPalette((FunctionDefinition) BFP.getBlock(function));

	}

	@Override
	public void undo() {
		int ID = BF.getID((FunctionDefinition) BFP.getBlock(function));
		palette.removeFunctionCallWithIDFromList(ID);
		palette.setFunctionDefinitionId(ID);
		GCF.removeBlockFromProgramArea(GC, function);

	}

}