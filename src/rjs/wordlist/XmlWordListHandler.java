package rjs.wordlist;

import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Does the XML-file management part like loading and saving a word list.
public class XmlWordListHandler
{
	// Read a word list from an XML-file.
	public static WordList readFromFile(String filepath)
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(filepath);
			
			NodeList nodes = doc.getElementsByTagName("item");
			WordList wordList = new WordList();
			
			for(int index = 0; index < nodes.getLength(); ++index)
			{
				// Iterate over nodes of type "item" and generate word list items.
				Node xmlNode = nodes.item(index);
				WordListItem item = new WordListItem();
				
				NodeList children = xmlNode.getChildNodes();
				for(int childIndex = 0; childIndex < children.getLength(); ++childIndex)
				{
					Node xmlChild = children.item(childIndex);
					
					// Read as many values as possible.
					if(xmlChild.getNodeName().equals("romaji"))
					{
						item.setRomajiText(xmlChild.getTextContent());
					}
					if(xmlChild.getNodeName().equals("hiragana"))
					{
						item.setHiraganaText(xmlChild.getTextContent());
					}
					if(xmlChild.getNodeName().equals("katakana"))
					{
						item.setKatakanaText(xmlChild.getTextContent());
					}
					if(xmlChild.getNodeName().equals("kanji"))
					{
						item.setKanjiText(xmlChild.getTextContent());
					}
					if(xmlChild.getNodeName().equals("translation"))
					{
						item.setTranslationText(xmlChild.getTextContent());
					}
				}
				
				wordList.addWord(item);
			}
			
			return wordList;
		}
		catch(FileNotFoundException fnfex)
		{
			System.out.println("Former word list not found, reset active word list.");
			return new WordList();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return new WordList();
		}
	}
}
