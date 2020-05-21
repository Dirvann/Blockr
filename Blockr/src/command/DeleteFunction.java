package command;

import java.util.ArrayList;
import java.util.List;

import domain.GameController;
import domain.ImplementationGameController;
import domain.block.ImplementationBlock;
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
public class DeleteFunction implements Command {
	GameController GC;
	PresentationBlock<?> block;
	List<DeleteBlock> callsOfFunction;
	ImplementationGameController GCF = new ImplementationGameController();
	ImplementationBlock BF = new ImplementationBlock();

	/**
	 * Makes a delete block Commmand. This Command includes all of the info needed
	 * to undo and redo a block deletion Command.
	 * 
	 * @param GC    the gamecontroller where the block is deleted.
	 * @param block the block that gets deleted.
	 * 
	 * @Post The objects block and GC are stored in this command for later use.
	 */
	public DeleteFunction(GameController GC, PresentationBlock<?> block) {
		this.GC = GC;
		this.block = block;
		this.callsOfFunction = new ArrayList<DeleteBlock>();
	}
	
	/**
	 * Adds a command to the list that keeps all of the function calls that have been deleted. 
	 * @param command The command of the deletion of the function call block.
	 */
	public void addFunctionCall(DeleteBlock command) {
		this.callsOfFunction.add(command);
	}

	@Override
	public void execute() {
		GCF.removeBlockFromProgramArea(GC, block);
		
		for(int i = 0; i < callsOfFunction.size(); i++) {
			callsOfFunction.get(i).execute();
		}

	}

	@Override
	public void undo() {
		for(int i = callsOfFunction.size() - 1; i <= 0; i--) {
			callsOfFunction.get(i).undo();
		}
		GCF.addBlockToProgramArea(GC, block);

	}

}