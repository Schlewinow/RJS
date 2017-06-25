package rjs.container;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Container storing data connected to number learning sessions.
 * Also contains static string array with numbers in hiragana.
 * @author Schlewinow
 */
public class NumberContainer extends AbstractDataContainer
{
	/**
	 * XML-tag used for the container.
	 */
	private static final String XML_TAG = "number";
	
	/**
	 * XML-tag used to describe how many digits are in front of the comma.
	 */
	private static final String XML_TAG_BEFORE_COMMA = "digits_before_comma";
	
	/**
	 * XML-tag used to describe how many digits are behind the comma.
	 */
	private static final String XML_TAG_AFTER_COMMA = "digits_after_comma";
	
	/**
	 * XML-tag used to describe whether negative numbers are allowed or not.
	 */
	private static final String XML_TAG_MINUS = "allow_minus";
	
	/**
	 * String containing katakana minus.
	 */
	public static final String Minus = "\u30DE\u30A4\u30CA\u30B9";
	
	/**
	 * String containing hiragana comma.
	 */
	public static final String Comma = "comma";
	
	/**
	 * String array containing hiragana zero.
	 */
	public static final String[] Zero = {"\u308C\u3044", "\u305C\u308D"};			// rei, zero
	
	/**
	 * String array containing hiragana one to nine.
	 */
	public static final String[][] Ones = {{"\u3044\u3061"},					// ichi
											{"\u306B"},							// ni
											{"\u3055\u3093"},					// san
											{"\u3057", "\u3088\u3093"},			// shi, yon
											{"\u3054"},							// go
											{"\u308D\u304F"},					// roku
											{"\u306A\u306A", "\u3057\u3061"},	// nana, shichi
											{"\u306F\u3061"},					// hachi
											{"\u304D\u3085\u3046", "\u304F"}};	// kyuu, ku
	
	/**
	 * String array containing hiragana ten to ninety.
	 */
	public static final String[][] Tens= {{"\u3058\u3085\u3046"},													// juu
											{"\u306B\u3058\u3085\u3046"},											// nijuu
											{"\u3055\u3093\u3058\u3085\u3046"},										// sanjuu
											{"\u3057\u3058\u3085\u3046", "\u3088\u3093\u3058\u3085\u3046"},			// shijuu, yonjuu
											{"\u3054\u3058\u3085\u3046"},											// gojuu
											{"\u308D\u304F\u3058\u3085\u3046"},										// rokujuu
											{"\u306A\u306A\u3058\u3085\u3046", "\u3057\u3061\u3058\u3085\u3046"},	// nanajuu, shichijuu
											{"\u306F\u3061\u3058\u3085\u3046"},										// hachijuu
											{"\u304D\u3085\u3046\u3058\u3085\u3046", "\u304F\u3058\u3085\u3046"}};	// kyuujuu, kujuu
	
	/**
	 * String array containing hiragana hundred to ninehundred.
	 */
	public static final String[] Hundreds = {"\u3072\u3083\u304F",						// hyaku
											"\u306B\u3072\u3083\u304F",					// nihyaku
											"\u3055\u3093\u3073\u3083\u304F",			// sanbyaku
											"\u3088\u3093\u3072\u3083\u304F",			// yonhyaku
											"\u3054\u3072\u3083\u304F",					// gohyaku
											"\u308D\u3063\u3074\u3083\u304F",			// roppyaku
											"\u306A\u306A\u3072\u3083\u304F",			// nanahyaku
											"\u306F\u3063\u3074\u3083\u304F",			// happyaku
											"\u304D\u3085\u3046\u3072\u3083\u304F",};	// kyuuhyaku
	
	/**
	 * String array containing hiragana thousand to ninethousand.
	 */
	public static final String[] Thousands = {"\u3044\u3063\u305B\u3093",			// issen
												"\u306B\u305B\u3093",				// nisen
												"\u3055\u3093\u305C\u3093",			// sanzen
												"\u3088\u3093\u305B\u3093",			// yonsen
												"\u3054\u305B\u3093",				// gosen
												"\u308D\u304F\u305B\u3093",			// rokusen
												"\u306A\u306A\u305B\u3093",			// nanasen
												"\u306F\u3063\u305B\u3093",			// hassen
												"\u304D\u3085\u3046\u305B\u3093"};	// kyuusen
	
	/**
	 * String array containing hiragana for digit 10,000.
	 */
	public static final String[] TenThousands = { "\u3044\u3061\u307E\u3093",			// ichiman
													"\u306B\u307E\u3093",				// niman
													"\u3055\u3093\u307E\u3093",			// sanman
													"\u3088\u3093\u307E\u3093",			// yonman
													"\u3054\u307E\u3093",				// goman
													"\u308D\u304F\u307E\u3093",			// lokuman
													"\u306A\u306A\u307E\u3093",			// nanaman
													"\u306F\u3061\u307E\u3093",			// hachiman
													"\u304D\u3085\u3046\u307E\u3093",	// kyuman
													"\u307E\u3093"};					// man
	
	/**
	 * String array containing hiragana for digit 100,000,000.
	 */
	public static final String[] HundredMilllions = {"\u3044\u3061\u304A\u304F",			// ichioku
														"\u306B\u304A\u304F",				// nioku
														"\u3055\u3093\u304A\u304F",			// sanoku
														"\u3088\u3093\u304A\u304F",			// yonoku
														"\u3054\u304A\u304F",				// gooku
														"\u308D\u304F\u304A\u304F",			// rokuoku
														"\u306A\u306A\u304A\u304F",			// nanaoku
														"\u306F\u3061\u304A\u304F", 		// hachiman
														"\u304D\u3085\u3046\u304A\u304F",	// kyuman
														"\u304A\u304F"};					// oku
	
	/**
	 * How many digits should be a random number have as maximum?
	 * These are only the digits before the comma.
	 * If the value is 0, there will always be a 0 before the comma.
	 */
	private int mDigitsBeforeComma = 0;
	
	/**
	 * Are there digits behind the comma?
	 * If yes, the comma-text may be applied.
	 */
	private int mDigitsAfterComma = 0;
	
	/**
	 * Maximum allowed number of digits before comma.
	 */
	private final int mMaxDigitsBeforeComma = 9;
	
	/**
	 * Are negative numbers allowed?
	 * If yes, the minus-text may be applied.
	 */
	private boolean mAllowMinus = false;
	
	/**
	 * Default constructor. Just init basic values.
	 */
	public NumberContainer()
	{
		this.setDigitsBeforeComma(2);
		this.setDigitsAfterComma(0);
		this.mAllowMinus = false;
	}
	
	/**
	 * Setter number of digits before comma. 
	 * When set to 0 this value will always be zero.
	 * There is a maximum the value may take. 
	 * Also a value less than 0 is not allowed.
	 * The setter will automatically keep this bounds.
	 * @param digits The new maximum number of digits before the comma.
	 */
	public void setDigitsBeforeComma(int digits)
	{
		this.mDigitsBeforeComma = digits;
		
		// Keep application bounds.
		if(this.mDigitsBeforeComma < 0)
		{
			this.mDigitsBeforeComma = 0;
		}
		if(this.mDigitsBeforeComma > mMaxDigitsBeforeComma)
		{
			this.mDigitsBeforeComma = mMaxDigitsBeforeComma;
		}
	}
	
	/**
	 * Getter number of digits before comma.
	 * @return The current number of maximum digits before the comma.
	 */
	public int getDigitsBeforeComma()
	{
		return this.mDigitsBeforeComma;
	}
	
	/**
	 * Setter number of digits before comma.
	 * When 0, this value will always be zero.
	 * Negative values not allowed.
	 * @param digits
	 */
	public void setDigitsAfterComma(int digits)
	{
		this.mDigitsAfterComma = digits;
		
		// Keep application bounds.
		if(this.mDigitsAfterComma < 0)
		{
			this.mDigitsAfterComma = 0;
		}
		if(this.mDigitsAfterComma > 10)
		{
			this.mDigitsAfterComma = 10;
		}
	}
	
	/**
	 * Getter number of digits after comma.
	 * @return The current maximum number of digits after the comma.
	 */
	public int getDigitsAfterComma()
	{
		return this.mDigitsAfterComma;
	}
	
	/**
	 * Setter state whether negative number may be generated or not.
	 * @param allowed New value of the state.
	 */
	public void setMinusAllowed(boolean allowed)
	{
		this.mAllowMinus = allowed;
	}
	
	/**
	 * Getter minus activation state.
	 * @return The boolean state whether a minus may be generated or not.
	 */
	public boolean getMinusAllowed()
	{
		return this.mAllowMinus;
	}
	
	/**
	 * Write the data of this container into an XML-document.
	 * @param doc XML-Document to modify.
	 */
	public void writeToXml(Document doc)
	{
		this.removeNodesOfType(doc, NumberContainer.XML_TAG);
		
		// Create new xml-nodes and add them to the document.
		Element numberElement = doc.createElement(XML_TAG);
		numberElement.appendChild(this.createXmlElement(doc, XML_TAG_BEFORE_COMMA, "" + this.mDigitsBeforeComma));
		numberElement.appendChild(this.createXmlElement(doc, XML_TAG_AFTER_COMMA, "" + this.mDigitsAfterComma));
		numberElement.appendChild(this.createXmlElement(doc, XML_TAG_MINUS, "" + this.mAllowMinus));
		this.addElementToRoot(doc, numberElement);
	}

	/**
	 * Read the data of this container from an XML-document.
	 * @param doc XML-document to read from.
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
				Integer beforeComma = this.readIntFromXmlNode(xmlChild, XML_TAG_BEFORE_COMMA);
				this.mDigitsBeforeComma = beforeComma == null ? this.mDigitsBeforeComma : beforeComma;
				
				Integer afterComma = this.readIntFromXmlNode(xmlChild, XML_TAG_AFTER_COMMA);
				this.mDigitsAfterComma = afterComma == null ? this.mDigitsAfterComma : afterComma;
				
				Boolean minus = this.readBoolFromXmlNode(xmlChild, XML_TAG_MINUS);
				this.mAllowMinus = minus == null ? this.mAllowMinus : minus;
			}
		}
		
		// This will make sure the values are inside the application parameters.
		// (in case of external editing of the xml file)
		this.setDigitsBeforeComma(this.mDigitsBeforeComma);
		this.setDigitsAfterComma(this.mDigitsAfterComma);
		this.setMinusAllowed(this.mAllowMinus);
	}
}
