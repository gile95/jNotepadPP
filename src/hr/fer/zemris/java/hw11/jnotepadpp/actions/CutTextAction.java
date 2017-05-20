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
 * Action which occurs when cutting text.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class CutTextAction extends LocalizableAction{

	/**	SerialVersionUID. */
	private static final long serialVersionUID = -4622902714298863530L;
	/**	TabbedPane containing all opened files. */
	private JTabbedPane tabbedPane;
	/**	Editor. */
	private JNotepadPP jNotepadPP;
	
	/**
	 * Creates a new {@link CutTextAction}.
	 * @param jNotepadPP Editor.
	 * @param tabbedPane TabbedPane containing all opened files.
	 * @param flp {@link FormLocalizationProvider} whose homework is to connect and disconnect listeners.
	 */
	public CutTextAction(JNotepadPP jNotepadPP, JTabbedPane tabbedPane, FormLocalizationProvider flp){
		super("cut", "cutDescription", flp);
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
			doc.remove(offset, len);
		} catch (BadLocationException el){
			el.printStackTrace();
		}
	}
}
