package GameConnections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import logging.Logging;
import GameConnections.CommandBuilder.CommandConverter;
import GameUtilities.Command;

/**
 * TCP Connection Client
 * 
 * @author Schoenegger / Purkart / Koch
 *
 */

/**
 * Constructor
 */
public class TCPConnectionClient extends Connection
{
	BufferedReader inputReader;
	BufferedWriter outputStream;

	Socket clientSocket;
	CommandConverter convert;

	/**
	 * Constructor
	 * 
	 * @param port
	 * @param ipAdress
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public TCPConnectionClient(int port, String ipAdress)
			throws UnknownHostException, IOException
	{
		try
		{
			this.clientSocket = new Socket(ipAdress, port);
			System.out.println("Just connected to "
					+ clientSocket.getRemoteSocketAddress());

			this.outputStream = new BufferedWriter(new OutputStreamWriter(
					clientSocket.getOutputStream()));

			this.inputReader = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));

			convert = new CommandConverter();
		}
		catch (Exception e)
		{

			JOptionPane.showMessageDialog(null, "No Client Available",
					"No Client Available", JOptionPane.OK_CANCEL_OPTION);

			Logging.writeErrorMessage("TCPConnectionServer -> No Connectiont available");
			System.exit(1);
		}
	}

	/**
	 * receive command
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
			Logging.writeErrorMessage("TCPConnectionClient -> receive Stream is not possible");
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
			Logging.writeErrorMessage("TcpConnectionClient -> Send Stream not possible");
		}
	}

	/**
	 * close connection
	 */
	@Override
	public void close()
	{

		try
		{
			System.out.println("Close Connection!");
			this.clientSocket.close();
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