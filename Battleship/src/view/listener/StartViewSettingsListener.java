package view.listener;

import gameSounds.GameSoundPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import view.GlobalStrings.Definitions;
import view.settings.StartSettingsWindow;
import Game.Logic;

/**
 * StartViewSettingsListener
 * 
 * @author Thomas Schönegger
 * @version 1.0
 *
 */
public class StartViewSettingsListener implements ActionListener, KeyListener,
		WindowListener
{
	private Logic refLogic;
	private StartSettingsWindow refSettingsWindow;

	public StartViewSettingsListener(Logic refLogic,
			StartSettingsWindow refSettingsWindow)
	{
		this.refLogic = refLogic;
		this.refSettingsWindow = refSettingsWindow;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();

		switch (command)
		{
		case Definitions.SAVE_BUTTON_START_SETTINGS_VIEW :
			refSettingsWindow.saveSettingsIfValidAndWriteToFile();
			break;
		case Definitions.SHOW_LOG_BUTTON_START_SETTINGS_VIEW :
			refSettingsWindow.showLogView();
			break;
		case Definitions.TURN_MUSIC_ON_OFF :
			refLogic.turnSoundOnOrOff();
			break;
		default :
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_ESCAPE :
			refLogic.stopBackGroundSounds();
			refLogic.startBackgroundSound(GameSoundPlayer.SOUND_MENUE_WAV);
			refSettingsWindow.closeWindow();
			break;
		case KeyEvent.VK_ENTER :
			if (refSettingsWindow.focusOnBtnSave())
			{
				refSettingsWindow.saveSettingsIfValidAndWriteToFile();
			}
			break;
		default :
			break;
		}
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		refLogic.stopBackGroundSounds();
		refLogic.startBackgroundSound(GameSoundPlayer.SOUND_SETTING_WAV);
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		refLogic.stopBackGroundSounds();
		refLogic.startBackgroundSound(GameSoundPlayer.SOUND_MENUE_WAV);
	}

	@Override
	public void windowActivated(WindowEvent e)
	{

	}

	@Override
	public void windowClosed(WindowEvent e)
	{

	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{

	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{

	}

	@Override
	public void windowIconified(WindowEvent e)
	{

	}

	@Override
	public void keyReleased(KeyEvent e)
	{// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}
}
