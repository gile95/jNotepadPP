package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Describes what will happen when a specified action occurs.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class LocalizableAction extends AbstractAction{

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -7976798279485256699L;
	
	/**
	 * Creates a new {@link LocalizableAction}.
	 * @param key Key for wanted translation.
	 * @param keyDescription Key for wanted action description translation.
	 * @param provider Provider which return translations for given keys.
	 */
	public LocalizableAction(String key, String keyDescription, ILocalizationProvider provider){
		
		String keyTranslation = provider.getString(key);
		String keyDescriptionTranslation = provider.getString(keyDescription);
		putValue(NAME, keyTranslation);
		putValue(Action.SHORT_DESCRIPTION, keyDescriptionTranslation);
		
		provider.addLocalizationListener(() -> {
			String newKeyTranslation = provider.getString(key);
			String newKeyDescriptionTranslation = provider.getString(keyDescription);
			putValue(NAME, newKeyTranslation);
			putValue(Action.SHORT_DESCRIPTION, newKeyDescriptionTranslation);
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
