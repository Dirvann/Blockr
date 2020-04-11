package exceptions.domainExceptions;

public class CantRunConditionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5234849393698137149L;
    public CantRunConditionException() {
        super("You can't run condition blocks");
    }
}
