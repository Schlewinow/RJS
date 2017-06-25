package rjs.panels;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import rjs.container.FontContainer;
import rjs.frame.IMainFrame;
import rjs.panels.interfaces.IPanel;

/**
 * Add some base functionality used by every RJS panel.
 * @author Schlewinow
 */
public abstract class AbstractPanel implements IPanel
{
	/**
	 * Every panel manages a JPanel hidden inside.
	 * This panel is used to store the various UI-elements visible to the user.
	 * It is also used by the main frame when replacing the currently shown panel.
	 */
	private JPanel mPanel = null;
	
	/**
	 * Reference to the main frame.
	 * This is usually used to access the IContainerManager inside the main frame.
	 */
	private IMainFrame mMainFrame = null;
	
	/**
	 * Initializes the rjs-panel. This will create a new swing-panel.
	 * The implementations of this class should overwrite this method and add UI-elements to the managed swing panel.
	 * @param mainFrame Reference to the main frame. Necessary to properly initialize the panel.
	 */
	public void initialize(IMainFrame mainFrame)
	{
		this.mMainFrame = mainFrame;
		this.mPanel = new JPanel();
	}

	/**
	 * Getter managed swing panel. The swing panel will be created anew whenever the rjs-panel is initialized. 
	 * @return Reference to the managed swing-panel.
	 */
	public JPanel getSwingPanel()
	{
		return this.mPanel;
	}
	
	/**
	 * Getter main frame. The main frame acts as mediator.
	 * @return Reference to the main frame.
	 */
	protected IMainFrame getMainFrame()
	{
		return this.mMainFrame;
	}
	
	/**
	 * Helper to set constraints in a single line. Simplified overload.
	 * @param con The GridBagConstraints-object to put the values into.
	 * @param x X-Position of the constraints.
	 * @param y Y-Position of the constraints.
	 * @param width Width of the constraints.
	 * @param height Height of the constraints.
	 */
	protected void setConstraints(GridBagConstraints con, int x, int y, int width, int height)
	{
		this.setConstraints(con, x, y, width, height, con.weightx, con.weighty);
	}
	
	/**
	 * Helper to set constraints in a single line. Simplified overload.
	 * @param con The GridBagConstraints-object to put the values into.
	 * @param x X-Position of the constraints.
	 * @param y Y-Position of the constraints.
	 * @param width Width of the constraints.
	 * @param height Height of the constraints.
	 * @param weightX The X-weight of the constraints.
	 * @param weightY The Y-weight of the constraints.
	 */
	protected void setConstraints(GridBagConstraints con, int x, int y, int width, int height, double weightX, double weightY)
	{
		this.setConstraints(con, x, y, width, height, weightX, weightY, con.anchor, con.fill);
	}
	
	/**
	 * Helper to set constraints in a single line.
	 * @param con The GridBagConstraints-object to put the values into.
	 * @param x X-Position of the constraints.
	 * @param y Y-Position of the constraints.
	 * @param width Width of the constraints.
	 * @param height Height of the constraints.
	 * @param weightX The X-weight of the constraints.
	 * @param weightY The Y-weight of the constraints.
	 * @param anchor The anchor. Used to bound the UI-Element to a given direction or position.
	 * @param fill The fill-mode. E.g. horizontal, vertical or both.
	 */
	protected void setConstraints(GridBagConstraints con, int x, int y, int width, int height, double weightX, double weightY, int anchor, int fill)
	{
		con.gridx = x;
		con.gridy = y;
		con.gridwidth = width;
		con.gridheight = height;
		
		con.weightx = weightX;
		con.weighty = weightY;
		
		con.anchor = anchor;
		con.fill = fill;
	}
	
	/**
	 * Allows easier creation of labels.
	 * @param caption Caption for this UI-element.
	 * @param constraints GridBagConstraints used to align this UI-element.
	 * @param fontContainer Use font container to get proper font for panel. If null, use Java standard UI font.
	 * @return Reference to the newly created label.
	 */
	protected JLabel createLabel(String caption, GridBagConstraints constraints, FontContainer fontContainer)
	{
		JLabel newLabel = new JLabel();
		newLabel.setText(caption);
		
		if(fontContainer != null)
		{
			newLabel.setFont(fontContainer.getKanaFont().deriveFont(fontContainer.getKanaFontSize()));
		}
		
		this.getSwingPanel().add(newLabel, constraints);
		return newLabel;
	}
	
	/**
	 * Allows easier creation of buttons.
	 * @param caption Caption for this UI-element.
	 * @param constraints GridBagConstraints used to align this UI-element.
	 * @param fontContainer Use font container to get proper font for panel. If null, use Java standard UI font.
	 * @param action ActionListener to be added to UI-element. If null, no listener is added on button creation.
	 * @return Reference to the newly created button.
	 */
	protected JButton createButton(String caption, GridBagConstraints constraints, FontContainer fontContainer, ActionListener action)
	{
		JButton newButton = new JButton();
		newButton.setText(caption);
		
		if(fontContainer != null)
		{
			newButton.setFont(fontContainer.getKanaFont().deriveFont(fontContainer.getKanaFontSize()));
		}
		
		if(action != null)
		{
			newButton.addActionListener(action);
		}
		
		this.getSwingPanel().add(newButton, constraints);
		return newButton;
	}
	
	/**
	 * Allows easier creation of check boxes.
	 * @param caption Caption for this UI-element.
	 * @param constraints GridBagConstraints used to align this UI-element.
	 * @param fontContainer Use font container to get proper font for panel. If null, use Java standard UI font.
	 * @param action ActionListener to be added to UI-element. If null, no listener is added on button creation.
	 * @param isSelected Start value of the created check box.
	 * @return Reference to the newly created check box.
	 */
	protected JCheckBox createCheckBox(String caption, GridBagConstraints constraints, FontContainer fontContainer, ActionListener action, Boolean isSelected)
	{
		JCheckBox newCheckBox = new JCheckBox();
		newCheckBox.setText(caption);
		newCheckBox.setSelected(isSelected);
		
		if(fontContainer != null)
		{
			newCheckBox.setFont(fontContainer.getKanaFont().deriveFont(fontContainer.getKanaFontSize()));
		}
		
		if(action != null)
		{
			newCheckBox.addActionListener(action);
		}
		
		this.getSwingPanel().add(newCheckBox, constraints);
		return newCheckBox;
	}
}
