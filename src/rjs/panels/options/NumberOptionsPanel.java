package rjs.panels.options;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import rjs.container.NumberContainer;
import rjs.frame.IMainFrame;
import rjs.panels.AbstractPanel;

/**
 * Panel that allows the user to manipalte the options belonging to the random number learning session.
 * @author Schlewinow
 */
public class NumberOptionsPanel extends AbstractPanel
{
	/**
	 * Shows the current maximum number of digits before comma.
	 */
	JTextArea mBeforeCommaArea = null;
	
	/**
	 * Shows the current maximum number of digits after comma.
	 */
	JTextArea mAfterCommaArea = null;
	
	/**
	 * Checkbox to edit the state whether negative numbers are allowed or not.
	 */
	JCheckBox mAllowMinusBox = null;
	
	/**
	 * Default constructor. Empty.
	 */
	public NumberOptionsPanel()
	{
	}
	
	/**
	 * Initialize the UI necessary to edit the random number learning session options.
	 * @param mainFrame Reference to the local main frame.
	 * Necessary to successfully initialize this panel.
	 */
	public void initialize(IMainFrame mainFrame)
	{
		super.initialize(mainFrame);
		
		NumberContainer numberContainer = (NumberContainer)this.getMainFrame().getContainerManager().getContainer(NumberContainer.class);
		
		this.getSwingPanel().setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		this.setConstraints(constraints, 0, 0, 1, 1, 1.0f, 1.0f, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		
		// Label to describe the value (maximum number of digits before comma).
		JLabel beforeCommaLabel = new JLabel();
		beforeCommaLabel.setText("digits before comma");
		this.setConstraints(constraints, 0, 0, 2, 1, 2.0, 1.0);
		this.getSwingPanel().add(beforeCommaLabel, constraints);
		
		// Shows maximum number of digits before comma.
		this.mBeforeCommaArea = new JTextArea();
		this.mBeforeCommaArea.setEditable(false);
		this.mBeforeCommaArea.setText("" + numberContainer.getDigitsBeforeComma());
		this.setConstraints(constraints, 2, 0, 1, 1, 1.0, 1.0);
		this.getSwingPanel().add(this.mBeforeCommaArea, constraints);
		
		// Button to increase the number of digits before comma.
		JButton beforeCommaUpButton = new JButton();
		beforeCommaUpButton.setText("+");
		beforeCommaUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				NumberContainer numberContainer = (NumberContainer)getMainFrame().getContainerManager().getContainer(NumberContainer.class);
				numberContainer.setDigitsBeforeComma(numberContainer.getDigitsBeforeComma() + 1);
				mBeforeCommaArea.setText("" + numberContainer.getDigitsBeforeComma());
			}
		});
		this.setConstraints(constraints, 3, 0, 1, 1);
		this.getSwingPanel().add(beforeCommaUpButton, constraints);
	
		// Button to decrease the number of digits before comma.
		JButton beforeCommaDownButton = new JButton();
		beforeCommaDownButton.setText("-");
		beforeCommaDownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				NumberContainer numberContainer = (NumberContainer)getMainFrame().getContainerManager().getContainer(NumberContainer.class);
				numberContainer.setDigitsBeforeComma(numberContainer.getDigitsBeforeComma() - 1);
				mBeforeCommaArea.setText("" + numberContainer.getDigitsBeforeComma());
			}
		});
		this.setConstraints(constraints, 4, 0, 1, 1);
		this.getSwingPanel().add(beforeCommaDownButton, constraints);
	
		// Label to describe the value (maximum number of digits before comma).
		JLabel afterCommaLabel = new JLabel();
		afterCommaLabel.setText("digits after comma");
		this.setConstraints(constraints, 0, 1, 2, 1, 2.0, 1.0);
		this.getSwingPanel().add(afterCommaLabel, constraints);
		
		// Shows maximum number of digits before comma.
		this.mAfterCommaArea = new JTextArea();
		this.mAfterCommaArea.setEditable(false);
		this.mAfterCommaArea.setText("" + numberContainer.getDigitsAfterComma());
		this.setConstraints(constraints, 2, 1, 1, 1, 1.0, 1.0);
		this.getSwingPanel().add(this.mAfterCommaArea, constraints);
		
		// Button to increase the number of digits after comma.
		JButton afterCommaUpButton = new JButton();
		afterCommaUpButton.setText("+");
		afterCommaUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				NumberContainer numberContainer = (NumberContainer)getMainFrame().getContainerManager().getContainer(NumberContainer.class);
				numberContainer.setDigitsAfterComma(numberContainer.getDigitsAfterComma() + 1);
				mAfterCommaArea.setText("" + numberContainer.getDigitsAfterComma());
			}
		});
		this.setConstraints(constraints, 3, 1, 1, 1);
		this.getSwingPanel().add(afterCommaUpButton, constraints);
		// TODO: enable once implemented
		afterCommaUpButton.setEnabled(false);
	
		// Button to decrease the number of digits after comma.
		JButton afterCommaDownButton = new JButton();
		afterCommaDownButton.setText("-");
		afterCommaDownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				NumberContainer numberContainer = (NumberContainer)getMainFrame().getContainerManager().getContainer(NumberContainer.class);
				numberContainer.setDigitsAfterComma(numberContainer.getDigitsAfterComma() - 1);
				mAfterCommaArea.setText("" + numberContainer.getDigitsAfterComma());
			}
		});
		this.setConstraints(constraints, 4, 1, 1, 1);
		this.getSwingPanel().add(afterCommaDownButton, constraints);
		// TODO: enable once implemented
		afterCommaDownButton.setEnabled(false);

		// Checkbox to disable/enable negative numbers.
		this.mAllowMinusBox = new JCheckBox();
		this.mAllowMinusBox.setText("allow negative numbers");
		this.mAllowMinusBox.setSelected(numberContainer.getMinusAllowed());
		this.mAllowMinusBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				NumberContainer numberContainer = (NumberContainer)getMainFrame().getContainerManager().getContainer(NumberContainer.class);
				numberContainer.setMinusAllowed(mAllowMinusBox.isSelected());
			}
		});
		this.setConstraints(constraints, 0, 2, 1, 1);
		this.getSwingPanel().add(this.mAllowMinusBox, constraints);
		
		// Button to save the settings into the config file.
		JButton saveButton = new JButton();
		saveButton.setText("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				getMainFrame().getContainerManager().WriteContainerToXml(NumberContainer.class);
			}
		});
		this.setConstraints(constraints, 3, 3, 2, 1, 2.0, 1.0);
		this.getSwingPanel().add(saveButton, constraints);
		
		// Button to reload the settings from the config file.
		JButton reloadButton = new JButton();
		reloadButton.setText("Reload");
		reloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				getMainFrame().getContainerManager().loadContainerFromXml(NumberContainer.class);
				
				NumberContainer numberContainer = (NumberContainer)getMainFrame().getContainerManager().getContainer(NumberContainer.class);
				mBeforeCommaArea.setText("" + numberContainer.getDigitsBeforeComma());
				mAfterCommaArea.setText("" + numberContainer.getDigitsAfterComma());
				mAllowMinusBox.setSelected(numberContainer.getMinusAllowed());
			}
		});
		this.setConstraints(constraints, 2, 3, 1, 1, 1.0, 1.0);
		this.getSwingPanel().add(reloadButton, constraints);
	}
}
