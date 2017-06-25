package rjs.panels.options;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rjs.container.FontContainer;
import rjs.container.LetterContainer;
import rjs.container.OptionsContainer;
import rjs.frame.IMainFrame;
import rjs.panels.AbstractPanel;

public class FontOptionsPanel extends AbstractPanel
{
	/**
	 * Label to show example text of Kana in current font.
	 */
	private JTextArea mMainFontExampleLabel = null;
	
	/**
	 * Combobox to choose the font to display Kana.
	 */
	private JComboBox<String> mMainFontBox = null;
	
	/**
	 * Slider to change the size of the Kana.
	 */
	private JSlider mKanaFontSizeSlider = null;
	
	/**
	 * Label to show example text of Kana in current font.
	 */
	private JTextArea mKanjiFontExampleLabel = null;
	
	/**
	 * Combobox to choose the font to display Kana.
	 */
	private JComboBox<String> mKanjiFontBox = null;
	
	/**
	 * Slider to change the size of the Kana.
	 */
	private JSlider mKanjiFontSizeSlider = null;
	
	/**
	 * Fonts that may be loaded from the fonts-folder at runtime.
	 */
	private ArrayList<String> mLoadableFonts = null;
	
	/**
	 * Default constructor.
	 */
	public FontOptionsPanel()
	{
	}
	
	/**
	 * Initialize the options panel.
	 */
	public void initialize(IMainFrame mainFrame)
	{
		super.initialize(mainFrame);
		
		FontContainer fontContainer = (FontContainer)this.getMainFrame().getContainerManager().getContainer(FontContainer.class);
		
		// Start with part A (Kana font stuff).
		JPanel optionsPanelA = new JPanel();
		optionsPanelA.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		// Label with some info text for Kana font.
		JLabel mainFontLabel = new JLabel();
		mainFontLabel.setText("Hiragana/Katakana font:");
		this.setConstraints(constraints, 0, 0, 3, 1, 3.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		optionsPanelA.add(mainFontLabel, constraints);
		
		// Font example is within a scroll pane to save some space.
		this.mMainFontExampleLabel = new JTextArea();
		this.mMainFontExampleLabel.setLineWrap(true);
		this.mMainFontExampleLabel.setText(this.createFontExampleText());
		this.mMainFontExampleLabel.setFont(fontContainer.getKanaFont().deriveFont(fontContainer.getKanaFontSize()));
		
		// Combo box to choose Kana-font from.
		this.mMainFontBox = new JComboBox<String>();
		this.mMainFontBox.addItem("default");
		mLoadableFonts = new ArrayList<String>();
		this.CheckFolderForFonts("fonts", mLoadableFonts);
		for(String fileName : this.justNamesOfPaths(mLoadableFonts))
		{
			this.mMainFontBox.addItem(fileName);
		}
		int currentKanaIndex = 0;
		if(!fontContainer.getKanaFontPath().equals(""))
		{
			// Get the proper font to be shown in the combo box if a font was already selected.
			for(int index = 0; index < this.mMainFontBox.getItemCount(); ++index)
			{
				if(fontContainer.getKanaFontPath().contains(this.mMainFontBox.getItemAt(index)))
				{
					currentKanaIndex = index;
				}
			}
		}
		this.mMainFontBox.setSelectedIndex(currentKanaIndex);
		this.mMainFontBox.addActionListener(new MainFontBoxListener());
		this.setConstraints(constraints, 3, 0, 3, 1, 3.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		optionsPanelA.add(this.mMainFontBox, constraints);
		
		// Scroll pane with example text for Kana.
		JScrollPane fontExampleScrollPane = new JScrollPane(this.mMainFontExampleLabel);
		fontExampleScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setConstraints(constraints, 0, 1, 6, 2, 6.0, 2.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		optionsPanelA.add(fontExampleScrollPane, constraints);
		
		// Label with info: kana font size.
		JLabel kanaFontSizeLabel = new JLabel();
		kanaFontSizeLabel.setText("Kana font size: ");
		this.setConstraints(constraints, 0, 3, 3, 1, 3.0, 1.0);
		optionsPanelA.add(kanaFontSizeLabel, constraints);
		
		// Slider to change kana font size.
		this.mKanaFontSizeSlider = new JSlider();
		this.mKanaFontSizeSlider.setMinimum(1);
		this.mKanaFontSizeSlider.setMaximum((int)OptionsContainer.maximumKanaFontSize);
		this.mKanaFontSizeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event)
			{
				FontContainer fontContainer = (FontContainer)getMainFrame().getContainerManager().getContainer(FontContainer.class);
				fontContainer.setKanaFontSize(FontOptionsPanel.this.mKanaFontSizeSlider.getValue());
				FontOptionsPanel.this.mMainFontExampleLabel.setFont(fontContainer.getKanaFont().deriveFont(fontContainer.getKanaFontSize()));
			}
		});
		this.mKanaFontSizeSlider.setValue((int)fontContainer.getKanaFontSize());
		this.setConstraints(constraints, 3, 3, 3, 1, 3.0, 1.0);
		optionsPanelA.add(this.mKanaFontSizeSlider, constraints);
		
		
		
		// Start with part B (Kanji font stuff).
		JPanel optionsPanelB = new JPanel();
		optionsPanelB.setLayout(new GridBagLayout());
		
		// Label with some info text for Kanji font.
		JLabel kanjiFontLabel = new JLabel();
		kanjiFontLabel.setText("Kanji font:");
		this.setConstraints(constraints, 0, 0, 3, 1, 3.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		optionsPanelB.add(kanjiFontLabel, constraints);
		
		// Font example is within a scroll pane to save some space.
		this.mKanjiFontExampleLabel = new JTextArea();
		this.mKanjiFontExampleLabel.setLineWrap(true);
		this.mKanjiFontExampleLabel.setText(this.createFontExampleText());
		this.mKanjiFontExampleLabel.setFont(fontContainer.getKanjiFont().deriveFont(fontContainer.getKanjiFontSize()));
		
		// Combo box to choose Kanji-font from.
		this.mKanjiFontBox = new JComboBox<String>();
		this.mKanjiFontBox.addItem("default");
		for(String fileName : this.justNamesOfPaths(mLoadableFonts))
		{
			this.mKanjiFontBox.addItem(fileName);
		}
		int currentKanjiIndex = 0;
		if(!fontContainer.getKanjiFontPath().equals(""))
		{
			// Get the proper font to be shown in the combo box if a font was already selected.
			for(int index = 0; index < this.mKanjiFontBox.getItemCount(); ++index)
			{
				if(fontContainer.getKanjiFontPath().contains(this.mKanjiFontBox.getItemAt(index)))
				{
					currentKanjiIndex = index;
				}
			}
		}
		this.mKanjiFontBox.setSelectedIndex(currentKanjiIndex);
		this.mKanjiFontBox.addActionListener(new KanjiFontBoxListener());
		this.setConstraints(constraints, 3, 0, 3, 1, 3.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		optionsPanelB.add(this.mKanjiFontBox, constraints);
		
		// Scroll pane with example text for Kanji.
		JScrollPane kanjiFontExampleScrollPane = new JScrollPane(this.mKanjiFontExampleLabel);
		kanjiFontExampleScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setConstraints(constraints, 0, 1, 6, 2, 6.0, 2.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		optionsPanelB.add(kanjiFontExampleScrollPane, constraints);
		
		// Label with info: kanji font size.
		JLabel kanjiFontSizeLabel = new JLabel();
		kanjiFontSizeLabel.setText("Kanji font size: ");
		this.setConstraints(constraints, 0, 3, 3, 1, 3.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		optionsPanelB.add(kanjiFontSizeLabel, constraints);
		
		// Slider to change kana font size.
		this.mKanjiFontSizeSlider = new JSlider();
		this.mKanjiFontSizeSlider.setMinimum(1);
		this.mKanjiFontSizeSlider.setMaximum((int)OptionsContainer.maximumKanaFontSize);
		this.mKanjiFontSizeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event)
			{
				FontContainer fontContainer = (FontContainer)getMainFrame().getContainerManager().getContainer(FontContainer.class);
				fontContainer.setKanjiFontSize(FontOptionsPanel.this.mKanjiFontSizeSlider.getValue());
				FontOptionsPanel.this.mKanjiFontExampleLabel.setFont(fontContainer.getKanjiFont().deriveFont(fontContainer.getKanjiFontSize()));
			}
		});
		this.mKanjiFontSizeSlider.setValue((int)fontContainer.getKanjiFontSize());
		this.setConstraints(constraints, 3, 3, 3, 1, 3.0, 1.0);
		optionsPanelB.add(this.mKanjiFontSizeSlider, constraints);
		
		
		
		// Start with part C (button save etc.).
		JPanel optionsPanelC = new JPanel();
		optionsPanelC.setLayout(new GridBagLayout());
		
		// Button to reload the current option set.
		JButton resetOptionsButton = new JButton();
		resetOptionsButton.setText("reset options");
		resetOptionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				// Reset container.
				getMainFrame().getContainerManager().addContainer(new FontContainer());
				
				// Update UI.
				getMainFrame().switchToPanel(FontOptionsPanel.this);
			}
		});
		this.setConstraints(constraints, 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		optionsPanelC.add(resetOptionsButton, constraints);
		
		// Button to reload the current option set.
		JButton reloadOptionsButton = new JButton();
		reloadOptionsButton.setText("reload options");
		reloadOptionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				// Reload container.
				getMainFrame().getContainerManager().loadContainerFromXml(FontContainer.class);
				
				// Update UI.
				getMainFrame().switchToPanel(FontOptionsPanel.this);
			}
		});
		this.setConstraints(constraints, 1, 0, 1, 1, 1.0, 1.0);
		optionsPanelC.add(reloadOptionsButton, constraints);
		
		// Button to save the current option set.
		JButton saveOptionsButton = new JButton();
		saveOptionsButton.setText("save options");
		saveOptionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				getMainFrame().getContainerManager().WriteContainerToXml(FontContainer.class);
			}
		});
		this.setConstraints(constraints, 2, 0, 2, 1, 2.0, 1.0);
		optionsPanelC.add(saveOptionsButton, constraints);
		
		
		
		// Order subpanels.
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new GridBagLayout());
		
		optionsPanelA.setBorder(new LineBorder(Color.BLACK));
		this.setConstraints(constraints, 0, 0, 3, 3, 3.0, 3.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		optionsPanel.add(optionsPanelA, constraints);
		
		optionsPanelB.setBorder(new LineBorder(Color.BLACK));
		this.setConstraints(constraints, 0, 3, 3, 3, 3.0, 3.0);
		optionsPanel.add(optionsPanelB, constraints);
		
		optionsPanelC.setBorder(new LineBorder(Color.BLACK));
		this.setConstraints(constraints, 0, 6, 1, 3, 1.0, 3.0);
		optionsPanel.add(optionsPanelC, constraints);
		
		this.getSwingPanel().setLayout(new java.awt.GridLayout());
		this.getSwingPanel().add(optionsPanel);
	}
	
	/**
	 * Gets the absolute path of every .ttf font file inside "folderPath".
	 * These are saved into "fontPaths".
	 * @param folderPath Path of the folder to scan for font files.
	 * @param fontPaths The path(s) of font files found in the given folder will be stored here.
	 */
	private void CheckFolderForFonts(String folderPath, ArrayList<String> fontPaths)
	{
		File currentFolder = new File(folderPath);
		
		// If the folder to scan does not exist, there is no need to go on.
		if(currentFolder == null || currentFolder.listFiles() == null)
		{
			return;
		}
		
		// Check current directory for font files.
		for(File file : currentFolder.listFiles())
		{
			if(file.isFile())
			{
				if(file.getName().endsWith(".ttf") || file.getName().endsWith(".ttc"))
				{
					fontPaths.add(file.getAbsolutePath());
				}
			}
		}
		
		// Check sub-directories for fonts.
		// (Code is a little redundant, yet I somehow prefer this splitting.)
		for(File subFolder : currentFolder.listFiles())
		{
			if(subFolder.isDirectory())
			{
				this.CheckFolderForFonts(subFolder.getPath(), fontPaths);
			}
		}
	}
	
	/**
	 * Creates a list of file names from a list of file paths.
	 * Used to get items into the combo boxes more properly.
	 * @param filePaths The file paths that shall be reduced to just names.
	 * @return An ArrayList containing just the names of the files given in filePaths.
	 */
	private ArrayList<String> justNamesOfPaths(ArrayList<String> filePaths)
	{
		ArrayList<String> justNames = new ArrayList<String>();
		
		for(String filePath : filePaths)
		{
			File file = new File(filePath);
			String name = file.getName();
			justNames.add(name);
		}
		
		return justNames;
	}
	
	/**
	 * Create the example text used to show hiragana/katakana font.
	 * @return The example text for Kana.
	 */
	private String createFontExampleText()
	{
		String example = "";
		
		for(String[] innerArray : LetterContainer.hiragana)
		{
			for(String hiragana : innerArray)
			{
				example += hiragana;
			}
			
			example += " ";
		}
		
		example += "\n";
		for(String[] innerArray : LetterContainer.katakana)
		{
			for(String katakana : innerArray)
			{
				example += katakana;
			}

			example += " ";
		}
		
		return example;
	}
	
	/**
	 * When the selected Kana font changes, update the example text label using this listener.
	 * @author Schlewinow
	 */
	private class MainFontBoxListener implements ActionListener
	{
		/**
		 * When the chosen font changes, load the font and update the example text of the Kana.
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			FontContainer fontContainer = (FontContainer)getMainFrame().getContainerManager().getContainer(FontContainer.class);	
			
			if(FontOptionsPanel.this.mMainFontBox.getSelectedIndex() == 0)
			{
				// Set current Kana font to default font of Java.
				fontContainer.setKanaFontPath("");
				FontOptionsPanel.this.mMainFontExampleLabel.setFont(fontContainer.getKanaFont().deriveFont(fontContainer.getKanaFontSize()));
			}
			else
			{
				// Some font from the folder "fonts" was chosen.
				int fontIndex = FontOptionsPanel.this.mMainFontBox.getSelectedIndex() - 1;
				String chosenFont = FontOptionsPanel.this.mLoadableFonts.get(fontIndex);
				fontContainer.setKanaFontPath(chosenFont);
				FontOptionsPanel.this.mMainFontExampleLabel.setFont(fontContainer.getKanaFont().deriveFont(fontContainer.getKanaFontSize()));
			}			
		}	
	}
	
	/**
	 * When the selected Kanji font changes, update the example text label using this listener.
	 * @author Schlewinow
	 */
	private class KanjiFontBoxListener implements ActionListener
	{
		/**
		 * When the chosen font changes, load the font and update the example text of the Kanji.
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			FontContainer fontContainer = (FontContainer)getMainFrame().getContainerManager().getContainer(FontContainer.class);	
			
			if(FontOptionsPanel.this.mKanjiFontBox.getSelectedIndex() == 0)
			{
				// Set current Kana font to default font of Java.
				fontContainer.setKanjiFontPath("");
				FontOptionsPanel.this.mKanjiFontExampleLabel.setFont(fontContainer.getKanjiFont().deriveFont(fontContainer.getKanjiFontSize()));
			}
			else
			{
				// Some font from the folder "fonts" was chosen.
				int fontIndex = FontOptionsPanel.this.mKanjiFontBox.getSelectedIndex() - 1;
				String chosenFont = FontOptionsPanel.this.mLoadableFonts.get(fontIndex);
				fontContainer.setKanjiFontPath(chosenFont);
				FontOptionsPanel.this.mKanjiFontExampleLabel.setFont(fontContainer.getKanjiFont().deriveFont(fontContainer.getKanjiFontSize()));
			}			
		}	
	}
}
