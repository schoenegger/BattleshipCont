package view.GlobalStrings;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import logging.Logging;

public class LanguageView
{
	private ResourceBundle bundle;

	public static final String VIEW_STRING_EN = "ENGLISH";
	public static final String VIEW_STRING_DE = "DEUTSCH";

	public static final String DEFAULT_STRING = " ";
	// GUI Start View
	public static final String PLAYER_VS_PLAYER = "Player_vs._Player";
	public static final String PLAYER_VS_CPU = "Player_vs._CPU";
	public static final String SETTINGS = "Settings";
	// GUI Setting View
	public static final String IP_ADDRESS = "IP_Address";
	public static final String PORT = "Port";
	public static final String HOST = "Host";
	public static final String LEVEL = "Level";
	public static final String LANGUAGE = "Language";
	public static final String SAVE = "Save";
	public static final String SHOW_LOG = "Show_Log";
	// GUI Log View
	public static final String SAVE_LOG = "Save_Log";
	public static final String CLEAR_LOG = "Clear_Log";

	public static final String GERMAN = "German";
	public static final String ENGLISH = "English";
	public static final String IMPORT_FILE = "ImportFile";

	public static final String HELP = "Help";
	// Player vs CPU gui
	public static final String GAME_FIELD = "Game_Field";
	public static final String OWN_FIELD = "Own_Field";
	public static final String ENEMY_FIELD = "Enemy_Field";
	public static final String ATTACK = "Attack";
	public static final String SET_SCHIP = "Set_Ship";
	// Shiptypes
	public static final String DESTROYER = "Destroyer";
	public static final String AIR_CARRIER = "Air_Carrier";
	public static final String YELLOW_SUBMARINE = "yellow_Submarine";
	// Label MSGS
	public static final String MESSAGE = "Message";
	public static final String INIT_FIELD = "Init_Field";
	public static final String PLEASE_ATTACK_YOUR_ENEMY = "Please_Attack_Your_Enemy";
	public static final String ATTACK_THE_ENEMY = "Attack_The_Enemy";
	public static final String ENEMY_TURN = "Enemy_Turn";
	public static final String YOUR_TURN = "Your_Turn";
	public static final String ATTACK_FROM_ENEMY = "Attack_From_Enemy:";

	public LanguageView(String language)
	{

		setLanguage(language);
	}

	/**
	 * Sets the current language
	 * 
	 * @param languageDictionary
	 */
	public void setLanguage(String languageDictionary)
	{
		try
		{
			this.bundle = ResourceBundle.getBundle("LanguageViewStrings",
					new Locale(languageDictionary));
		}
		catch (MissingResourceException e)
		{
			Logging.writeErrorMessage("Cannot load Resource Bundle for Multilanguage");
		}
	}

	/**
	 * returns the language string by choosen Language
	 * 
	 * @param languageDictionary
	 * @return
	 */
	public String getResourceString(String languageDictionary)
	{
		String resourceString;
		try
		{
			resourceString = this.bundle.getString(languageDictionary);
		}
		catch (MissingResourceException mre)
		{
			if (languageDictionary != null)
			{
				resourceString = languageDictionary;
			}
			else
			{
				resourceString = DEFAULT_STRING;
			}

			Logging.writeInfoMessage("translation string \""
					+ languageDictionary
					+ "\" wasn't found in File! Using default string");
		}
		return resourceString;
	}

}
