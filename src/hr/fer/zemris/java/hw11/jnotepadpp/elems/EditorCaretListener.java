package hr.fer.zemris.java.hw11.jnotepadpp.elems;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * Listener for caret of an editor of currently selected tab.
 * Sets the label showing caret position info.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class EditorCaretListener implements CaretListener{

	/**
	 * Editor
	 */
	private JNotepadPP jNotepadPP;
	/**
	 * TabbedPane with tabs containing all opened files.
	 */
	private JTabbedPane tabbedPane;
	
	/**
	 * Creates a new {@link EditorCaretListener}.
	 * @param jNotepadPP Editor.
	 * @param tabbedPane TabbedPane with tabs containing all opened files.
	 */
	public EditorCaretListener(JNotepadPP jNotepadPP, JTabbedPane tabbedPane){
		this.jNotepadPP = jNotepadPP;
		this.tabbedPane = tabbedPane;
	}
	
	@Override
	public void caretUpdate(CaretEvent e) {
		JScrollPane scrolledEditor = (JScrollPane) tabbedPane.getSelectedComponent();
		JTextArea editor = (JTextArea) scrolledEditor.getViewport().getView();
		String editorText = editor.getText();
		
		JLabel editorInfo = jNotepadPP.getEditorInfo();
		JLabel caretInfo = jNotepadPP.getCaretInfo();
		
		int length = editorText.length();
		int ln = 0;
		int col = 0;
		try {
			ln = editor.getLineOfOffset(e.getDot());
			col = e.getDot() - editor.getLineStartOffset(ln);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		int sel = Math.abs(e.getDot() - e.getMark());
		
		if(sel != 0){
			int numOfItems = jNotepadPP.getChangeCaseSubmenu().getItemCount();
			for(int i = 0; i < numOfItems; i++){
				jNotepadPP.getChangeCaseSubmenu().getItem(i).setEnabled(true);
			}
		}
		else{
			int numOfItems = jNotepadPP.getChangeCaseSubmenu().getItemCount();
			for(int i = 0; i < numOfItems; i++){
				jNotepadPP.getChangeCaseSubmenu().getItem(i).setEnabled(false);
			}
		}
		
		editorInfo.setText("length: " + length);
		caretInfo.setText("Ln: " + ln + "    Col: " + col + "    Sel: " + sel);
	}

}
