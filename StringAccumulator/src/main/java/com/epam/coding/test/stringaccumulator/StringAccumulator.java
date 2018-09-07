package com.epam.coding.test.stringaccumulator;

import java.util.ArrayList;
import java.util.List;

public class StringAccumulator {
	private static final String DEFAULT_DELIMITERS = ",|\n";
	private static final String DELIMITER_LINE_PREFIX = "//";
	private static final String DELIMITER_OF_DELIMITERS = "|";
	private static final String INTERNAL_DELIMITER = ",";
	
	public static int add(String numbers) throws NegativeNumberException {
		String delimiters = DEFAULT_DELIMITERS;
		if (numbers.startsWith(DELIMITER_LINE_PREFIX)) {
			int indexOfEndOfFirstLine = numbers.indexOf("\n");
			delimiters = numbers.substring(DELIMITER_LINE_PREFIX.length(), indexOfEndOfFirstLine);
			numbers = numbers.substring(indexOfEndOfFirstLine + 1);
		}

		/* Note: the reason why I do not use split() method directly is that 
		 * split() takes a regular expression, and it will be hard to handle 
		 * all special characters for regular expressions. But replace() method takes 
		 * normal strings. By replacing user defined delimiters with a internal 
		 * delimiter which does not containing any special characters of regular 
		 * expressions, we can easily use split() to delimit the string afterwards.
		 */
		// replace all user defined delimiters to internal delimiter
		for (String delimiter : delimiters.split("\\" + DELIMITER_OF_DELIMITERS)) {
			numbers = numbers.replace(delimiter, INTERNAL_DELIMITER);
		}

		int sum = 0;
		List<Integer> negativeNumberList = new ArrayList<Integer>();
		for (String numberStr : numbers.split(INTERNAL_DELIMITER)) {
			if (numberStr.isEmpty()) {
				continue;
			}
			int number = Integer.valueOf(numberStr);
			if (number < 0) {
				/* Note: the reason why I do not throw exception directly is that 
				 * the requirements asks to print all negative numbers in the 
				 * exception thrown
				 */
				negativeNumberList.add(number);
				continue;
			} else if (number > 1000) {
				continue;
			}

			/* Note: do addition only if negativeNumberList is not empty 
			 * otherwise the addition is not necessary (summation will not 
			 * be returned anyway)
			 */
			if (negativeNumberList.isEmpty()) {
				sum += number;
			}
		}
		if (!negativeNumberList.isEmpty()) {
			StringBuffer errorMessage = new StringBuffer("negatives not allowed : ");
			for (int negativeNumber : negativeNumberList) {
				errorMessage.append(negativeNumber + ", ");
			}
			errorMessage.delete(errorMessage.length()-2, errorMessage.length());
			throw new NegativeNumberException(errorMessage.toString());
		}
		return sum;
	}
}

class NegativeNumberException extends Exception {
	private static final long serialVersionUID = 1L;

	public NegativeNumberException(String errorMessage) {
		super (errorMessage);
	}
}
