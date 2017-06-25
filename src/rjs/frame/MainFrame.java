package rjs.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import rjs.container.*;
import rjs.container.dateandtime.DateAndTimeContainer;
import rjs.container.interfaces.IContainerManager;
import rjs.panels.*;
import rjs.panels.interfaces.*;
import rjs.panels.options.*;
import rjs.panels.session.*;


/**
 * The main frame.
 * This class handles the different RJS-panels by allowing the user to switch between them.
 * It also acts as mediator between the different panels.
 * @author Schlewinow
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame implements IMainFrame
{
	/**
	 * Current version number of the nice application RJS.
	 */
	private final String mVersionNumber = "0.4.2";
	
	/**
	 * UI-elements to create and visualize random output.
	 * There may only be a single RJS-panel active at a time.
	 */
	private JPanel mCurrentPanel;
	
	/**
	 * Strores the different options and runtime data used by the panels.
	 * These data are stored in so called data containers.
	 */
	private IContainerManager mContainerManager;
	
	/**
	 * Default constructor.
	 * Initialize the container manager and it's containers.
	 * Initialize the main frame and show the loading screen.
	 */
	public MainFrame()
	{
		this.mContainerManager = new ContainerManager();
		
		this.mContainerManager.addContainer(new OptionsContainer());
		this.mContainerManager.addContainer(new FontContainer());
		this.mContainerManager.addContainer(new LetterContainer());
		this.mContainerManager.addContainer(new NumberContainer());
		this.mContainerManager.addContainer(new DateAndTimeContainer());
		this.mContainerManager.addContainer(new WordListContainer());
		this.mContainerManager.addContainer(new ShortcutContainer());
		
		this.mContainerManager.loadContainerFromXml(OptionsContainer.class);
		this.mContainerManager.loadContainerFromXml(FontContainer.class);
		this.mContainerManager.loadContainerFromXml(LetterContainer.class);
		this.mContainerManager.loadContainerFromXml(NumberContainer.class);
		this.mContainerManager.loadContainerFromXml(DateAndTimeContainer.class);
		this.mContainerManager.loadContainerFromXml(WordListContainer.class);
		this.mContainerManager.loadContainerFromXml(ShortcutContainer.class);
		
		// Set window icon. The icon is not necessary, so just try-catch that like there is now tommorow.
		try
		{
			ImageIcon icon = new ImageIcon("images/icon.png");
			this.setIconImage(icon.getImage());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		// Frame properties.
		this.setTitle("Random-J-Shit " + this.mVersionNumber);
		this.setSize(600, 400);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		StartScreenPanel startScreen = new StartScreenPanel();
		startScreen.initialize(this);
		this.mCurrentPanel = startScreen.getSwingPanel();
		
		this.add(this.createMenu(), BorderLayout.NORTH);
		this.add(mCurrentPanel, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	/**
	 * Getter ContainerManager.
	 * The container manager may be used to access various specialized data containers.
	 * These containers are used to interchange data between panels and permanently store them.
	 * @return Reference to the ContainerManager managed by the main frame.
	 */
	public IContainerManager getContainerManager()
	{
		return this.mContainerManager;
	}
	
	/**
	 * Creates the menu bar used by the main frame.
	 * Any panel in RJS is accessed this way.
	 * @return A menu bar containing the menu of the main frame.
	 */
	private JMenuBar createMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		
		// Menu to start a new session of any kind.
		JMenu menuStart = new JMenu();
		menuStart.setText("Start");
		menuBar.add(menuStart);
		
		// Menu to change and apply options.
		JMenu menuOptions = new JMenu();
		menuOptions.setText("Options");
		menuBar.add(menuOptions);
		
		this.addMenuItem(menuStart, new RandomTextPanel(), "randomized letter session");
		this.addMenuItem(menuStart, new RandomNumberPanel(), "randomized number session");
		this.addMenuItem(menuStart, new RandomDateAndTimePanel(), "randomized date and time session");
		this.addMenuItem(menuStart, new RandomWordPanel(), "randomized word session");
		
		this.addMenuItem(menuOptions, new OptionsPanel(), "Options");
		this.addMenuItem(menuOptions, new FontOptionsPanel(), "Choose fonts");
		this.addMenuItem(menuOptions, new ChooseLetterPanel(), "Choose letters");
		this.addMenuItem(menuOptions, new NumberOptionsPanel(), "Number options");
		this.addMenuItem(menuOptions, new ChooseWordlistPanel(), "Choose word list");
		
		return menuBar;
	}
	
	/**
	 * Small helper to add a menu item to a menu that switches to a given panel.
	 * @param menu The JMenu to add the newly created JMenuItem to.
	 * @param panel The RJS-panel that should be triggered by the menu item.
	 * @param caption The caption of the menu item.
	 */
	private void addMenuItem(JMenu menu, IPanel panel, String caption)
	{
		// Create menu item.
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText(caption);
		menuItem.addActionListener(new SwitchToPanelListener(panel));
		
		// Add item to menu.
		menu.add(menuItem);
	}
	
	/**
	 * Switch the currently shown panel to another one.
	 * @param panel The RJS-panel to switch to.
	 */
	public void switchToPanel(IPanel panel)
	{
		panel.initialize(this);
		
		this.remove(this.mCurrentPanel);
		this.mCurrentPanel = panel.getSwingPanel();
		this.add(this.mCurrentPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	/**
	 * ActionListener to switch the panel when a menu item is clicked.
	 * @author Schlewinow
	 */
	private class SwitchToPanelListener implements ActionListener
	{
		/**
		 * The panel to switch to.
		 */
		private IPanel mPanel;
		
		/**
		 * Constructor needs panel to switch to. Otherwise it would be rather useless.
		 * @param panel The panel to switch to once the callback is called.
		 */
		public SwitchToPanelListener(IPanel panel)
		{
			this.mPanel = panel;
		}

		/**
		 * ActionListener callback method.
		 * If a menu item is clicked, switch to the given panel.
		 */
		public void actionPerformed(ActionEvent e)
		{	
			// Do switching.
			MainFrame.this.switchToPanel(this.mPanel);
		}
	}
}
