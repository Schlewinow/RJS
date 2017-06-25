package rjs.container.interfaces;

import org.w3c.dom.Document;

/**
 * Contains a set of data necessary to configure a specific option set (e.g. shortcuts, wordlist, Kana)
 * @author Schlewinow
 */
public interface IDataContainer
{
	/**
	 * The root tag of the xml config file.
	 */
	String XML_ROOT = "config"; 
	
	/**
	 * Write data to xml-document.
	 * @param doc The XML-document to append the data of the container to.
	 */
	public void writeToXml(Document doc);
	
	/**
	 * Read data from xml-document.
	 * @param doc The XML-document to read the of the container data from.
	 */
	public void readFromXml(Document doc);
}
