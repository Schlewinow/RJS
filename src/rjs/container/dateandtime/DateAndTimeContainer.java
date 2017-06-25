package rjs.container.dateandtime;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rjs.container.AbstractDataContainer;

/**
 * Container storing data connected to time and date learning sessions.
 * @author Schlewinow
 */
public class DateAndTimeContainer extends AbstractDataContainer
{
	/**
	 * XML-tag used for the container.
	 */
	private static final String XML_TAG = "dateAndTime";
		
	/**
	 * XML-tag used to describe whether a letter is used or not.
	 */
	private static final String XML_TAG_KANJI_STATE = "use_Kanji";
	
	/**
	 * XML-tag used to describe a time unit of type month.
	 */
	private static final String XML_TAG_TIMEUNIT_MONTH = "month";
	
	/**
	 * XML-tag used to describe a time unit of type day.
	 */
	private static final String XML_TAG_TIMEUNIT_DAY = "day";
	
	/**
	 * XML-tag used to describe a time unit of type day.
	 */
	private static final String XML_TAG_TIMEUNIT_HOUR = "hour";
	
	/**
	 * 12 months in Kana and Kanji.
	 */
	private HashMap<Integer, TimeUnit> months;
	
	/**
	 * 31 days in Kana and Kanji.
	 */
	private HashMap<Integer, TimeUnit> days;
	
	/**
	 * 24 hours in Kana and Kanji.
	 */
	private HashMap<Integer, TimeUnit> hours;
	
	
	public static final String[] minutes = {"\u3044\u3063",			// ip
											"\u306B",				// ni
											"\u3055\u3093",			// san
											"\u3088\u3093",			// yon
											"\u3054",				// go
											"\u308D\u3063",			// rop
											"\u306A\u306A",			// nana
											"\u306F\u3063",			// hap
											"\u304D\u3085\u3046",	// kyu
											"\u3058\u3085\u3063",	// jup
											"\u306F\u3093"};		// han
	
	/**
	 * Hiragana with meaning "hour".
	 */
	public static final String hourHiragana = "\u3058";		// ji
	
	/**
	 * Kanji with meaning "hour".
	 */
	public static final String hourKanji = "\u6642";		// hour kanji
	
	/**
	 * Hiragana with meaning "minute".
	 */
	public static final String[] minuteHiragana = {"\u3075\u3093", "\u3077\u3093"};		// hun, pun
	
	/**
	 * Kanji with meaning "minute".
	 */
	public static final String minuteKanji = "\u5206";		// minute Kanji
	
	/**
	 * Hiragana used like "AM".
	 */
	public static final String morningHiragana = "\u3054\u305C\u3093";		// gosen
	
	/**
	 * Hiragana used like "PM".
	 */
	public static final String eveningHiragana = "\u3054\u3054";			// gogo
	
	/**
	 * Use Kanji in japanese time (true) or Kana (false)?
	 */
	private boolean mUseKanji = true;
	
	/**
	 * Default constructor.
	 * Initializes internal data containers.
	 */
	public DateAndTimeContainer()
	{
		this.months = new HashMap<Integer, TimeUnit>();
		this.days = new HashMap<Integer, TimeUnit>();
		this.hours = new HashMap<Integer, TimeUnit>();
	}
	
	/**
	 * Getter months.
	 * @param value The index of the month (e.g. January has index 1).
	 * @return The time unit containing the requested data.
	 */
	public TimeUnit getMonth(int value)
	{
		return this.months.get(value);
	}
	
	/**
	 * Getter days.
	 * @param value The index of the day (e.g. first day of month has index 1).
	 * @return The time unit containing the requested data.
	 */
	public TimeUnit getDay(int value)
	{
		return this.days.get(value);
	}
	
	/**
	 * Getter hours.
	 * @param value The index of the hour (e.g. first hour of day has index 1).
	 * @return The time unit containing the requested data.
	 */
	public TimeUnit getHour(int value)
	{
		return this.hours.get(value);
	}
	
	/**
	 * Setter Kanji use state.
	 * @param useKanji New Kanji use state.
	 */
	public void setUseKanji(boolean useKanji)
	{
		this.mUseKanji = useKanji;
	}
	
	/**
	 * Getter Kanji use state.
	 * @return Current Kanji use state.
	 */
	public boolean getUseKanji()
	{
		return this.mUseKanji;
	}
	
	/**
	 * 
	 */
	public void writeToXml(Document doc)
	{
		this.removeNodesOfType(doc, XML_TAG);
		
		// Create new xml-nodes and add them to the document.
		Element dateTimeElement = doc.createElement(XML_TAG);
		dateTimeElement.appendChild(this.createXmlElement(doc, XML_TAG_KANJI_STATE, "" + this.getUseKanji()));
		this.addElementToRoot(doc, dateTimeElement);
	}

	/**
	 * 
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

				Boolean useKanji = this.readBoolFromXmlNode(xmlChild, XML_TAG_KANJI_STATE);
				this.mUseKanji = useKanji == null ? this.mUseKanji : useKanji;
			}
		}
		
		// This will make sure the values are inside the application parameters.
		// (in case of external editing of the xml file)
		this.setUseKanji(this.mUseKanji);
		
		this.loadContainerData("contents/common/date_and_time.xml");
	}
	
	/**
	 * Load the whole date and time container data from a special XML file at given path.
	 * @param filePath Path to the XML-file containing the time and date container data.
	 */
	private void loadContainerData(String filePath)
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(filePath);
			
			this.extractTimeUnits(doc, XML_TAG_TIMEUNIT_MONTH, this.months);
			this.extractTimeUnits(doc, XML_TAG_TIMEUNIT_DAY, this.days);
			this.extractTimeUnits(doc, XML_TAG_TIMEUNIT_HOUR, this.hours);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * Extract the container data from a specific XML document.
	 * @param doc XML document to extract from.
	 * @param parentNode Name of the node type that contains the time unit data.
	 * @param destMap HashMap to write data into.
	 */
	private void extractTimeUnits(Document doc, String parentNode, HashMap<Integer, TimeUnit> destMap)
	{
		NodeList nodes = doc.getElementsByTagName(parentNode);
		
		// Iterate over parent nodes (time units).
		for(int index = 0; index < nodes.getLength(); ++index)
		{
			Node xmlNode = nodes.item(index);
			NodeList children = xmlNode.getChildNodes();
			
			String kana = "";
			String kanji = "";
			
			// Iterate over childeren (data of time units).
			for(int childIndex = 0; childIndex < children.getLength(); ++childIndex)
			{
				Node childNode = children.item(childIndex);
				if(childNode.getNodeName().equals("kana"))
				{
					kana = childNode.getTextContent();
				}
				else if(childNode.getNodeName().equals("kanji"))
				{
					kanji = childNode.getTextContent();
				}
			}
			
			TimeUnit newUnit = new TimeUnit(kana, kanji);
			int newIndex = Integer.parseInt(xmlNode.getAttributes().getNamedItem("value").getNodeValue());
			destMap.put(newIndex, newUnit);
		}
	}
}
