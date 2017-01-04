package utd.persistentDataStore.simpleSocket.server;

import java.io.IOException;

import org.apache.log4j.Logger;

import utd.persistentDataStore.utils.StreamUtil;

public class EchoHandler extends Handler
{
	private static Logger logger = Logger.getLogger(EchoHandler.class);

	public void run() throws IOException
	{
		// Read message
		String inMessage = StreamUtil.readLine(inputStream);
		logger.debug("inMessage: " + inMessage);

		// Write response
		String outMessage = inMessage + "\n";
		StreamUtil.writeLine(outMessage, outputStream);
		logger.debug("Finished writing message");
	}
}
