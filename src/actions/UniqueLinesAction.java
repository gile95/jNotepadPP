package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action which occurs when removing duplicate lines.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class UniqueLinesAction extends LocalizableAction{

	/**	SerialVersionUID. */
	private static final long serialVersionUID = -96696038951386486L;
	/**	TabbedPane containing all opened files. */
	private JTabbedPane tabbedPane;
	
	/**
	 * Creates a new {@link UniqueLinesAction}.
	 * @param jNotepadPP Editor.
	 * @param tabbedPane TabbedPane containing all opened files.
	 * @param flp {@link FormLocalizationProvider} whose homework is to connect and disconnect listeners.
	 */
	public UniqueLinesAction(JNotepadPP jNotepadPP, JTabbedPane tabbedPane, FormLocalizationProvider flp) {
		super("unique", "uniqueDescription", flp);
		this.tabbedPane = tabbedPane;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JScrollPane scrolledEditor = (JScrollPane) tabbedPane.getSelectedComponent();
		JTextArea editor = (JTextArea) scrolledEditor.getViewport().getView();
		
		Document doc = editor.getDocument();
		
		try {
			int caret = editor.getCaretPosition();
			int mark = editor.getCaret().getMark();
			if (mark < caret) {
				int i = caret;
				caret = mark;
				mark = i;
			}
			
			int dotLinePosition = editor.getLineOfOffset(caret);
			int begin = editor.getLineStartOffset(dotLinePosition);
			int markLinePosition = editor.getLineOfOffset(mark);
			int end = editor.getLineEndOffset(markLinePosition);
			
			String selectedLines = doc.getText(begin, end - begin);
			doc.remove(begin, end - begin);
			doc.insertString(begin, removeDuplicateLines(selectedLines), null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
	}
	
	/**
	 * Helper method for removing duplicate lines.
	 * @param selectedLines Selected lines.
	 * @return New Lines.
	 */
	private String removeDuplicateLines(String selectedLines) {
		String elements[] = selectedLines.split("\n");
		Set<String> lines = new LinkedHashSet<>();
		for (String line : elements) {
			lines.add(line);
		}
		
		String ret = "";
		for (String line : lines) {
			ret += (line + System.lineSeparator());
		}
		
		return ret;
	}
}
