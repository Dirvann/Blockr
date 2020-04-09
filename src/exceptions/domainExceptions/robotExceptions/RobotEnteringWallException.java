package exceptions.domainExceptions.robotExceptions;

public class RobotEnteringWallException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4339705619466555019L;
    public RobotEnteringWallException() {
        super("The robot can't walk into a wall");
    }
}