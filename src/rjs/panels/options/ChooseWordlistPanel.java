package rjs.panels.options;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;

import rjs.container.WordListContainer;
import rjs.frame.IMainFrame;
import rjs.panels.AbstractPanel;
import rjs.wordlist.WordListItem;

public class ChooseWordlistPanel extends AbstractPanel
{
	// Shows the currently loaded word list file path.
	private JTextArea mWordListArea = null;
	
	// Contains the items of the currently loaded word list to get an overview.
	private JScrollPane mWordPane = null;
	
	// Default constructor.
	public ChooseWordlistPanel()
	{
	}

	// Initialize the panel to choose a word list.
	public void initialize(IMainFrame mainFrame)
	{
		super.initialize(mainFrame);
		
		WordListContainer wordlistContainer = (WordListContainer)this.getMainFrame().getContainerManager().getContainer(WordListContainer.class);
		
		this.getSwingPanel().setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		// Label showing that this is the word list path.
		JLabel currentPathLabel = new JLabel();
		currentPathLabel.setText("Current word list:");
		this.setConstraints(constraints, 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		this.getSwingPanel().add(currentPathLabel, constraints);
		
		// Read-only area showing the current word list path.
		this.mWordListArea = new JTextArea();
		this.mWordListArea.setText(wordlistContainer.getWordListPath());
		this.mWordListArea.setEditable(false);
		this.setConstraints(constraints, 1, 0, 2, 1, 2.0, 1.0);
		this.getSwingPanel().add(this.mWordListArea, constraints);
		
		// Button to open a dialog to choose the word list.
		JButton loadListButton = new JButton();
		loadListButton.setText("Choose word list");
		loadListButton.addActionListener(new ChooseWordListListener());
		this.setConstraints(constraints, 3, 0, 1, 1, 1.0, 1.0);
		this.getSwingPanel().add(loadListButton, constraints);
		
		// Contains the items of the word list.
		this.mWordPane = new JScrollPane();
		this.mWordPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.mWordPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setConstraints(constraints, 0, 1, 4, 8, 4.0, 8.0);
		this.getSwingPanel().add(this.mWordPane, constraints);
		
		// Make sure word pane contains items in case a word list is already loaded.
		this.updateWordPane();
	}
	
	// Update the scroll pane showing the current word list.
	private void updateWordPane()
	{
		WordListContainer wordlistContainer = (WordListContainer)getMainFrame().getContainerManager().getContainer(WordListContainer.class);
		JPanel completeListPanel = new JPanel();
		completeListPanel.setLayout(new GridLayout(0, 1));
		
		for(int index = 0; index < wordlistContainer.getWordList().getWordCount(); ++index)
		{
			WordListItem item = wordlistContainer.getWordList().getWordAt(index);
			
			JPanel itemPanel = new JPanel();
			itemPanel.setLayout(new GridBagLayout());
			itemPanel.setBorder(new LineBorder(Color.black));
			
			GridBagConstraints constraints = new GridBagConstraints();
			setConstraints(constraints, 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
			
			// Translation.
			JLabel translationLabel = new JLabel();
			translationLabel.setText(item.getTranslation());
			setConstraints(constraints, 0, 0, 1, 1);
			itemPanel.add(translationLabel, constraints);
			
			// Romaji.
			JLabel romajiLabel = new JLabel();
			romajiLabel.setText(item.getRomajiText());
			setConstraints(constraints, 0, 1, 1, 1);
			itemPanel.add(romajiLabel, constraints);
			
			// Hiragana.
			JLabel hiraganaLabel = new JLabel();
			hiraganaLabel.setText(item.getHiraganaText());
			setConstraints(constraints, 1, 0, 1, 1);
			itemPanel.add(hiraganaLabel, constraints);
			
			// Katakana.
			JLabel katakanaLabel = new JLabel();
			katakanaLabel.setText(item.getKatakanaText());
			setConstraints(constraints, 1, 1, 1, 1);
			itemPanel.add(katakanaLabel, constraints);
			
			// Add panel of item to panel holding every item.
			completeListPanel.add(itemPanel);
		}
		
		// Create new scroll pane (seems to be necessary to update the input of a scrollpane...).
		JScrollPane wordPane = new JScrollPane(completeListPanel);
		this.getSwingPanel().remove(this.mWordPane);
		this.mWordPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.mWordPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.mWordPane = wordPane;
		GridBagConstraints con = new GridBagConstraints();
		this.setConstraints(con, 0, 1, 4, 8, 4.0, 8.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		this.getSwingPanel().add(ChooseWordlistPanel.this.mWordPane, con);
		
		// Update complete panel to make sure the UI is updated properly.
		this.getSwingPanel().setVisible(true);
	}
	
	// Used when clicking the button to choose a word list.
	private class ChooseWordListListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			// Open dialog to choose file from.
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new WordListFilter());
			String currentAppPath = Paths.get("").toAbsolutePath().toString().replace("\\", "/");
			fileChooser.setCurrentDirectory(new File(currentAppPath));
			int dialogReturn = fileChooser.showOpenDialog((JFrame)getMainFrame());
			
			if(dialogReturn == JFileChooser.APPROVE_OPTION)
			{
				String filepath = fileChooser.getSelectedFile().getAbsolutePath();
				WordListContainer wordlistContainer = (WordListContainer)getMainFrame().getContainerManager().getContainer(WordListContainer.class);
				wordlistContainer.setWordListPath(filepath);
				
				// Update UI.
				ChooseWordlistPanel.this.mWordListArea.setText(wordlistContainer.getWordListPath());
				ChooseWordlistPanel.this.updateWordPane();
				
				// Once a word list was loaded, save the path permanently.
				ChooseWordlistPanel.this.getMainFrame().getContainerManager().WriteContainerToXml(WordListContainer.class);
			}
		}
	}
	
	// Filter used when picking word list.
	private class WordListFilter extends FileFilter
	{
		// Whether a file is shown by the filter or not.
		public boolean accept(File pathname)
		{
			return pathname.getName().endsWith(".xml") || pathname.isDirectory();
		}

		// Getter filter description.
		public String getDescription()
		{
			return "Wordlists (.XML-files)";
		}
	}
}
