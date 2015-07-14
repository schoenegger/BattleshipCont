package view.GlobalStrings;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import logging.Logging;

public class LanguageView
{
	private ResourceBundle bundle;
	public static final String EN = "en";
	public static final String GER = "de";

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
		setLanguage(EN); // default language
							// is English
	}

	public void setLanguage(String languageDictionary)
	{
		String langDicString = EN;

		try
		{
			this.bundle = ResourceBundle.getBundle("LanguageViewStrings",
					new Locale(langDicString));
		}
		catch (MissingResourceException e)
		{
			Logging.writeErrorMessage("Cannot load Resource Bundle for Multilanguage");
		}
	}

	public String getResourceString(String languageDictionary)
	{

		// TODO DIDI -- create Error Handling for Function
		return this.bundle.getString(languageDictionary);

	}

}
