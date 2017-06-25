package rjs.panels.interfaces;

import javax.swing.JPanel;

import rjs.frame.IMainFrame;

/**
 * Interface for RJS-panels.
 * @author Schlewinow
 */
public interface IPanel
{
	/**
	 * Initialize the panel. Add the various UI-elements.
	 * @param mainFrame Reference to the main frame. Necessary for initialization.
	 */
	void initialize(IMainFrame mainFrame);
	
	/**
	 * Getter swing panel managed by the RJS panel.
	 * @return A java swing panel holding the UI-elements of this RJS-panel.
	 */
	JPanel getSwingPanel();
}
