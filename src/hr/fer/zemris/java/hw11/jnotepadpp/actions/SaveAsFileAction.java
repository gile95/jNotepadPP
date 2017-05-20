package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Action which occurs when saving a file.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class SaveAsFileAction extends LocalizableAction{

	/**	SerialVersionUID. */
	private static final long serialVersionUID = -6287658076165854692L;
	/**	Editor. */
	private JNotepadPP jNotepadPP;;
	/**	TabbedPane containing all opened files. */
	private JTabbedPane tabbedPane;
	/**	Path to an opened file. */
	private Path openedFilePath;
	/**	Text area for file. */
	private JTextArea textArea;
	
	/**
	 * Creates a new {@link SaveAsFileAction}.
	 * @param jNotepadPP Editor.
	 * @param tabbedPane TabbedPane containing all opened files.
	 * @param flp {@link FormLocalizationProvider} whose homework is to connect and disconnect listeners.
	 */
	public SaveAsFileAction(JNotepadPP jNotepadPP, JTabbedPane tabbedPane, FormLocalizationProvider flp) {
		super("saveAs", "saveAsDescription", flp);
		this.jNotepadPP = jNotepadPP;
		this.tabbedPane = tabbedPane;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Tab selected = (Tab) tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex());
		this.openedFilePath = selected.getOpenedFilePath();
		
		JScrollPane a = (JScrollPane) tabbedPane.getSelectedComponent();
		textArea = (JTextArea) a.getViewport().getView();
		
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		if(jfc.showSaveDialog(jNotepadPP) != JFileChooser.APPROVE_OPTION){
			JOptionPane.showMessageDialog(jNotepadPP, 
					"Ništa nije snimljno.",
					"Upozorenje",
					JOptionPane.WARNING_MESSAGE
					);
			return;
		}
		openedFilePath = jfc.getSelectedFile().toPath();
		
		if(openedFilePath.toFile().exists()){
			int selection;
			while((selection = JOptionPane.showConfirmDialog(jNotepadPP, 
					"File " + openedFilePath.getFileName().toString() + " already exists. Dou you want to replace it?",
					"System message",
					JOptionPane.YES_NO_CANCEL_OPTION
					)) 
					!= JOptionPane.YES_OPTION){
						if(selection == JOptionPane.CANCEL_OPTION) return;
						jfc.showSaveDialog(jNotepadPP);
						openedFilePath = jfc.getSelectedFile().toPath();
						if(!openedFilePath.toFile().exists()) break;
			}
		}
		selected.setOpenedFilePath(openedFilePath);
		
		byte[] podatci = textArea.getText().getBytes(StandardCharsets.UTF_8);
		try{
			Files.write(openedFilePath, podatci);
		} catch (IOException el){
			JOptionPane.showMessageDialog(jNotepadPP, 
					"Pogreška prilikom zapisivanja datoteke " + openedFilePath,
					"Pogreška",
					JOptionPane.ERROR_MESSAGE
					);
			return;
		}
		JOptionPane.showMessageDialog(jNotepadPP, 
				"Datoteka je snimljena.", 
				"Informacija",
				JOptionPane.INFORMATION_MESSAGE);
		selected.updateTab(openedFilePath);
		jNotepadPP.setFrameTitle(openedFilePath.getFileName().toString());
	}
}
