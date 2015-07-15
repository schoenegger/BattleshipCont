package view.GlobalStrings;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import logging.Logging;

public class LanguageView
{
	private ResourceBundle bundle;
	private String actualLanguageToken;
	private int languageIndex;
	// public static final String EN = "en";
	// public static final String GER = "de";

	public static final String VIEW_STRING_EN = "ENGLISH";
	public static final String VIEW_STRING_DE = "DEUTSCH";

	public static final String DEFAULT_STRING_NOT_EXIST = "empty placeholder";

	public static final String PLAYER_VS_CPU = "Player_vs._CPU_Button";

	public static final String LECTURE_PLAN = "LecturePlan";
	public static final String CALENDAR_WEEK = "CalendarWeek";
	public static final String CALENDAR_WEEK_SHORT = "CalendarWeekShort";
	public static final String SETTINGS = "Settings";
	public static final String GERMAN = "German";
	public static final String ENGLISH = "English";
	public static final String IMPORT_FILE = "ImportFile";
	public static final String EXPORT_CSV = "ExportCSV";
	public static final String EXPORT_XML = "ExportXML";
	public static final String FILE = "File";
	public static final String LANGUAGE = "Language";
	public static final String THEME = "Theme";
	public static final String COLOR = "Color";
	public static final String SAVE = "Save";
	public static final String CHANGE = "Change";

	public LanguageView()
	{
		actualLanguageToken = this.ENGLISH; // default language
		languageIndex = 0;
		// is English
	}

	public LanguageView(String language)
	{
		// actualLanguageToken =
		// convertToLanguageTokenAndSetLanguageIndex(language);
		setLanguage(language);
	}

	// The languageIndex shows the correct used Language in the view (combobox)
	// The
	// private String convertToLanguageTokenAndSetLanguageIndex(String language)
	// {
	// switch (language)
	// {
	// case VIEW_STRING_EN :
	// languageIndex = 0;
	// return EN;
	//
	// case VIEW_STRING_DE :
	// languageIndex = 1;
	// return GER;
	//
	// default :
	// Logging.writeInfoMessage("Can't find selected language \""
	// + language + "\"! Using default language English!");
	// return EN;
	// }
	// }

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

	public String getResourceString(String languageDictionary)
	{
		String resourceString;
		// TODO DIDI -- create Error Handling for Function

		// returnstring = this.bundle.getString(languageDictionary);
		try
		{
			resourceString = this.bundle.getString(languageDictionary);
		}
		catch (MissingResourceException mre)
		{
			resourceString = DEFAULT_STRING_NOT_EXIST;
			Logging.writeInfoMessage("translation string \""
					+ languageDictionary
					+ "\" wasn't found in File! Using default string");
		}
		return resourceString;
	}

	public int getLanguageIndex()
	{
		return languageIndex;
	}

}
