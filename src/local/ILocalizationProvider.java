package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Provides translations for given keys.
 * @author Mislav Gillinger
 * @version 1.0
 */
public interface ILocalizationProvider {

	/**
	 * Fetches a translation for a given key.
	 * @param key Key for a wanted translation.
	 * @return A translation for a given key.
	 */
	String getString(String key);
	/**
	 * Adds a localization listener to an inner list.
	 * @param l New localization listener.
	 */
	void addLocalizationListener(ILocalizationListener l);
	/**
	 * Removes a localization listener from an inner list.
	 * @param l Localization listener to be removed.
	 */
	void removeLocalizationListener(ILocalizationListener l);
}
