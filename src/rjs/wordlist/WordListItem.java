package rjs.wordlist;

// The container of an item in the word list.
public class WordListItem
{
	public static final String emptySign = "-";
	
	// Word in romaji (latin letters).
	private String mRomajiText = "";
	
	// Word in Hiragana.
	private String mHiraganaText = "";
	
	// Word in Katakana.
	private String mKatakanaText = "";
	
	// Word as Kanji.
	private String mKanjiText = "";
	
	// Translation of the word (in non-japanese).
	private String mTranslation = "";
	
	public WordListItem()
	{
		this.mRomajiText = WordListItem.emptySign;
		this.mHiraganaText = WordListItem.emptySign;
		this.mKatakanaText = WordListItem.emptySign;
		this.mKanjiText = WordListItem.emptySign;
		this.mTranslation = WordListItem.emptySign;
	}
	
	// Setter word in latin letters.
	public void setRomajiText(String romaji)
	{
		this.mRomajiText = this.doWordEmptyCheck(romaji);
	}
	
	// Getter word in latin letters.
	public String getRomajiText()
	{
		return this.mRomajiText;
	}
	
	// Setter word in hiragana.
	public void setHiraganaText(String hiragana)
	{
		this.mHiraganaText = this.doWordEmptyCheck(hiragana);
	}
	
	// Getter word in hiragana.
	public String getHiraganaText()
	{
		return this.mHiraganaText;
	}
	
	// Setter word in katakana.
	public void setKatakanaText(String katakana)
	{
		this.mKatakanaText = this.doWordEmptyCheck(katakana);
	}
	
	// Getter word in katakana.
	public String getKatakanaText()
	{
		return this.mKatakanaText;
	}
	
	// Setter word as kanji.
	public void setKanjiText(String kanji)
	{
		this.mKanjiText = this.doWordEmptyCheck(kanji);
	}
	
	// Getter Kanji as string.
	public String getKanjiText()
	{
		return this.mKanjiText;
	}
	
	// Setter word as kanji.
	public void setTranslationText(String translation)
	{
		this.mTranslation = this.doWordEmptyCheck(translation);
	}
	
	// Getter translation text.
	public String getTranslation()
	{
		return this.mTranslation;
	}
	
	// Make sure a word (romaji, hiragana, katakana, kanji, translation) is set properly.
	private String doWordEmptyCheck(String word)
	{
		if(word == null || word == "")
		{
			return WordListItem.emptySign;
		}
		
		return word;
	}
	
	// Output this item in a human readable way.
	public String toString()
	{
		String output = "";
		output += "Romaji: " + this.getRomajiText() + "\n";
		output += "Hiragana: " + this.getHiraganaText() + "\n";
		output += "Katakana: " + this.getKatakanaText() + "\n";
		output += "Kanji: " + this.getKanjiText() + "\n";
		output += "Translation: " + this.getTranslation() + "\n";
		return output;
	}
}
