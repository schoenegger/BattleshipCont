package Game;

import gameSounds.GameSoundPlayer;

import java.awt.Point;

import view.StartView;
import GameConnections.ConnectionCommandHandler;
import GameUtilities.Command;
import GameUtilities.AttackPosition.AttackPosition;
import GameUtilities.Field.Field;

/**
 * Contains the logic of the game
 * 
 * @author Schoenegger / Purkart / Koch
 *
 */
public class Logic
{
	private Frontend referenceFrontend;
	private StartView startView;
	private CommandHandler commandHandler;
	// private StartViewSettingData startSettData;
	// private LanguageView languageView;

	private GameSoundPlayer gameSoundPlayer;
	private Command currAttacCommand = null;
	private Field ownField = new Field();
	private Field enemyField = new Field();
	private boolean isMyTurn;
	private boolean isHost;

	// ********only for tests---------------------
	// private TestEnemy testEnemy = new TestEnemy();

	// *****************************************

	/**
	 * Logic
	 * 
	 * @param isFirstPlayer
	 */
	public Logic(boolean isFirstPlayer)
	{
		// startSettData = new StartViewSettingData();

		// languageView = new LanguageView(startSettData.getLanguage());

		startView = new StartView(this); // , startSettData
		gameSoundPlayer = new GameSoundPlayer();

		this.isMyTurn = isFirstPlayer;
	}

	/********************** FUNCTION FOR FRONTEND ********************************/

	public void openStartViewSettings()
	{
		startView.openStartViewSettings();
	}

	public void openViewGameField()
	{
		startView.openViewGameFields(ownField, enemyField);
	}

	/**
	 * Set The Reference for the Logic
	 * 
	 * @param refFrontend
	 */
	public void setFrontendReference(Frontend refFrontend) // ----->>>REF
															// FRONTEND
	{
		this.referenceFrontend = refFrontend;
	}

	/**
	 * set the initial field
	 * 
	 * @param ownInitField
	 */
	public void setInitField(Field ownInitField)
	{
		this.ownField = ownInitField;
		sendFieldToOtherPlayer();
	}

	public boolean isAttacMoveValid(String nextMove)
	{
		System.out.println(nextMove);
		int[] attacCoordinates = buildCoordinatesByString(nextMove);

		return enemyField.IsValidAttacPosition(attacCoordinates[0],
				attacCoordinates[1]);
	}

	/********************** FUNCTION FOR LOGIC ***********************************/

	private void startNextMove()
	{
		System.out.println("*****************************************");
		System.out.println("Own Field:");
		ownField.display();
		System.out.println("\nEnemy Field:");
		enemyField.displayIncognito();
		System.out.println("*****************************************");

		if (isMyTurn)
		{
			fireToFieldPosition(referenceFrontend.getNextCommand());
			setIsMyTurn(false);
			wait(100);
		}
		else
		{
			// wait for enemy move
			System.out.println("Wait for enemy move");
			// waitForEnemyMove();
			setIsMyTurn(true);
		}
		commandHandler.receiveCommandFromDataBox();
	}

	public void setIsMyTurn(boolean isMyTurn)
	{
		this.isMyTurn = isMyTurn;
	}

	private boolean fireToFieldPosition(String fireMove)
	{
		int[] attacCoordinates = buildCoordinatesByString(fireMove);
		boolean returnValue = false;
		Command attacCommand = buildAttacCommand(fireMove);

		enemyField.fireToPosition(attacCoordinates[0], attacCoordinates[1]);
		commandHandler.sendAttacCommand(attacCommand);
		if (enemyField.checkIfAllShipsAreCountersunk())
		{
			referenceFrontend.displayWin();
			wait(10000);
			ConnectionCommandHandler.abortConnection();
		}
		return returnValue;
	}

	private Command buildAttacCommand(String fireMove)
	{
		String segments[] = fireMove.split(",");
		Point point = new Point(Integer.parseInt(segments[0]),
				Integer.parseInt(segments[1]));
		AttackPosition attackPos = new AttackPosition(point);

		Command command = new Command(1, attackPos, "ATTAC_COMMAND");
		return command;
	}

	private int[] buildCoordinatesByString(String nextMove)
	{
		int[] attacCoordinates = new int[2];
		String[] coordinatesAsString = nextMove.split(",");
		attacCoordinates[0] = Integer.parseInt(coordinatesAsString[0]);
		attacCoordinates[1] = Integer.parseInt(coordinatesAsString[1]);
		return attacCoordinates;
	}

	/**
	 * set the enemy field
	 * 
	 * @param enemyField
	 */
	public void setEnemyField(Field enemyField)
	{
		this.enemyField = enemyField;
	}

	private void wait(int ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	/********************** FUNCTION FOR COMMAND HANDLER *************************/

	public void setEnemyAttacCommand(AttackPosition attacPosition)// called from
																	// Command
																	// Handler
	{
		currAttacCommand = new Command(1, attacPosition, "ATTAC_COMMAND");
		ownField.fireToPosition(attacPosition.getXyPosition().x,
				attacPosition.getXyPosition().y);

		if (ownField.checkIfAllShipsAreCountersunk())
		{
			referenceFrontend.displayGameOver();
			wait(10000);
			ConnectionCommandHandler.abortConnection();
		}

		startNextMove();
	}

	private void sendFieldToOtherPlayer()
	{
		commandHandler = new CommandHandler(this); // Create commandHandler and
													// send reference

		commandHandler.sendInitField(buildInitCommand(this.ownField));

		waitForSettingsOtherPlayer();
	}

	private void waitForSettingsOtherPlayer()
	{
		while (enemyField == null)
		{
			wait(300);
			System.out.println("wait for settings other player");
		}

		startNextMove();
	}

	private Command buildInitCommand(Field field)
	{
		Command initCommand = new Command(1, field, "INIT_FIELD");

		return initCommand;
	}

	// Add Didi Function for listener Keyevent
	/**
	 * 
	 * @return the actual focus in the startview
	 */
	public String checkFocusInStartView()
	{
		return startView.checkFocusButton();
	}

	public void quitGame()
	{
		startView.setVisible(false);
		startView.dispose();
		System.exit(0);
		// startView.closeWindow();
	}
}