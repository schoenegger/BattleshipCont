package GameConnections;

import Game.CpuPlayer;
import GameUtilities.Command;

/**
 * Local Connection - CPU Player
 * 
 * @author Schoenegger
 *
 */
public class LocalConnection extends Connection
{
	private CpuPlayer cpuPlayer;

	/**
	 * Local Connection - CPU Player
	 */
	public LocalConnection()
	{
		this.cpuPlayer = new CpuPlayer();
	}

	/**
	 * is connectection available
	 * 
	 * @return true
	 */
	@Override
	public boolean isConnectionAvailable()
	{
		return true;
	}

	/**
	 * Receive Command
	 * 
	 * @return command
	 */
	@Override
	public Command receiveCommand()
	{
		return cpuPlayer.receiveCommand();
	}

	/**
	 * send command
	 */
	@Override
	public void sendCommand(Command command)
	{
		if (command != null && command.isValid())
		{
			cpuPlayer.sendCommand(command);
		}
	}

	/**
	 * close the connection
	 */
	@Override
	public void close()
	{
		this.close();
	}

}
