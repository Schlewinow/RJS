package rjs.panels.session;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import rjs.container.FontContainer;
import rjs.container.NumberContainer;
import rjs.container.dateandtime.DateAndTimeContainer;
import rjs.frame.IMainFrame;
import rjs.panels.AbstractRandomPanel;

/**
 * 
 * @author Schlewinow
 */
public class RandomDateAndTimePanel extends AbstractRandomPanel
{
	private JLabel mTimeStantardLabel = null;
	
	private JLabel mTimeJapaneseLabel = null;
	
	/**
	 * Randomly generated hour.
	 */
	private int mCurrentHour = 0;
	
	/**
	 * Randomly generated minute.
	 */
	private int mCurrentMinute = 0;
	
	/**
	 * Is either empty, AM or PM.
	 */
	private String mAMPM = "";
	
	/**
	 * Is eiter empty, gosen or gogo.
	 */
	private String mJAMPM = "";
	
	/**
	 * Default constructor.
	 */
	public RandomDateAndTimePanel()
	{
	}
	
	/**
	 *  Initialize a random text learning panel.
	 */
	public void initialize(IMainFrame mainFrame)
	{
		super.initialize(mainFrame);
		
		FontContainer fontContainer = (FontContainer)mainFrame.getContainerManager().getContainer(FontContainer.class);
		
		this.getSwingPanel().setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		this.setConstraints(constraints, 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		
		// Label to show time.
		this.setConstraints(constraints, 0, 0, 1, 1, 1.0, 1.0);
		this.mTimeStantardLabel = this.createLabel("time", constraints, fontContainer);
		
		// Label to show japanese time.
		this.setConstraints(constraints, 1, 0, 1, 1, 1.0, 1.0);
		this.mTimeJapaneseLabel = this.createLabel("japanese time", constraints, fontContainer);
		
		// Button to generate random date and time.
		this.setConstraints(constraints, 0, 1, 2, 1, 2.0, 1.0);
		this.createButton("Random!", constraints, null, new RandomButtonActionListener());
	}
	
	/**
	 * Update values and visibility of date and time UI.
	 */
	private void updateUI()
	{
		DateAndTimeContainer dateAndTimeContainer = (DateAndTimeContainer)getMainFrame().getContainerManager().getContainer(DateAndTimeContainer.class);
		
		String japHour = DateAndTimeContainer.hourHiragana;
		String japMinute = DateAndTimeContainer.minuteHiragana[0];
		
		if(dateAndTimeContainer.getUseKanji())
		{
			japHour = DateAndTimeContainer.hourKanji;
			japMinute = DateAndTimeContainer.minuteKanji;
		}
		else if(this.mCurrentMinute%10 == 1 || this.mCurrentMinute%10 == 3 || this.mCurrentMinute%10 == 6 || this.mCurrentMinute%10 == 8 || this.mCurrentMinute%10 == 0)
		{
			// Cases like 1 = ippun (not ichihun).
			japMinute = DateAndTimeContainer.minuteHiragana[1];
		}
		
		// Second digit of minute.
		String hiraganaMinute = "";
		int secondMinuteDigit = (this.mCurrentMinute % 10) - 1;
		if(secondMinuteDigit < 0)
		{
			secondMinuteDigit = 9;
		}
		hiraganaMinute = DateAndTimeContainer.minutes[secondMinuteDigit];
		
		// First digit of minute.
		int firstMinuteDigit = (this.mCurrentMinute / 10) - 1;
		if(this.mCurrentMinute % 10 != 0 && this.mCurrentMinute > 10)
		{
			hiraganaMinute = NumberContainer.Tens[firstMinuteDigit][0] + hiraganaMinute;
		}
		else if (this.mCurrentMinute > 10)
		{
			// Always use 0 index, but 1 when hour is 4 (use jon, not shi).
			int numberIndex = firstMinuteDigit == 3 ? 1 : 0;
			hiraganaMinute = NumberContainer.Ones[firstMinuteDigit][numberIndex] + hiraganaMinute;
		}
		
		if(this.mCurrentMinute == 30)
		{
			// Possibility of "han" at thirty minutes.
			japMinute = japMinute + (this.mCurrentMinute == 30 ? (" / " + DateAndTimeContainer.minutes[10]) : "");
		}
		
		this.mTimeStantardLabel.setText((this.mCurrentHour < 10 ? "0" : "") + this.mCurrentHour + ":" + (this.mCurrentMinute < 10 ? "0" : "") + this.mCurrentMinute + " " + this.mAMPM);
		//this.mTimeJapaneseLabel.setText(this.mJAMPM + " " + DateAndTimeContainer.hours[this.mCurrentHour-1] + japHour + " " + hiraganaMinute + japMinute);
		this.mTimeJapaneseLabel.setText(this.mJAMPM + " " + dateAndTimeContainer.getHour(this.mCurrentHour).getKana() + " " + hiraganaMinute + japMinute);
	}
	
	/**
	 * Action listener to be used with the random date and time generator button.
	 * @author Schlewinow
	 */
	private class RandomButtonActionListener implements ActionListener
	{
		/**
		 * Generate random time and date and update UI.
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			boolean useAMPM = RandomDateAndTimePanel.this.rollDie(2) > 1;
			
			// Hour and AM/PM-sign depend on this state.
			if(useAMPM)
			{
				RandomDateAndTimePanel.this.mCurrentHour = RandomDateAndTimePanel.this.rollDie(12);
				RandomDateAndTimePanel.this.mAMPM = rollDie(2) > 1 ? "AM" : "PM";
				RandomDateAndTimePanel.this.mJAMPM = mAMPM.equals("AM") ? DateAndTimeContainer.morningHiragana : DateAndTimeContainer.eveningHiragana;
			}
			else
			{
				RandomDateAndTimePanel.this.mCurrentHour = RandomDateAndTimePanel.this.rollDie(24);
				RandomDateAndTimePanel.this.mAMPM = "";
				RandomDateAndTimePanel.this.mJAMPM = "";
			}
			
			RandomDateAndTimePanel.this.mCurrentMinute = RandomDateAndTimePanel.this.rollDie(1, 59);
			
			RandomDateAndTimePanel.this.updateUI();
		}	
	}
}
