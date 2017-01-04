package utd.persistentDataStore.simpleSocket.server;

import java.io.IOException;

import org.apache.log4j.Logger;

import utd.persistentDataStore.utils.StreamUtil;

public class ReverseHandler extends Handler
{
	private static Logger logger = Logger.getLogger(ReverseHandler.class);

	public void run() throws IOException
	{
		// Read message
		String inMessage = StreamUtil.readLine(inputStream);
		logger.debug("inMessage: " + inMessage);

		// Write response
		String outMessage = reverse(inMessage) + "\n";
		StreamUtil.writeLine(outMessage, outputStream);
		logger.debug("Finished writing message");
	}

	private String reverse(String data)
	{
		byte dataBuff[] = data.getBytes();
		int buffSize = dataBuff.length;
		byte reverseBuff[] = new byte[buffSize];
		for (int idx = 0; idx < buffSize; idx++) {
			reverseBuff[idx] = dataBuff[(buffSize - idx) - 1];
		}
		return new String(reverseBuff);
	}

}
