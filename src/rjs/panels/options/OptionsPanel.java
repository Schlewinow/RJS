package rjs.panels.options;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import rjs.container.OptionsContainer;
import rjs.frame.IMainFrame;
import rjs.panels.AbstractPanel;

/**
 * Panel that allows the user to modify random session options.
 * @author Schlewinow
 */
public class OptionsPanel extends AbstractPanel
{
	/**
	 * Text field to enter minimum random text length.
	 */
	private JTextField mMinimumRandomTextField = null;
	
	/**
	 * Text field to enter maximum random text length.
	 */
	private JTextField mMaximumRandomTextField = null;
	
	/**
	 * JCheckBox to (de)activate linear word learning sessions.
	 */
	private JCheckBox mLinearLearningBox = null;
	
	/**
	 * Default constructor.
	 */
	public OptionsPanel()
	{
	}
	
	/**
	 * Initialize the options panel.
	 */
	public void initialize(IMainFrame mainFrame)
	{
		super.initialize(mainFrame);
		
		OptionsContainer optionsContainer = (OptionsContainer)this.getMainFrame().getContainerManager().getContainer(OptionsContainer.class);
		
		this.getSwingPanel().setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		// Label describing first text area.
		JLabel minimumRandomLabel = new JLabel();
		minimumRandomLabel.setText("minimum count of letters of random output:");
		this.setConstraints(constraints, 0, 0, 3, 1, 3.0f, 1.0f, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
		this.getSwingPanel().add(minimumRandomLabel, constraints);
		
		// Label describing second text area.
		JLabel maximumRandomLabel = new JLabel();
		maximumRandomLabel.setText("minimum count of letters of random output:");
		this.setConstraints(constraints, 0, 1, 3, 1, 3.0f, 1.0f);
		this.getSwingPanel().add(maximumRandomLabel, constraints);
		
		// Textfield minimum random letter count.
		this.mMinimumRandomTextField = new JTextField();
		this.mMinimumRandomTextField.setText("" + optionsContainer.getMinimumRandomTextSize());
		this.mMinimumRandomTextField.setEditable(false);
		this.setConstraints(constraints, 3, 0, 1, 1, 1.0f, 1.0f);
		this.getSwingPanel().add(this.mMinimumRandomTextField, constraints);
		
		// Button to increase the maximum random text size.
		JButton minRandomUpButton = new JButton();
		minRandomUpButton.setText("+");
		minRandomUpButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent arg0)
			{
				OptionsContainer optionsContainer = (OptionsContainer)getMainFrame().getContainerManager().getContainer(OptionsContainer.class);
				optionsContainer.setMinimumRandomTextSize(optionsContainer.getMinimumRandomTextSize() + 1);
				mMinimumRandomTextField.setText("" + optionsContainer.getMinimumRandomTextSize());
				mMaximumRandomTextField.setText("" + optionsContainer.getMaximumRandomTextSize());
			}
		});
		this.setConstraints(constraints, 4, 0, 1, 1);
		this.getSwingPanel().add(minRandomUpButton, constraints);
		
		// Button to decrease the maximum random text size.
		JButton minRandomDownButton = new JButton();
		minRandomDownButton.setText("-");
		minRandomDownButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent arg0)
			{
				OptionsContainer optionsContainer = (OptionsContainer)getMainFrame().getContainerManager().getContainer(OptionsContainer.class);
				optionsContainer.setMinimumRandomTextSize(optionsContainer.getMinimumRandomTextSize() - 1);
				mMinimumRandomTextField.setText("" + optionsContainer.getMinimumRandomTextSize());
				mMaximumRandomTextField.setText("" + optionsContainer.getMaximumRandomTextSize());
			}
		});
		this.setConstraints(constraints, 5, 0, 1, 1);
		this.getSwingPanel().add(minRandomDownButton, constraints);
		
		// Textfield maximum random letter count.
		this.mMaximumRandomTextField = new JTextField();
		this.mMaximumRandomTextField.setText("" + optionsContainer.getMaximumRandomTextSize());
		this.mMaximumRandomTextField.setEditable(false);
		this.setConstraints(constraints, 3, 1, 1, 1, 1.0f, 1.0f);
		this.getSwingPanel().add(this.mMaximumRandomTextField, constraints);
		
		// Button to increase the maximum random text size.
		JButton maxRandomUpButton = new JButton();
		maxRandomUpButton.setText("+");
		maxRandomUpButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent arg0)
			{
				OptionsContainer optionsContainer = (OptionsContainer)getMainFrame().getContainerManager().getContainer(OptionsContainer.class);
				optionsContainer.setMaximumRandomTextSize(optionsContainer.getMaximumRandomTextSize() + 1);
				mMinimumRandomTextField.setText("" + optionsContainer.getMinimumRandomTextSize());
				mMaximumRandomTextField.setText("" + optionsContainer.getMaximumRandomTextSize());
			}
		});
		this.setConstraints(constraints, 4, 1, 1, 1);
		this.getSwingPanel().add(maxRandomUpButton, constraints);
		
		// Button to decrease the maximum random text size.
		JButton maxRandomDownButton = new JButton();
		maxRandomDownButton.setText("-");
		maxRandomDownButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent arg0)
			{
				OptionsContainer optionsContainer = (OptionsContainer)getMainFrame().getContainerManager().getContainer(OptionsContainer.class);
				optionsContainer.setMaximumRandomTextSize(optionsContainer.getMaximumRandomTextSize() - 1);
				mMinimumRandomTextField.setText("" + optionsContainer.getMinimumRandomTextSize());
				mMaximumRandomTextField.setText("" + optionsContainer.getMaximumRandomTextSize());
			}
		});
		this.setConstraints(constraints, 5, 1, 1, 1);
		this.getSwingPanel().add(maxRandomDownButton, constraints);
	
		// Check box to (de)activate linear word learning mode.
		this.mLinearLearningBox = new JCheckBox();
		this.mLinearLearningBox.setText("Linear word learning");
		this.mLinearLearningBox.setSelected(optionsContainer.getLinearLearning());
		this.mLinearLearningBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				OptionsContainer optionsContainer = (OptionsContainer)getMainFrame().getContainerManager().getContainer(OptionsContainer.class);
				optionsContainer.setLinearLearning(mLinearLearningBox.isSelected());
			}
		});
		this.setConstraints(constraints, 0, 2, 3, 1, 3.0, 1.0);
		this.getSwingPanel().add(this.mLinearLearningBox, constraints);
		
		// Button to reload the current option set.
		JButton resetOptionsButton = new JButton();
		resetOptionsButton.setText("reset options");
		resetOptionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				// Reset container.
				getMainFrame().getContainerManager().addContainer(new OptionsContainer());
				
				// Update UI.
				getMainFrame().switchToPanel(OptionsPanel.this);
			}
		});
		this.setConstraints(constraints, 0, 3, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
		this.getSwingPanel().add(resetOptionsButton, constraints);
		
		// Button to reload the current option set.
		JButton reloadOptionsButton = new JButton();
		reloadOptionsButton.setText("reload options");
		reloadOptionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				// Reload container.
				getMainFrame().getContainerManager().loadContainerFromXml(OptionsContainer.class);
				
				// Update UI.
				getMainFrame().switchToPanel(OptionsPanel.this);
			}
		});
		this.setConstraints(constraints, 1, 3, 1, 1, 1.0, 1.0);
		this.getSwingPanel().add(reloadOptionsButton, constraints);
		
		// Button to save the current option set.
		JButton saveOptionsButton = new JButton();
		saveOptionsButton.setText("save options");
		saveOptionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				getMainFrame().getContainerManager().WriteContainerToXml(OptionsContainer.class);
			}
		});
		this.setConstraints(constraints, 4, 3, 2, 1, 2.0, 1.0);
		this.getSwingPanel().add(saveOptionsButton, constraints);
	}
}
