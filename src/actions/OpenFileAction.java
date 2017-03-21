package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.elems.EditorCaretListener;
import hr.fer.zemris.java.hw11.jnotepadpp.elems.EditorListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;

/**
 * Action which occurs when opening an existing file..
 * @author Mislav Gillinger
 * @version 1.0
 */
public class OpenFileAction extends LocalizableAction{

	/**	SerialVersionUID. */
	private static final long serialVersionUID = 6190355430457541087L;
	/**	Editor. */
	private JNotepadPP jNotepadPP;;
	/**	TabbedPane containing all opened files. */
	private JTabbedPane tabbedPane;
	/**	{@link FormLocalizationProvider} whose homework is to connect and disconnect listeners. */
	private FormLocalizationProvider flp;
	
	/**
	 * Creates a new {@link OpenFileAction}.
	 * @param jNotepadPP Editor.
	 * @param tabbedPane TabbedPane containing all opened files.
	 * @param flp {@link FormLocalizationProvider} whose homework is to connect and disconnect listeners.
	 */
	public OpenFileAction(JNotepadPP jNotepadPP, JTabbedPane tabbedPane, FormLocalizationProvider flp){
		super("open", "openDescription", flp);
		this.jNotepadPP = jNotepadPP;
		this.tabbedPane = tabbedPane;
		this.flp = flp;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		if(fc.showOpenDialog(jNotepadPP) != JFileChooser.APPROVE_OPTION) return;
		
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		
		if(!Files.isReadable(filePath)){
			JOptionPane.showMessageDialog(jNotepadPP, 
					"Datoteka " + fileName.getAbsolutePath() + " ne postoji!",
					"Pogreška",
					JOptionPane.ERROR_MESSAGE
					);
			return;
		}
		
		byte[] okteti;
		try{
			okteti = Files.readAllBytes(filePath);
		} catch (Exception ex){
			JOptionPane.showMessageDialog(jNotepadPP, 
					"Pogreška prilikom čitanja datoteke " + fileName.getAbsolutePath(),
					"Pogreška",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String tekst = new String(okteti, StandardCharsets.UTF_8);
		JTextArea textArea = new JTextArea();
		textArea.setText(tekst);
		textArea.getDocument().addDocumentListener(new EditorListener(jNotepadPP, tabbedPane));
		textArea.addCaretListener(new EditorCaretListener(jNotepadPP, tabbedPane));
		
		JScrollPane scrollTextArea = new JScrollPane(textArea);
		
		Tab tab = new Tab(filePath.getName(0).toString(), tabbedPane, scrollTextArea, filePath, jNotepadPP, flp);
		tab.setSaved();
		tab.setSavedIcon();
		
		tabbedPane.addTab("", null, scrollTextArea, filePath.toString());
		tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(scrollTextArea), tab);
		
		tabbedPane.setSelectedComponent(scrollTextArea);
	}
}
