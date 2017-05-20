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
 * Action which occurs when inverting case.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class InvertCaseAction extends LocalizableAction{

	/**	SerialVersionUID. */
	private static final long serialVersionUID = 5726969375918961338L;
	/**	TabbedPane containing all opened files. */
	private JTabbedPane tabbedPane;
	
	/**
	 * Creates a new {@link InvertCaseAction}.
	 * @param jNotepadPP Editor.
	 * @param tabbedPane TabbedPane containing all opened files.
	 * @param flp {@link FormLocalizationProvider} whose homework is to connect and disconnect listeners.
	 */
	public InvertCaseAction(JNotepadPP jNotepadPP, JTabbedPane tabbedPane, FormLocalizationProvider flp) {
		super("invert", "invertDescription", flp);
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
			text = changeCase(text);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException el){
			el.printStackTrace();
		}
	}
	
	/**
	 * Changes upper letters to lower and lower ones to upper.
	 * @param text Text to be changed.
	 * @return Changed text.
	 */
	private String changeCase(String text) {
		char[] znakovi = text.toCharArray();
		for(int i = 0; i < znakovi.length; i++){
			char c = znakovi[i];
			if(Character.isLowerCase(c)){
				znakovi[i] = Character.toUpperCase(c);
			} else if (Character.isUpperCase(c)){
				znakovi[i] = Character.toLowerCase(c);
			}
		}
		return new String(znakovi);
	}

}
