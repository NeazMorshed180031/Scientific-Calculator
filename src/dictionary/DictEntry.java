package dictionary;
/**
 * <p>
 * Title: DictEntry Class - A class component of the Dictionary system
 * </p>
 *
 * <p>
 * Description: An entity object class that implements a dictionary entry
 * </p>
 *
 * <p>
 * Copyright: Copyright © 2018-05-05
 * </p>
 *
 * @author Lynn Robert Carter
 * @version 2.00 - Baseline for transition from Swing to JavaFX 2018-05-05
 */

public class DictEntry {
	
	/**********************************************************************************************

	Class Attributes
	
	**********************************************************************************************/
	private String word;			// A dictionary entry's word
	private String definition;		// A dictionary entry's definition
	
	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/
	
	/**********
	 * This is the default constructor.  We do not expect it to be used.
	 */
	public DictEntry() {
		word = "";
		definition = "";
	}
	
	/**********
	 * This is defining constructor.  This is the one we expect people to use.
	 */
	public DictEntry(String w, String d) {
		word = w;
		definition = d;
	}
	
	/**********************************************************************************************

	Standard support methods
	
	**********************************************************************************************/
	
	/**********
	 * This is the debugging toString method.
	 */
	public String toString(){
		return word + "\n" + definition;
	}
	
	/**********
	 * This is the formatted toString method.
	 */
	public String formattedToString(){
		return word + "\n" + definition + "\n--------------------\n";
	}
	
	public void setWord(String w) {
		word =w;
	}
	public void setDefn(String d) {
		definition=d;
	}
	
	
	/**********
	 * These are the getters and setters for the class
	 */
	public String wordToString(){return word + "\n";}
	public String getWord(){return word;}
	public String getDefinition(){return definition;}

}
