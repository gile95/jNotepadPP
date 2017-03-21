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
 * Action which occurs when pasting text.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class PasteTextAction extends LocalizableAction{

	/**	SerialVersionUID. */
	private static final long serialVersionUID = 636812536871202558L;
	/**	TabbedPane containing all opened files. */
	private JTabbedPane tabbedPane;
	/**	Editor. */
	private JNotepadPP jNotepadPP;
	
	/**
	 * Creates a new {@link PasteTextAction}.
	 * @param jNotepadPP Editor.
	 * @param tabbedPane TabbedPane containing all opened files.
	 * @param flp {@link FormLocalizationProvider} whose homework is to connect and disconnect listeners.
	 */
	public PasteTextAction(JNotepadPP jNotepadPP, JTabbedPane tabbedPane, FormLocalizationProvider flp){
		super("paste", "pasteDescription", flp);
		this.tabbedPane = tabbedPane;
		this.jNotepadPP = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JScrollPane scrolledEditor = (JScrollPane) tabbedPane.getSelectedComponent();
		JTextArea editor = (JTextArea) scrolledEditor.getViewport().getView();
		
		Document doc = editor.getDocument();
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		try{
			doc.remove(offset, len);
			System.out.println(jNotepadPP.getStorage());
			System.out.println(editor.getCaretPosition());
			//editor.insert(jNotepadPP.getStorage(), editor.getCaretPosition());
			doc.insertString(editor.getCaret().getDot(), jNotepadPP.getStorage(), null);
		} catch (BadLocationException el){
			el.printStackTrace();
		}
	}
	
	
}
