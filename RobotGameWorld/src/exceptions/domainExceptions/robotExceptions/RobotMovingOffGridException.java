package exceptions.domainExceptions.robotExceptions;

public class RobotMovingOffGridException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5486106075389932430L;
    public RobotMovingOffGridException() {
        super("The robot can't walk out of the grid");
    }
}
