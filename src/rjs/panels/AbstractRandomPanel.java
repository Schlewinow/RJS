package rjs.panels;

/**
 * Version of abstract panel that adds randomization methods.
 * @author Schlewinow
 */
public class AbstractRandomPanel extends AbstractPanel
{
	/**
	 * Roll a die, get value between one and max eyes.
	 * @param maxEyes maximum value to be returned
	 * @return random integer between zero and maxEyes
	 */
	protected int rollDie(int maxEyes)
	{
		return rollDie(1, maxEyes);
	}
	
	/**
	 * Roll a die, get value between min eyes and max eyes.
	 * @param minEyes minimum value to be returned
	 * @param maxEyes maximum value to be returned
	 * @return random integer between zero and maxEyes
	 */
	protected int rollDie(int minEyes, int maxEyes)
	{
		return (int)(minEyes + Math.round(Math.random() * (maxEyes - minEyes)));
	}
}
