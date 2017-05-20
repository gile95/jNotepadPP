package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.FlowLayout;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.NewFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;

/**
 * Represents one tab of a {@link JTabbedPane}.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class Tab extends JPanel{

	/** SerialVersionUID */
	private static final long serialVersionUID = 1368362859696088834L;
	/** Name of a file in this tab. */
	private String fileName;
	/** Path of an opened file. */
	private Path openedFilePath;
	/** Flag indicating whether a file in this tab is saved. */
	private boolean saved;
	
	/** Icon for unsaved files. */
	ImageIcon unsavedIcon = getImageIcon("icons/unsaved.png");
	/** Icon for saved files. */
	ImageIcon savedIcon = getImageIcon("icons/saved.png");
	/** Icon for button for exiting tab. */
	ImageIcon closeIcon = getImageIcon("icons/x.png");
	
	/**
	 * Creates a new {@link Tab}.
	 * @param fileName Name of a file in this tab.
	 * @param tabbedPane Parent component of this tab.
	 * @param scrollTextArea Text area showing file text if in this component.
	 * @param openedFilePath Path of an opened file.
	 * @param jNotepadPP Frame which contains this tab.
	 * @param flp {@link FormLocalizationProvider} whose homework is to connect and disconnect listeners.
	 */
	public Tab(String fileName, JTabbedPane tabbedPane, JScrollPane scrollTextArea, Path openedFilePath,
			JNotepadPP jNotepadPP, FormLocalizationProvider flp) {
		this.fileName = fileName;
		this.openedFilePath = openedFilePath;
		
		
		
		setLayout(new FlowLayout());
		add(new JLabel(unsavedIcon));
		add(new JLabel(fileName));
		JButton closeButton = new JButton(closeIcon);
		add(closeButton);
		closeButton.addActionListener(l -> {
			boolean last = false;
			NewFileAction newFileAction = new NewFileAction(jNotepadPP, tabbedPane, flp);
			if(tabbedPane.getTabCount() == 1) {
				last = true;
			}
			
			if (saved){
				tabbedPane.remove(scrollTextArea);
				if(last) newFileAction.actionPerformed(null);
			}
			else{
				int odluka = JOptionPane.showConfirmDialog(jNotepadPP, 
						"File unsaved. Do you want to save it?", 
						"File unsaved", 
						JOptionPane.YES_NO_OPTION);
		
				if(odluka != JOptionPane.YES_OPTION){
					tabbedPane.remove(scrollTextArea);
					if(last) newFileAction.actionPerformed(null);
				}
				else{
					SaveFileAction saveFileAction = new SaveFileAction(jNotepadPP, tabbedPane, flp);
					saveFileAction.actionPerformed(null);
					tabbedPane.remove(scrollTextArea);
					if(last) newFileAction.actionPerformed(null);
				}
			}
		});
	}
	
	/**
	 * Fetches the image icon.
	 * @param string String representing an image.
	 * @return The image icon.
	 */
	private ImageIcon getImageIcon(String string) {
		InputStream is = this.getClass().getResourceAsStream(string);
		byte[] bytes = new byte[10];
		List<Byte> bytesList = new ArrayList<>();
		int read;
		try {
			while(true){
				read = is.read(bytes);
				if(read < 0) break;
				for(int i = 0; i < read; i++){
					bytesList.add(bytes[i]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] retBytes = new byte[bytesList.size()];
		for(int i = 0; i < bytesList.size(); i++){
			retBytes[i] = bytesList.get(i);
		}
		
		return new ImageIcon(retBytes);
	}

	/**
	 * Fetches the path of currently opened file.
	 * @return The path of currently opened file.
	 */
	public Path getOpenedFilePath(){
		return openedFilePath;
	}
	
	/**
	 * Sets the path of currently opened file.
	 * @param newOpenedFilePath Path of currently opened file.
	 */
	public void setOpenedFilePath(Path newOpenedFilePath){
		openedFilePath = newOpenedFilePath;
	}
	
	/**
	 * Fetches the fileName of file shown on this tab.
	 * @return The fileName of file shown on this tab.
	 */
	public String getFileName(){
		return fileName;
	}
	
	/**
	 * Updates tab.
	 * @param openedFilePath Path of currently opened file.
	 */
	public void updateTab(Path openedFilePath){
		this.remove(0);
		this.remove(0);
		this.add(new JLabel(openedFilePath.getFileName().toString()), 0);
		this.add(new JLabel(savedIcon), 0);
		setToolTipText(openedFilePath.toString());
		setSaved();
		revalidate();
	}
	
	/**
	 * Sets a saved icon.
	 */
	public void setSavedIcon(){
		this.remove(0);
		this.add(new JLabel(savedIcon), 0);
		revalidate();
	}
	
	/**
	 * Sets an unsaved icon.
	 */
	public void setUnsavedIcon(){
		this.remove(0);
		this.add(new JLabel(unsavedIcon), 0);
		revalidate();
	}
	
	/**
	 * Sets {@link #saved}.
	 */
	public void setSaved(){
		saved = true;
	}
	
	/**
	 * Resets {@link #saved}.
	 */
	public void setUnsaved(){
		saved = false;
	}
	
	/**
	 * Fetches flag {@link #saved}.
	 * @return Flag {@link #saved}.
	 */
	public boolean isSaved(){
		return saved;
	}
}
