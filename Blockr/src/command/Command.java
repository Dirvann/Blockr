package command;
/**
 * A general Command interface having the functions undo and redo.
 * 
 * @version 3.0
 * @author Andreas Awouters, Thomas Van Erum, Dirk Vanbeveren, Geert Wesemael
 *
 */
public interface Command {

	/**
	 * Executes this command and makes the required changes for this command. This
	 * command can be a connectingCommand, disconnectingCommand, MakeBlock,
	 * drag Block etc.
	 * 
	 * @post This function does all of the things needed to do the execution of
	 *       this command.
	 */
	public void execute();

	/**
	 * Undoes this command and makes the required changes for this Command.This
	 * command can be a connectingCommand, disconnectingCommand, MakeBlock,
	 * drag Block etc.
	 * 
	 * @post This function does all of the things needed to undo the execution of
	 *       this command.
	 */
	public void undo();
}
