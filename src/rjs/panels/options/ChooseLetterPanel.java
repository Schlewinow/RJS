package rjs.panels.options;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import rjs.container.LetterContainer;
import rjs.frame.IMainFrame;
import rjs.panels.AbstractPanel;

/**
 * Special panel to allow the user to choose the letters used by random text generation.
 * @author Schlewinow
 */
public class ChooseLetterPanel extends AbstractPanel
{
	/**
	 * Default constructor.
	 */
	public ChooseLetterPanel()
	{
	}
	
	/**
	 * Initialize the panel to choose the letters in use.
	 */
	public void initialize(IMainFrame mainFrame)
	{
		super.initialize(mainFrame);
		
		LetterContainer letterContainer = (LetterContainer)this.getMainFrame().getContainerManager().getContainer(LetterContainer.class);
		
		this.getSwingPanel().setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		this.setConstraints(constraints, 0, 0, 1, 1, 1.0f, 1.0f, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL);
		
		// Create the check boxes to (de)activate lines of letters.
		// a-ka-sa-ta-na-ha-ma-ya-ra-wa-n
		for(int index = 0; index < 11; ++index)
		{
			this.getSwingPanel().add(this.createLetterChooseCheckBox(LetterContainer.latin[index], 
					LetterContainer.hiragana[index], 
					LetterContainer.katakana[index], 
					letterContainer.isUsingLetters(index), 
					index), constraints);
			constraints.gridy++;
		}
		
		// ga-za-da-ba-pa
		constraints.gridx = 1;
		constraints.gridy = 1;
		for(int index = 11; index < 16; ++index)
		{
			this.getSwingPanel().add(this.createLetterChooseCheckBox(LetterContainer.latin[index], 
					LetterContainer.hiragana[index], 
					LetterContainer.katakana[index], 
					letterContainer.isUsingLetters(index), 
					index), constraints);
			constraints.gridy++;
			if(constraints.gridy == 4)
			{
				constraints.gridy++;
			}
		}
		
		// kya-sha-cha-nya-hya-mya-rya
		constraints.gridx = 2;
		constraints.gridy = 1;
		for(int index = 16; index < 23; ++index)
		{
			this.getSwingPanel().add(this.createLetterChooseCheckBox(LetterContainer.latin[index], 
					LetterContainer.hiragana[index], 
					LetterContainer.katakana[index], 
					letterContainer.isUsingLetters(index), 
					index), constraints);
			constraints.gridy++;
			if(constraints.gridy == 7)
			{
				constraints.gridy++;
			}
		}
		
		// gya-ja-bya-pya
		constraints.gridx = 3;
		constraints.gridy = 1;
		for(int index = 23; index < LetterContainer.hiragana.length; ++index)
		{
			this.getSwingPanel().add(this.createLetterChooseCheckBox(LetterContainer.latin[index], 
					LetterContainer.hiragana[index], 
					LetterContainer.katakana[index], 
					letterContainer.isUsingLetters(index), 
					index), constraints);
			constraints.gridy++;
			if(constraints.gridy == 3)
			{
				constraints.gridy += 2;
			}
		}
		
		// Button to instantly select every possible letter.
		JButton selectAllButton = new JButton();
		selectAllButton.setText("select all");
		selectAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				for(Component comp : ChooseLetterPanel.this.getSwingPanel().getComponents())
				{
					try
					{
						JCheckBox box = (JCheckBox)comp;
						box.setSelected(true);
					}
					catch(Exception ex)
					{
					}
				}
			}
		});
		constraints.gridx = 0;
		constraints.gridy = 11;
		this.getSwingPanel().add(selectAllButton, constraints);
		
		// Button to instantly select no letter at all.
		JButton selectNoneButton = new JButton();
		selectNoneButton.setText("select none");
		selectNoneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				for(Component comp : ChooseLetterPanel.this.getSwingPanel().getComponents())
				{
					try
					{
						JCheckBox box = (JCheckBox)comp;
						box.setSelected(false);
					}
					catch(Exception ex)
					{
					}
				}
			}
		});
		constraints.gridx = 1;
		constraints.gridy = 11;
		this.getSwingPanel().add(selectNoneButton, constraints);
		
		// Button to reload the letter settings from config file.
		JButton reloadSettingsButton = new JButton();
		reloadSettingsButton.setText("reload settings");
		reloadSettingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				// Reload container.
				getMainFrame().getContainerManager().loadContainerFromXml(LetterContainer.class);
				
				// Update UI.
				getMainFrame().switchToPanel(ChooseLetterPanel.this);
			}
		});
		this.setConstraints(constraints, 2, 11, 1, 1);
		this.getSwingPanel().add(reloadSettingsButton, constraints);
		
		// Button to save the current letter settings
		JButton saveOptionsButton = new JButton();
		saveOptionsButton.setText("save settings");
		saveOptionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				getMainFrame().getContainerManager().WriteContainerToXml(LetterContainer.class);
			}
		});
		constraints.gridx = 3;
		constraints.gridy = 11;
		this.getSwingPanel().add(saveOptionsButton, constraints);
	}
	
	/**
	 * Helper to create check boxes for choosing letters.
	 * @param lettersLatin The letters to choose from in latin (romaji).
	 * @param lettersHiragana The letters to choose from in hiragana.
	 * @param lettersKatakana The letters to choose from in katakana.
	 * @param inUse Whether the letter at index is currently used or not.
	 * @param letterIndex The index of the letter in the given letter lists. 
	 * @return A JCheckBox to edit whether the letter at the index will be used or not.
	 */
	private JCheckBox createLetterChooseCheckBox(String[] lettersLatin, 
			String[] lettersHiragana, 
			String[] lettersKatakana, 
			boolean inUse,
			int letterIndex)
	{
		JCheckBox checkBox = new JCheckBox();
		String checkText = "";
		for(String letter : lettersLatin)
		{
			checkText += letter + " ";
		}
		checkText += " - ";
		for(String letter : lettersHiragana)
		{
			checkText += letter + " ";
		}
		checkText += " - ";
		for(String letter : lettersKatakana)
		{
			checkText += letter + " ";
		}
		checkBox.setText(checkText);
		checkBox.setSelected(inUse);
		checkBox.addItemListener(new ChooseLetterCheckboxListener(letterIndex));
		
		return checkBox;
	}
	
	/**
	 * Action listener used with check-boxes to choose used letter lines.
	 * @author Schlewinow
	 */
	private class ChooseLetterCheckboxListener implements ItemListener
	{
		/**
		 * Index is stored to know which letter line to (de)activate.
		 */
		private int letterIndex;
		
		/**
		 * Constructor. Be sure an index is given.
		 * @param letterIndex The index of the letter that is (de)activated by the corresponding check box.
		 */
		public ChooseLetterCheckboxListener(int letterIndex)
		{
			this.letterIndex = letterIndex;
		}
		
		/**
		 * Implementation of the listener method. Switch the state of a single letter line.
		 * @param event Event data of the check box.
		 */
		public void itemStateChanged(ItemEvent event)
		{
			LetterContainer letterContainer = (LetterContainer)getMainFrame().getContainerManager().getContainer(LetterContainer.class);
			letterContainer.setUsingLetters(this.letterIndex, event.getStateChange() == ItemEvent.SELECTED);
		}		
	}
}
