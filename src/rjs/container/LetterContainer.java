package rjs.container;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LetterContainer extends AbstractDataContainer
{
	// Letters in latin.
	public static final String[][] latin =  
		{ 	{"a", "i", "u", "e", "o"},
			{"ka", "ki", "ku", "ke", "ko"},
			{"sa", "shi", "su", "se", "so"},
			{"ta", "chi", "tsu", "te", "to"},
			{"na", "ni", "nu", "ne", "no"},
			{"ha", "hi", "hu", "he", "ho"},
			{"ma", "mi", "mu", "me", "mo"},
			{"ya", "yu", "yo"},
			{"ra", "ri", "ru", "re", "ro"},
			{"wa", "wo"},
			{"n"},
			{"ga", "gi", "gu", "ge", "go"},
			{"za", "ji", "zu", "ze", "zo"},
			{"da", "ji", "zu", "de", "do"},
			{"ba", "bi", "bu", "be", "bo"},
			{"pa", "pi", "pu", "pe", "po"},
			{"kya", "kyu", "kyo"},
			{"sha", "shu", "sho"},
			{"cha", "chu", "cho"},
			{"nya", "nyu", "nyo"},
			{"hya", "hyu", "hyo"},
			{"mya", "myu", "myo"},
			{"rya", "ryu", "ryo"},
			{"gya", "gyu", "gyo"},
			{"ja", "ju", "jo"},
			{"bya", "byu", "byo"},
			{"pya", "pyu", "pyo"} };
	
	// Letters in Hiragana.
	// A unicode list may be found here: http://de.wikipedia.org/wiki/Hiragana#Hiragana_im_Unicode
	public static final String[][] hiragana = 
		{	{"\u3042", "\u3044", "\u3046", "\u3048", "\u304A"},	// a i u e o
			{"\u304B", "\u304D", "\u304F", "\u3051", "\u3053"},	// ka ki ku ke ko
			{"\u3055", "\u3057", "\u3059", "\u305B", "\u305D"},	// sa shi su se so
			{"\u305F", "\u3061", "\u3064", "\u3066", "\u3068"},	// ta chi tsu te to
			{"\u306A", "\u306B", "\u306C", "\u306D", "\u306E"},	// na ni nu ne no
			{"\u306F", "\u3072", "\u3075", "\u3078", "\u307B"},	// ha hi hu he ho
			{"\u307E", "\u307F", "\u3080", "\u3081", "\u3082"},	// ma mi mu me mo
			{"\u3084", "\u3086", "\u3088"},						// ya yu yo
			{"\u3089", "\u308A", "\u308B", "\u308C", "\u308D"},	// ra ri ru re ro
			{"\u308F", "\u3092"},								// wa wo
			{"\u3093"},											// n
			{"\u304C", "\u304E", "\u3050", "\u3052", "\u3054"},	// ga gi gu ge go
			{"\u3056", "\u3058", "\u305A", "\u305C", "\u305E"},	// za ji zu ze zo
			{"\u3060", "\u3062", "\u3065", "\u3067", "\u3069"},	// da ji zu de do 
			{"\u3070", "\u3073", "\u3076", "\u3079", "\u307C"},	// ba bi bu be bo
			{"\u3071", "\u3074", "\u3077", "\u307A", "\u307D"},	// pa pi pu pe po
			{"\u304D\u3083", "\u304D\u3085", "\u304D\u3087"},
			{"\u3057\u3083", "\u3057\u3085", "\u3057\u3087"},
			{"\u3061\u3083", "\u3061\u3085", "\u3061\u3087"},
			{"\u306B\u3083", "\u306B\u3085", "\u306B\u3087"},
			{"\u3072\u3083", "\u3072\u3085", "\u3072\u3087"},
			{"\u307F\u3083", "\u307F\u3085", "\u307F\u3087"},
			{"\u308A\u3083", "\u308A\u3085", "\u308A\u3087"},
			{"\u304E\u3083", "\u304E\u3085", "\u304E\u3087"},
			{"\u3058\u3083", "\u3058\u3085", "\u3058\u3087"},
			{"\u3073\u3083", "\u3073\u3085", "\u3073\u3087"},
			{"\u3074\u3083", "\u3074\u3085", "\u3074\u3087"} };
	
	// Letters in Katakana.
	// A unicode list may be found here: http://de.wikipedia.org/wiki/Katakana#Unicode
	public static final String[][] katakana =  
		{ 	{"\u30A2", "\u30A4", "\u30A6", "\u30A8", "\u30AA"},	// a i u e o
			{"\u30AB", "\u30AD", "\u30AF", "\u30B1", "\u30B3"},	// ka ki ku ke ko
			{"\u30B5", "\u30B7", "\u30B9", "\u30BB", "\u30BD"},	// sa shi su se so
			{"\u30BF", "\u30C1", "\u30C4", "\u30C6", "\u30C8"},	// ta chi tsu te to
			{"\u30CA", "\u30CB", "\u30CC", "\u30CD", "\u30CE"},	// na ni nu ne no
			{"\u30CF", "\u30D2", "\u30D5", "\u30D8", "\u30DB"},	// ha hi hu he ho
			{"\u30DE", "\u30DF", "\u30E0", "\u30E1", "\u30E2"},	// ma mi mu me mo
			{"\u30E4", "\u30E6", "\u30E8"},						// ya yu yo
			{"\u30E9", "\u30EA", "\u30EB", "\u30EC", "\u30ED"},	// ra ri ru re ro
			{"\u30EF", "\u30F2"},								// wa wo
			{"\u30F3"},											// n
			{"\u30AC", "\u30AE", "\u30B0", "\u30B2", "\u30B4"},	// ga gi gu ge go
			{"\u30B6", "\u30B8", "\u30BA", "\u30BC", "\u30BE"},	// za ji zu ze zo
			{"\u30C0", "\u30C2", "\u30C5", "\u30C7", "\u30C9"},	// da ji zu de do
			{"\u30D0", "\u30D3", "\u30D6", "\u30D9", "\u30DC"},	// ba bi bu be bo
			{"\u30D1", "\u30D4", "\u30D7", "\u30DA", "\u30DD"},	// pa pi pu pe po
			{"\u30AD\u30E3", "\u30AD\u30E5", "\u30AD\u30E7"},	// kya kyu kyo
			{"\u30B7\u30E3", "\u30B7\u30E5", "\u30B7\u30E7"},	// sha shu sho
			{"\u30C1\u30E3", "\u30C1\u30E5", "\u30C1\u30E7"},	// cha chu cho
			{"\u30CB\u30E3", "\u30CB\u30E5", "\u30CB\u30E7"},	// nya nyu nyo
			{"\u30D2\u30E3", "\u30D2\u30E5", "\u30D2\u30E7"},	// hya hyu hyoa
			{"\u30DF\u30E3", "\u30DF\u30E5", "\u30DF\u30E7"},	// mya myu myo
			{"\u30EA\u30E3", "\u30EA\u30E5", "\u30EA\u30E7"},	// rya ryu ryo
			{"\u30AE\u30E3", "\u30AE\u30E5", "\u30AE\u30E7"},	// gya gyu gyo
			{"\u30B8\u30E3", "\u30B8\u30E5", "\u30B8\u30E7"},	// ja ju jo
			{"\u30D3\u30E3", "\u30D3\u30E5", "\u30D3\u30E7"},	// bya byu byo
			{"\u30D4\u30E3", "\u30D4\u30E5", "\u30D4\u30E7"} };	// pya pyu pyo
	
	// XML-tag used for the container.
	private static final String XML_TAG = "letter";
	
	// XML-tag used to describe whether a letter is used or not.
	private static final String XML_TAG_LETTER_STATE = "use_letter";
	
	// The letters in use to create random text.
	private ArrayList<Boolean> mUseLetters = null;
	
	// Default constructor.
	public LetterContainer()
	{
		this.mUseLetters = new ArrayList<Boolean>();
		for(int counter = 0; counter < LetterContainer.latin.length; ++ counter)
		{
			this.mUseLetters.add(true);
		}
	}
	
	// Getter to know which letter lines are used or not.
	public boolean isUsingLetters(int lineIndex)
	{
		if(lineIndex < this.mUseLetters.size())
		{
			return this.mUseLetters.get(lineIndex);
		}
		else
		{
			return false;
		}
	}
	
	// Setter letter line states.
	public void setUsingLetters(int lineIndex, boolean use)
	{
		if(lineIndex >= 0 && lineIndex < this.mUseLetters.size())
		{
			this.mUseLetters.set(lineIndex, use);
		}
	}
	
	// Write data of the container into a xml-document.
	public void writeToXml(Document doc)
	{
		this.removeNodesOfType(doc, XML_TAG);
		
		// Create new xml-nodes and add them to the document.
		Element letterElement = doc.createElement(XML_TAG);
		for(int index = 0; index < this.mUseLetters.size(); ++index)
		{
			Element useLetterElement = this.createXmlElement(doc, XML_TAG_LETTER_STATE, "" + this.mUseLetters.get(index));
			
			// Add the letters as attribute to make the file human readable.
			String letterLine = "";
			for(int letterIndex = 0; letterIndex < LetterContainer.latin[index].length; ++letterIndex)
			{
				letterLine += LetterContainer.latin[index][letterIndex] + " ";
			}
			letterLine = letterLine.substring(0, letterLine.length() - 1);
			useLetterElement.setAttribute("letters", letterLine);
			
			letterElement.appendChild(useLetterElement);
		}
		this.addElementToRoot(doc, letterElement);
	}
	
	// Read data from a xml document.
	public void readFromXml(Document doc)
	{
		NodeList nodes = doc.getElementsByTagName(XML_TAG);
		ArrayList<Boolean> letterStates = new ArrayList<Boolean>();
		
		// Iterate over nodes (container).
		for(int index = 0; index < nodes.getLength(); ++index)
		{
			Node xmlNode = nodes.item(index);
			NodeList children = xmlNode.getChildNodes();
			
			// Iterate over childeren (values of container).
			for(int childIndex = 0; childIndex < children.getLength(); ++childIndex)
			{
				Node xmlChild = children.item(childIndex);
				
				Boolean value = this.readBoolFromXmlNode(xmlChild, XML_TAG_LETTER_STATE);
				if(value != null)
				{
					letterStates.add(value);
				}
			}
		}
		
		// Only append values if lists share the same size.
		if(this.mUseLetters.size() == letterStates.size())
		{
			for(int index = 0; index < this.mUseLetters.size(); ++index)
			{
				this.setUsingLetters(index, letterStates.get(index));
			}
		}
	}
}
