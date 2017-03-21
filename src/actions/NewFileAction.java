package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.elems.EditorCaretListener;
import hr.fer.zemris.java.hw11.jnotepadpp.elems.EditorListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;

/**
 * Action which occurs when creating a new file.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class NewFileAction extends LocalizableAction{

	/**	SerialVersionUID. */
	private static final long serialVersionUID = -8290148115178383135L;
	/**	Editor. */
	private JNotepadPP jNotepadPP;
	/**	TabbedPane containing all opened files. */
	private JTabbedPane tabbedPane;
	/**	Number of new tabs in {@link JNotepadPP}. */
	private int numOfNewFiles;
	/**	{@link FormLocalizationProvider} whose homework is to connect and disconnect listeners. */
	private FormLocalizationProvider flp;
	
	/**
	 * Creates a new {@link NewFileAction}.
	 * @param jNotepadPP Editor.
	 * @param tabbedPane TabbedPane containing all opened files.
	 * @param flp {@link FormLocalizationProvider} whose homework is to connect and disconnect listeners.
	 */
	public NewFileAction(JNotepadPP jNotepadPP, JTabbedPane tabbedPane, FormLocalizationProvider flp){
		super("new", "newDescription", flp);
		this.jNotepadPP = jNotepadPP;
		this.tabbedPane = tabbedPane;
		this.flp = flp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
				
		JTextArea textArea = new JTextArea();
		textArea.getDocument().addDocumentListener(new EditorListener(jNotepadPP, tabbedPane));
		textArea.addCaretListener(new EditorCaretListener(jNotepadPP, tabbedPane));
		JScrollPane scrollTextArea = new JScrollPane(textArea);
		
		Tab tab = new Tab("new" + String.valueOf(++numOfNewFiles), tabbedPane, scrollTextArea, null, jNotepadPP, flp);
		tab.setSaved();
		tab.setSavedIcon();
		
		tabbedPane.addTab("", null, scrollTextArea, "new" + String.valueOf(numOfNewFiles));
		tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(scrollTextArea), tab);
	}

}
