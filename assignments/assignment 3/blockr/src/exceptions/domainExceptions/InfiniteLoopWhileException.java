package exceptions.domainExceptions;

public class InfiniteLoopWhileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6998654760149018360L;
    public InfiniteLoopWhileException() {
        super("The while block has an infinite loop");
    }
}
