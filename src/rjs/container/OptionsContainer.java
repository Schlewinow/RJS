package rjs.container;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *  Class to store the data necessary to save and load the random session options.
 * @author Schlewinow
 */
public class OptionsContainer extends AbstractDataContainer
{
	/**
	 *  XML-tag used for the container.
	 */
	private static final String XML_TAG = "options";
	
	/**
	 *  XML-tag used to describe the minimum random text size.
	 */
	private static final String XML_TAG_MINIMUM_RANDOM_TEXT_SIZE = "minumum_random_text_size";
	
	/**
	 *  XML-tag used to describe the maximum random text size.
	 */
	private static final String XML_TAG_MAXIMUM_RANDOM_TEXT_SIZE = "maximum_random_text_size";
	
	/**
	 *  XML-tag used to describe the maximum random text size.
	 */
	private static final String XML_TAG_LINEAR_WORD_LEARNING = "linear_word_learning";

	/**
	 *  Default value of minimum random text length.
	 */
	public static final int defaultMinimumRandomTextSize = 1;
	
	/**
	 *  Default value of maximum random text length.
	 */
	public static final int defaultMaximumRandomTextSize = 5;
	
	/**
	 *  Default value of kana font size.
	 */
	public static final float defaultKanaFontSize = 12.0f;
	
	/**
	 *  Maximum value of Kana font size.
	 */
	public static final float maximumKanaFontSize = 120.0f;
	
	/**
	 *  Current minimum size of random texts.
	 */
	private int mMinimumRandomTextSize = 0;
	
	/**
	 *  Current maximum size of random texts.
	 */
	private int mMaximumRandomTextSize = 0;
	
	/**
	 * If true, the word learning session will be linear. Otherwise the words will be chosen randomly.
	 */
	private boolean mLinearLearning = false;
	
	/**
	 * Default constructor. Creating an option set with default settings.
	 */
	public OptionsContainer()
	{
		this.mMinimumRandomTextSize = OptionsContainer.defaultMinimumRandomTextSize;
		this.mMaximumRandomTextSize = OptionsContainer.defaultMaximumRandomTextSize;
		
		this.mLinearLearning = false;
	}
	
	/**
	 * Getter minimum number of random letters.
	 * @return The minimum number of letters in a generated random text.
	 */
	public int getMinimumRandomTextSize()
	{
		return this.mMinimumRandomTextSize;
	}
	
	/**
	 * Setter minimum number of random letters.
	 * @param value The new minimum number of letters in a randomly generated text.
	 */
	public void setMinimumRandomTextSize(int value)
	{
		// Keep bounds.
		if(value < 1)
		{
			value = 1;
		}
		if(value > 10)
		{
			value = 10;
		}
		if(value > this.mMaximumRandomTextSize)
		{
			this.mMaximumRandomTextSize = value;
		}
		
		this.mMinimumRandomTextSize = value;
	}
	
	/**
	 * Getter maximum number of random letters.
	 * @return The maximum number of letters in a generated random text.
	 */
	public int getMaximumRandomTextSize()
	{
		return this.mMaximumRandomTextSize;
	}
	
	/**
	 * Setter minimum number of random letters.
	 * @param value The new maximum number of letters in a randomly generated text.
	 */
	public void setMaximumRandomTextSize(int value)
	{
		// Keep bounds.
		if(value < 1)
		{
			value = 1;
		}
		if(value > 10)
		{
			value = 10;
		}
		if(value < this.mMinimumRandomTextSize)
		{
			this.mMinimumRandomTextSize = value;
		}
		
		this.mMaximumRandomTextSize = value;
	}
	
	/**
	 * Setter linear word learning state.
	 * @param linear Should the word learning be linear (true) or random (false)?
	 */
	public void setLinearLearning(boolean linear)
	{
		this.mLinearLearning = linear;
	}
	
	/**
	 * Getter linear word learning state.
	 * @return True if linear learning is active, false otherwise.
	 */
	public boolean getLinearLearning()
	{
		return this.mLinearLearning;
	}
	
	/**
	 * Write data of the container into a xml-document.
	 */
	public void writeToXml(Document doc)
	{
		// Remove existing node of type.
		this.removeNodesOfType(doc, XML_TAG);
		
		// Create new xml-nodes and add them to the document.
		Element optionsElement = doc.createElement(XML_TAG);
		optionsElement.appendChild(this.createXmlElement(doc, XML_TAG_MINIMUM_RANDOM_TEXT_SIZE, "" + this.mMinimumRandomTextSize));
		optionsElement.appendChild(this.createXmlElement(doc, XML_TAG_MAXIMUM_RANDOM_TEXT_SIZE, "" + this.mMaximumRandomTextSize));
		optionsElement.appendChild(this.createXmlElement(doc, XML_TAG_LINEAR_WORD_LEARNING, "" + this.mLinearLearning));
		this.addElementToRoot(doc, optionsElement);
	}
	
	/**
	 * Read data from a xml document.
	 */
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
				Integer min = this.readIntFromXmlNode(xmlChild, XML_TAG_MINIMUM_RANDOM_TEXT_SIZE);
				this.mMinimumRandomTextSize = min == null ? this.mMinimumRandomTextSize : min;
				
				Integer max = this.readIntFromXmlNode(xmlChild, XML_TAG_MAXIMUM_RANDOM_TEXT_SIZE);
				this.mMaximumRandomTextSize = max == null ? this.mMaximumRandomTextSize : max;
				
				Boolean linear = this.readBoolFromXmlNode(xmlChild, XML_TAG_LINEAR_WORD_LEARNING);
				this.mLinearLearning = linear == null ? this.mLinearLearning : linear;
			}
		}
		
		// This will make sure the values are inside the application parameters.
		// (in case of external editing of the xml file)
		this.setMinimumRandomTextSize(this.mMinimumRandomTextSize);
		this.setMaximumRandomTextSize(this.mMaximumRandomTextSize);
		this.setLinearLearning(this.mLinearLearning);
	}
}
