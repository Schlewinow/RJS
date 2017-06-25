package rjs.container;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import rjs.container.interfaces.IContainerManager;
import rjs.container.interfaces.IDataContainer;

// Manages data container.
public class ContainerManager implements IContainerManager
{
	// Path to the config file.
	private static final String CONFIG_PATH = "config.xml"; 
	
	// Map containing the managed containers.
	private HashMap<Type, IDataContainer> mContainerMap = null;
	
	// Default constructor. Used to initialize inner structure.
	public ContainerManager()
	{
		this.mContainerMap = new HashMap<Type, IDataContainer>();
	}
	
	// Add a container to the managed data. Every container type may only occur once.
	public void addContainer(IDataContainer container)
	{
		this.mContainerMap.put(container.getClass(), container);
	}

	// Getter data container by type.
	public IDataContainer getContainer(Type type)
	{
		return this.mContainerMap.get(type);
	}

	// Load a specific container from the config file.
	public void loadContainerFromXml(Type type)
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(CONFIG_PATH);
			
			IDataContainer container = this.getContainer(type);
			if(container != null)
			{
				container.readFromXml(doc);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	// Write a given container to the config file.
	public void WriteContainerToXml(Type type)
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			//transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			// If no config file exists, create one.
			File configFile = new File(CONFIG_PATH);
			if(configFile == null || !configFile.exists())
			{
				Document emptyDoc = builder.newDocument();
				Element root = emptyDoc.createElement(IDataContainer.XML_ROOT);
				emptyDoc.appendChild(root);

				DOMSource source = new DOMSource(emptyDoc);
				StreamResult result = new StreamResult(new FileOutputStream(CONFIG_PATH));
				transformer.transform(source, result);
				
				result.getOutputStream().close();
			}
			
			Document doc = builder.parse(CONFIG_PATH);
			
			IDataContainer container = this.getContainer(type);
			if(container != null)
			{
				container.writeToXml(doc);
				
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new FileOutputStream(CONFIG_PATH));
				transformer.transform(source, result);
				
				result.getOutputStream().close();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
