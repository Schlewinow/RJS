package rjs.container;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rjs.container.interfaces.IDataContainer;

// Adds some helper methods that are used in nearly every container.
public abstract class AbstractDataContainer implements IDataContainer 
{
	// Create a xml node of type tag with given value.
	protected Element createXmlElement(Document doc, String tag, String value)
	{
		Element element = doc.createElement(tag);
		element.setTextContent(value);
		return element;
	}
	
	// Remove nodes of a given type from a xml document.
	protected void removeNodesOfType(Document doc, String tag)
	{
		NodeList nodesToRemove = doc.getElementsByTagName(tag);
		for(int index = 0; index < nodesToRemove.getLength(); ++index)
		{
			nodesToRemove.item(index).getParentNode().removeChild(nodesToRemove.item(index));
		}
	}
	
	// Find the root node and add the node to the root.
	protected void addElementToRoot(Document doc, Element element)
	{
		NodeList rootAsList = doc.getElementsByTagName(IDataContainer.XML_ROOT);
		rootAsList.item(0).appendChild(element);
	}
	
	// Read the data of a node as string.
	protected String readStringFromXmlNode(Node node, String tag)
	{
		if(node.getNodeName().equals(tag))
		{
			return node.getTextContent();
		}
		
		return null;
	}
	
	// Read the data of a node as integer.
	protected Integer readIntFromXmlNode(Node node, String tag)
	{
		if(node.getNodeName().equals(tag))
		{
			try
			{
				Integer output = Integer.parseInt(node.getTextContent());
				return output;
			}
			catch(Exception ex)
			{
				return null;
			}
		}
		
		return null;
	}
	
	// Read the data of a node as float.
	protected Float readFloatFromXmlNode(Node node, String tag)
	{
		if(node.getNodeName().equals(tag))
		{
			try
			{
				Float output = Float.parseFloat(node.getTextContent());
				return output;
			}
			catch(Exception ex)
			{
				return null;
			}
		}
		
		return null;
	}
	
	// Read the data of a node as bool.
	protected Boolean readBoolFromXmlNode(Node node, String tag)
	{
		if(node.getNodeName().equals(tag))
		{
			try
			{
				Boolean output = Boolean.parseBoolean(node.getTextContent());
				return output;
			}
			catch(Exception ex)
			{
				return null;
			}
		}
		
		return null;
	}
}
