
package calculator;

/**
 * <p>
 * Title: BusinessLogic Class.
 * </p>
 * 
 * <p>
 * @author Neaz Morshed
 * This Calculator can store data from the user in a particular path. The user can edit the table and update data as well as search
 * data. The user can also remove data according to his own will.
 * @version 4.00.
 * </p>
 * 
 * <p>
 * Copyright: Lynn Robert Carter � 2017
 * </p>
 
 */

public class BusinessLogic {

	/**********************************************************************************************
	 * 
	 * Attributes
	 * 
	 **********************************************************************************************/

	// These are the major calculator values
	private CalculatorValue operand1 = new CalculatorValue(0, 0);
	private CalculatorValue operand2 = new CalculatorValue(0, 0);
	private CalculatorValue result = new CalculatorValue(0, 0);
	private String operand1ErrorMessage = "";
	private boolean operand1Defined = false;
	private String operand2ErrorMessage = "";
	private boolean operand2Defined = false;
	private String resultErrorMessage = "";

	/**********************************************************************************************
	 * 
	 * Constructors
	 * 
	 **********************************************************************************************/

	/**********
	 * This method initializes all of the elements of the business logic aspect of
	 * the calculator. There is no special computational initialization required, so
	 * the initialization of the attributes above are all that are needed.
	 */
	public BusinessLogic() {
	}

	/**********************************************************************************************
	 * 
	 * Getters and Setters
	 * 
	 **********************************************************************************************/

	/**********
	 * This public method takes an input String , checks to see if there is a
	 * non-empty input string. If so, it places the converted CalculatorValue into
	 * operand1 and error term, any associated error message into
	 * operand1ErrorMessage, and sets flags accordingly.
	 * 
	 * @param value
	 * @return True if the set did not generate an error; False if there was invalid
	 *         input
	 */
	public boolean setOperand1(String value, String value1) {
		operand1Defined = false; // Assume the operand will not be defined
		if (value.length() <= 0) { // See if the input is empty. If so no error
			operand1ErrorMessage = ""; // message, but the operand is not defined.
			return true; // Return saying there was no error.
		}
		operand1 = new CalculatorValue(value, value1); // If there was input text, try to convert it
		operand1ErrorMessage = operand1.getErrorMessage(); // into a CalculatorValue and see if it
		if (operand1ErrorMessage.length() > 0) // worked. If there is a non-empty error
			return false; // message, signal there was a problem.
		operand1Defined = true; // Otherwise, set the defined flag and
		return true; // signal that the set worked
	}

	/**********
	 * This public method takes an input String, checks to see if there is a
	 * non-empty input string. If so, it places the converted CalculatorValue into
	 * operand2 and error term, any associated error message into
	 * operand1ErrorMessage, and sets flags accordingly.
	 * 
	 * The logic of this method is the same as that for operand1 above.
	 * 
	 * @param value
	 * @return True if the set did not generate an error; False if there was invalid
	 *         input
	 */
	public boolean setOperand2(String value, String value1) { // The logic of this method is exactly the
		operand2Defined = false; // same as that for operand1, above.
		if (value.length() <= 0) {
			operand2ErrorMessage = "";
			return true;
		}
		operand2 = new CalculatorValue(value, value1);
		operand2ErrorMessage = operand2.getErrorMessage();
		if (operand2ErrorMessage.length() > 0)
			return false;
		operand2Defined = true;
		return true;
	}

	/**********
	 * This public method takes an input String, checks to see if there is a
	 * non-empty input string. If so, it places the converted CalculatorValue into
	 * result and error term, any associated error message into resuyltErrorMessage,
	 * and sets flags accordingly.
	 * 
	 * The logic of this method is similar to that for operand1 above. (There is no
	 * defined flag.)
	 * 
	 * @param value
	 * @return True if the set did not generate an error; False if there was invalid
	 *         input
	 */
	public boolean setResult(String value, String value1) { // The logic of this method is similar to
		if (value.length() <= 0) { // that for operand1, above.
			operand2ErrorMessage = "";
			return true;
		}
		result = new CalculatorValue(value, value1);
		resultErrorMessage = operand2.getErrorMessage();
		if (operand2ErrorMessage.length() > 0)
			return false;
		return true;
	}

	/**********
	 * This public setter sets the String explaining the current error in operand1.
	 * 
	 * @return
	 */
	public void setOperand1ErrorMessage(String m) {
		operand1ErrorMessage = m;
		return;
	}

	/**********
	 * This public getter fetches the String explaining the current error in
	 * operand1, it there is one, otherwise, the method returns an empty String.
	 * 
	 * @return and error message or an empty String
	 */
	public String getOperand1ErrorMessage() {
		return operand1ErrorMessage;
	}

	/**********
	 * This public setter sets the String explaining the current error into
	 * operand1.
	 * 
	 * @return
	 */
	public void setOperand2ErrorMessage(String m) {
		operand2ErrorMessage = m;
		return;
	}

	/**********
	 * This public getter fetches the String explaining the current error in
	 * operand2, it there is one, otherwise, the method returns an empty String.
	 * 
	 * @return and error message or an empty String
	 */
	public String getOperand2ErrorMessage() {
		return operand2ErrorMessage;
	}

	/**********
	 * This public setter sets the String explaining the current error in the
	 * result.
	 * 
	 * @return
	 */
	public void setResultErrorMessage(String m) {
		resultErrorMessage = m;
		return;
	}

	/**********
	 * This public getter fetches the String explaining the current error in the
	 * result, it there is one, otherwise, the method returns an empty String.
	 * 
	 * @return and error message or an empty String
	 */
	public String getResultErrorMessage() {
		return resultErrorMessage;
	}

	/**********
	 * This public getter fetches the defined attribute for operand1. You can't use
	 * the lack of an error message to know that the operand is ready to be used. An
	 * empty operand has no error associated with it, so the class checks to see if
	 * it is defined and has no error before setting this flag true.
	 * 
	 * @return true if the operand is defined and has no error, else false
	 */
	public boolean getOperand1Defined() {
		return operand1Defined;
	}

	/**********
	 * This public getter fetches the defined attribute for operand2. You can't use
	 * the lack of an error message to know that the operand is ready to be used. An
	 * empty operand has no error associated with it, so the class checks to see if
	 * it is defined and has no error before setting this flag true.
	 * 
	 * @return true if the operand is defined and has no error, else false
	 */
	public boolean getOperand2Defined() {
		return operand2Defined;
	}

	/**********************************************************************************************
	 * 
	 * The toString() Method
	 * 
	 **********************************************************************************************/

	/**********
	 * This toString method invokes the toString method of the result type
	 * (CalculatorValue is this case) to convert the value from its hidden internal
	 * representation into a String, which can be manipulated directly by the
	 * BusinessLogic and the UserInterface classes.
	 */
	public String toString() {
		return result.toString();
	}

	/**********
	 * This public toString method is used to display all the values of the
	 * BusinessLogic class in a textual representation for debugging purposes.
	 * 
	 * @return a String representation of the class
	 */
	public String debugToString() {
		String r = "\n******************\n*\n* Business Logic\n*\n******************\n";
		r += "operand1 = " + operand1.toString() + "\n";
		r += "     operand1ErrorMessage = " + operand1ErrorMessage + "\n";
		r += "     operand1Defined = " + operand1Defined + "\n";
		r += "operand2 = " + operand2.toString() + "\n";
		r += "     operand2ErrorMessage = " + operand2ErrorMessage + "\n";
		r += "     operand2Defined = " + operand2Defined + "\n";
		r += "result = " + result.toString() + "\n";
		r += "     resultErrorMessage = " + resultErrorMessage + "\n";
		r += "*******************\n\n";
		return r;
	}

	// This method pass the UNumber value to error term.
	public String errorTerm() {
		String str = CalculatorValue.toStringErrorTerm();
		return str;
	}

	/**********************************************************************************************
	 * 
	 * Business Logic Operations (e.g. addition)
	 * 
	 **********************************************************************************************/

	/**********
	 * This public method computes the sum of the two operands using the
	 * CalculatorValue class method for addition. The goal of this class is to
	 * support a wide array of different data representations without requiring a
	 * change to this class, user interface class, or the Calculator class.
	 * 
	 * This method assumes the operands are defined and valid. It replaces the left
	 * operand with the result of the computation and it leaves an error message, if
	 * there is one, in a String variable set aside for that purpose.
	 * 
	 * This method does not take advantage or know any detail of the representation!
	 * All of that is hidden from this class by the CalculatorValue class and any
	 * other classes that it may use.
	 * 
	 * @return a String representation of the result
	 */
	public String addition() {
		result = new CalculatorValue(operand1);
		result.add(operand2);
		resultErrorMessage = result.getErrorMessage();
		return result.toString();
	}

	// Subtraction Method
	/**********
	 * This public method computes the difference of the two operands using the
	 * CalculatorValue class method for subtraction. The goal of this class is to
	 * support a wide array of different data representations without requiring a
	 * change to this class, user interface class, or the Calculator class.
	 * 
	 * This method assumes the operands are defined and valid. It replaces the left
	 * operand with the result of the computation and it leaves an error message, if
	 * there is one, in a String variable set aside for that purpose.
	 * 
	 * This method does not take advantage or know any detail of the representation!
	 * All of that is hidden from this class by the CalculatorValue class and any
	 * other classes that it may use.
	 * 
	 * @return a String representation of the result
	 */
	public String subtraction() {
		result = new CalculatorValue(operand1);
		result.sub(operand2);
		resultErrorMessage = result.getErrorMessage();
		return result.toString();
	}

	// Multiplication Method
	/**********
	 * This public method computes the Product of the two operands using the
	 * CalculatorValue class method for multiplication. The goal of this class is to
	 * support a wide array of different data representations without requiring a
	 * change to this class, user interface class, or the Calculator class.
	 * 
	 * This method assumes the operands are defined and valid. It replaces the left
	 * operand with the result of the computation and it leaves an error message, if
	 * there is one, in a String variable set aside for that purpose.
	 * 
	 * This method does not take advantage or know any detail of the representation!
	 * All of that is hidden from this class by the CalculatorValue class and any
	 * other classes that it may use.
	 * 
	 * @return a String representation of the result
	 */

	public String multiplication() {
		result = new CalculatorValue(operand1);
		result.mpy(operand2);
		resultErrorMessage = result.getErrorMessage();
		return result.toString();
	}

	// Division Method
	/**********
	 * This public method computes the quotient of the two operands using the
	 * CalculatorValue class method for division. The goal of this class is to
	 * support a wide array of different data representations without requiring a
	 * change to this class, user interface class, or the Calculator class.
	 * 
	 * This method assumes the operands are defined and valid. It replaces the left
	 * operand with the result of the computation and it leaves an error message, if
	 * there is one, in a String variable set aside for that purpose.
	 * 
	 * This method does not take advantage or know any detail of the representation!
	 * All of that is hidden from this class by the CalculatorValue class and any
	 * other classes that it may use.
	 * 
	 * @return a String representation of the result
	 */
	public static boolean one = false;
	public static boolean zero = false;

	public String division() {
		result = new CalculatorValue(operand1);
		if (operand2.measuredValue == 0) {
			one = true;
		} else {
			one = false;
			result.div(operand2);
		}
		resultErrorMessage = result.getErrorMessage();
		return result.toString();
	}

	// Sqaure Root Method
	/**********
	 * This public method computes the log of the one operand using the
	 * CalculatorValue class method for logarithmic. The goal of this class is to
	 * support a wide array of different data representations without requiring a
	 * change to this class, user interface class, or the Calculator class.
	 * 
	 * This method assumes the operands are defined and valid. It replaces the left
	 * operand with the result of the computation and it leaves an error message, if
	 * there is one, in a String variable set aside for that purpose.
	 * 
	 * This method does not take advantage or know any detail of the representation!
	 * All of that is hidden from this class by the CalculatorValue class and any
	 * other classes that it may use.
	 * 
	 * @return a String representation of the result
	 */

	public String squareRoot() {
		result = new CalculatorValue(operand1);
		if (operand1.measuredValue < 0) {
			zero = true;
		}

		else {
			zero = false;
			result.sqrt(operand2);
		}
		resultErrorMessage = result.getErrorMessage();
		return result.toString();
	}

	/********
	 * Following method is for getting the result error term
	 * 
	 * @return str formated output
	 */
	public String unitvalue() {
		String ut = CalculatorValue.unitOfResult();
		return ut;
	}

	/*************
	 * This methods checks the compatiblity of the units of addition and subtraction
	 * 
	 */

	public boolean unitsCheck() {
		boolean checkvalue = result.setUnitcheck();
		if (!checkvalue) {
			System.out.println("Units are not accordant");
		}

		return result.setUnitcheck();
	}
}
