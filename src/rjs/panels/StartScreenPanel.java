package rjs.panels;

import javax.swing.JLabel;

import rjs.frame.IMainFrame;

public class StartScreenPanel extends AbstractPanel
{
	// Default constructor.
	public StartScreenPanel()
	{
	}
	
	public void initialize(IMainFrame mainFrame)
	{
		super.initialize(mainFrame);
		
		JLabel welcomeLabel = new JLabel();
		welcomeLabel.setText("Welcome to Random J Shit!");
		this.getSwingPanel().add(welcomeLabel);
	}
}
