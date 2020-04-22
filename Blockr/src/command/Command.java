package command;

public interface Command {

	/**
	 * @post This function does all of the things needed to execute this command.
	 */
	public void execute();
	
	/**
	 * @post This function does all of the things needed to undo the execution of this command.
	 */
	public void undo();
}
