package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * LocalizationProviderBridge is a decorator for some other IlocalizationProvider. 
 * This class offers two additional methods: connect() and disconnect(), and it
 * manages a connection status. When asked to resolve a key delegates this request to wrapped
 * (decorated) ILocalizationProvider object. When user calls connect() on it, the method will register an
 * instance of anonymous ILocalizationListener on the decorated object. When user calls disconnect(),
 * this object will be deregistered from decorated object.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider{

	/**
	 * Indicates whether connection is already made.
	 */
	private boolean connected;
	/**
	 * Provider which gets a translation for given key.
	 */
	private ILocalizationProvider provider;
	
	/**
	 * Creates a new {@link LocalizationProviderBridge}.
	 * @param provider Provider which gets a translation for given key.
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider){
		this.provider = provider;
	}
	
	/**
	 * Disconnects this object from decorated object.
	 */
	public void disconnect(){
		connected = false;
		for(ILocalizationListener l : listeners){
			provider.removeLocalizationListener(l);
		}
	}
	
	/**
	 * Connects this object to a decorated object.
	 */
	public void connect(){
		if(!connected){
			connected = true;
			for(ILocalizationListener l : listeners){
				provider.addLocalizationListener(l);
			}
		}
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}
}
