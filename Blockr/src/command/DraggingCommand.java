package command;

import game_world.Vector;
import presentation.block.ImplementationPresentationBlock;
import presentation.block.PresentationBlock;

public class DraggingCommand implements Command {

	ImplementationPresentationBlock BPF = new ImplementationPresentationBlock();
	Command preCommand;
	Command postCommand;
	Vector oldPos;
	Vector newPos;
	PresentationBlock<?> block;

	/**
	 * 
	 * @param oldPos
	 * @param newPos
	 * @param block
	 * @param preCommand  The command that has been executed before the block has
	 *                    been dragged. This can be making a block or disconnecting.
	 * @param postCommand The command that has been executed after the block is
	 *                    released. This can be deleting or connecting a block.
	 * @post This command will hold the information of the preCommand,
	 *       draggingCommand and postCommand together in one command.
	 */
	public DraggingCommand(Vector oldPos, Vector newPos, PresentationBlock<?> block, Command preCommand,
			Command postCommand) {
		this.preCommand = preCommand;
		this.postCommand = postCommand;
		this.oldPos = oldPos;
		this.newPos = newPos;
		this.block = block;
	}

	@Override
	public void execute() {
		if (preCommand != null) {
			preCommand.execute();
		}
		BPF.setPosition(block, newPos);
		if (postCommand != null) {
			postCommand.execute();
		}

	}

	@Override
	public void undo() {
		if (postCommand != null) {
			postCommand.undo();
		}
		BPF.setPosition(block, oldPos);
		if (preCommand != null) {
			preCommand.undo();
		}

	}

}
