package rjs.kanjilist;

/**
 * Single item of a kanji learning list.
 * @author Schlewinow
 */
public class KanjiListItem
{
	public static final String EMPTY_SIGN = "-";
	
	private String kanji = EMPTY_SIGN;
	private String onReadings = EMPTY_SIGN;
	private String kunReadings = EMPTY_SIGN;
	private String meanings = EMPTY_SIGN;
	private String remarks = EMPTY_SIGN;
	
	/**
	 * Constructor to initialize item with every possible value set manually.
	 * @param kanji Single kanji letter.
	 * @param onReadings On readings of the given kanji.
	 * @param kunReadings Kun readings of the given kanji.
	 * @param meanings Meanings of the given kanji.
	 * @param remarks Aditional remarks. Optional parameter for user preference.
	 */
	public KanjiListItem(String kanji, String onReadings, String kunReadings, String meanings, String remarks)
	{
		this.kanji = kanji;
		this.onReadings = onReadings;
		this.kunReadings = kunReadings;
		this.meanings = meanings;
		this.remarks = remarks;
	}
	
	/**
	 * Getter kanji.
	 * @return The single kanji letter managed by this list item.
	 */
	public String getKanji()
	{
		return this.kanji;
	}
	
	/**
	 * Getter On reading.
	 * @return The On reading(s) of the kanji managed by this list item.
	 */
	public String getOnReadings()
	{
		return this.onReadings;
	}
	
	/**
	 * Getter Kun reading.
	 * @return The Kun reading(s) of the kanji managed by this list item.
	 */
	public String getKunReadings()
	{
		return this.kunReadings;
	}
	
	/**
	 * Getter kanji meaning.
	 * @return The meaning(s) of the kanji managed by this list item.
	 */
	public String getMeanings()
	{
		return this.meanings;
	}
	
	/**
	 * Getter remarks.
	 * @return Additional remarks on the kanji managed by this list item.
	 */
	public String getRemarks()
	{
		return this.remarks;
	}
	
	/**
	 * Output this item in a human readable way.
	 */
	@Override
	public String toString()
	{
		String output = "";
		output += "Kanji: " + this.getKanji() + "\n";
		output += "On Reading: " + this.getOnReadings() + "\n";
		output += "Kun Reading: " + this.getKunReadings() + "\n";
		output += "Meaning: " + this.getMeanings() + "\n";
		output += "Remarks: " + this.getRemarks() + "\n";
		return output;
	}
}
