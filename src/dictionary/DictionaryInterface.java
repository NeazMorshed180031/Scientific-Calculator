package dictionary;

// import gui.WindowManager;

/**
 * <p>
 * Title: Dictionary Interface - A component of the Dictionary system
 * </p>
 *
 * <p>
 * Description: The interface between the Dictionary and user interfaces (textual and/or graphical)
 * </p>
 *
 * <p>
 * Copyright: Copyright © 2018
 * </p>
 *
 * @author Lynn Robert Carter
 * @version 2.00 - Baseline for transition from Swing to JavaFX 2018-05-05
 */

public interface DictionaryInterface {
	
	/** Add a dictionary entry to the dictionary
	 * 
	 * @param w	is the word being added to the dictionary
	 * @param d	is the definition of the word being added to the dictionary
	 */
	public void addEntry(String w, String d);
	
	
	/** Provides the number of entries in the dictionary as the returned value.
	 * 
	 * @return the number of dictionary entries
	 */
	public int getNumEntries();
	
	/** Resets internal state attributes so a sequence of findNextEntry calls can return
	 * all of the entries whose word contains the specified string (somewhere in the word)
	 * at least once
	 * 
	 * @param s is the string that must be found in the word
	 */
	public void setSearchString(String s);
	
	/** Return the next dictionary entry whose word contains the String specified by the
	 * most recent call to the setSearchString method call.  Each call to the findNextEntry
	 * methods returns the next dictionary entry from the sequence of entries containing the
	 * string.  After the last dictionary entry is returned (or if no dictionary entry 
	 * contains the specified string), a null is returned.
	 * 
	 * @return the found DictEntry to the caller
	 */
	public DictEntry findNextEntry();
	
	/** Return a formatted String of the sequence of dictionary entries whose words contain
	 * the target String in the order they were found in the dictionary and separated by
	 * a line containing a number of dashes.
	 * 
	 * @param target is the String used to find the sequence of words.
	 * @return the formatted string to the caller
	 */
	public String findAll(String target);
	
	/** Return a DictEntry object specified by the zero-based index ndx.
	 * 
	 * @param ndx is the zero-based index of the DictEntry object to be returned.
	 * @return the specified DictEntry to the caller. If the index is negative or
	 * 		larger than the number of elements in the dictionary, null should be
	 * 		returned.
	 */
	public DictEntry getDictEntry(int ndx);
	
	/** Remove a DictEntry from the dictionary that has been most recently found by
	 * the findNextEntry method. If that method has not been called or it did not
	 * find a DictEntry, this method should just return. If the method has been
	 * called and a DictEntry had been found, this remove routine should remove it
	 * and update the dictionary's attributes to reflect the change.
	 * 
	 */
	public void remove();
	
	/** Returns a formatted String containing all of the entries from the dictionary
	 * 
	 * @return the formatted String
	 */
	public String listAll();
	
	/**********
	 * Get the index of the current entry
	 * 
	 * @return - an index
	 */
	public int getCurrentIndex();
	
	/**********
	 * Set the index of the current entry
	 * 
	 * @return - an index
	 */
	public void setCurrentIndex(int ndx);

}
