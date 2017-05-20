package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Provides translations for given keys. Extends {@link AbstractLocalizationProvider}.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class LocalizationProvider extends AbstractLocalizationProvider{

	/**
	 * {@link ResourceBundle} for wanted language.
	 */
	private ResourceBundle bundle;
	/**
	 * Instance of this {@link LocalizationProvider}.
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();
	/**
	 * Current language.
	 */
	private String language;
	
	/**
	 * Private constructor which can be called only from this class. Ensures singleton pattern.
	 */
	private LocalizationProvider(){
	
	}
	
	/**
	 * Fetches the only instance of this {@link LocalizationProvider}.
	 * @return The only instance of this {@link LocalizationProvider}.
	 */
	public static LocalizationProvider getInstance(){
		return instance;
	}
	
	/**
	 * Sets the language on the given one.
	 * @param language New language.
	 */
	public void setLanguage(String language){
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		ResourceBundle bundle = ResourceBundle
				.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.translations.translations", locale);
		this.bundle = bundle;
		fire();
	}
	
	public String getString(String string){
		return bundle.getString(string);
	}
	
	/**
	 * Fetches current language.
	 * @return Current language.
	 */
	public String getLanguage(){
		return language;
	}
}
