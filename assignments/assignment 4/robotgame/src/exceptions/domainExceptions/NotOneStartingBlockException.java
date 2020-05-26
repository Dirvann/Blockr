package exceptions.domainExceptions;

public class NotOneStartingBlockException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3546488514197848332L;
    public NotOneStartingBlockException() {
        super("You can only run one block at a time");
    }
}
