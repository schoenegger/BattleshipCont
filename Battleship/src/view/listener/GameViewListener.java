package view.listener;

import gameSounds.GameSoundPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import logging.Logging;
import view.GlobalStrings.Definitions;
import Game.Logic;

/**
 * GameViewListener
 * 
 * @author Thomas Schönegger
 * @version 1.0
 * 
 */
public class GameViewListener implements ActionListener, MouseMotionListener,
		MouseListener, WindowListener

{
	private Logic refLogic;

	public GameViewListener(Logic refLogic)
	{
		this.refLogic = refLogic;
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{

	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		try
		{
			Thread.sleep(20);
		}
		catch (InterruptedException e1)
		{
			Logging.writeErrorMessage("Mouse Listener failed");
		}

		refLogic.sendMousePositionsToGameView(e.getX(), e.getY());

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		switch (command)
		{
		case Definitions.BUTTON_SET_SHIP :
			refLogic.setShipButtonPressed();
			break;
		case Definitions.BUTTON_ATTAC :
			refLogic.attacShipButtonPressed();
			break;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		refLogic.mouseClickToGameView();
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}

	@Override
	public void mousePressed(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
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
	public void windowClosing(WindowEvent e)
	{
		refLogic.stopBackGroundSounds();
		refLogic.startBackgroundSound(GameSoundPlayer.SOUND_MENUE_WAV);
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
	public void windowOpened(WindowEvent e)
	{
		refLogic.stopBackGroundSounds();
		refLogic.startBackgroundSound(GameSoundPlayer.SOUND_GAME_WAV);

	}

}
