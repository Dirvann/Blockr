package command;

import java.util.ArrayList;
import java.util.List;

import domain.GameController;
import domain.block.FunctionCall;
import domain.block.FunctionDefinition;
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
 * @version 3.0
 * @author Andreas Awouters, Thomas Van Erum, Dirk Vanbeveren, Geert Wesemael
 *
 */
public class DeleteFunctionDefinition implements Command {
	GameController GC;
	PresentationBlock<FunctionDefinition> definition;
	List<DeleteCallerCommand> deletionCommandsOfCalls;
	ImplementationBlock BF = new ImplementationBlock();
	ImplementationPresentationBlock BFP = new ImplementationPresentationBlock();
	PalettePresentation palette;

	/**
	 * Makes a delete function definition Commmand. This Command includes all of the info needed
	 * to undo and redo a block function definition deletion Command.
	 * 
	 * @param GC    the gamecontroller where the block is deleted.
	 * @param caller the block that gets deleted.
	 * 
	 * @Post The objects block and GC are stored in this command for later use.
	 */
	public DeleteFunctionDefinition(GameController GC, PresentationBlock<FunctionDefinition> definition, PalettePresentation palette) {
		this.GC = GC;
		this.definition = definition;
		this.palette = palette;
		this.deletionCommandsOfCalls = new ArrayList<DeleteCallerCommand>();
		FunctionDefinition defBlock = (FunctionDefinition) BFP.getBlock(definition);
		List<FunctionCall> functionCalls = GC.getAllFunctionCallsOfID(BF.getID(defBlock));
		for (FunctionCall functionCall : functionCalls) {
			deletionCommandsOfCalls.add(new DeleteCallerCommand(GC, functionCall));
		}
		
	}

	@Override
	public void execute() {
		GC.removeBlockFromProgramArea(definition);

		int ID = BF.getID((FunctionDefinition) BFP.getBlock(definition));
		palette.removeFunctionCallWithIDFromList(ID);
		palette.setFunctionDefinitionId(ID);

	}

	@Override
	public void undo() {
		for(int i = deletionCommandsOfCalls.size() - 1; i >= 0; i--) {
			deletionCommandsOfCalls.get(i).undo();
		}
		GC.addBlockToProgramArea(definition);

		palette.addFunctionCallToPalette((FunctionDefinition) BFP.getBlock(definition));
	}

}