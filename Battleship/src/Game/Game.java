package Game;

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
		Frontend frontend = new Frontend();
		
		frontend.callMenue();
	}

}
