package Game;

import gameSounds.GameSoundPlayer;

import java.awt.Point;

import view.StartView;
import view.GlobalStrings.LanguageView;
import view.settings.StartViewSettingData;
import GameConnections.ConnectionCommandHandler;
import GameUtilities.Command;
import GameUtilities.AttackPosition.AttackPosition;
import GameUtilities.Field.Field;

/**
 * Contains the logic of the game
 * 
 * @author Schoenegger / Purkart / Koch @
 */
public class Logic
{
	private Thread connectionCommandHandler;

	private Frontend referenceFrontend;
	private StartView startView;
	private CommandHandler commandHandler;

	private StartViewSettingData startSettData;
	private LanguageView languageView;

	private GameSoundPlayer gameSoundPlayer;
	private Command currAttacCommand = null;
	private Field ownField = new Field(true);
	private Field enemyField = new Field();
	private boolean isMyTurn;
	private boolean isHost;

	/**
	 * Logic
	 * 
	 * @param isFirstPlayer
	 */
	public Logic(boolean isFirstPlayer)
	{
		this.startView = new StartView(this); // , startSettData
		this.gameSoundPlayer = new GameSoundPlayer();

		this.startSettData = new StartViewSettingData();
		this.languageView = new LanguageView(startSettData.getLanguage());

		this.isMyTurn = isFirstPlayer;
	}

	/********************** FUNCTION FOR FRONTEND ********************************/

	public void openStartViewSettings()
	{
		startView.openStartViewSettings();
	}

	public void openViewGameField()
	{
		startView.openViewGameFields();
	}

	public void openViewGameFieldCPU()
	{
		startView.openViewGameFieldsCPU();
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
		startView.getNextCommandFromGameWindow();
		startView.sendGameWindowMessage(languageView
				.getResourceString(LanguageView.ATTACK_THE_ENEMY));
	}

	/**
	 * send attac commando to Enemy
	 * 
	 * @param fireCommand
	 */
	public void sendAttackCommandToEnemy(String fireCommand)
	{
		startView.sendGameWindowMessage(languageView
				.getResourceString(LanguageView.ENEMY_TURN));
		fireToFieldPosition(fireCommand);
		// commandHandler.receiveCommandFromDataBox();
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

		if (enemyField.fireToPosition(attacCoordinates[0], attacCoordinates[1]))
		{
			startBombSound(GameSoundPlayer.SOUND_CANNON_HIT,
					GameSoundPlayer.VOLUME_OWN_CANNON_SOUND);
		}
		else
		{
			startBombSound(GameSoundPlayer.SOUND_CANNON_MISS,
					GameSoundPlayer.VOLUME_OWN_CANNON_SOUND);
		}

		commandHandler.sendAttacCommand(attacCommand);
		if (enemyField.checkIfAllShipsAreCountersunk())
		{
			// referenceFrontend.displayWin();
			startView.displayWin();
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
	 * set the enemy field called by command Handler
	 * 
	 * @param enemyField
	 */
	public void setEnemyField(Field enemyField)
	{
		this.enemyField = enemyField;
		this.startView.setEnemyFieldInGameWindow(enemyField);

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

	public boolean startConnection(String defConnType, String ipAdd, String port)
	{
		switch (defConnType.toLowerCase())
		{
		case "host" :
			connectionCommandHandler = new Thread(new ConnectionCommandHandler(
					Integer.parseInt(port)));
			connectionCommandHandler.start();
			return true;

		case "client" :
			connectionCommandHandler = new Thread(new ConnectionCommandHandler(
					Integer.parseInt(port), ipAdd));
			connectionCommandHandler.start();
			isMyTurn = false;
			return true;

		case "cpu" :
			connectionCommandHandler = new Thread(new ConnectionCommandHandler(
					startSettData.getLevel()));
			connectionCommandHandler.start();
			return true;

		default :
			connectionCommandHandler = new Thread(new ConnectionCommandHandler(
					"1"));
			return false;
		}

	}

	/********************** FUNCTION FOR COMMAND HANDLER *************************/

	public void setEnemyAttacCommand(AttackPosition attacPosition)// called from
																	// Command
																	// Handler
	{
		startView.sendGameWindowMessage(languageView
				.getResourceString(LanguageView.ATTACK_FROM_ENEMY)// ("Attac from Enemy : "
				+ " " + attacPosition.toString());
		currAttacCommand = new Command(1, attacPosition, "ATTAC_COMMAND");
		if (ownField.fireToPosition(attacPosition.getXyPosition().x,
				attacPosition.getXyPosition().y))
		{
			startBombSound(GameSoundPlayer.SOUND_CANNON_HIT,
					GameSoundPlayer.VOLUME_ENEMY_CANNON_SOUND);
		}
		else
		{
			startBombSound(GameSoundPlayer.SOUND_CANNON_MISS,
					GameSoundPlayer.VOLUME_ENEMY_CANNON_SOUND);
		}

		if (ownField.checkIfAllShipsAreCountersunk())
		{

			startView.displayGameOver();
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
			this.startView
					.sendGameWindowMessage("Wait for settings other Player");
		}

		startNextMove();
	}

	private Command buildInitCommand(Field field)
	{
		Command initCommand = new Command(1, field, "INIT_FIELD");

		return initCommand;
	}

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

	public GameSoundPlayer getgameSoundPlayer()
	{
		return gameSoundPlayer;
	}

	// ************* Commands To Logic from startview*************

	/**
	 * inform view to sedn next move
	 */
	public void getNextMoveFromStartView()
	{
		this.startView.getNextCommandFromGameWindow();
	}

	/**
	 * send read mouse position to view
	 * 
	 * @param x
	 * @param y
	 */
	public void sendMousePositionsToGameView(int x, int y)
	{
		startView.sendMouseMoveToGameView(x, y);
	}

	/**
	 * inform View that set Ship button was pressed
	 */
	public void setShipButtonPressed()
	{
		startView.sendSetButtonPressed();
	}

	/**
	 * inform view that attack ship button was pressed
	 */
	public void attacShipButtonPressed()
	{
		startView.attacShipButtonPressed();
	}

	/**
	 * inform view that mouse were happened
	 */
	public void mouseClickToGameView()
	{
		startView.mouseClickToGameView();
	}

	// ************* Commands To Logic from startSettingsWindow*************

	/**
	 * stops the background sound
	 */
	public void stopBackGroundSounds()
	{
		gameSoundPlayer.stopBackGroundSounds();
	}

	/**
	 * starts the background sound
	 * 
	 * @param wavFilename
	 */
	public void startBackgroundSound(String wavFilename)
	{
		gameSoundPlayer.startBackgroundSound(wavFilename);
	}

	public void turnSoundOnOrOff()
	{
		gameSoundPlayer.turnSoundOnOrOFF();
	}

	public void startBombSound(String wavFilename, Float soundVolume)
	{
		gameSoundPlayer.startBombSound(wavFilename, soundVolume);
	}
}