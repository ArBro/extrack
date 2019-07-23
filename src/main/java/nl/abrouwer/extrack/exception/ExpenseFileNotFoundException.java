package nl.abrouwer.extrack.exception;

public class ExpenseFileNotFoundException extends ExpenseFileStorageException
{
	private static final long serialVersionUID = 1L;


	public ExpenseFileNotFoundException(String string)
	{
		super(string);
	}


	public ExpenseFileNotFoundException(String string, Throwable exception)
	{
		super(string, exception);
	}
}
