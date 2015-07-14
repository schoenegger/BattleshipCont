package view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.GlobalStrings.ViewStringDefinitions;

public class StartViewSettingsListener implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();

		switch (command)
		{
		case ViewStringDefinitions.SAVE_BUTTON_START_SETTINGS_VIEW :

			break;

		default :
			break;
		}

	}
}
