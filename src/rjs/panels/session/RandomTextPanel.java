package rjs.panels.session;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import rjs.container.FontContainer;
import rjs.container.LetterContainer;
import rjs.container.OptionsContainer;
import rjs.container.ShortcutContainer;
import rjs.frame.IMainFrame;
import rjs.panels.AbstractRandomPanel;

/**
 * Creates and outputs random letter constructions to practise the kana.
 * @author Schlewinow
 */
public class RandomTextPanel extends AbstractRandomPanel
{	
	/**
	 * Label to output the randomly generated text.
	 */
	private JLabel mRandomOutputLabel = null;
	
	/**
	 * Button to switch to Romaji.
	 */
	private JRadioButton mLatinButton = null;
	
	/**
	 * Button to switch to Hiragana.
	 */
	private JRadioButton mHiraganaButton = null;
	
	/**
	 * Button to switch to Katakana.
	 */
	private JRadioButton mKatakataButton = null;
	
	/**
	 * The current random output as list of indices picked from the currently activated letters.
	 */
	private ArrayList<Integer> mCurrentRandomOutput = null;
	
	/**
	 * Letter type currently shown to the user.
	 * 0 = Latin, 1 = Hiragana, 2 = Katakana
	 */
	private int mCurrentLetterType = 0;
	
	/**
	 * Default constructor.
	 */
	public RandomTextPanel()
	{
	}
	
	/**
	 * Initialize a random text learning panel.
	 */
	public void initialize(IMainFrame mainFrame)
	{
		super.initialize(mainFrame);
		
		this.mCurrentLetterType = 0;
		this.mCurrentRandomOutput = new ArrayList<Integer>();
		
		FontContainer fontContainer = (FontContainer)this.getMainFrame().getContainerManager().getContainer(FontContainer.class);
		
		this.getSwingPanel().setLayout(new GridBagLayout());
		this.getSwingPanel().addKeyListener(new GenerateRandomKeyListener());
		
		GridBagConstraints constraints = new GridBagConstraints();
		this.setConstraints(constraints, 0, 0, 1, 1, 1.0f, 1.0f, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		
		// Button to generate random text.
		this.setConstraints(constraints, 0, 2, 2, 1, 1.0f, 0.5f);
		JButton generateRandomButton = this.createButton("Generate random", constraints, null, null);
		generateRandomButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				OptionsContainer optionsContainer = (OptionsContainer)getMainFrame().getContainerManager().getContainer(OptionsContainer.class);
				createRandomText(optionsContainer.getMinimumRandomTextSize(), optionsContainer.getMaximumRandomTextSize());
			}
		});
		generateRandomButton.addKeyListener(new GenerateRandomKeyListener());
		
		// Label to output generated text.
		this.setConstraints(constraints, 1, 0, 3, 2, 0.0f, 1.0f);
		this.mRandomOutputLabel = this.createLabel("Random!", constraints, fontContainer);
		
		// Radio-buttons to switch between letter types.
		ButtonGroup letterTypeButtons = new ButtonGroup();
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(0, 1));
		
		this.mLatinButton = this.createLetterRadioButton("Latin", 0);
		letterTypeButtons.add(this.mLatinButton);
		radioPanel.add(this.mLatinButton);

		this.mHiraganaButton = this.createLetterRadioButton("Hiragana", 1);
		letterTypeButtons.add(this.mHiraganaButton);
		radioPanel.add(this.mHiraganaButton);
		
		this.mKatakataButton = this.createLetterRadioButton("Katakana", 2);
		letterTypeButtons.add(this.mKatakataButton);
		radioPanel.add(this.mKatakataButton);
		
		this.setConstraints(constraints, 3, 2, 1, 1, 0.0, 0.0);
		this.getSwingPanel().add(radioPanel, constraints);
	}
	
	/**
	 * Create a JRadioButton used to switch between the different letter types.
	 * @param text Caption of the radio button to be created.
	 * @param letterType Letter type to switch to using the radio button.
	 * @return Reference to the newly created JRadioButton.
	 */
	private JRadioButton createLetterRadioButton(String text, int letterType)
	{
		JRadioButton rb = new JRadioButton();
		rb.setText(text);
		rb.setSelected(this.mCurrentLetterType == letterType);
		rb.setActionCommand("" + letterType);
		rb.addActionListener(new LetterRadioButtonListener());
		rb.addKeyListener(new GenerateRandomKeyListener());
		
		return rb;
	}
	
	/**
	 * Listener used by button to create new random text.
	 * @author Schlewinow
	 */
	private class GenerateRandomKeyListener implements KeyListener
	{
		/**
		 * Apply shortcuts for various UI-elements.
		 */
		public void keyPressed(KeyEvent event)
		{
			ShortcutContainer shortcuts = (ShortcutContainer)getMainFrame().getContainerManager().getContainer(ShortcutContainer.class);
			
			if(event.getKeyCode() == shortcuts.getShortcut(RandomTextPanel.class, "randomButton"))
			{
				OptionsContainer optionsContainer = (OptionsContainer)getMainFrame().getContainerManager().getContainer(OptionsContainer.class);
				RandomTextPanel.this.createRandomText(optionsContainer.getMinimumRandomTextSize(), optionsContainer.getMaximumRandomTextSize());
			}
			if(event.getKeyCode() == shortcuts.getShortcut(RandomTextPanel.class, "latinRadio"))
			{
				RandomTextPanel.this.mLatinButton.setSelected(true);
				RandomTextPanel.this.mCurrentLetterType = 0;
				RandomTextPanel.this.updateRandomOutput();
			}
			if(event.getKeyCode() == shortcuts.getShortcut(RandomTextPanel.class, "hiraganaRadio"))
			{
				RandomTextPanel.this.mHiraganaButton.setSelected(true);
				RandomTextPanel.this.mCurrentLetterType = 1;
				RandomTextPanel.this.updateRandomOutput();
			}
			if(event.getKeyCode() == shortcuts.getShortcut(RandomTextPanel.class, "katakanaRadio"))
			{
				RandomTextPanel.this.mKatakataButton.setSelected(true);
				RandomTextPanel.this.mCurrentLetterType = 2;
				RandomTextPanel.this.updateRandomOutput();
			}
		}

		/**
		 * Not implemented.
		 */
		public void keyReleased(KeyEvent arg0){}

		/**
		 * Not implemented.
		 */
		public void keyTyped(KeyEvent arg0){}
	}
	
	/**
	 * Listener for radio buttons to show different letter types.
	 * @author Schlewinow
	 */
	private class LetterRadioButtonListener implements ActionListener
	{
		/**
		 * If a radio button is clicked, switch the visible letter type according to the radio button.
		 */
		public void actionPerformed(ActionEvent event)
		{
			RandomTextPanel.this.mCurrentLetterType = Integer.parseInt(event.getActionCommand());
			RandomTextPanel.this.updateRandomOutput();
		}
	}
	
	/**
	 * Create a random text.
	 * @param minLength The minimum letter count of the created text.
	 * @param maxLength The maximum letter count of the created text.
	 */
	private void createRandomText(int minLength, int maxLength)
	{
		// Bounds to always have correct minimum and maximum length.
		if(minLength < 1)
		{
			minLength = 1;
		}
		if(maxLength > 10)
		{
			maxLength = 10;
		}
		if(minLength > maxLength)
		{
			int tmp = minLength;
			minLength = maxLength;
			maxLength = tmp;
		}
		
		LetterContainer letterContainer = (LetterContainer)this.getMainFrame().getContainerManager().getContainer(LetterContainer.class);
		ArrayList<String> letters = new ArrayList<String>();
		
		for(int index = 0; index < LetterContainer.latin.length; ++index)
		{
			letters.addAll(this.createListFromLetterType(LetterContainer.latin[index], 
					letterContainer.isUsingLetters(index)));
		}
		
		int randomLength = RandomTextPanel.this.rollDie(minLength, maxLength);
		
		this.mCurrentRandomOutput.clear();
		
		// Get random indices of letters.
		for(int i = 0; i < randomLength; ++i)
		{
			int randomIndex = RandomTextPanel.this.rollDie(0, letters.size()-1);
			this.mCurrentRandomOutput.add(randomIndex);
		}
		
		this.updateRandomOutput();
	}
	
	/**
	 * Update the last created random output to the user. Use the proper letter type.
	 */
	private void updateRandomOutput()
	{
		LetterContainer letterContainer = (LetterContainer)this.getMainFrame().getContainerManager().getContainer(LetterContainer.class);
		ArrayList<String> letters = new ArrayList<String>();
		
		if(this.mCurrentLetterType == 0)
		{
			for(int index = 0; index < LetterContainer.latin.length; ++index)
			{
				letters.addAll(this.createListFromLetterType(LetterContainer.latin[index], 
						letterContainer.isUsingLetters(index)));
			}
		}
		if(this.mCurrentLetterType == 1)
		{
			for(int index = 0; index < LetterContainer.hiragana.length; ++index)
			{
				letters.addAll(this.createListFromLetterType(LetterContainer.hiragana[index], 
						letterContainer.isUsingLetters(index)));
			}
		}
		if(this.mCurrentLetterType == 2)
		{
			for(int index = 0; index < LetterContainer.katakana.length; ++index)
			{
				letters.addAll(this.createListFromLetterType(LetterContainer.katakana[index], 
						letterContainer.isUsingLetters(index)));
			}
		}
		
		// Create string from random indices in current letter type.
		String output = "";
		if(letters.size() == 0)
		{
			output = "No letters selected!";
		}
		else
		{
			for(int index = 0; index < this.mCurrentRandomOutput.size(); ++index)
			{
				output += letters.get(this.mCurrentRandomOutput.get(index)) + " ";
			}
		}
		
		this.mRandomOutputLabel.setText(output);
	}
	
	/**
	 * Small helper method to avoid redundant code.
	 * @param letterType the type of letters to use (part of list of letters in latin, hiragana or katakana).
	 * @param applied Flag declares whether this type of letter is currently in use or not.
	 * @return An ArrayList containing the letters currently in use.
	 */
	private ArrayList<String> createListFromLetterType(String[] letterType, boolean applied)
	{
		ArrayList<String> letters = new ArrayList<String>();
		if(applied)
		{
			for(String letter : letterType)
			{
				letters.add(letter);
			}
		}
		return letters;
	}
}
