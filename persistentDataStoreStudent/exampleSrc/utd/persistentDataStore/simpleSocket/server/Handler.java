package utd.persistentDataStore.simpleSocket.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

public abstract class Handler
{
	private static Logger logger = Logger.getLogger(Handler.class);
	
	protected InputStream inputStream;
	protected OutputStream outputStream;
	
	protected abstract void run() throws IOException;

	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	public void setOutputStream(OutputStream outputStream)
	{
		this.outputStream = outputStream;
	}

}
