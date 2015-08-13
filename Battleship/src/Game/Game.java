package Game;

import logging.Logging;

/**
 * Game - Start Class
 * 
 * @author Schoenegger / Purkart / Koch
 *
 */
public class Game
{
	/**
	 * Startpoint
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{

		try
		{
			Logic gameLogic = new Logic(true);
			Logging.writeInfoMessage("Start Game");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Logging.writeInfoMessage("Game Exception" + e.getMessage());
		}
	}

}
