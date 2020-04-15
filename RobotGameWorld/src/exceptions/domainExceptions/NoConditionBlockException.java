package exceptions.domainExceptions;

public class NoConditionBlockException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -329718728717294819L;
    public NoConditionBlockException() {
        super("The block misses a condition");
    }
}
