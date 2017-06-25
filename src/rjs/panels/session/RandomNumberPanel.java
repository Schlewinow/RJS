package rjs.panels.session;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import rjs.container.FontContainer;
import rjs.container.NumberContainer;
import rjs.frame.IMainFrame;
import rjs.panels.AbstractRandomPanel;

public class RandomNumberPanel extends AbstractRandomPanel
{
	/**
	 * Show number in numbers.
	 */
	private JLabel mNumberLabel = null;
	
	/**
	 * Show number in hiragana.
	 */
	private JLabel mHiraganaLabel = null;
	
	/**
	 * (De)Activate label to show number.
	 */
	private JCheckBox mShowNumberBox = null;
	
	/**
	 * (De)Activate label to show hiragana.
	 */
	private JCheckBox mShowHiraganaBox = null;
	
	/**
	 * The current random number.
	 */
	private String mNumberString = " ";
	
	/**
	 * The current number in hiragana.
	 */
	private String mHiraganaString = " ";
	
	/**
	 * Seperates part before comma from part after comma.
	 */
	private final String mSeperatorString = ".";
	
	/**
	 * Indicates a minus within the number string.
	 */
	private final String mMinusString = "-";
	
	/**
	 *  Default constructor.
	 */
	public RandomNumberPanel()
	{
	}
	
	/**
	 *  Initialize a panel to create random numbers.
	 */
	public void initialize(IMainFrame mainFrame)
	{
		super.initialize(mainFrame);
		
		FontContainer fontContainer = (FontContainer)this.getMainFrame().getContainerManager().getContainer(FontContainer.class);
		
		this.getSwingPanel().setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		this.setConstraints(constraints, 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		
		// Label to show the generated number.
		this.setConstraints(constraints, 0, 0, 4, 1);
		this.mNumberLabel = this.createLabel(" ", constraints, fontContainer);
		
		// Label to show the generated hiragana.
		this.setConstraints(constraints, 0, 1, 4, 1);
		this.mHiraganaLabel = this.createLabel(" ", constraints, fontContainer);
		
		// Button to generate a random number.
		this.setConstraints(constraints, 0, 2, 3, 2, 3.0, 1.0);
		this.createButton("Generate number!", constraints, null, new GenerateNumberListener());
		
		// Checkbox to (de)activate the label showing the number.
		this.setConstraints(constraints, 3, 2, 1, 1, 1.0, 1.0);
		this.mShowNumberBox = this.createCheckBox("Show number", constraints, null, new UpdateLabelListener(), true);
		
		// Checkbox to (de)activate the label showing the number.
		this.setConstraints(constraints, 3, 3, 1, 1);
		this.mShowHiraganaBox = this.createCheckBox("Show hiragana", constraints, null, new UpdateLabelListener(), true);;
	}
	
	/**
	 * Update the output labels.
	 */
	private void UpdateLabels()
	{
		this.mNumberLabel.setText(this.mShowNumberBox.isSelected() ? this.mNumberString : " ");
		this.mHiraganaLabel.setText(this.mShowHiraganaBox.isSelected() ? this.mHiraganaString : " ");
	}
	
	/**
	 * Listener when a checkbox to toggle label visibility is clicked.
	 * @author Schlewinow
	 */
	private class UpdateLabelListener implements ActionListener
	{
		/**
		 * Update the label belonging to the checkbox. Toggle visibility (pseudo).
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			RandomNumberPanel.this.UpdateLabels();
		}
	}
	
	/**
	 * Used when a new number shall be generated.
	 * @author Schlewinow
	 */
	private class GenerateNumberListener implements ActionListener
	{
		/**
		 * Button click callback.
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			NumberContainer numCon = (NumberContainer)getMainFrame().getContainerManager().getContainer(NumberContainer.class);
			String numberString = "0";
			
			// Apply digits before comma.
			if(numCon.getDigitsBeforeComma() != 0)
			{
				numberString = "" + (int)(Math.pow(10, numCon.getDigitsBeforeComma()) * Math.random());
			}
			
			// Apply digits after comma.
			if(numCon.getDigitsAfterComma() != 0)
			{
				numberString += mSeperatorString + (int)(Math.pow(10, numCon.getDigitsAfterComma()) * Math.random());
			}
			
			// Apply negative numbers.
			if(numCon.getMinusAllowed())
			{
				numberString = Math.random() > 0.5 ? numberString : (mMinusString + numberString);
			}
			
			// Update internal variables and output.
			RandomNumberPanel.this.mNumberString = numberString;
			RandomNumberPanel.this.mHiraganaString = this.createHiraganaFromNumber(numberString);
			RandomNumberPanel.this.UpdateLabels();
		}
		
		/**
		 * Create a hiragana-string from a number-string.
		 * @param numberString The number to be converted to Hiragana as string.
		 * @return The given number as string in Hiragana.
		 */
		private String createHiraganaFromNumber(String numberString)
		{
			String hiraganaString = "";
			
			String beforeComma = numberString;
			if(numberString.contains(mSeperatorString))
			{
				beforeComma = numberString.split(mSeperatorString)[0];
			}
			
			// Check for minus.
			if(beforeComma.startsWith(mMinusString))
			{
				hiraganaString = NumberContainer.Minus;
				beforeComma = beforeComma.substring(mMinusString.length(), beforeComma.length());
			}
			
			// Special case: single zero.
			if(beforeComma.equals("0"))
			{
				hiraganaString = NumberContainer.Zero[RandomNumberPanel.this.rollDie(NumberContainer.Zero.length) - 1];
				return hiraganaString;
			}
			
			// Handle millions-digit.
			if(beforeComma.length () == 9)
			{
				hiraganaString += this.getNumberAsString(NumberContainer.HundredMilllions, beforeComma, NumberContainer.HundredMilllions[9]);
				beforeComma = beforeComma.substring(1, beforeComma.length());
			}
			
			// Handle millions-digit.
			if(beforeComma.length () == 8)
			{
				hiraganaString += this.getNumberAsString(NumberContainer.Thousands, beforeComma, "");
				beforeComma = beforeComma.substring(1, beforeComma.length());
			}
			
			// Handle millions-digit.
			if(beforeComma.length () == 7)
			{
				hiraganaString += this.getNumberAsString(NumberContainer.Hundreds, beforeComma, "");
				beforeComma = beforeComma.substring(1, beforeComma.length());
			}
			
			// Handle hundred-thousands-digit.
			if(beforeComma.length () == 6)
			{
				hiraganaString += this.getNumberAsString(NumberContainer.Tens, beforeComma, "");
				beforeComma = beforeComma.substring(1, beforeComma.length());
			}
			
			// Handle ten-thousands-digit.
			if(beforeComma.length () == 5)
			{
				hiraganaString += this.getNumberAsString(NumberContainer.TenThousands, beforeComma, NumberContainer.TenThousands[9]);
				beforeComma = beforeComma.substring(1, beforeComma.length());
			}
			
			// Handle thousands-digit.
			if(beforeComma.length () == 4)
			{
				hiraganaString += this.getNumberAsString(NumberContainer.Thousands, beforeComma, "");
				beforeComma = beforeComma.substring(1, beforeComma.length());
			}
			
			// Handle hundred-digit.
			if(beforeComma.length () == 3)
			{
				hiraganaString += this.getNumberAsString(NumberContainer.Hundreds, beforeComma, "");
				beforeComma = beforeComma.substring(1, beforeComma.length());
			}
			
			// Handle ten-digit.
			if(beforeComma.length() == 2)
			{
				hiraganaString += this.getNumberAsString(NumberContainer.Tens, beforeComma, "");
				beforeComma = beforeComma.substring(1, beforeComma.length());
			}
			
			// Handle last digit.
			if(beforeComma.length() == 1)
			{
				hiraganaString += this.getNumberAsString(NumberContainer.Ones, beforeComma, "");
			}
			
			return hiraganaString;
		}
		
		/**
		 * Transform a numerical digit to a hiragana string.
		 * @param numberStrings array of string with numbers in hiragana
		 * @param digitString string of which the leading digit is read
		 * @param zeroString string wich is returned if the digit is zero (no unique definition possible)
		 * @return Read digit as hiragana taken from the array of numberStrings.
		 */
		private String getNumberAsString(String[] numberStrings, String digitString, String zeroString)
		{
			if(!digitString.startsWith("0"))
			{
				String firstDigit = digitString.substring(0, 1);
				int number = Integer.parseInt(firstDigit);
				return numberStrings[number-1];
			}
			else
			{
				return zeroString;
			}
		}
		
		/**
		 * Transform a numerical digit to a hiragana string. Multiple options for a single digit allowed.
		 * @param numberStrings array of arrays of string with numbers in hiragana
		 * @param digitString string of which the leading digit is read
		 * @param zeroString string wich is returned if the digit is zero (no unique definition possible)
		 * @return Read digit as hiragana taken from the array of numberStrings.
		 */
		private String getNumberAsString(String[][] numberStrings, String digitString, String zeroString)
		{
			if(!digitString.startsWith("0"))
			{
				String firstDigit = digitString.substring(0, 1);				
				int number = Integer.parseInt(firstDigit);
				String[] hiraganaNumbers = numberStrings[number-1];
				return hiraganaNumbers[RandomNumberPanel.this.rollDie(hiraganaNumbers.length) - 1];
			}
			else
			{
				return zeroString;
			}
		}
	}
}
