package hr.fer.zemris.java.hw11.jnotepadpp.elems;

import javax.swing.JTabbedPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;

/**
 * Listener for editor of TextArea of currently selected tab. Changes tab icon on unsaved.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class EditorListener implements DocumentListener{
	
	/**
	 * TabbedPane with tabs containing all opened files.
	 */
	private JTabbedPane tabbedPane;
	
	/**
	 * Creates a new {@link EditorListener}.
	 * @param jNotepadPP Editor.
	 * @param tabbedPane TabbedPane with tabs containing all opened files.
	 */
	public EditorListener(JNotepadPP jNotepadPP, JTabbedPane tabbedPane){
		this.tabbedPane = tabbedPane;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
			Tab selected = (Tab) tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex());
			selected.setUnsaved();
			selected.setUnsavedIcon();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		
	}

}
