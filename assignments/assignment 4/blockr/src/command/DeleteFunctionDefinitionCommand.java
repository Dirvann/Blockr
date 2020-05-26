package command;

import java.util.ArrayList;
import java.util.List;

import domain.GameController;
import domain.block.FunctionCallBlock;
import domain.block.FunctionDefinitionBlock;
import domain.block.ImplementationBlock;
import presentation.PalettePresentation;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

/**
 * A class that holds all the information about the action where a block is
 * deleted. This information consists of the objects block and
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
public class DeleteFunctionDefinitionCommand implements Command {
	GameController GC;
	PresentationBlock<FunctionDefinitionBlock> definition;
	List<DeleteCallerCommand> deletionCommandsOfCalls;
	ImplementationBlock BF = new ImplementationBlock();
	ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();
	PalettePresentation palette;

	/**
	 * Makes a delete function definition Commmand. This Command includes all of the info needed
	 * to undo and redo a block function definition deletion Command.
	 * 
	 * @param GC    
	 * 		  The GameController where the block is deleted.
	 * @param caller 
	 * 		  The block that gets deleted.
	 * @post  The objects definition, palette and GC are stored in this command for later use.
	 * 		  | new.GC == GC
	 * 		  | new.definition == definition
	 * 		  | new.palette == palette
	 * @post  List with DeleteCallerCommands is stored, the Callers from 
	 * 		  these commands have the same ID as the definition.
	 * 		  | new.deletionCommandsOfCalls.contains(DeleteCallerCommand with same ID as definition)
	 */
	public DeleteFunctionDefinitionCommand(GameController GC, PresentationBlock<FunctionDefinitionBlock> definition, PalettePresentation palette) {
		this.GC = GC;
		this.definition = definition;
		this.palette = palette;
		this.deletionCommandsOfCalls = new ArrayList<DeleteCallerCommand>();
		FunctionDefinitionBlock defBlock = (FunctionDefinitionBlock) BFP.getBlock(definition);
		List<FunctionCallBlock> functionCalls = GC.getAllFunctionCallsOfID(BF.getID(defBlock));
		for (FunctionCallBlock functionCall : functionCalls) {
			deletionCommandsOfCalls.add(new DeleteCallerCommand(GC, functionCall));
		}
		
	}

	@Override
	public void execute() {
		GC.removeBlockFromProgramArea(definition);

		int ID = BF.getID((FunctionDefinitionBlock) BFP.getBlock(definition));
		palette.removeFunctionCallWithIDFromList(ID);
		palette.setFunctionDefinitionId(ID);

	}

	@Override
	public void undo() {
		for(int i = deletionCommandsOfCalls.size() - 1; i >= 0; i--) {
			deletionCommandsOfCalls.get(i).undo();
		}
		GC.addBlockToProgramArea(definition);

		palette.addFunctionCallToPalette((FunctionDefinitionBlock) BFP.getBlock(definition));
	}

}