package rjs.container;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rjs.wordlist.WordList;
import rjs.wordlist.XmlWordListHandler;

// Container holding relevant data connected to the active word list.
public class WordListContainer extends AbstractDataContainer
{
	// XML-tag used for the container.
	private static final String XML_TAG = "wordlist";
	
	// XML-tag used to describe the path to the recently loaded word list file.
	private static final String XML_TAG_PATH = "wordlist_path";
	
	// The path of the loaded word list. Saved so it may be automatically loaded on next start.
	private String mCurrentWordListPath = "";
	
	// The currently loaded word list. Used in word learning panels.
	private WordList mWordList = null;
	
	// Default constructor. Initialize with default values.
	public WordListContainer()
	{
		this.mCurrentWordListPath = "";
		this.mWordList = new WordList();
	}
	
	// Getter word list.
	public WordList getWordList()
	{
		return this.mWordList;
	}
	
	// Getter word list path.
	public String getWordListPath()
	{
		return this.mCurrentWordListPath;
	}
	
	// Setter word list path. Also loads the word list at the given path.
	public void setWordListPath(String path)
	{
		// Load word list from path.
		this.mWordList = XmlWordListHandler.readFromFile(path);
		
		if(this.mWordList.getWordCount() == 0)
		{
			// No success, seems to be invalid path.
			this.mCurrentWordListPath = "";
		}
		else
		{
			this.mCurrentWordListPath = path;
		}
	}
	
	// Write the content of this container into the xml-document.
	public void writeToXml(Document doc)
	{		
		// Remove existing node of type.
		this.removeNodesOfType(doc, XML_TAG);

		// Create new xml-nodes and add them to the document.
		Element wordListNode = doc.createElement(XML_TAG);
		wordListNode.appendChild(this.createXmlElement(doc, XML_TAG_PATH, this.mCurrentWordListPath));
		this.addElementToRoot(doc, wordListNode);
	}

	// Read the content of a xml-document into this container.
	public void readFromXml(Document doc)
	{
		NodeList nodes = doc.getElementsByTagName(XML_TAG);
		
		// Iterate over nodes (container).
		for(int index = 0; index < nodes.getLength(); ++index)
		{
			Node xmlNode = nodes.item(index);
			NodeList children = xmlNode.getChildNodes();
			
			// Iterate over childeren (values of container).
			for(int childIndex = 0; childIndex < children.getLength(); ++childIndex)
			{
				Node xmlChild = children.item(childIndex);
				
				// Read as many values as possible.
				String path = this.readStringFromXmlNode(xmlChild, XML_TAG_PATH);
				this.mCurrentWordListPath = path == null ? this.mCurrentWordListPath : path;
			}
		}
		
		// Try to load word list.
		this.setWordListPath(this.mCurrentWordListPath);
	}
}
