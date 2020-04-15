package exceptions.domainExceptions;

public class BlockColumnNotExecutableException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5038087371755459331L;

	public BlockColumnNotExecutableException() {
		super("Block chain is not executable, a condition could be missing.");
	}
}
