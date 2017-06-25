package rjs.frame;

import rjs.container.interfaces.IContainerManager;
import rjs.panels.interfaces.IPanel;

/**
 * Interface for the main frame.
 * Every panel holds a reference to the local main frame which serves as a mediator.
 * @author Schlewinow
 */
public interface IMainFrame
{
	/**
	 * Getter ContainerManager. Used to access the data containers managing derived system data.
	 * @return Reference to the ContainerManager managed by the main frame.
	 */
	IContainerManager getContainerManager();
	
	/**
	 * Switch the currently shown panel to another one.
	 * @param panel The RJS-panel to switch to.
	 */
	void switchToPanel(IPanel panel);
}
