package rjs.wordlist;

import java.util.ArrayList;

// List of words to be used to learn.
public class WordList
{
	// Core object: list of available words.
	ArrayList<WordListItem> mWordListItems = null;
	
	// Default constructor.
	public WordList()
	{
		this.mWordListItems = new ArrayList<WordListItem>();
	}
	
	// Add a word to the list.
	// Should be used at runtime mainly in the word list editor or when loading a word list.
	public void addWord(WordListItem item)
	{
		this.mWordListItems.add(item);
	}
	
	// Getter word from the list.
	public WordListItem getWordAt(int index)
	{
		return this.mWordListItems.get(index);
	}
	
	// Getter word count in the list.
	public int getWordCount()
	{
		return this.mWordListItems.size();
	}
}
