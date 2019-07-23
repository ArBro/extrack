package nl.abrouwer.extrack.exception;

public class ExpenseFileStorageException extends RuntimeException
{
	private static final long serialVersionUID = 1L;


	public ExpenseFileStorageException(String string)
	{
		super(string);
	}


	public ExpenseFileStorageException(String string, Throwable exception)
	{
		super(string, exception);
	}
}
