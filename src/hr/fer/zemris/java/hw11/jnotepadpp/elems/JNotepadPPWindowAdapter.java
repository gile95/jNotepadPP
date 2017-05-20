package hr.fer.zemris.java.hw11.jnotepadpp.elems;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;

/**
 * Listener for this JFrame. When the frame is closing, asks user whether to save unsaved files.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class JNotepadPPWindowAdapter extends WindowAdapter{

	/**
	 * TabbedPane with tabs containing all opened files.
	 */
	private JTabbedPane tabbedPane;
	/**
	 * Editor.
	 */
	private JNotepadPP jNotepadPP;
	/**
	 * {@link FormLocalizationProvider} whose homework is to connect and disconnect listeners.
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * Creates a new {@link JNotepadPPWindowAdapter}.
	 * @param tabbedPane TabbedPane with tabs containing all opened files.
	 * @param jNotepadPP Editor.
	 * @param flp {@link FormLocalizationProvider} whose homework is to connect and disconnect listeners.
	 */
	public JNotepadPPWindowAdapter(JTabbedPane tabbedPane, JNotepadPP jNotepadPP, FormLocalizationProvider flp){
		this.tabbedPane = tabbedPane;
		this.jNotepadPP = jNotepadPP;
		this.flp = flp;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		int numberOfTabs = tabbedPane.getTabCount();
		boolean somethingToSave = false;
		int decision = 0;
		
		for(int i = 0; i < numberOfTabs; i++){
			Tab tab = (Tab) tabbedPane.getTabComponentAt(i);
			if(!tab.isSaved()){
				decision = JOptionPane.showConfirmDialog(jNotepadPP, 
						"There are unsaved files. Do you want to save them?", 
						"System message", 
						JOptionPane.YES_NO_OPTION);
				somethingToSave = true;
				break;
			}
		}
		if(!somethingToSave) jNotepadPP.dispose();
		if(decision != JOptionPane.YES_OPTION) jNotepadPP.dispose();
		else{
			for(int i = 0; i < numberOfTabs; i++){
				Tab tab = (Tab) tabbedPane.getTabComponentAt(i);
				if(!tab.isSaved()){
					SaveFileAction saveFileAction = new SaveFileAction(jNotepadPP, tabbedPane, flp);
					tabbedPane.setSelectedIndex(i);
					saveFileAction.actionPerformed(null);
				}
			}
			jNotepadPP.dispose();
		}
	}
}
