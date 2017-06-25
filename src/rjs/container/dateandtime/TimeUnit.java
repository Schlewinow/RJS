package rjs.container.dateandtime;

/**
 * Small container class used within Date and Time management.
 * @author Schlewinow
 */
public class TimeUnit
{
	/**
	 * The presentation of the time unit in Kana.
	 */
	private String kana = "";
	
	/**
	 * The presentation of the time unit in Kanji.
	 */
	private String kanji = "";
	
	/**
	 * Constructor. Only way to set the time unit member values.
	 * @param kana The kana-writing of this time unit.
	 * @param kanji The kanji-writing of this time unit.
	 */
	public TimeUnit(String kana, String kanji)
	{
		this.kana = kana;
		this.kanji = kanji;
	}

	/**
	 * Getter Kana representation.
	 * @return Kana representation as string.
	 */
	public String getKana()
	{
		return this.kana;
	}
	
	/**
	 * Getter Kanji representation.
	 * @return Kanji representation as string.
	 */
	public String getKanji()
	{
		return this.kanji;
	}
}
