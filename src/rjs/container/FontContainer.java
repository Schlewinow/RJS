package rjs.container;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.UIManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *  Class to store font data.
 * @author Schlewinow
 */
public class FontContainer extends AbstractDataContainer
{
	/**
	 *  XML-tag used for the container.
	 */
	private static final String XML_TAG = "fontdata";
	
	/**
	 *  XML-tag used to describe the maximum random text size.
	 */
	private static final String XML_TAG_KANA_FONT_PATH = "kana_font_path";
		
	/**
	 *  XML-tag used to describe the maximum random text size.
	 */
	private static final String XML_TAG_KANA_FONT_SIZE = "kana_font_size";
	
	/**
	 *  XML-tag used to describe the maximum random text size.
	 */
	private static final String XML_TAG_KANJI_FONT_PATH = "kanji_font_path";
		
	/**
	 *  XML-tag used to describe the maximum random text size.
	 */
	private static final String XML_TAG_KANJI_FONT_SIZE = "kanji_font_size";
	
	/**
	 *  Default value of kana font size.
	 */
	public static final float defaultKanaFontSize = 12.0f;
	
	/**
	 *  Maximum value of Kana font size.
	 */
	public static final float maximumKanaFontSize = 120.0f;
	
	/**
	 * Relative path to the local folder "fonts" and a font in there. This is the current Kana font.
	 */
	private String mKanaFontPath = "";
	
	/**
	 *  The font currently used to print Hiragana and Katakana.
	 */
	private Font mKanaFont = null;
	
	/**
	 * The current font size of displayed Kana.
	 */
	private float mKanaFontSize = 0.0f;
	
	/**
	 * Relative path to the local folder "fonts" and a font in there. This is the current Kanji font.
	 */
	private String mKanjiFontPath = null;
	
	/**
	 * The font currently used to print Kanji.
	 */
	private Font mKanjiFont = null;
	
	/**
	 * The current font size of displayed Kanji.
	 */
	private float mKanjiFontSize = 0.0f;
	
	/**
	 * Default constructor. Creating a font data set with default settings.
	 */
	public FontContainer()
	{
		this.setKanaFontPath("");
		this.mKanaFontSize = OptionsContainer.defaultKanaFontSize;
		
		this.setKanjiFontPath("");
		this.mKanjiFontSize = OptionsContainer.defaultKanaFontSize;	
	}
	
	/**
	 * Setter Kana font path. Load the font contained in that path.
	 * @param kanaFontPath The path of the font file to load.
	 */
	public void setKanaFontPath(String kanaFontPath)
	{
		if(kanaFontPath == null || kanaFontPath.equals(""))
		{
			// Set to Java default font.
			this.mKanaFontPath = "";
			Font defaultFont = UIManager.getDefaults().getFont("TabbedPane.font");
			this.mKanaFont = defaultFont;
		}
		else
		{
			// Load font from file.
			try
			{
				File fontFile = new File(kanaFontPath);
				
				// Get the relative path to the font folder. Be sure to use uniform seperators.
				String completePath = fontFile.getPath().replace("\\\\", "/");
				completePath = completePath.replace("\\", "/");
				String currentAppPath = Paths.get("").toAbsolutePath().toString().replace("\\", "/");
				this.mKanaFontPath = completePath.replace(currentAppPath + "/", "");
				
				Font tmpFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(tmpFont);
				this.mKanaFont = tmpFont;				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				this.setKanaFontPath("");
			}
		}
	}
	
	/**
	 * Getter Kana font path.
	 * @return The relative path of the currently loaded Kana font file.
	 */
	public String getKanaFontPath()
	{
		return this.mKanaFontPath;
	}
	
	/**
	 * Getter font used to draw japanese text.
	 * @return The currently used font to diplay Kana.
	 */
	public Font getKanaFont()
	{
		return this.mKanaFont;
	}
	
	/**
	 * Setter Kana font size.
	 * @param size The new font size.
	 */
	public void setKanaFontSize(float size)
	{
		// Stay within bounds.
		if(size < 1.0f)
		{
			size = 1.0f;
		}
		if(size > OptionsContainer.maximumKanaFontSize)
		{
			size = OptionsContainer.maximumKanaFontSize;
		}
		
		this.mKanaFontSize = size;
	}
	
	/**
	 * Getter Kana font size.
	 * @return The current font size used for displaying Kana.
	 */
	public float getKanaFontSize()
	{
		return this.mKanaFontSize;
	}
	
	/**
	 * Setter Kana font path. Load the font contained in that path.
	 * @param kanaFontPath The path of the font file to load.
	 */
	public void setKanjiFontPath(String kanjiFontPath)
	{
		if(kanjiFontPath == null || kanjiFontPath.equals(""))
		{
			// Set to Java default font.
			this.mKanjiFontPath = "";
			Font defaultFont = UIManager.getDefaults().getFont("TabbedPane.font");
			this.mKanjiFont = defaultFont;
		}
		else
		{
			// Load font from file.
			try
			{
				File fontFile = new File(kanjiFontPath);
				
				// Get the relative path to the font folder. Be sure to use uniform seperators.
				String completePath = fontFile.getPath().replace("\\\\", "/");
				completePath = completePath.replace("\\", "/");
				String currentAppPath = Paths.get("").toAbsolutePath().toString().replace("\\", "/");
				this.mKanjiFontPath = completePath.replace(currentAppPath + "/", "");
				
				Font tmpFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(tmpFont);
				this.mKanjiFont = tmpFont;				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				this.setKanaFontPath("");
			}
		}
	}
	
	/**
	 * Getter Kana font path.
	 * @return The relative path of the currently loaded Kana font file.
	 */
	public String getKanjiFontPath()
	{
		return this.mKanjiFontPath;
	}
	
	/**
	 * Getter font used to draw japanese text.
	 * @return The currently used font to diplay Kana.
	 */
	public Font getKanjiFont()
	{
		return this.mKanjiFont;
	}
	
	/**
	 * Setter Kana font size.
	 * @param size The new font size.
	 */
	public void setKanjiFontSize(float size)
	{
		// Stay within bounds.
		if(size < 1.0f)
		{
			size = 1.0f;
		}
		if(size > OptionsContainer.maximumKanaFontSize)
		{
			size = OptionsContainer.maximumKanaFontSize;
		}
		
		this.mKanjiFontSize = size;
	}
	
	/**
	 * Getter Kana font size.
	 * @return The current font size used for displaying Kanji.
	 */
	public float getKanjiFontSize()
	{
		return this.mKanjiFontSize;
	}
	
	/**
	 * Write data of the container into a xml-document.
	 */
	public void writeToXml(Document doc)
	{
		// Remove existing node of type.
		this.removeNodesOfType(doc, XML_TAG);
		
		// Create new xml-nodes and add them to the document.
		Element fontElement = doc.createElement(XML_TAG);
		fontElement.appendChild(this.createXmlElement(doc, XML_TAG_KANA_FONT_PATH, this.mKanaFontPath));
		fontElement.appendChild(this.createXmlElement(doc, XML_TAG_KANA_FONT_SIZE, "" + this.mKanaFontSize));
		fontElement.appendChild(this.createXmlElement(doc, XML_TAG_KANJI_FONT_PATH, this.mKanjiFontPath));
		fontElement.appendChild(this.createXmlElement(doc, XML_TAG_KANJI_FONT_SIZE, "" + this.mKanjiFontSize));
		this.addElementToRoot(doc, fontElement);
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
				String path = this.readStringFromXmlNode(xmlChild, XML_TAG_KANA_FONT_PATH);
				this.mKanaFontPath = path == null ? this.mKanaFontPath : path;
				
				Float size = this.readFloatFromXmlNode(xmlChild, XML_TAG_KANA_FONT_SIZE);
				this.mKanaFontSize = size == null ? this.mKanaFontSize : size;
				
				String path2 = this.readStringFromXmlNode(xmlChild, XML_TAG_KANJI_FONT_PATH);
				this.mKanjiFontPath = path2 == null ? this.mKanjiFontPath : path2;
				
				Float size2 = this.readFloatFromXmlNode(xmlChild, XML_TAG_KANJI_FONT_SIZE);
				this.mKanjiFontSize = size2 == null ? this.mKanjiFontSize : size2;
			}
		}
		
		// This will make sure the values are inside the application parameters.
		// (in case of external editing of the xml file)
		this.setKanaFontPath(this.mKanaFontPath);
		this.setKanaFontSize(this.mKanaFontSize);
		this.setKanjiFontPath(this.mKanjiFontPath);
		this.setKanjiFontSize(this.mKanjiFontSize);
	}
}
