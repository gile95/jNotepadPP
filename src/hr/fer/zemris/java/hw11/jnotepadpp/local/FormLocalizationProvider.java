package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * FormLocalizationProvider is a class derived from {@link LocalizationProviderBridge}.
 * When frame is opened, it calls connect and when frame is closed, it calls disconnect.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class FormLocalizationProvider extends LocalizationProviderBridge{

	/**
	 * Creates a new {@link FormLocalizationProvider}.
	 * @param provider Provider which can fetch translation for given keys.
	 * @param frame Parent frame.
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowOpened(WindowEvent e) {
				super.windowOpened(e);
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				disconnect();
			}
		});
	}

}
