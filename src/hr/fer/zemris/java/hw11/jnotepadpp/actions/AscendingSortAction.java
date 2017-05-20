package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.Locale;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Action which occurs when sorting ascending.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class AscendingSortAction extends LocalizableAction{

	/**	SerialVersionUID. */
	private static final long serialVersionUID = 6503852331475997936L;
	/**	TabbedPane containing all opened files. */
	private JTabbedPane tabbedPane;
	
	/**
	 * Creates a new {@link AscendingSortAction}.
	 * @param jNotepadPP Editor.
	 * @param tabbedPane TabbedPane containing all opened files.
	 * @param flp {@link FormLocalizationProvider} whose homework is to connect and disconnect listeners.
	 */
	public AscendingSortAction(JNotepadPP jNotepadPP, JTabbedPane tabbedPane, FormLocalizationProvider flp) {
		super("ascending", "ascendingDescription", flp);
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

		Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
		Collator collator = Collator.getInstance(locale);
		
		try {
			int end = offset + len;
			while(offset <= end){
				int line1 = editor.getLineOfOffset(offset);
				
				String line1String = editor.getText(editor.getLineStartOffset(line1), editor.getLineEndOffset(line1));
				doc.remove(editor.getLineStartOffset(line1), editor.getLineEndOffset(line1));
				String line2String = editor.getText(editor.getLineStartOffset(line1), editor.getLineEndOffset(line1));
				doc.remove(editor.getLineStartOffset(line1), editor.getLineEndOffset(line1));
				
				if(collator.compare(line1String, line2String) < 0){
					doc.insertString(editor.getCaretPosition(), line1String, null);
					doc.insertString(editor.getCaretPosition(), System.lineSeparator(), null);
					doc.insertString(editor.getCaretPosition(), line2String, null);
				}
				else{
					doc.insertString(editor.getCaretPosition(), line2String, null);
					doc.insertString(editor.getCaretPosition(), System.lineSeparator(), null);
					doc.insertString(editor.getCaretPosition(), line1String, null);
				}
				
				offset = editor.getCaretPosition() + 1;
			}
		} catch (BadLocationException ignorable) {
			
		}
	}
}
