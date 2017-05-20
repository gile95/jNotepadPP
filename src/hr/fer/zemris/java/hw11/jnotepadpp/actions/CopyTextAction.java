package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action which occurs when copying text.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class CopyTextAction extends LocalizableAction{

	/**	SerialVersionUID. */
	private static final long serialVersionUID = -2018304384041704356L;
	/**	TabbedPane containing all opened files. */
	private JTabbedPane tabbedPane;
	/**	Editor. */
	private JNotepadPP jNotepadPP;
	
	/**
	 * Creates a new {@link CopyTextAction}.
	 * @param jNotepadPP Editor.
	 * @param tabbedPane TabbedPane containing all opened files.
	 * @param flp {@link FormLocalizationProvider} whose homework is to connect and disconnect listeners.
	 */
	public CopyTextAction(JNotepadPP jNotepadPP, JTabbedPane tabbedPane, FormLocalizationProvider flp){
		super("copy", "copyDescription", flp);
		this.tabbedPane = tabbedPane;
		this.jNotepadPP = jNotepadPP;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JScrollPane scrolledEditor = (JScrollPane) tabbedPane.getSelectedComponent();
		JTextArea editor = (JTextArea) scrolledEditor.getViewport().getView();
		
		Document doc = editor.getDocument();
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		if(len == 0) return;
		
		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		try{
			jNotepadPP.setStorage(doc.getText(offset, len));
		} catch (BadLocationException el){
			el.printStackTrace();
		}
	}

	
}
