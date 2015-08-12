package GameConnections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import logging.Logging;
import GameConnections.CommandBuilder.CommandConverter;
import GameUtilities.Command;

/**
 * TCP Connection Server
 * 
 * @author Schoenegger / Purkart / Koch
 *
 */
public class TCPConnectionServer extends Connection
{
	BufferedReader inputReader;
	BufferedWriter outputStream;
	ServerSocket serverSocket;
	Socket connectionSocket;

	CommandConverter convert;

	/**
	 * TCP Connection Server
	 * 
	 * @param port
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public TCPConnectionServer(int port) throws UnknownHostException,
			IOException
	{
		this.serverSocket = new ServerSocket(port);
		System.out.println("Waiting for client on port "
				+ serverSocket.getLocalPort() + "...");

		try
		{
			JOptionPane.showMessageDialog(null, "No Client Available",
					"No Client Available", JOptionPane.PLAIN_MESSAGE);

			connectionSocket = serverSocket.accept();
			System.out.println("Just connected to "
					+ connectionSocket.getRemoteSocketAddress());

			inputReader = new BufferedReader(new InputStreamReader(
					connectionSocket.getInputStream()));

			this.outputStream = new BufferedWriter(new OutputStreamWriter(
					connectionSocket.getOutputStream()));

			convert = new CommandConverter();
		}
		catch (Exception e)
		{

			JOptionPane.showMessageDialog(null, "No Connection Available",
					"No Connection Available", JOptionPane.OK_CANCEL_OPTION);

			Logging.writeErrorMessage("TCPConnectionServer -> No Connectiont available");
		}

	}

	/**
	 * Receive Command
	 * 
	 * @return command
	 */
	@Override
	public Command receiveCommand()
	{

		String inputString = recieveStream();

		return convert.convertToGameCommand(inputString);
	}

	private String recieveStream()
	{
		String inputString = "";

		try
		{

			inputString = inputReader.readLine();

		}
		catch (Exception exception)
		{
			// Todo
		}

		return inputString;
	}

	/**
	 * Send Command
	 */
	@Override
	public void sendCommand(Command command)
	{
		String tcpString = "";
		if (command == null)
		{
			// TODO
			tcpString = "99;";
		}
		else
		{
			tcpString = convert.convertToTCPString(command);
		}

		sendStream(tcpString);
	}

	private void sendStream(String tcpString)
	{
		try
		{
			outputStream.write(tcpString);
			outputStream.newLine();
			outputStream.flush();
		}
		catch (Exception exception)
		{
			// Todo
		}
	}

	/**
	 * close the connection
	 * 
	 */
	@Override
	public void close()
	{

		try
		{
			System.out.println("Close Connection!");
			this.serverSocket.close();
		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
	}

	/**
	 * is connection available
	 * 
	 * @return boolean
	 */
	@Override
	public boolean isConnectionAvailable()
	{

		return false;
	}
}