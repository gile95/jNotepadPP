package hr.fer.zemris.java.hw11.jnotepadpp.elems;

import javax.swing.JTabbedPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;

/**
 * Listener for TabbedPane. When a current tab is changed, these actions occur.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class TabbedPaneChangeListener implements ChangeListener {

	/**
	 * Editor.
	 */
	private JNotepadPP jNotepadPP;
	
	/**
	 * Creates a new {@link TabbedPaneChangeListener}.
	 * @param jNotepadPP Editor.
	 */
	public TabbedPaneChangeListener(JNotepadPP jNotepadPP){
		this.jNotepadPP = jNotepadPP;
	}
	
	@SuppressWarnings("serial")
	@Override
	public void stateChanged(ChangeEvent e) {
		JTabbedPane tabbedPane = (JTabbedPane) e.getSource();

		if(tabbedPane.getSelectedIndex() == -1) return;
		
		EditorCaretListener l = new EditorCaretListener(jNotepadPP, tabbedPane);
		l.caretUpdate(new CaretEvent(l) {
			@Override
			public int getMark() {
				return 0;
			}
			@Override
			public int getDot() {
				return 0;
			}
		});
		
		Tab tab = (Tab)tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex());
		if(tab == null) {
			jNotepadPP.setFrameTitle("new1");
		}
		else if(tab.getOpenedFilePath() == null){
			jNotepadPP.setFrameTitle(tab.getFileName());
		}
		else{
			jNotepadPP.setFrameTitle(tab.getOpenedFilePath().toString());
		}
	}
}
