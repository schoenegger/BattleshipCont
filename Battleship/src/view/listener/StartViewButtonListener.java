package view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import view.GlobalStrings.Definitions;
import Game.Logic;

/**
 * StartViewButtonListener
 * 
 * @author Neubauer Dietmar
 * @version 1.0
 * 
 */
public class StartViewButtonListener implements ActionListener, KeyListener
{
	private Logic refGameLogic;

	public StartViewButtonListener(Logic refGameLogic)
	{
		this.refGameLogic = refGameLogic;
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		String command = event.getActionCommand();

		switch (command)
		{
		case Definitions.PLAYER_VS_COM :
			refGameLogic.openViewGameFieldCPU();
			break;
		case Definitions.PLAYER_VS_PLAYER :
			refGameLogic.openViewGameField();
			break;
		case Definitions.SETTING_START_VIEW :
			refGameLogic.openStartViewSettings();
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
			refGameLogic.quitGame();
			break;
		case KeyEvent.VK_P :
			refGameLogic.openViewGameField();
			break;
		case KeyEvent.VK_C :
			refGameLogic.openViewGameFieldCPU();
			break;
		case KeyEvent.VK_S :
			refGameLogic.openStartViewSettings();
			break;
		case KeyEvent.VK_ENTER :

			switch (refGameLogic.checkFocusInStartView())
			{
			case Definitions.PLAYER_VS_COM :
				refGameLogic.openViewGameField();
				break;
			case Definitions.PLAYER_VS_PLAYER :
				;
				break;
			case Definitions.SETTING_START_VIEW :
				refGameLogic.openStartViewSettings();
				break;
			default :
				break;
			}
		default :
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

}
