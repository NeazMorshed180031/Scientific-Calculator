package calculator;

import java.util.Scanner;

import uNumberLibrary.UNumber;

/**
 * <p>
 * Title: CalculatorValue Class.
 * </p>
 * 
 * <p>
 * Description: A component of a JavaFX demonstration application that performs
 * computations
 * </p>
 *  * @author Neaz Morshed
 * This Calculator can store data from the user in a particular path. The user can edit the table and update data as well as search
 * data. The user can also remove data according to his own will.
 * @version 4.00
 * <p>
 * Copyright: Lynn Robert Carter � 2017
 * </p>
 *

 */

public class CalculatorValue {

	/**********************************************************************************************
	 * 
	 * Attributes
	 * 
	 **********************************************************************************************/

	// These are the major values that define a calculator value
	double measuredValue = 0;
	String errorMessage = "";
	double errorValue = 0.05;

	// These are the major values of UNumber that define a calculator value
	UNumber measuredValue1 = new UNumber(0);
	static UNumber errorValue1 = new UNumber(0.05);

	private static int IN_Operand1;
	private static int IN_Operand2;

	private static String unit = "";

	private String[] operand_units = { "m", "km", "s", "min", "h", "day", "Fahrenheit","Celsius", "No Unit" };

	public static double conversionTable[][] = {

			{ 1, 0.001, 1, 1, 1, 1, 1, 1, 1 }, /* Km */ 
			{ 1000, 1, 1, 1, 1, 1, 1, 1, 1 }, // This is for meter
			{ 1, 1, 1, 0.0166667, 0.00027777833333, 1.157409722208333465e-5, 1, 1, 1 }, // This is for sec
			{ 1, 1, 60, 1, 0.0166666999998, 06.9444583332500006527e-4, 1, 1, 1 }, // This is for min
			{ 1, 1, 3600, 60, 1, 0.041666749999500006518, 1, 1, 1 }, // This is for hours
			{ 1, 1, 86400, 1440, 24, 1, 1, 1, 1 }, // This is for days
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },// This is for Fahrenheit
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1 },// This is for Celsius
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }};// This is for No Unit

	private boolean lookUpTableAddSub[][] =

			{ { true, true, false, false, false, false, false, false, false }, // This is for meter
					{ true, true, false, false, false, false, false, false, false }, // This is for Km
					{ false, false, true, true, true, true, false, false, false }, // This is for sec
					{ false, false, true, true, true, true, false, false, false }, // This is for min
					{ false, false, true, true, true, true, false, false, false }, // This is for hours
					{ false, false, true, true, true, true, false, false, false }, // This is for days
					{ false, false, true, true, true, true, false, false, false }, // This is for Fahrenheit
					{ false, false, true, true, true, true, false, false, false }, // This is for Celsius
					{ false, false, false, false, false, false, true, true, true } };// This is for No Unit

	/**********************************************************************************************
	 * 
	 * Constructors
	 * 
	 **********************************************************************************************/

	/*****
	 * This is the default constructor
	 */
	public CalculatorValue() {
	}

	/*****
	 * This constructor creates a calculator value based on a double. For future
	 * calculators, it is best to avoid using this constructor.
	 */
	public CalculatorValue(double v, double e) {
		measuredValue = v;
		errorValue = e;
	}

	/*****
	 * This copy constructor creates a duplicate of an already existing calculator
	 * value
	 */
	public CalculatorValue(CalculatorValue v) {
		measuredValue = v.measuredValue;
		errorValue = v.errorValue;
		errorMessage = v.errorMessage;
	}

	/*****
	 * This constructor creates a calculator value from a string... Due to the
	 * nature of the input, there is a high probability that the input has errors,
	 * so the routine returns the value with the error message value set to empty or
	 * the string of an error message.
	 */
	public CalculatorValue(String s, String e) {
		measuredValue = 0;
		if (s.length() == 0.0) { // If there is nothing there,
			errorMessage = "***Error*** Input is empty"; // signal an error
			return;
		}
		// If the first character is a plus sign, ignore it.
		int start = 0; // Start at character position zero
		boolean negative = false; // Assume the value is not negative
		if (s.charAt(start) == '+') // See if the first character is '+'
			start++; // If so, skip it and ignore it

		// If the first character is a minus sign, skip over it, but remember it
		else if (s.charAt(start) == '-') { // See if the first character is '-'
			start++; // if so, skip it
			negative = true; // but do not ignore it
		}

		// See if the user-entered string can be converted into an Double value
		Scanner tempScanner = new Scanner(s.substring(start));// Create scanner for the digits
		if (!tempScanner.hasNextDouble()) { // See if the next token is a valid
			errorMessage = "This character may only be an \"E\", an \"e\", a digit , " + "a \".\", or "
					+ "it must be the end of the input. \n ";

			// double value. If not, signal there
			tempScanner.close(); // return a zero
			return;
		}

		// Convert the user-entered string to a double value and see if something else
		// is following it
		measuredValue = tempScanner.nextDouble(); // Convert the value and check to see
		if (tempScanner.hasNext()) { // that there is nothing else is
			errorMessage = "***Error*** Excess data"; // following the value. If so, it
			tempScanner.close(); // is an error. Therefore we must
			measuredValue = 0; // return a zero value.
			return;
		}
		tempScanner.close();
		errorMessage = "";
		if (negative) // Return the proper value based
			measuredValue = -measuredValue; // on the state of the flag that

		/*
		 * Below is the code for the input of error term in the above constructor to
		 * accept error term
		 */
		errorValue = 0.05;
		boolean negative1 = false;
		if (e.length() == 0.0) { // If there is nothing there,
			errorMessage = ""; // signal an error
			return;
		}
		// If the first character is a plus sign, ignore it.
		int start1 = 0; // Start at character position zero
		// Assume the value is not negative
		if (e.charAt(start1) == '+') // See if the first character is '+'
			start1++; // If so, skip it and ignore it

		// If the first character is a minus sign, skip over it, but remember it
		else if (e.charAt(start1) == '-') { // See if the first character is '-'
			start1++; // if so, skip it
			negative1 = true; // but do not ignore it
		}

		// See if the user-entered string can be converted into an Double value
		Scanner tempScanner1 = new Scanner(e.substring(start));// Create scanner for the digits
		if (!tempScanner1.hasNextDouble()) { // See if the next token is a valid
			errorMessage = "This character may only be an \"E\", an \"e\", a digit , " + "a \".\", or "
					+ "it must be the end of the input. \n ";
			// double value. If not, signal there
			tempScanner1.close(); // return a zero
			return;
		}

		// Convert the user-entered string to a double value and see if something else
		// is following it
		errorValue = tempScanner1.nextDouble(); // Convert the value and check to see
		if (tempScanner1.hasNext()) { // that there is nothing else is
			errorMessage = "***Error*** Excess data"; // following the value. If so, it
			tempScanner1.close(); // is an error. Therefore we must
			errorValue = 0.05; // return a zero value.
			return;
		}
		tempScanner1.close();
		errorMessage = "";
		if (negative1) // Return the proper value based
			errorValue = -errorValue; // on the state of the flag that
	}

	/**********************************************************************************************
	 * 
	 * Getters and Setters
	 * 
	 **********************************************************************************************/

	/*****
	 * This is the start of the getters and setters
	 * 
	 * Get the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/*****
	 * Set the current value of a calculator value to a specific long integer
	 */
	public void setValue(double v) {
		measuredValue = v;
	}

	/*****
	 * Set the current value of a calculator error message to a specific string
	 */
	public void setErrorMessage(String m) {
		errorMessage = m;
	}

	/*****
	 * Set the current value of a calculator value to the value of another (copy)
	 */
	public void setValue(CalculatorValue v) {
		measuredValue = v.measuredValue;
		errorMessage = v.errorMessage;
	}

	/*****
	 * This is the toString method
	 * 
	 * When UNumber Value is passed it convert the measuredValue to decimal form
	 */
	public String toString() {
		return measuredValue1.toStringDecimal();
	}

	/*****
	 * This is the toString method
	 * 
	 * When UNumber Value is passed it convert the errorTerm to decimal form
	 */
	public static String toStringErrorTerm() {
		return errorValue1.toStringDecimal();

	}

	/*****
	 * This is the default toString method
	 * 
	 * When more complex calculator values are creating this routine will need to be
	 * updated
	 */
	public String debugToString() {
		return "measuredValue = " + measuredValue + "\nerrorMessage = " + errorMessage + "\n";
	}

	/*****
	 * This is the debug toString method
	 * 
	 * When more complex calculator values are creating this routine will need to be
	 * updated
	 */
	public String debugToStringErrTerm() {
		return "errorTerm = " + errorValue + "\n";
	}

	public void setIndexofUnits(int ndx1, int ndx2) {
		IN_Operand1 = ndx1;
		IN_Operand2 = ndx2;
	}

	public boolean setUnitcheck() {
		return lookUpTableAddSub[IN_Operand1][IN_Operand2];
	}

	public static String unitOfResult() {
		return unit;
	}

	public void setUnitAddSub() {
		if (IN_Operand1 < IN_Operand2)
			unit = operand_units[IN_Operand1];
		else
			unit = operand_units[IN_Operand2];
	}

	/**********************************************************************************************
	 * 
	 * Result attributes to be used for GUI applications where the returned string
	 * error message and pointer to the character of the error are not adequate for
	 * the required output.
	 * 
	 */

	public static String measuredValueErrorMessage = ""; // The alternate error message text
	public static String measuredValueInput = ""; // The input being processed
	public static int measuredValueIndexofError = -1; // The index where the error was located
	private static int state = 0; // The current state value
	private static int nextState = 0; // The next state value
	private static String inputLine = ""; // The input line
	private static char currentChar; // The current character in the line
	private static int currentCharNdx; // The index of the current character
	private static boolean running; // The flag that specifies if it is running

	/**********
	 * This private method display the input line and then on a line under it
	 * displays an up arrow at the point where an error was detected. This method is
	 * designed to be used to display the error message on the console terminal.
	 * 
	 * @param input          The input string
	 * @param currentCharNdx The location where an error was found
	 * @return Two lines, the entire input line followed by a line with an up arrow
	 */
	private static String displayInput(String input, int currentCharNdx) {
		// Display the entire input line
		String result = input + "\n";

		// Display a line with enough spaces so the up arrow point to the point of an
		// error
		for (int ndx = 0; ndx < currentCharNdx; ndx++)
			result += " ";

		// Add the up arrow to the end of the second line
		return result + "\u21EB"; // A Unicode up arrow with a base
	}

	/*
	 * private static void displayDebuggingInfo() { if (currentCharNdx >=
	 * inputLine.length()) System.out.println(((state < 10) ? "  " : " ") + state +
	 * ((finalState) ? "       F   " : "           ") + "None"); else
	 * System.out.println(((state < 10) ? "  " : " ") + state + ((finalState) ?
	 * "       F   " : "           ") + "  " + currentChar + " " + ((nextState < 10)
	 * && (nextState != -1) ? "    " : "   ") + nextState ); }
	 */

	private static void moveToNextCharacter() {
		currentCharNdx++;
		if (currentCharNdx < inputLine.length())
			currentChar = inputLine.charAt(currentCharNdx);
		else {
			currentChar = ' ';
			running = false;
		}
	}

	/**********
	 * This method is a mechanical transformation of a Finite State Machine diagram
	 * into a Java method.
	 * 
	 * @param input The input string for the Finite State Machine
	 * @return An output string that is empty if every things is okay or it will be
	 *         a string with a help description of the error follow by two lines
	 *         that shows the input line follow by a line with an up arrow at the
	 *         point where the error was found.
	 */
	public static String checkMeasureValue(String input) {
		if (input.length() <= 0)
			return "";
		// The following are the local variable used to perform the Finite State Machine
		// simulation
		state = 0; // This is the FSM state number
		inputLine = input; // Save the reference to the input line as a global
		currentCharNdx = 0; // The index of the current character
		currentChar = input.charAt(0); // The current character from the above indexed position

		// The Finite State Machines continues until the end of the input is reached or
		// at some
		// state the current character does not match any valid transition to a next
		// state

		measuredValueInput = input; // Set up the alternate result copy of the input
		running = true; // Start the loop
		// System.out.println("\nCurrent Final Input Next\nState State Char State");

		// The Finite State Machines continues until the end of the input is reached or
		// at some
		// state the current character does not match any valid transition to a next
		// state
		while (running) {
			// The switch statement takes the execution to the code for the current state,
			// where
			// that code sees whether or not the current character is valid to transition to
			// a
			// next state
			switch (state) {
			case 0:
				// State 0 has three valid transitions. Each is addressed by an if statement.

				// If the current character is in the range from 1 to 9, it transitions to state
				// 1
				if (currentChar >= '0' && currentChar <= '9') {
					nextState = 1;
					break;
				}
				// If the current character is a decimal point, it transitions to state 3
				else if (currentChar == '.') {
					nextState = 3;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					running = false;

				// The execution of this state is finished
				break;

			case 1:
				// State 1 has three valid transitions. Each is addressed by an if statement.

				// In state 1, if the character is 0 through 9, it is accepted and we stay in
				// this
				// state
				if (currentChar >= '0' && currentChar <= '9') {
					nextState = 1;
					break;
				}

				// If the current character is a decimal point, it transitions to state 2
				else if (currentChar == '.') {
					nextState = 2;
					break;
				}
				// If the current character is an E or an e, it transitions to state 5
				else if (currentChar == 'E' || currentChar == 'e') {
					nextState = 5;
					break;
				}
				// If it is none of those characters, the FSM halts
				else
					running = false;

				// The execution of this state is finished
				break;

			case 2:
				// State 2 has two valid transitions. Each is addressed by an if statement.

				// If the current character is in the range from 1 to 9, it transitions to state
				// 1
				if (currentChar >= '0' && currentChar <= '9') {
					nextState = 2;
					break;
				}
				// If the current character is an 'E' or 'e", it transitions to state 5
				else if (currentChar == 'E' || currentChar == 'e') {
					nextState = 5;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					running = false;

				// The execution of this state is finished
				break;

			case 3:
				// State 3 has only one valid transition. It is addressed by an if statement.

				// If the current character is in the range from 1 to 9, it transitions to state
				// 1
				if (currentChar >= '0' && currentChar <= '9') {
					nextState = 4;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					running = false;

				// The execution of this state is finished
				break;

			case 4:
				// State 4 has two valid transitions. Each is addressed by an if statement.

				// If the current character is in the range from 1 to 9, it transitions to state
				// 4
				if (currentChar >= '0' && currentChar <= '9') {
					nextState = 4;
					break;
				}
				// If the current character is an 'E' or 'e", it transitions to state 5
				else if (currentChar == 'E' || currentChar == 'e') {
					nextState = 5;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					running = false;

				// The execution of this state is finished
				break;

			case 5:
				if (currentChar >= '0' && currentChar <= '9') {
					nextState = 7;
					break;
				} else if (currentChar == '+' || currentChar == '-') {
					nextState = 6;
					break;
				}
				break;

			case 6:
				if (currentChar >= '0' && currentChar <= '9') {
					nextState = 7;
					break;
				} else
					running = false;
				break;

			case 7:
				if (currentChar >= '0' && currentChar <= '9') {
					nextState = 7;
					break;
				} else
					running = false;
				break;
			}

			if (running) {
				// displayDebuggingInfo();
				// When the processing of a state has finished, the FSM proceeds to the next
				// character
				// in the input and if there is one, it fetches that character and updates the
				// currentChar. If there is no next character the currentChar is set to a blank.
				moveToNextCharacter();

				// Move to the next state
				state = nextState;

			}
			// Should the FSM get here, the loop starts again

		}

		// System.out.println("The loop has ended.");

		measuredValueIndexofError = currentCharNdx; // Copy the index of the current character;

		// When the FSM halts, we must determine if the situation is an error or not.
		// That depends
		// of the current state of the FSM and whether or not the whole string has been
		// consumed.
		// This switch directs the execution to separate code for each of the FSM
		// states.
		switch (state) {
		case 0:
			// State 0 is not a final state, so we can return a very specific error message
			measuredValueIndexofError = currentCharNdx; // Copy the index of the current character;
			measuredValueErrorMessage = "The first character must be a digit";
			return "The first character must be a digit or a decimal point.";

		case 1:
			// State 1 is a final state, so we must see if the whole string has been
			// consumed.
			if (currentCharNdx < input.length()) {
				// If not all of the string has been consumed, we point to the current character
				// in the input line and specify what that character must be in order to move
				// forward.
				measuredValueErrorMessage = "This character may only be an \"E\", an \"e\", a digit, "
						+ "a \".\", or it must be the end of the input.\n";
				return measuredValueErrorMessage + displayInput(input, currentCharNdx);
			} else {
				measuredValueIndexofError = -1;
				measuredValueErrorMessage = "";
				return measuredValueErrorMessage;
			}

		case 2:
		case 4:
			// States 2 and 4 are the same. They are both final states with only one
			// possible
			// transition forward, if the next character is an E or an e.
			if (currentCharNdx < input.length()) {
				measuredValueErrorMessage = "This character may only be an \"E\", an \"e\", or it must"
						+ " be the end of the input.\n";
				return measuredValueErrorMessage + displayInput(input, currentCharNdx);
			}
			// If there is no more input, the input was recognized.
			else {
				measuredValueIndexofError = -1;
				measuredValueErrorMessage = "";
				return measuredValueErrorMessage;
			}
		case 3:
		case 6:
			// States 3, and 6 are the same. None of them are final states and in order to
			// move forward, the next character must be a digit.
			measuredValueErrorMessage = "This character may only be a digit.\n";
			return measuredValueErrorMessage + displayInput(input, currentCharNdx);

		case 7:
			// States 7 is similar to states 3 and 6, but it is a final state, so it must be
			// processed differently. If the next character is not a digit, the FSM stops
			// with an
			// error. We must see here if there are no more characters. If there are no more
			// characters, we accept the input, otherwise we return an error
			if (currentCharNdx < input.length()) {
				measuredValueErrorMessage = "This character may only be a digit.\n";
				return measuredValueErrorMessage + displayInput(input, currentCharNdx);
			} else {
				measuredValueIndexofError = -1;
				measuredValueErrorMessage = "";
				return measuredValueErrorMessage;
			}

		case 5:
			// State 5 is not a final state. In order to move forward, the next character
			// must be
			// a digit or a plus or a minus character.
			measuredValueErrorMessage = "This character may only be a digit, a plus, or minus " + "character.\n";
			return measuredValueErrorMessage + displayInput(input, currentCharNdx);
		default:
			return "";
		}
	}

	/**********
	 * This method is a mechanical transformation of a Finite State Machine diagram
	 * into a Java method.
	 * 
	 * @param input The input string for the Finite State Machine
	 * @return An output string that is empty if every things is okay or it will be
	 *         a string with a help description of the error follow by two lines
	 *         that shows the input line follow by a line with an up arrow at the
	 *         point where the error was found.
	 */

	public static String errorTermErrorMessage = ""; // The alternate error message text
	public static String errorTermInput = ""; // The input being processed
	public static int errorTermIndexofError = -1; // The index where the error was located
	private static int state1 = 0; // The current state value
	private static int nextState1 = 0; // The next state value
	private static String inputLine1 = ""; // The input line
	private static char currentChar1; // The current character in the line
	private static int currentCharNdx1; // The index of the current character
	private static boolean running1; // The flag that specifies if it is running

	/**********
	 * This private method display the input line and then on a line under it
	 * displays an up arrow at the point where an error was detected. This method is
	 * designed to be used to display the error message on the console terminal.
	 * 
	 * @param input           The input string
	 * @param currentCharNdx1 The location where an error was found
	 * @return Two lines, the entire input line followed by a line with an up arrow
	 */
	private static String displayInput1(String input, int currentCharNdx1) {
		// Display the entire input line
		String result = input + "\n";

		// Display a line with enough spaces so the up arrow point to the point of an
		// error
		for (int ndx = 0; ndx < currentCharNdx1; ndx++)
			result += " ";

		// Add the up arrow to the end of the second line
		return result + "\u21EB"; // A Unicode up arrow with a base
	}

	/*
	 * private static void displayDebuggingInfo() { if (currentCharNdx1 >=
	 * inputLine1.length()) System.out.println(((state1 < 10) ? "  " : " ") + state1
	 * + ((finalState1) ? "       F   " : "           ") + "None"); else
	 * System.out.println(((state < 10) ? "  " : " ") + state + ((finalState) ?
	 * "       F   " : "           ") + "  " + currentChar + " " + ((nextState < 10)
	 * && (nextState != -1) ? "    " : "   ") + nextState ); }
	 */

	private static void moveToNextCharacter1() {
		currentCharNdx1++;
		if (currentCharNdx1 < inputLine1.length())
			currentChar1 = inputLine1.charAt(currentCharNdx1);
		else {
			currentChar1 = ' ';
			running1 = false;
		}
	}

	/**********
	 * This method is a mechanical transformation of a Finite State Machine diagram
	 * into a Java method.
	 * 
	 * @param input The input string for the Finite State Machine
	 * @return An output string that is empty if every things is okay or it will be
	 *         a string with a help description of the error follow by two lines
	 *         that shows the input line follow by a line with an up arrow at the
	 *         point where the error was found.
	 */
	public static String checkErrorTerm(String input) {
		if (input.length() <= 0)
			return "";
		// The following are the local variable used to perform the Finite State Machine
		// simulation
		state1 = 0; // This is the FSM state number
		inputLine1 = input; // Save the reference to the input line as a global
		currentCharNdx1 = 0; // The index of the current character
		currentChar1 = input.charAt(0); // The current character from the above indexed position

		// The Finite State Machines continues until the end of the input is reached or
		// at some
		// state the current character does not match any valid transition to a next
		// state

		errorTermInput = input; // Set up the alternate result copy of the input
		running1 = true; // Start the loop
		// System.out.println("\nCurrent Final Input Next\nState State Char State");

		// The Finite State Machines continues until the end of the input is reached or
		// at some
		// state the current character does not match any valid transition to a next
		// state
		while (running1) {
			// The switch statement takes the execution to the code for the current state,
			// where
			// that code sees whether or not the current character is valid to transition to
			// a
			// next state
			switch (state1) {
			case 0:
				// State 0 has three valid transitions. Each is addressed by an if statement.

				// If the current character is in the range from 1 to 9, it transitions to state
				// 1
				if (currentChar1 >= '1' && currentChar1 <= '9') {
					nextState1 = 1;
					break;
				}
				// If the current character is a decimal point, it transitions to state 3
				else if (currentChar1 == '.') {
					nextState1 = 3;
					break;
				} else if (currentChar1 == '0') {
					nextState1 = 8;
				}

				// If it is none of those characters, the FSM halts
				else
					running1 = false;

				// The execution of this state is finished
				break;

			case 1:
				// State 1 has three valid transitions. Each is addressed by an if statement.

				// In state 1, if the character is 0 through 9, it is accepted and we stay in
				// this
				// state
				if (currentChar1 >= '0' && currentChar1 <= '9') {
					nextState1 = 1;
					break;
				}

				// If the current character is a decimal point, it transitions to state 2
				else if (currentChar1 == '.') {
					nextState1 = 2;
					break;
				}
				// If the current character is an E or an e, it transitions to state 5
				else if (currentChar1 == 'E' || currentChar1 == 'e') {
					nextState1 = 5;
					break;
				}
				// If it is none of those characters, the FSM halts
				else
					running1 = false;

				// The execution of this state is finished
				break;

			case 2:
				// State 2 has two valid transitions. Each is addressed by an if statement.

				// If the current character is in the range from 1 to 9, it transitions to state
				// 1
				if (currentChar1 >= '0' && currentChar1 <= '9') {
					nextState1 = 2;
					break;
				}
				// If the current character is an 'E' or 'e", it transitions to state 5
				else if (currentChar1 == 'E' || currentChar1 == 'e') {
					nextState1 = 5;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					running1 = false;

				// The execution of this state is finished
				break;

			case 3:
				// State 3 has only one valid transition. It is addressed by an if statement.

				// If the current character is in the range from 1 to 9, it transitions to state
				// 1
				if (currentChar1 >= '0' && currentChar1 <= '9') {
					nextState1 = 4;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					running1 = false;

				// The execution of this state is finished
				break;

			case 4:
				// State 4 has two valid transitions. Each is addressed by an if statement.

				// If the current character is in the range from 1 to 9, it transitions to state
				// 4
				if (currentChar1 >= '0' && currentChar1 <= '9') {
					nextState1 = 4;
					break;
				}
				// If the current character is an 'E' or 'e", it transitions to state 5
				else if (currentChar1 == 'E' || currentChar1 == 'e') {
					nextState1 = 5;
					break;
				}

				// If it is none of those characters, the FSM halts
				else
					running1 = false;

				// The execution of this state is finished
				break;

			case 5:
				if (currentChar1 >= '0' && currentChar1 <= '9') {
					nextState1 = 7;
					break;
				} else if (currentChar1 == '+' || currentChar1 == '-') {
					nextState1 = 6;
					break;
				}
				break;

			case 6:
				if (currentChar1 >= '0' && currentChar1 <= '9') {
					nextState1 = 7;
					break;
				} else
					running1 = false;
				break;

			case 7:
				if (currentChar1 >= '0' && currentChar1 <= '9') {
					nextState1 = 7;
					break;
				} else
					running1 = false;
				break;

			case 8:
				if (currentChar1 == '.') {
					nextState1 = 9;
					break;
				} else
					running1 = false;
				break;

			case 9:
				if (currentChar1 == '0') {
					nextState1 = 9;
					break;
				} else if (currentChar1 >= '1' && currentChar1 <= '9') {
					nextState1 = 4;
					break;
				} else
					running1 = false;
				break;
			}

			if (running1) {
				// displayDebuggingInfo();
				// When the processing of a state has finished, the FSM proceeds to the next
				// character
				// in the input and if there is one, it fetches that character and updates the
				// currentChar. If there is no next character the currentChar is set to a blank.
				moveToNextCharacter1();

				// Move to the next state
				state1 = nextState1;

			}
			// Should the FSM get here, the loop starts again

		}

		// System.out.println("The loop has ended.");

		errorTermIndexofError = currentCharNdx1; // Copy the index of the current character;

		// When the FSM halts, we must determine if the situation is an error or not.
		// That depends
		// of the current state of the FSM and whether or not the whole string has been
		// consumed.
		// This switch directs the execution to separate code for each of the FSM
		// states.
		switch (state1) {
		case 0:
			// State 0 is not a final state, so we can return a very specific error message
			errorTermIndexofError = currentCharNdx1; // Copy the index of the current character;
			errorTermErrorMessage = "The first character must be a digit or a decimal point.";
			return "The first character must be a digit or a decimal point.";

		case 1:
			// State 1 is a final state, so we must see if the whole string has been
			// consumed.
			if (currentCharNdx1 < input.length()) {
				// If not all of the string has been consumed, we point to the current character
				// in the input line and specify what that character must be in order to move
				// forward.
				errorTermErrorMessage = "This character may only be an \"E\", an \"e\", a digit, "
						+ "a \".\", or it must be the end of the input.\n";
				return errorTermErrorMessage + displayInput1(input, currentCharNdx1);
			} else {
				errorTermIndexofError = -1;
				errorTermErrorMessage = "";
				return errorTermErrorMessage;
			}

		case 2:
		case 4:
			// States 2 and 4 are the same. They are both final states with only one
			// possible
			// transition forward, if the next character is an E or an e.
			if (currentCharNdx1 < input.length()) {
				errorTermErrorMessage = "This character may only be an \"E\", an \"e\", or it must"
						+ " be the end of the input.\n";
				return errorTermErrorMessage + displayInput1(input, currentCharNdx1);
			}
			// If there is no more input, the input was recognized.
			else {
				errorTermIndexofError = -1;
				errorTermErrorMessage = "";
				return errorTermErrorMessage;
			}
		case 3:
		case 6:
			// States 3, and 6 are the same. None of them are final states and in order to
			// move forward, the next character must be a digit.
			errorTermErrorMessage = "This character may only be a digit.\n";
			return errorTermErrorMessage + displayInput1(input, currentCharNdx1);

		case 7:
		case 8:
		case 9:
			// States 7,8 and 9 is similar to states 3 and 6, but it is a final state, so it
			// must be
			// processed differently. If the next character is not a digit, the FSM stops
			// with an
			// error. We must see here if there are no more characters. If there are no more
			// characters, we accept the input, otherwise we return an error
			if (currentCharNdx1 < input.length()) {
				errorTermErrorMessage = "This character may only be a digit.\n";
				return errorTermErrorMessage + displayInput1(input, currentCharNdx1);
			} else {
				errorTermIndexofError = -1;
				errorTermErrorMessage = "";
				return errorTermErrorMessage;
			}

		case 5:
			// State 5 is not a final state. In order to move forward, the next character
			// must be
			// a digit or a plus or a minus character.
			errorTermErrorMessage = "This character may only be a digit, a plus, or minus " + "character.\n";
			return errorTermErrorMessage + displayInput1(input, currentCharNdx1);
		default:
			return "";
		}
	}

	/**********************************************************************************************
	 * 
	 * The computation methods
	 * 
	 **********************************************************************************************/

	/*******************************************************************************************************
	 * The following methods implement computation on the calculator values with
	 * error term having values as UNumber. These routines assume that the caller
	 * has verified that things are okay for the operation to take place. These
	 * methods understand the technical details of the values and their reputations,
	 * hiding those details from the business logic and user interface modules.
	 * 
	 * Since this is addition and we do not yet support units, we don't recognize
	 * any errors.
	 */
	public void add(CalculatorValue v) {

		UNumber Linked_value = new UNumber();
		Linked_value = new UNumber(Linked_value, 25);

		UNumber two = new UNumber(2);
		UNumber measuredTerm1 = new UNumber(measuredValue); // Value of Operand 1 is saved into the variable
															// measuredTerm1
		measuredTerm1 = new UNumber(measuredTerm1, 30);

		UNumber measuredTerm2 = new UNumber(v.measuredValue); // Value of Operand 2 is saved into the variable
																// measuredTerm2
		measuredTerm2 = new UNumber(measuredTerm2, 30);

		UNumber errorTerm1 = new UNumber(errorValue); // Value of errorTerm 1 is saved into the variable errorTerm1
		errorTerm1 = new UNumber(errorTerm1, 30);

		UNumber errorTerm2 = new UNumber(v.errorValue); // Value of errorTerm 2 is saved into the variable errorTerm2
		errorTerm2 = new UNumber(errorTerm2, 30);

		if (IN_Operand1 > IN_Operand2) {
			Linked_value = new UNumber(conversionTable[IN_Operand1][IN_Operand2]);
			measuredTerm1.mpy(Linked_value);
			errorTerm1.mpy(Linked_value);

		}

		else {
			Linked_value = new UNumber(conversionTable[IN_Operand2][IN_Operand1]);
			measuredTerm2.mpy(Linked_value);
			errorTerm2.mpy(Linked_value);
		}

		// Logic used for the calculation of Addition with error term
		UNumber upperBound1 = new UNumber(measuredTerm1);
		upperBound1 = new UNumber(upperBound1, 30);
		UNumber upperBound2 = new UNumber(measuredTerm2);
		upperBound2 = new UNumber(upperBound2, 30);

		measuredTerm1.sub(errorTerm1);
		upperBound1.add(errorTerm1);

		measuredTerm2.sub(errorTerm2);
		upperBound2.add(errorTerm2);

		measuredTerm1.add(measuredTerm2);
		upperBound1.add(upperBound2);
		UNumber definiteTerm1 = new UNumber(measuredTerm1);
		definiteTerm1 = new UNumber(definiteTerm1, 30);

		UNumber definiteTerm2 = new UNumber(upperBound1);
		definiteTerm2 = new UNumber(definiteTerm2, 30);

		measuredTerm1.add(upperBound1);
		measuredTerm1.div(two);
		definiteTerm2.sub(definiteTerm1);
		definiteTerm2.div(two);

		measuredValue1 = new UNumber(measuredTerm1);

		errorValue1 = new UNumber(definiteTerm2);

		errorMessage = "";

		setUnitAddSub();

	}

	/*****
	 * The below is the subtraction which subtracts the value with error term having
	 * values as UNumber and round off the error term to one significant digit.
	 * 
	 * @param v
	 */
	public void sub(CalculatorValue v) {

		/*
		 * Logic for Double double lowerBound1, upperBound1, lowerBound2, upperBound2,
		 * definiteTerm1, definiteTerm2, errorValue1; lowerBound1 = measuredValue -
		 * errorValue; upperBound1 = measuredValue + errorValue;
		 * 
		 * lowerBound2 = v.measuredValue - v.errorValue; upperBound2 = v.measuredValue +
		 * v.errorValue;
		 * 
		 * definiteTerm1 = upperBound1 - upperBound2; definiteTerm2 = lowerBound1 -
		 * lowerBound2;
		 * 
		 * measuredValue =(definiteTerm1+definiteTerm2)/2 ; errorValue1 =
		 * (definiteTerm1-definiteTerm2)/2; errorValue =
		 * Math.round(errorValue1*100.0)/100.0; errorMessage = "";
		 * 
		 */

		UNumber Linked_value = new UNumber();
		Linked_value = new UNumber(Linked_value, 25);

		UNumber two = new UNumber(2);
		UNumber measuredTerm1 = new UNumber(measuredValue); // Value of Operand 1 is saved into the variable
															// measuredTerm1
		measuredTerm1 = new UNumber(measuredTerm1, 30);

		UNumber measuredTerm2 = new UNumber(v.measuredValue); // Value of Operand 2 is saved into the variable
																// measuredTerm2
		measuredTerm2 = new UNumber(measuredTerm2, 30);

		UNumber errorTerm1 = new UNumber(errorValue); // Value of errorTerm 1 is saved into the variable errorTerm1
		errorTerm1 = new UNumber(errorTerm1, 30);

		UNumber errorTerm2 = new UNumber(v.errorValue); // Value of errorTerm 2 is saved into the variable errorTerm2
		errorTerm2 = new UNumber(errorTerm2, 30);

		if (IN_Operand1 > IN_Operand2) {
			Linked_value = new UNumber(conversionTable[IN_Operand1][IN_Operand2]);
			measuredTerm1.mpy(Linked_value);
			errorTerm1.mpy(Linked_value);

		}

		else {
			Linked_value = new UNumber(conversionTable[IN_Operand2][IN_Operand1]);
			measuredTerm2.mpy(Linked_value);
			errorTerm2.mpy(Linked_value);
		}

		// Logic used for the calculation of subtraction with error term
		UNumber upperBound1 = new UNumber(measuredTerm1);
		upperBound1 = new UNumber(upperBound1, 30);

		UNumber upperBound2 = new UNumber(measuredTerm2);
		upperBound2 = new UNumber(upperBound2, 30);

		measuredTerm1.sub(errorTerm1);
		upperBound1.add(errorTerm1);

		measuredTerm2.sub(errorTerm2);
		upperBound2.add(errorTerm2);

		measuredTerm1.sub(measuredTerm2);
		upperBound1.sub(upperBound2);

		UNumber definiteTerm1 = new UNumber(measuredTerm1);
		definiteTerm1 = new UNumber(definiteTerm1, 30);

		UNumber definiteTerm2 = new UNumber(upperBound1);
		definiteTerm2 = new UNumber(definiteTerm2, 30);

		measuredTerm1.add(upperBound1);
		measuredTerm1.div(two);

		definiteTerm2.sub(definiteTerm1);
		definiteTerm2.div(two);

		measuredValue1 = new UNumber(measuredTerm1);

		errorValue1 = new UNumber(definiteTerm2);

		errorMessage = "";

		setUnitAddSub();
	}

	/*****
	 * The below is the multiplication which multiplies the value with error term
	 * having values as UNumber and round off the error term to one significant
	 * digit.
	 * 
	 * @param v
	 */

	public void mpy(CalculatorValue v) {

		/*
		 * Logic for Double double value1ErrorFraction, value2ErrorFraction,
		 * errorValue1;
		 * 
		 * value1ErrorFraction = errorValue / measuredValue; value2ErrorFraction =
		 * v.errorValue / v.measuredValue;
		 * 
		 * measuredValue *= v.measuredValue; errorValue1 =
		 * (value1ErrorFraction+value2ErrorFraction) * measuredValue; errorValue =
		 * Math.round(errorValue1*100.0)/100.0; errorMessage = "";
		 */

		UNumber Linked_value = new UNumber();
		Linked_value = new UNumber(Linked_value, 25);

		UNumber measuredTerm1 = new UNumber(measuredValue); // Value of Operand 1 is saved into the variable
															// measuredTerm1
		measuredTerm1 = new UNumber(measuredTerm1, 30);

		UNumber measuredTerm2 = new UNumber(v.measuredValue); // Value of Operand 2 is saved into the variable
																// measuredTerm2
		measuredTerm2 = new UNumber(measuredTerm2, 30);

		UNumber errorTerm1 = new UNumber(errorValue); // Value of errorTerm 1 is saved into the variable errorTerm1
		errorTerm1 = new UNumber(errorTerm1, 30);

		UNumber errorTerm2 = new UNumber(v.errorValue); // Value of errorTerm 2 is saved into the variable errorTerm2
		errorTerm2 = new UNumber(errorTerm2, 30);

		if (IN_Operand1 > IN_Operand2) {
			Linked_value = new UNumber(conversionTable[IN_Operand1][IN_Operand2]);
			measuredTerm1.mpy(Linked_value);
			errorTerm1.mpy(Linked_value);

		}

		else {
			Linked_value = new UNumber(conversionTable[IN_Operand2][IN_Operand1]);
			measuredTerm2.mpy(Linked_value);
			errorTerm2.mpy(Linked_value);
		}

		// Logic used for the calculation of multiplication with error term
		UNumber measuredTerm3 = new UNumber(measuredTerm1);
		measuredTerm3 = new UNumber(measuredTerm3, 30);

		UNumber measuredTerm4 = new UNumber(measuredTerm2);
		measuredTerm4 = new UNumber(measuredTerm4, 30);

		measuredTerm3.mpy(measuredTerm4);

		errorTerm1.div(measuredTerm1);

		errorTerm2.div(measuredTerm2);

		errorTerm1.add(errorTerm2);

		errorTerm1.mpy(measuredTerm3);

		measuredValue1 = new UNumber(measuredTerm3);

		errorValue1 = new UNumber(errorTerm1);

		errorMessage = "";

		if (IN_Operand1 != IN_Operand2)
			if (IN_Operand1 > 1 || IN_Operand2 > 1)
				unit = operand_units[IN_Operand1] + "." + operand_units[IN_Operand2];
			else if (IN_Operand1 > IN_Operand2)
				unit = operand_units[IN_Operand2] + "\u00B2";
			else

				unit = operand_units[IN_Operand1] + "\u00B2";

		else
			unit = operand_units[IN_Operand1] + "\u00B2";
	}

	/*****
	 * The below is the division which divides the value with error term having
	 * values as UNumber and round off the error term to one significant digit.
	 * 
	 * @param v
	 */
	public void div(CalculatorValue v) {

		/*
		 * Logic for Double double value1ErrorFraction, value2ErrorFraction,errorValue1;
		 * 
		 * value1ErrorFraction = errorValue / measuredValue; value2ErrorFraction =
		 * v.errorValue / v.measuredValue;
		 * 
		 * measuredValue /= v.measuredValue; errorValue1 =
		 * (value1ErrorFraction+value2ErrorFraction) * measuredValue; errorValue =
		 * Math.round(errorValue1*100.0)/100.0; errorMessage = "";
		 */

		UNumber Linked_value = new UNumber();
		Linked_value = new UNumber(Linked_value, 30);

		UNumber measuredTerm1 = new UNumber(measuredValue); // Value of Operand 1 is saved into the variable
															// measuredTerm1
		measuredTerm1 = new UNumber(measuredTerm1, 30);

		UNumber measuredTerm2 = new UNumber(v.measuredValue); // Value of Operand 2 is saved into the variable
																// measuredTerm2
		measuredTerm2 = new UNumber(measuredTerm2, 30);

		UNumber errorTerm1 = new UNumber(errorValue); // Value of errorTerm 1 is saved into the variable errorTerm1
		errorTerm1 = new UNumber(errorTerm1, 30);

		UNumber errorTerm2 = new UNumber(v.errorValue); // Value of errorTerm 2 is saved into the variable errorTerm2
		errorTerm2 = new UNumber(errorTerm2, 30);

		if (IN_Operand1 > IN_Operand2) {
			Linked_value = new UNumber(conversionTable[IN_Operand1][IN_Operand2]);
			measuredTerm1.mpy(Linked_value);
			errorTerm1.mpy(Linked_value);

		}

		else {
			Linked_value = new UNumber(conversionTable[IN_Operand2][IN_Operand1]);
			measuredTerm2.mpy(Linked_value);
			errorTerm2.mpy(Linked_value);
		}

		// Logic used for the calculation of division with error term
		UNumber measuredTerm3 = new UNumber(measuredTerm1);
		measuredTerm3 = new UNumber(measuredTerm3, 30);

		UNumber measuredTerm4 = new UNumber(measuredTerm2);
		measuredTerm4 = new UNumber(measuredTerm4, 30);

		measuredTerm3.div(measuredTerm4);

		errorTerm1.div(measuredTerm1);

		errorTerm2.div(measuredTerm2);

		errorTerm1.add(errorTerm2);

		errorTerm1.mpy(measuredTerm3);

		measuredValue1 = new UNumber(measuredTerm3);

		errorValue1 = new UNumber(errorTerm1);

		errorMessage = "";

		if (IN_Operand1 != IN_Operand2)
			if (IN_Operand1 > 1 || IN_Operand2 > 1)
				if (IN_Operand1 == 6)
					unit = operand_units[IN_Operand2] + "\u207B" + "\u00B9";
				else if (IN_Operand2 == 6)
					unit = operand_units[IN_Operand1];
				else
					unit = operand_units[IN_Operand1] + "/" + operand_units[IN_Operand2];

			else if (IN_Operand1 > IN_Operand2)
				unit = "No unit";
			else
				unit = "No unit";

		else
			unit = "No unit";
	}

	/*****
	 * The below is the sqrt which calculates Square Root having values as UNumber
	 * 
	 * @param v
	 */

	public void sqrt(CalculatorValue v) {

		/*
		 * Logic used for Double double valueErrorFraction, errorValue2;
		 * 
		 * valueErrorFraction = errorValue / measuredValue; measuredValue =
		 * Math.pow(measuredValue, 0.5); errorValue2 = 0.5 * valueErrorFraction *
		 * measuredValue;
		 * 
		 * errorValue = Math.round(errorValue2*100.0)/100.0; errorMessage = "";
		 * 
		 */

		UNumber one = new UNumber(1);
		UNumber two = new UNumber(2);
		UNumber half = new UNumber(0);

		one.div(two);

		half = new UNumber(one);

		UNumber errorValue2 = new UNumber(measuredValue);
		errorValue2 = new UNumber(errorValue2);

		UNumber measuredTerm1 = new UNumber(measuredValue); // Value of Operand 1 is saved into the variable
															// measuredTerm1
		measuredTerm1 = new UNumber(measuredTerm1, 30);

		UNumber errorTerm1 = new UNumber(errorValue); // Value of errorTerm 1 is saved into the variable errorTerm1
		errorTerm1 = new UNumber(errorTerm1, 15);

		errorTerm1.div(measuredTerm1);
		UNumber valueErrorFraction = new UNumber(errorTerm1);
		valueErrorFraction = new UNumber(valueErrorFraction, 30);
		errorValue2.mpy(half);
		errorValue2.mpy(valueErrorFraction);

		measuredTerm1.sqrt();

		measuredValue1 = new UNumber(measuredTerm1);

		errorValue1 = new UNumber(errorValue2);

		errorMessage = "";

		unit = operand_units[IN_Operand1];
	}

}
