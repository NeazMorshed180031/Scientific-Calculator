package uNumberLibrary;

/**
 * <p>
 * Title: UNumberWithSqrt Class, a component of the Unlimited Precision Math
 * Package.
 * </p>
 * 
 * <p>
 * Description: A demonstration package to show the notion of a package and to
 * exercise array usage
 * </p>
 * 
 * <p>
 * Copyright: Lynn Robert Carter © 2014
 * </p>
 * 
 * 

 * 
 * 
 * @version 1.03 Addition of code to support the Square root function for the
 *          UNumber.
 * 
 * @version 1.02a Addition of code to support quality improvements, and the
 *          removal of the div2 method
 * 
 * @version 1.01 The initial version plus an improved add/sub, normalize, div,
 *          div2, lessThan, greaterThan, abs, and compareTo
 *
.
 */
public class UNumberWithSqrt extends UNumber {

	public UNumberWithSqrt() {
		// TODO Auto-generated constructor stub
	}

	public UNumberWithSqrt(int v) {
		super(v);
		// TODO Auto-generated constructor stub
	}

	public UNumberWithSqrt(long v) {
		super(v);
		// TODO Auto-generated constructor stub
	}

	public UNumberWithSqrt(String str, int dec, boolean sign) {
		super(str, dec, sign);
		// TODO Auto-generated constructor stub
	}

	public UNumberWithSqrt(String str, int dec, boolean sign, int size) {
		super(str, dec, sign, size);
		// TODO Auto-generated constructor stub
	}

	public UNumberWithSqrt(UNumber that) {
		super(that);
		// TODO Auto-generated constructor stub
	}

	public UNumberWithSqrt(UNumber that, int size) {
		super(that, size);
		// TODO Auto-generated constructor stub
	}

	public UNumberWithSqrt(UNumber that, UNumber another) {
		super(that, another);
		// TODO Auto-generated constructor stub
	}

	public UNumberWithSqrt(double v) {
		super(v);
		// TODO Auto-generated constructor stub
	}

}