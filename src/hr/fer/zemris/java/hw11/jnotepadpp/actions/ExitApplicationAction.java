package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.elems.JNotepadPPWindowAdapter;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
/**
 * Action which occurs when exiting application.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class ExitApplicationAction extends LocalizableAction{

	/**	SerialVersionUID. */
	private static final long serialVersionUID = 1L;
	/**	Editor. */
	private JNotepadPP jNotepadPP;
	/**	TabbedPane containing all opened files. */
	private JTabbedPane tabbedPane;
	/**	{@link FormLocalizationProvider} whose homework is to connect and disconnect listeners. */
	private FormLocalizationProvider flp;
	
	/**
	 * Creates a new {@link ExitApplicationAction}.
	 * @param jNotepadPP Editor.
	 * @param tabbedPane TabbedPane containing all opened files.
	 * @param flp {@link FormLocalizationProvider} whose homework is to connect and disconnect listeners.
	 */
	public ExitApplicationAction(JNotepadPP jNotepadPP, JTabbedPane tabbedPane, FormLocalizationProvider flp) {
		super("exit", "exitDescription", flp);
		this.jNotepadPP = jNotepadPP;
		this.tabbedPane = tabbedPane;
		this.flp = flp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JNotepadPPWindowAdapter jNotepadPPWindowAdapter = new JNotepadPPWindowAdapter(tabbedPane, jNotepadPP, flp);
		jNotepadPPWindowAdapter.windowClosing(null);
	}

}
