package hr.fer.zemris.java.hw11.jnotepadpp.elems;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Represents a custom JMenu which changes its text based on set language. 
 * @author Mislav Gillinger
 * @version 1.0
 */
public class LJMenu extends JMenu{

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -424445730902027098L;
	
	/**
	 * Creates a new {@link LJMenu}.
	 * @param key Key of wanted translation.
	 * @param provider Provider for getting language translation.
	 */
	public LJMenu(String key, ILocalizationProvider provider){
		
		String keyTranslation = provider.getString(key);
		setText(keyTranslation);
		
		provider.addLocalizationListener(() -> {
			String newKeyTranslation = provider.getString(key);
			setText(newKeyTranslation);
		});
	}
}
