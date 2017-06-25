package rjs.container;

import java.awt.event.KeyEvent;
import java.lang.reflect.Type;
import java.util.HashMap;

import org.w3c.dom.Document;

import rjs.panels.session.RandomTextPanel;

/**
 * Data container to store current set of shortcuts used within the application.
 * @author Schlewinow
 */
public class ShortcutContainer extends AbstractDataContainer
{
	/**
	 * Holds the shortcuts availiable in the panel creating random text.
	 * Key is the panel type using the shortcuts.
	 * Value is a hashmap of shortcuts used in the given panel.
	 */
	private HashMap<Type, HashMap<String, Integer>> mShortcuts = null;
	
	/**
	 * Default constructor. Initialize local data containers.
	 * Set stadard shortcut layout.
	 */
	public ShortcutContainer()
	{
		HashMap<String, Integer> randomTextShortcuts = new HashMap<String, Integer>();
		randomTextShortcuts.put("randomButton", KeyEvent.VK_ENTER);
		randomTextShortcuts.put("latinRadio", KeyEvent.VK_Q);
		randomTextShortcuts.put("hiraganaRadio", KeyEvent.VK_W);
		randomTextShortcuts.put("katakanaRadio", KeyEvent.VK_E);
		
		this.mShortcuts = new HashMap<Type, HashMap<String,Integer>>();
		this.mShortcuts.put(RandomTextPanel.class, randomTextShortcuts);
	}
	
	/**
	 * Getter shortcut virtual key.
	 * @param panelType The type of the panel the shortcut is used within.
	 * @param uiElementKey The pseudo-name of the ui-element identified with the shortcut.
	 * @return The integer representing the virtual key that serves as shortcut. -1 in case no virtual key was found.
	 */
	public int getShortcut(Type panelType, String uiElementKey)
	{
		if(this.mShortcuts.containsKey(panelType) && 
			this.mShortcuts.get(panelType).containsKey(uiElementKey))
		{
			return this.mShortcuts.get(panelType).get(uiElementKey);
		}
		
		// Error case: virtual key does not exist in hashmaps.
		return -1;
	}
	
	/**
	 * 
	 */
	public void writeToXml(Document doc)
	{
	}

	/**
	 * 
	 */
	public void readFromXml(Document doc)
	{
	}
}
