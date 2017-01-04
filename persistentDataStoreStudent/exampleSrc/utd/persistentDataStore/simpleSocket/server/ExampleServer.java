package utd.persistentDataStore.simpleSocket.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import utd.persistentDataStore.utils.ServerException;
import utd.persistentDataStore.utils.StreamUtil;

public class ExampleServer
{
	private static Logger logger = Logger.getLogger(ExampleServer.class);

	static public final int port = 10023;

	public void startup() throws IOException
	{
		logger.debug("Starting Service at port " + port);

		InputStream inputStream = null;
		OutputStream outputStream = null;

		ServerSocket serverSocket = new ServerSocket(port);
		
		while (true) {
			try {
				logger.debug("Waiting for request");
				Socket clientSocket = serverSocket.accept();

				logger.debug("Request received");
				inputStream = clientSocket.getInputStream();
				outputStream = clientSocket.getOutputStream();
				Handler handler = parseRequest(inputStream);
				
				logger.debug("Processing Request: " + handler);
				handler.setInputStream(inputStream);
				handler.setOutputStream(outputStream);
				handler.run();
				
				StreamUtil.closeSocket(inputStream);
			}
			catch (ServerException ex) {
				logger.info("Problem processing request. " + ex.getMessage());
				StreamUtil.sendError(ex.getMessage(), outputStream);
				StreamUtil.closeSocket(inputStream);
			}
			catch (IOException ex) {
				logger.error("Exception while processing request. " + ex.getMessage());
				ex.printStackTrace();
				StreamUtil.closeSocket(inputStream);
			}
		}
	}

	private Handler parseRequest(InputStream inputStream) throws IOException, ServerException
	{
		String commandString = StreamUtil.readLine(inputStream);

		if ("echo".equalsIgnoreCase(commandString)) {
			Handler handler = new EchoHandler();
			return handler;
		}
		else if ("reverse".equalsIgnoreCase(commandString)) {
			Handler handler = new ReverseHandler();
			return handler;
		}
		else {
			throw new ServerException("Unknown Request: " + commandString);
		}
	}

	public static void main(String args[])
	{
		try {
			ExampleServer simpleServer = new ExampleServer();
			simpleServer.startup();
		}
		catch (IOException ex) {
			logger.error(ex);
		}
	}
}
