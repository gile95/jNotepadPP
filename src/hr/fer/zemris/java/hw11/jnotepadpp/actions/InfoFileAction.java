package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action which occurs when getting an info about a file..
 * @author Mislav Gillinger
 * @version 1.0
 */
public class InfoFileAction extends LocalizableAction{

	/**	SerialVersionUID. */
	private static final long serialVersionUID = 5477753955475547808L;
	/**	Editor. */
	private JNotepadPP jNotepadPP;
	/**	TabbedPane containing all opened files. */
	private JTabbedPane tabbedPane;
	
	/**
	 * Creates a new {@link InfoFileAction}.
	 * @param jNotepadPP Editor.
	 * @param tabbedPane TabbedPane containing all opened files.
	 * @param flp {@link FormLocalizationProvider} whose homework is to connect and disconnect listeners.
	 */
	public InfoFileAction(JNotepadPP jNotepadPP, JTabbedPane tabbedPane, FormLocalizationProvider flp){
		super("info", "infoDescription", flp);
		this.jNotepadPP = jNotepadPP;
		this.tabbedPane = tabbedPane;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JScrollPane scrolledEditor = (JScrollPane) tabbedPane.getSelectedComponent();
		JTextArea editor = (JTextArea) scrolledEditor.getViewport().getView();
		String editorText = editor.getText();
		
		int numberOfCharacters = editorText.length();
		String textWithoutBlanks = editorText.replaceAll("\\s+", "");
		int numberOfNonBlankCharacters = textWithoutBlanks.length();
		int numberOfLines = editor.getLineCount();
		
		String message = "Number of characters found in document: " + numberOfCharacters + System.lineSeparator();
		message += "Number of non-blank characters found in document: " + numberOfNonBlankCharacters + System.lineSeparator();
		message += "Number of lines that this document contains: " + numberOfLines;
		
		JOptionPane.showMessageDialog(jNotepadPP, 
				message, 
				"Statistical info",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
