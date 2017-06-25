package rjs.panels.session;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import rjs.container.FontContainer;
import rjs.container.OptionsContainer;
import rjs.container.WordListContainer;
import rjs.frame.IMainFrame;
import rjs.panels.AbstractRandomPanel;
import rjs.wordlist.WordListItem;

/**
 * Panel used for random word learning session.
 * @author Schlewinow
 */
public class RandomWordPanel extends AbstractRandomPanel
{
	/**
	 * The index of the word currently shown.
	 * Must be valid within the current word list.
	 */
	private int mCurrentWordIndex = 0;
	
	/**
	 * Label presenting the translation of the current word.
	 */
	private JLabel mTranslationLabel = null;
	
	/**
	 * Label presenting the romaji writing of the current word.
	 */
	private JLabel mRomajiLabel = null;
	
	/**
	 * Label presenting the hiragana writing of the current word.
	 */
	private JLabel mHiraganaLabel = null;
	
	/**
	 * Label presenting the katakana writing of the current word.
	 */
	private JLabel mKatakanaLabel = null;
	
	/**
	 * Label presenting the kanji of the current word.
	 */
	private JLabel mKanjiLabel = null;
	
	/**
	 * Check box to show/hide the translation.
	 */
	private JCheckBox mTranslationBox = null;
	
	/**
	 * Check box to show/hide the romaji writing.
	 */
	private JCheckBox mRomajiBox = null;
	
	/**
	 * Check box to show/hide the kana writing(s).
	 */
	private JCheckBox mKanaBox = null;
	
	/**
	 * Check box to show/hide the kanji writing.
	 */
	private JCheckBox mKanjiBox = null;
	
	/**
	 * Default constructor. Empty.
	 */
	public RandomWordPanel()
	{
	}
	
	/**
	 * Initialize the UI-elements of a random word learning session panel.
	 */
	public void initialize(IMainFrame mainFrame)
	{
		super.initialize(mainFrame);
		
		OptionsContainer optionsContainer = (OptionsContainer)this.getMainFrame().getContainerManager().getContainer(OptionsContainer.class);
		FontContainer fontContainer = (FontContainer)this.getMainFrame().getContainerManager().getContainer(FontContainer.class);
		
		this.getSwingPanel().setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		this.setConstraints(constraints, 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		
		// Label to show translation of the current word.
		this.setConstraints(constraints, 0, 0, 2, 2, 2.0, 2.0);
		this.mTranslationLabel = this.createLabel("translation", constraints, fontContainer);
		
		// Label to show romaji of the current word.
		this.setConstraints(constraints, 0, 2, 2, 2, 2.0, 2.0);
		this.mRomajiLabel = this.createLabel("romaji", constraints, fontContainer);
		
		// Label to show hiragana of the current word.
		this.setConstraints(constraints, 2, 0, 2, 2, 2.0, 2.0);
		this.mHiraganaLabel = this.createLabel("hiragana", constraints, fontContainer);
		
		// Label to show katakana of the current word.
		this.setConstraints(constraints, 2, 2, 2, 2, 2.0, 2.0);
		this.mKatakanaLabel = this.createLabel("katakana", constraints, fontContainer);
		
		// Label to show kanji of the current word.
		this.setConstraints(constraints, 2, 4, 2, 2, 2.0, 2.0);
		this.mKanjiLabel = this.createLabel("kanji", constraints, fontContainer);
		
		// Check whether learning is linear or random. Different buttons are needed in each case.
		if(optionsContainer.getLinearLearning())
		{
			// Button to go to previous word in list.
			this.setConstraints(constraints, 0, 6, 2, 3, 2.0, 3.0);
			JButton prevWordButton =this.createButton("prev", constraints, null, null);
			prevWordButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0)
				{
					// Go to previous word in the list.
					int newIndex = RandomWordPanel.this.mCurrentWordIndex - 1;
					
					if(newIndex < 0)
					{
						WordListContainer wlc = (WordListContainer)getMainFrame().getContainerManager().getContainer(WordListContainer.class);
						newIndex = wlc.getWordList().getWordCount() - 1;
					}
					
					RandomWordPanel.this.updateShownWord(newIndex);
				}
			});
			
			// Button to go to next word in list.
			this.setConstraints(constraints, 2, 6, 2, 3, 2.0, 3.0);
			JButton nextWordButton = this.createButton("next", constraints, null, null);
			nextWordButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0)
				{
					// Go to next word in the list.
					int newIndex = RandomWordPanel.this.mCurrentWordIndex + 1;
					
					WordListContainer wlc = (WordListContainer)getMainFrame().getContainerManager().getContainer(WordListContainer.class);
					if(newIndex >= wlc.getWordList().getWordCount())
					{
						newIndex = 0;
					}
					
					RandomWordPanel.this.updateShownWord(newIndex);
				}
			});
		}
		else
		{
			this.setConstraints(constraints, 0, 6, 4, 3, 4.0, 3.0);
			JButton randomButton = this.createButton("random", constraints, null, null);
			randomButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0)
				{
					// Go to random word in the list.
					WordListContainer wlc = (WordListContainer)getMainFrame().getContainerManager().getContainer(WordListContainer.class);
					int max = wlc.getWordList().getWordCount() - 1;
					int newIndex = mCurrentWordIndex;
					
					// Make sure a new word is picked.
					while(newIndex == mCurrentWordIndex)
					{
						newIndex = (int)Math.round((Math.random() * max));
					}
					
					updateShownWord(newIndex);
				}
			});
		}
		
		// Checkboxes to change visibility.
		this.setConstraints(constraints, 4, 6, 1, 1, 1.0, 1.0);
		this.mTranslationBox = this.createCheckBox("translation",  constraints, null, new UpdateVisibilityListener(), true);
		
		this.setConstraints(constraints, 4, 7, 1, 1, 1.0, 1.0);
		this.mRomajiBox = this.createCheckBox("romaji",  constraints, null, new UpdateVisibilityListener(), true);
		
		this.setConstraints(constraints, 5, 6, 1, 1, 1.0, 1.0);
		this.mKanaBox = this.createCheckBox("kana",  constraints, null, new UpdateVisibilityListener(), true);

		this.setConstraints(constraints, 5, 7, 1, 1, 1.0, 1.0);
		this.mKanjiBox = this.createCheckBox("kanji",  constraints, null, new UpdateVisibilityListener(), true);

		if(optionsContainer.getLinearLearning())
		{
			// If session is linear, show first word of list at start.
			this.updateShownWord(this.mCurrentWordIndex);
		}
	}
	
	/**
	 * Update the UI-elements to show the content of a given word list item.
	 * @param wordIndex The index of the word in the current word list to be shown by the panel.
	 */
	private void updateShownWord(int wordIndex)
	{
		WordListContainer wlc = (WordListContainer)this.getMainFrame().getContainerManager().getContainer(WordListContainer.class);
		
		// Safety check for proper index.
		if(wordIndex >= 0 && wordIndex < wlc.getWordList().getWordCount())
		{
			this.mCurrentWordIndex = wordIndex;

			// Update UI elements with proper values.
			this.updateVisibility();
		}
	}
	
	/**
	 * Update the visibility of the data of the current item of the word list.
	 * Only show data activated by the user.
	 */
	private void updateVisibility()
	{
		WordListContainer wlc = (WordListContainer)this.getMainFrame().getContainerManager().getContainer(WordListContainer.class);
		WordListItem item = wlc.getWordList().getWordAt(this.mCurrentWordIndex);
		
		String translation = this.mTranslationBox.isSelected() ? item.getTranslation() : " ";
		this.mTranslationLabel.setText(translation);
		
		String romaji = this.mRomajiBox.isSelected() ? item.getRomajiText() : " ";
		this.mRomajiLabel.setText(romaji);
		
		String hiragana = this.mKanaBox.isSelected() ? item.getHiraganaText() : " ";
		this.mHiraganaLabel.setText(hiragana);
		
		String katakana = this.mKanaBox.isSelected() ? item.getKatakanaText() : " ";
		this.mKatakanaLabel.setText(katakana);
		
		String kanji = this.mKanjiBox.isSelected() ? item.getKanjiText() : " ";
		this.mKanjiLabel.setText(kanji);
	}
	
	/**
	 * ActionListener to update the visible elements if a checkbox changes it's value.
	 * @author Schlewinow
	 */
	private class UpdateVisibilityListener implements ActionListener
	{
		/**
		 * Update the visibility of current word data.
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			RandomWordPanel.this.updateVisibility();
		}
	}
}
