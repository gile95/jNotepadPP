package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a localization provider which is able to add, remove and notify listeners.
 * Implements {@link ILocalizationProvider}.
 * @author Mislav Gillinger
 * @version 1.0
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{

	/**
	 * List of all listeners.
	 */
	protected List<ILocalizationListener> listeners;
	
	/**
	 * Creates a new {@link AbstractLocalizationProvider}.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	@Override
	public abstract String getString(String key);

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies all listeners that a change occured.
	 */
	public void fire(){
		for(ILocalizationListener l : listeners){
			l.localizationChanged();
		}
	}
}
