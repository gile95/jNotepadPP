package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.AscendingSortAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CopyTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CutTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.DescendingSortAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitApplicationAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.InfoFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.InvertCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.NewFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PasteTextAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ToLowerCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ToUpperCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.UniqueLinesAction;
import hr.fer.zemris.java.hw11.jnotepadpp.elems.JNotepadPPWindowAdapter;
import hr.fer.zemris.java.hw11.jnotepadpp.elems.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.elems.TabbedPaneChangeListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * This is an application which is able to work with text files. Available options are similar to options
 * of Windows Notepad++ editor.
 * @author Mislav Gillinger
 * @version 1.0
 */
public class JNotepadPP extends JFrame{

	/** SerialVersionUID */
	private static final long serialVersionUID = -7095049877310247673L;
	/**	Action which occurs when opening existing file. */
	private OpenFileAction openFileAction;
	/**	Action which occurs when opening new file. */
	private NewFileAction newFileAction;
	/**	Action which occurs when saving file. */
	private SaveAsFileAction saveAsFileAction;
	/**	Action which occurs when saving file. */
	private SaveFileAction saveFileAction;
	/**	Action which occurs when exiting application. */
	private ExitApplicationAction exitApplicationAction;
	/**	Action which occurs when cutting text. */
	private CutTextAction cutTextAction;
	/**	Action which occurs when copying text. */
	private CopyTextAction copyTextAction;
	/**	Action which occurs when pasting text. */
	private PasteTextAction pasteTextAction;
	/**	Action which occurs when info about the file is wanted. */
	private InfoFileAction infoFileAction;
	/**	Action which occurs when inverted case is wanted. */
	private InvertCaseAction invertCaseAction;
	/**	Action which occurs when text is wanted to upper. */
	private ToUpperCaseAction toUpperCaseAction;
	/**	Action which occurs when text is wanted to lower. */
	private ToLowerCaseAction toLowerCaseAction;
	/**	Action which occurs when sorting ascending. */
	private AscendingSortAction ascendingSortAction;
	/**	Action which occurs when sorting descending. */
	private DescendingSortAction descendingSortAction;
	/**	Action which occurs when removing duplicate lines. */
	private UniqueLinesAction uniqueLinesAction;
	/**	Pane with tabs which contain currently opened files. */
	private JTabbedPane tabbedPane;
	/**	Storage for copied or cut text. */
	private String storage;
	/**	Holds information about a file character number. */
	private JLabel editorInfo;
	/**	Holds informations about caret position. */
	private JLabel caretInfo;
	/**	Shows current time. */
	private JLabel timeLabel;
	/**	Measures time. */
	private Timer timer;
	
	
	/**	Submenu with change case options. */
	private LJMenu changeCaseSubmenu;
	
	/**	{@link FormLocalizationProvider} whose homework is to connect and disconnect listeners. */
	private FormLocalizationProvider flp;
	
	/**
	 * Creates a new {@link JNotepadPP}.
	 */
	public JNotepadPP(){
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, 700, 500);
		setTitle("JNotepad++");
		
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		
		initGUI();
	}
	
	/**
	 * Initializes graphical user interface.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addChangeListener(new TabbedPaneChangeListener(this));
		cp.add(tabbedPane, BorderLayout.CENTER);
		
		editorInfo = new JLabel("length: 0");
		caretInfo = new JLabel("Ln: 0    Col: 0    Sel: 0");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		timeLabel = new JLabel();
		timer = new Timer(1000, e -> {	
				Calendar now = Calendar.getInstance();
		        timeLabel.setText(dateFormat.format(now.getTime()));
		});
		timer.start();
		
		JToolBar bottomToolBar = new JToolBar("Info");
		bottomToolBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bottomToolBar.add(editorInfo);
		bottomToolBar.addSeparator(new Dimension(50, 0));
		bottomToolBar.add(caretInfo);
		bottomToolBar.addSeparator(new Dimension(350, 0));
		bottomToolBar.add(timeLabel);
		cp.add(bottomToolBar, BorderLayout.SOUTH);
		
		changeCaseSubmenu = new LJMenu("changeCase", flp);
		
		createActions();
		createMenus();
		createToolbars();
		
		addWindowListener(new JNotepadPPWindowAdapter(tabbedPane, this, flp));
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosed(WindowEvent e) {
				timer.stop();
			}
		});
	}

	/**
	 * Creates actions for {@link JNotepadPP}.
	 */
	private void createActions() {
		openFileAction = new OpenFileAction(this, tabbedPane, flp);
		newFileAction = new NewFileAction(this, tabbedPane, flp);
		saveAsFileAction = new SaveAsFileAction(this, tabbedPane, flp);
		saveFileAction = new SaveFileAction(this, tabbedPane, flp);
		exitApplicationAction = new ExitApplicationAction(this, tabbedPane, flp);
		cutTextAction = new CutTextAction(this, tabbedPane, flp);
		copyTextAction = new CopyTextAction(this, tabbedPane, flp);
		pasteTextAction = new PasteTextAction(this, tabbedPane, flp);
		infoFileAction = new InfoFileAction(this, tabbedPane, flp);
		invertCaseAction = new InvertCaseAction(this, tabbedPane, flp);
		toUpperCaseAction = new ToUpperCaseAction(this, tabbedPane, flp);
		toLowerCaseAction = new ToLowerCaseAction(this, tabbedPane, flp);
		ascendingSortAction = new AscendingSortAction(this, tabbedPane, flp);
		descendingSortAction = new DescendingSortAction(this, tabbedPane, flp);
		uniqueLinesAction = new UniqueLinesAction(this, tabbedPane, flp);
		
		openFileAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		
		newFileAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newFileAction.actionPerformed(null);
		
		saveAsFileAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveAsFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		
		saveFileAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		
		exitApplicationAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exitApplicationAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		
		cutTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		
		copyTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		
		pasteTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
		pasteTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		
		infoFileAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		infoFileAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		
		invertCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
		invertCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		
		toUpperCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUpperCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		
		toLowerCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		toLowerCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		
		ascendingSortAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control G"));
		ascendingSortAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
		
		descendingSortAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		descendingSortAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		
		uniqueLinesAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		uniqueLinesAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
	}

	/**
	 * Creates menus for {@link JNotepadPP}.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new LJMenu("file", flp);
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(newFileAction));
		fileMenu.add(new JMenuItem(openFileAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(saveAsFileAction));
		fileMenu.add(new JMenuItem(saveFileAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(infoFileAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitApplicationAction));
		
		JMenu editMenu = new LJMenu("edit", flp);
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(cutTextAction));
		editMenu.add(new JMenuItem(copyTextAction));
		editMenu.add(new JMenuItem(pasteTextAction));
		
		JMenu languagesMenu = new LJMenu("languages", flp);
		menuBar.add(languagesMenu);
		
		JMenuItem hr = new JMenuItem("Croatian");
		JMenuItem en = new JMenuItem("English");
		JMenuItem de = new JMenuItem("German");
		hr.addActionListener(l -> {
			LocalizationProvider.getInstance().setLanguage("hr");
		});
		en.addActionListener(l -> {
			LocalizationProvider.getInstance().setLanguage("en");
		});
		de.addActionListener(l -> {
			LocalizationProvider.getInstance().setLanguage("de");
		});
		languagesMenu.add(hr);
		languagesMenu.add(en);
		languagesMenu.add(de);
		
		JMenu toolsMenu = new LJMenu("tools", flp);
		menuBar.add(toolsMenu);
		
		JMenuItem invertCase = new JMenuItem(invertCaseAction);
		JMenuItem toUpperCase = new JMenuItem(toUpperCaseAction);
		JMenuItem toLowerCase = new JMenuItem(toLowerCaseAction);
		invertCase.setEnabled(false);
		toUpperCase.setEnabled(false);
		toLowerCase.setEnabled(false);
		changeCaseSubmenu.add(invertCase);
		changeCaseSubmenu.add(toLowerCase);
		changeCaseSubmenu.add(toUpperCase);
		toolsMenu.add(changeCaseSubmenu);
		
		JMenu sort = new LJMenu("sort", flp);
		sort.add(new JMenuItem(ascendingSortAction));
		sort.add(new JMenuItem(descendingSortAction));
		toolsMenu.add(sort);
		
		toolsMenu.add(new JMenuItem(uniqueLinesAction));
		
		this.setJMenuBar(menuBar);
	}

	/**
	 * Creates toolbars for {@link JNotepadPP}.
	 */
	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Tools");
		
		toolBar.add(new JButton(newFileAction));
		toolBar.add(new JButton(openFileAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(saveAsFileAction));
		toolBar.add(new JButton(saveFileAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(infoFileAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(cutTextAction));
		toolBar.add(new JButton(copyTextAction));
		toolBar.add(new JButton(pasteTextAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(exitApplicationAction));
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	/**
	 * Program execution starts with this method.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			LocalizationProvider.getInstance().setLanguage("en");
			new JNotepadPP().setVisible(true);
		});
	}

	/**
	 * Sets frame title.
	 * @param title New frame title.
	 */
	public void setFrameTitle(String title){
		setTitle(title + " - JNotepad++");
	}
	
	/**
	 * Sets storage for cut and copy actions.
	 * @param text New storage value.
	 */
	public void setStorage(String text){
		storage = text;
	}
	
	/**
	 * Fetches the storage value.
	 * @return The storage value.
	 */
	public String getStorage(){
		return storage;
	}
	
	/**
	 * Fetches a label holding info about caret position.
	 * @return A label holding info about caret position.
	 */
	public JLabel getCaretInfo(){
		return caretInfo;
	}
	
	/**
	 * Fetches a label holding info about character number in editor.
	 * @return A label holding info about character number in editor.
	 */
	public JLabel getEditorInfo(){
		return editorInfo;
	}
	
	/**
	 * Fetches a submenu with change case options.
	 * @return A submenu with change case options.
	 */
	public JMenu getChangeCaseSubmenu(){
		return changeCaseSubmenu;
	}
}
