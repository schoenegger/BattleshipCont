package view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.GlobalStrings.ViewStringDefinitions;
import Game.Logic;

public class StartViewButtonListener implements ActionListener
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
		case ViewStringDefinitions.PLAYER_VS_COM :
			refGameLogic.openViewGameField();
			break;
		case ViewStringDefinitions.PLAYER_VS_PLAYER :
			;
			break;
		case ViewStringDefinitions.SETTING_START_VIEW :
			refGameLogic.openStartViewSettings();
			break;
		default :
			break;
		}
	}

}
