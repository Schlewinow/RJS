package rjs.container.interfaces;

import java.lang.reflect.Type;

/**
 * Manages the different data container types.
 * @author Schlewinow
 */
public interface IContainerManager
{
	/**
	 * Add a new container to the manager.
	 * @param container Container to add to the manager.
	 */
	void addContainer(IDataContainer container);
	
	/**
	 * Getter specific data container.
	 * @param type The type of container to access.
	 * @return The container of the given type. Null if no such container was found.
	 */
	IDataContainer getContainer(Type type);
	
	/**
	 * Load a single container from the config file.
	 * @param type The type of container to load from the config file.
	 */
	void loadContainerFromXml(Type type);
	
	/**
	 * Write a given container to xml.
	 * @param type The type of container to write into the config file.
	 */
	void WriteContainerToXml(Type type);
}
