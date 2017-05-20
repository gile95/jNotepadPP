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
 * Action which occurs when inverting text to upper case.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class ToUpperCaseAction extends LocalizableAction{

	/**	SerialVersionUID. */
	private static final long serialVersionUID = 5729617195728020861L;
	/**	TabbedPane containing all opened files. */
	private JTabbedPane tabbedPane;
	
	/**
	 * Creates a new {@link ToUpperCaseAction}.
	 * @param jNotepadPP Editor.
	 * @param tabbedPane TabbedPane containing all opened files.
	 * @param flp {@link FormLocalizationProvider} whose homework is to connect and disconnect listeners.
	 */
	public ToUpperCaseAction(JNotepadPP jNotepadPP, JTabbedPane tabbedPane, FormLocalizationProvider flp) {
		super("toUpper", "toUpperDescription", flp);
		this.tabbedPane = tabbedPane;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JScrollPane scrolledEditor = (JScrollPane) tabbedPane.getSelectedComponent();
		JTextArea editor = (JTextArea) scrolledEditor.getViewport().getView();
		
		Document doc = editor.getDocument();
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		int offset = 0;
		if(len != 0){
			offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		}
		else{
			return;
		}
		try{
			String text = doc.getText(offset, len);
			text = text.toUpperCase();
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException el){
			el.printStackTrace();
		}
	}
}
