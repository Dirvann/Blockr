package exceptions.domainExceptions;

public class OutOfBoundsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9027385624441431061L;
    public OutOfBoundsException() {
        super("You can't acces something out of the grid");
    }
}
